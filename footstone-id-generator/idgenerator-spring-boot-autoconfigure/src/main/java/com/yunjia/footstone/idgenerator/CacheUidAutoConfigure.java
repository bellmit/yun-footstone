/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.idgenerator;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.yunjia.footstone.idgenerator.core.UidGenerator;
import com.yunjia.footstone.idgenerator.core.impl.CachedUidGenerator;
import com.yunjia.footstone.idgenerator.properties.BaseUidProperties;
import com.yunjia.footstone.idgenerator.properties.CachedUidProperties;
import com.yunjia.footstone.idgenerator.worker.database.DisposableWorkerIdAssigner;
import com.yunjia.footstone.idgenerator.worker.WorkerIdAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * CacheUid自动配置类
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/26 5:27 PM
 */
@Configuration
@AutoConfigureAfter(value = MybatisPlusAutoConfiguration.class)
@EnableConfigurationProperties(value = {BaseUidProperties.class, CachedUidProperties.class})
public class CacheUidAutoConfigure {

    @Autowired
    private BaseUidProperties baseUidProperties;

    @Autowired
    private CachedUidProperties cachedUidProperties;

    @Bean
    @ConditionalOnMissingBean(WorkerIdAssigner.class)
    public WorkerIdAssigner getWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Bean
    @ConditionalOnMissingBean(UidGenerator.class)
    public UidGenerator getUidGenerator() {
        return new CachedUidGenerator(baseUidProperties, cachedUidProperties, getWorkerIdAssigner());
    }
}
