package com.example.juc.assist;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * JUC常用辅助类:CyclicBarrier(加法计数器)
 *      集齐几颗龙珠召唤神龙
 */
public class CyclicBarrierTest01 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->System.out.println("开始召唤神龙"));

        System.out.println("开始收集龙珠");

        for (int i = 0; i < 7; i++) {
            // 中间常量
            final int temp = i+1;
            new Thread(() ->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"获得了第 "+temp+" 颗龙珠");
                try {
                    // 等待集齐
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }

}
