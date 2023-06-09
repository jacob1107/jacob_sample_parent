package com.jacob.controller;

import com.jacob.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.*;

@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @RequestMapping("/hello2")
    public String hello() throws Exception {

        log.trace("==============trace=================");
        log.info("===============info================");
        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync,supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor);

        CompletableFuture<Result> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync,supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor);

        CompletableFuture<Result> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync,supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor);
        CompletableFuture<Result> future4 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CompletableFuture<Result> supplyAsync = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture<Result> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
                return new Result(UUID.randomUUID().toString());
            }, threadPoolExecutor);
            CompletableFuture.allOf(supplyAsync,supplyAsync1).join();
            return new Result(UUID.randomUUID().toString());
        }, threadPoolExecutor);

        CompletableFuture.allOf(future,future2,future3,future4).join();
        return future.get().getMessage();

    }


}
