package com.example.juc.assist;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * JUC常用辅助类:CountDownLatch(减法计数器)
 *    图书馆锁门案例
 */
public class CountDownLatchTest01 {
    public static void main(String[] args) throws Exception {
        // 计数器，总数为6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 总共有6个人
        for (int i = 0; i < 6; i++) {
            new Thread(() ->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" 出去了");

                // 计数器-1
                countDownLatch.countDown();
            }).start();
        }

        System.out.println("等待走完关门");
        // 等待计数器归零，然后向下执行，注意是await()，不是wait()
        countDownLatch.await();

        System.out.println("走完了，关门");
    }
}


