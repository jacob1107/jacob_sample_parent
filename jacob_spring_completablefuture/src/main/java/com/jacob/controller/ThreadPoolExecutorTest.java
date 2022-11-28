package com.jacob.controller;

import com.jacob.bean.Result;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.UUID;
import java.util.concurrent.*;

@RestController
public class ThreadPoolExecutorTest {


    @Test
    public void test1() {
        System.out.println(Thread.currentThread().getName());
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, Executors.newSingleThreadExecutor());
    }

    @Test
    public void test2() {
        System.out.println(Thread.currentThread().hashCode());
        CompletableFuture.supplyAsync(() -> {

            System.err.println(Thread.currentThread().hashCode());
            return UUID.randomUUID().toString();
        }, Executors.newSingleThreadExecutor()).thenAcceptAsync(s -> {
            System.out.println(s + "你好");
            System.out.println(Thread.currentThread().hashCode());
        }, Executors.newSingleThreadExecutor()).join();
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().hashCode());
        String s = CompletableFuture.supplyAsync(() -> {

            System.err.println(Thread.currentThread().hashCode());
            return UUID.randomUUID().toString();
        }, Executors.newSingleThreadExecutor()).get();
        System.out.println(s);
    }

    @Test
    public void test4() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().hashCode());
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                TimeUnit.SECONDS.sleep(30);
                System.out.println("我是线程30");
            }
        }, Executors.newSingleThreadExecutor());
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                TimeUnit.SECONDS.sleep(80);
                System.out.println("我是线程80");
            }
        }, Executors.newSingleThreadExecutor());
        CompletableFuture.allOf(future, future1).join();
        System.out.println("执行完成");
    }

    @Test
    public void test5() throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println(Thread.currentThread().hashCode());

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return UUID.randomUUID().toString();
        }, Executors.newSingleThreadExecutor());
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return UUID.randomUUID().toString();
        }, Executors.newSingleThreadExecutor());

        String s = stringCompletableFuture.get(500, TimeUnit.SECONDS);
        System.out.println("执行完成" + s);
    }


}