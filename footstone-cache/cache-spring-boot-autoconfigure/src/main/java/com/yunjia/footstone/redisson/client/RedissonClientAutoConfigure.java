/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.redisson.client;

import cn.hutool.core.util.StrUtil;
import com.yunjia.footstone.redisson.common.CommonConstants;
import com.yunjia.footstone.redisson.common.RedissonNameMapper;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * Redisson Cache 自动配置
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 5:58 PM
 */
@Configuration
@ConditionalOnProperty(name = CommonConstants.REDISSON_CACHE_ENABLE, havingValue = CommonConstants.ENABLE)
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties({RedissonProperties.class, RedisProperties.class})
public class RedissonClientAutoConfigure {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Autowired(required = false)
    private List<RedissonAutoConfigureCustomizer> redissonAutoConfigureCustomizers;

    @Autowired
    private RedissonProperties redissonProperties;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private ApplicationContext ctx;

    @Value("${spring.application.name}")
    private String appName;

    @PostConstruct
    public void setRedissonNameMapper() {
        // 设置cache key的前缀
        RedissonNameMapper.setKeyPrefix(appName);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean
    @Lazy
    @ConditionalOnMissingBean(RedissonReactiveClient.class)
    public RedissonReactiveClient redissonReactive(RedissonClient redisson) {
        return redisson.reactive();
    }

    @Bean
    @Lazy
    @ConditionalOnMissingBean(RedissonRxClient.class)
    public RedissonRxClient redissonRxJava(RedissonClient redisson) {
        return redisson.rxJava();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() throws IOException {
        Config config = null;
        Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, CommonConstants.REDIS_METHOD_GET_CLUSTER);
        Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, CommonConstants.REDIS_METHOD_GET_TIMEOUT);
        Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
        int timeout;
        if(null == timeoutValue) {
            timeout = 10000;
        } else if(!(timeoutValue instanceof Integer)) {
            Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), CommonConstants.REDIS_METHOD_TO_MILLIS);
            timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
        } else {
            timeout = (Integer) timeoutValue;
        }

        if(redissonProperties.getConfig() != null) {
            try {
                config = Config.fromYAML(redissonProperties.getConfig());
            } catch(IOException e) {
                try {
                    config = Config.fromJSON(redissonProperties.getConfig());
                } catch(IOException e1) {
                    throw new IllegalArgumentException("Can't parse config", e1);
                }
            }
        } else if(redissonProperties.getFile() != null) {
            try {
                InputStream is = getConfigStream();
                config = Config.fromYAML(is);
            } catch(IOException e) {
                // trying next format
                try {
                    InputStream is = getConfigStream();
                    config = Config.fromJSON(is);
                } catch(IOException e1) {
                    throw new IllegalArgumentException("Can't parse config", e1);
                }
            }
        } else if(redisProperties.getSentinel() != null) {
            Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, CommonConstants.REDIS_METHOD_GET_NODES);
            Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

            String[] nodes;
            if(nodesValue instanceof String) {
                nodes = convert(Arrays.asList(((String) nodesValue).split(StrUtil.COMMA)));
            } else {
                nodes = convert((List<String>) nodesValue);
            }

            config = new Config();
            config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(nodes).setDatabase(redisProperties.getDatabase()).setConnectTimeout(timeout)
                    .setPassword(redisProperties.getPassword());
        } else if(clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
            Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
            Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), CommonConstants.REDIS_METHOD_GET_NODES);
            List<String> nodesObject = (List) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

            String[] nodes = convert(nodesObject);

            config = new Config();
            config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout)
                    .setPassword(redisProperties.getPassword());
        } else {
            config = new Config();
            String prefix = REDIS_PROTOCOL_PREFIX;
            Method method = ReflectionUtils.findMethod(RedisProperties.class, CommonConstants.REDIS_METHOD_IS_SSL);
            if(method != null && (Boolean) ReflectionUtils.invokeMethod(method, redisProperties)) {
                prefix = REDISS_PROTOCOL_PREFIX;
            }

            config.useSingleServer().setAddress(prefix + redisProperties.getHost() + StrUtil.COLON + redisProperties.getPort())
                    .setConnectTimeout(timeout).setDatabase(redisProperties.getDatabase())
                    .setPassword(redisProperties.getPassword());
        }
        if(redissonAutoConfigureCustomizers != null) {
            for(RedissonAutoConfigureCustomizer customizer : redissonAutoConfigureCustomizers) {
                customizer.customize(config);
            }
        }
        return Redisson.create(config);
    }

    private String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList<String>(nodesObject.size());
        for(String node : nodesObject) {
            if(!node.startsWith(REDIS_PROTOCOL_PREFIX) && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
                nodes.add(REDIS_PROTOCOL_PREFIX + node);
            } else {
                nodes.add(node);
            }
        }
        return nodes.toArray(new String[nodes.size()]);
    }

    private InputStream getConfigStream() throws IOException {
        Resource resource = ctx.getResource(redissonProperties.getFile());
        InputStream is = resource.getInputStream();
        return is;
    }
}
