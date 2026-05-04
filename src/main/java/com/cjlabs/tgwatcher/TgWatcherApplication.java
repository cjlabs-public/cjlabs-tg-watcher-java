package com.cjlabs.tgwatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.cjlabs.tgwatcher.business")
public class TgWatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgWatcherApplication.class, args);
    }
}
