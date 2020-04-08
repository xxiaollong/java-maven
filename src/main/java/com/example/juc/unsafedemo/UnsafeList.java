package com.example.juc.unsafedemo;

import java.util.ArrayList;

/**
 * 线程不安全案例三：线程不安全的集合
 */
public class UnsafeList {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
            }).start();
        }

        Thread.sleep(8000);

        System.out.println(list.size());

    }



}
