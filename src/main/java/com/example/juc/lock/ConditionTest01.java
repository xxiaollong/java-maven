package com.example.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步监视器：精准唤醒
 *
 *
 */
public class ConditionTest01 {
    public static void main(String[] args) {

        // 资源类
        Data3 data = new Data3();

        // 开启线程
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t--1").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t--2").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t--3").start();

    }

}

class Data3{
    // 锁对象
    private Lock lock = new ReentrantLock();
    // 同步监视器
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    // 标志位
    private int num = 1;    //  1A,2B,3C

    // 方法1
    public void printA() throws InterruptedException {
        try{
            lock.lock();
            while (num != 1){
                c1.await();
            }
            System.out.println(Thread.currentThread().getName() + " > AAA");

            // 通知B执行
            num = 2;
            c2.signal();
        }finally {
            lock.unlock();
        }
    }

    // 方法1
    public void printB() throws InterruptedException {
        try{
            lock.lock();
            while (num != 2){
                c2.await();
            }
            System.out.println(Thread.currentThread().getName() + " > BBB");

            // 通知C执行
            num = 3;
            c3.signal();
        }finally {
            lock.unlock();
        }
    }

    // 方法1
    public void printC() throws InterruptedException {
        try{
            lock.lock();
            while (num != 3){
                c3.await();
            }
            System.out.println(Thread.currentThread().getName() + " > CCC");

            // 通知A执行
            num = 1;
            c1.signal();
        }finally {
            lock.unlock();
        }
    }


}
