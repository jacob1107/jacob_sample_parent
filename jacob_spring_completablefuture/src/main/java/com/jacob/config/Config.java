package com.jacob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author jacob
 * @Date 2022/11/12 12:28
 * @Version 1.0
 */
@Configuration
public class Config {
    @Bean("threadPoolExecutor")
    public ThreadPoolExecutor getThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                13,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(600));
        return threadPoolExecutor;
    }
    @Bean("threadPoolExecutor2")
    public ThreadPoolExecutor getThreadPoolExecutor2() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                13,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(600));
        return threadPoolExecutor;
    }
}
