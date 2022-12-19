package com.jacob.controller;

import com.jacob.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController3 {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Resource(name = "threadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource(name = "threadPoolExecutor2")
    private ThreadPoolExecutor threadPoolExecutor2;

    @RequestMapping("/hello3")
    public String hello() throws Exception {

        log.trace("==============trace=================");
        log.info("===============info================");
        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> {

            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync, supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor2);

        CompletableFuture<Result> future2 = CompletableFuture.supplyAsync(() -> {

            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync, supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor2);


        CompletableFuture.allOf(future, future2).join();
        return "报文响应:" + UUID.randomUUID().toString();

    }


}

