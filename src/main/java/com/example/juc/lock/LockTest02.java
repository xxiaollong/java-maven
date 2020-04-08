package com.example.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * 可重入锁
 */
public class LockTest02 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(phone::send, "A").start();

        new Thread(phone::send, "B").start();

    }

}

class Phone{
    public synchronized void send(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " send");
        call();
    }

    public synchronized void call(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " call");
    }
}
