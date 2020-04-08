package com.example.juc.assist;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * JUC常用辅助类:Semaphore(信号变量，许可证)，限流的时候使用
 *      抢车位
 */
public class SemaphoreTest01 {
    public static void main(String[] args) {
        // 线程数量：停车位
        Semaphore semaphore = new Semaphore(3);

        // 有6辆车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // 获取
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "停了2秒开走了");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 释放
                    semaphore.release();
                }
            }).start();
        }
    }

}
