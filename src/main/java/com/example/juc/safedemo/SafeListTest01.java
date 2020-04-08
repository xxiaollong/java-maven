package com.example.juc.safedemo;

import java.util.ArrayList;

/**
 * 线程安全案例三：集合
 * 锁对象是变化的量，需要执行增删改操作
 */
public class SafeListTest01 {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                // list是变化的量，用它做锁对象
                synchronized (list){
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }

        Thread.sleep(8000);

        System.out.println(list.size());

    }



}
