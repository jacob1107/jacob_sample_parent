package com.jacob.hippo4j;

import cn.hippo4j.starter.enable.EnableDynamicThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicThreadPool
@SpringBootApplication
public class Hippo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hippo4jApplication.class, args);
    }

}
