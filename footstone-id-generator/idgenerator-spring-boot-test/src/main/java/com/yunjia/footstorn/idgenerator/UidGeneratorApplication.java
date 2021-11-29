/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstorn.idgenerator;

import com.yunjia.footstone.idgenerator.core.UidGenerator;
import javax.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * TODO 类注释
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/11/28 8:37 PM
 */
@SpringBootApplication(scanBasePackages = {"com.yunjia"})
@PropertySource({"classpath:database.properties"})
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.yunjia.**.database.mapper"})
public class UidGeneratorApplication implements CommandLineRunner {

    @Resource
    private UidGenerator uidGenerator;

    public static void main(String[] args) {
        SpringApplication.run(UidGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(uidGenerator.getUID());
    }
}
