package com.example.juc.lock;


import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * JMM
 *  Volatile: Volatile是Java虚拟机提供的轻量级的同步机制
 *      1、保证可见性；
 *      2、不保证原子性；
 *      3、禁止指令重排（通过内存屏障实现）；
 *
 *
 *  JMM: Java内存模型，是一种概念或约定
 *   关于JMM同步的一些约定：
 *      1、线程加锁前，必须读取主存中的最新值到工作内存中；
 *      2、线程解锁前，必须把共享变量like刷回主存（线程和主存之间有一条总线，Volatile修饰后开启协议，监听线程中的共享变量）；
 *      3、加锁和解锁是同一把锁；
 *   JMM的8中操作：
 *      1、lock(锁定)、unlock(解锁)
 *      2、read(读取)、load(载入)
 *      3、use(使用)、assign(赋值)
 *      4、store(存储)、write(写入)
 *
 *
 *   指令重排: 计算机执行程序，并不一定是按照写的代码顺序去执行(重排是会考虑数据依赖性)
 *      源代码-->编译器优化重排-->指令并行也可能重排-->内存系统重排-->执行
 *      volatile可以避免指令重排：内存屏障
 *
 */
public class VolatileTest01 {

    // volatile不能保证原子性
//    private volatile static int num = 1;
    // 原子类的Integer
    private volatile static AtomicInteger num = new AtomicInteger();

    public static void add(){
//        num ++;
        // AtomicInteger的+1操作
        num.getAndIncrement();
    }
    /**
     * 不保证原子性
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " num : " + num);
    }


    // 不加volatile时，程序就会死循环
    // 加volatile时，可以保存可见性
//    private volatile static int num = 1;
    /**
     * 可见性
     */
//    public static void main(String[] args) throws InterruptedException {
//        new Thread(() ->{
//            while (num == 1){
//
//            }
//        }).start();
//
//        TimeUnit.SECONDS.sleep(1);
//
//        num = 11;
//        System.out.println("num : " + num);
//
//    }




}
