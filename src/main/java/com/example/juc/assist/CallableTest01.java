package com.example.juc.assist;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable: 创建线程的方法之一，可以有返回值，可以抛出异常
 * 注意：
 *  1、获取结果时会阻塞
 *  2、结果会被缓存提高效率
 *
 */
public class CallableTest01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        new Thread(new MyThread1()).start();
//        new MyThread2().start();

        MyThread3 thread3 = new MyThread3();
        FutureTask<String> futureTask = new FutureTask<>(thread3);
        new Thread(futureTask, "AA").start();
        // 结果会被缓存，提高效率，所以只执行1次
        new Thread(futureTask, "BB").start();
        // futureTask.get()会产生阻塞，等待结果返回，一般放在最后或者使用异步操作来处理
        System.out.println(futureTask.get());

    }
}

// Callable
class MyThread3 implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " Callable");
        return "Callable";
    }
}

// 传统方式2
class MyThread2 extends Thread{
    @Override
    public void run() {
        System.out.println("传统方式--Thread");
    }
}

// 传统方式1
class MyThread1 implements Runnable{
    @Override
    public void run() {
        System.out.println("传统方式--Runnable");
    }
}