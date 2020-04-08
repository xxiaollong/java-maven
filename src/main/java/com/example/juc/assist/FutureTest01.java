package com.example.juc.assist;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用：CompletableFuture
 *  异步执行
 *  成功回调
 *  失败回调
 *
 */
public class FutureTest01 {


    /**
     * 有返回值的异步回调: supplyAsync
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        // 发起一个请求
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " : supplyAsync");

            // 模拟异常
            int i = 10/0;

            return 1024;
        });

        System.out.println("main");

        // 正常及异常的返回的调用
        Integer i = completableFuture.whenComplete((t, u) -> {
            // 正常的返回值
            System.out.println("t : " + t);
            // 异常信息，正常是为null
            System.out.println("u : " + u);
        }).exceptionally((e) -> {
            // 错误信息
            String message = e.getMessage();
            System.out.println("error : " + message);
            // 异常时的返回值
            return 500;
        }).get();

        System.out.println("result : " + i);
    }


    /**
     * 没有返回值的异步回调：runAsync
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {

        // 发起一个请求
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : runAsync");
        });

        System.out.println("main");

        // 获取阻塞结果
        completableFuture.get();
    }
}
