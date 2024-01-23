package com.jacob.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author jacob
 * @Date 2024/1/11 11:08
 * @Version 1.0
 */
@SpringBootApplication(proxyBeanMethods = false)
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }


}
