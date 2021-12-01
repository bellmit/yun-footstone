/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.web.config;

import com.google.common.collect.ImmutableList;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>
 * CORS 跨域
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/29 2:26 PM
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.setAllowCredentials(true);
        config.setAllowedMethods(ImmutableList.of("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "JSONP"));
        config.setExposedHeaders(ImmutableList.of("Link", "X-Total-Pages", "X-Total-Count", "ETag"));
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.setMaxAge(Duration.ofSeconds(1800L));
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(configSource);
    }
}
