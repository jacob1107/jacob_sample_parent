package com.jacob.hippo4j;

import cn.hippo4j.starter.core.DynamicThreadPool;
import cn.hippo4j.starter.toolkit.thread.ResizableCapacityLinkedBlockIngQueue;
import cn.hippo4j.starter.toolkit.thread.ThreadPoolBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean
    @DynamicThreadPool
    public ThreadPoolExecutor dynamicThreadPoolExecutor() {
        String threadPoolId = "message-produce";
        ThreadPoolExecutor dynamicExecutor = ThreadPoolBuilder.builder()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .corePoolSize(5)
                .maxPoolNum(10)
                .workQueue(new ResizableCapacityLinkedBlockIngQueue(1024))
                .rejected(new ThreadPoolExecutor.AbortPolicy())
                .keepAliveTime(6000, TimeUnit.MILLISECONDS)
                .dynamicPool()
                .build();
        return dynamicExecutor;
    }

}
