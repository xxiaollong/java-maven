package com.example.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class LockTest03 {
    public static void main(String[] args) throws InterruptedException {
        MySpinLock lock = new MySpinLock();

        new Thread(()->{
            try{
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }

        }, "T1").start();

        TimeUnit.SECONDS.sleep(1);


        // T2进入自旋等待获取锁
        new Thread(()->{
            try{
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }

        }, "T2").start();


    }

}

/**
 * 自定义自旋锁
 */
class MySpinLock{

    private AtomicReference<Thread> atomicReference =  new AtomicReference<>();

    // 加锁
    public void lock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "  lock");

        // 自旋锁
        while (!atomicReference.compareAndSet(null, thread)){}
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "  unlock");
        atomicReference.compareAndSet(thread, null);
    }

}