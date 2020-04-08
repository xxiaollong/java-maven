package com.example.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信问题：生产者消费者模型（对资源类加减操作）
 *
 * 虚假唤醒：等待应该总是出现在循环中，建议while(条件){等待}
 *
 *
 *
 */
public class PCTest02 {
    public static void main(String[] args) {
        // 资源类（synchronized版）
//        Data1 data = new Data1();

        // 资源类（Lock，Condition版）
        Data2 data = new Data2();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}


// 资源类（Lock，Condition版）
class Data2{
    private int num = 0;

    // 锁对象
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // +1
    public void increment() throws InterruptedException {
        try{
            // 加锁
            lock.lock();

            // 业务
            while (num != 0){   // 不能使用if，会出现虚假唤醒
                // 等待
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() +" => +1="+ num);

            // 通知
            condition.signalAll();

        }finally {
            // 解锁
            lock.unlock();
        }
    }

    // -1
    public void decrement() throws InterruptedException {
        try{
            // 加锁
            lock.lock();

            // 业务
            while (num == 0){   // 不能使用if，会出现虚假唤醒
                // 等待
                condition.await();
            }

            num--;
            System.out.println(Thread.currentThread().getName() +" => -1="+ num);

            // 通知
            condition.signalAll();
        }finally {
            // 解锁
            lock.unlock();
        }
    }
}

// 资源类（synchronized版）
class Data1{
    private int num = 0;

    // +1
    public synchronized void increment() throws InterruptedException {
//        if (num != 0){
        while (num != 0){   // 防止虚假唤醒问题
            // 等待
            this.wait();
        }

        num++;
        System.out.println(Thread.currentThread().getName() +" => +1="+ num);

        // 通知
        this.notifyAll();
    }

    // -1
    public synchronized void decrement() throws InterruptedException {
//        if (num == 0){
        while (num == 0){   // 防止虚假唤醒问题
            // 等待
            this.wait();
        }

        num--;
        System.out.println(Thread.currentThread().getName() +" => -1="+ num);

        // 通知
        this.notifyAll();

    }
}
