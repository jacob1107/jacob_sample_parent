package org.jacob.spring.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowableAppMain {
    public static void main(String[] args) {
        System.out.println("hello");
        SpringApplication.run(FlowableAppMain.class, args);
    }
}
