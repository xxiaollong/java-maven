package com.example.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 买票案例的OOP写法
 *  注意：线程是一个单独的资源类，没有任何附属的操作
 *
 *  公平锁和非公平锁（默认使用非公平锁）
 *      公平锁：线程排队在CPU中执行
 *      非公平锁：线程可以插队在CPU中执行
 *
 *  Synchronized和Lock的区别
 *      1、Synchronized是关键字，Lock是一个接口，对应有实现类；
 *      2、Synchronized无法判断获取锁的状态，Lock可以判断是否获取了锁；
 *      3、Synchronized自动释放锁（发生异常自动释放），Lock必须要手动释放锁；
 *      4、Synchronized当锁被某个线程占用时，其他线程等待，Lock中其他线程可以用tryLock()尝试获取锁；
 *      5、Synchronized是可重入、不可中断、非公平锁，Lock是可重入锁、可以判断、非公平（可以设置），自由度较高；
 *      6、Synchronized适合锁少量同步代码，Lock适合锁大量同步代码；
 *
 */
public class TicketTest01 {
    public static void main(String[] args) {
        // 资源类
//        Tickets1 tickets = new Tickets1();
        Tickets2 tickets = new Tickets2();

        // 线程开始操作资源类
        new Thread(() ->{
            for (int i = 0; i < 20; i++) tickets.sale();
        }).start();

        new Thread(() ->{
            for (int i = 0; i < 20; i++) tickets.sale();
        }).start();

        new Thread(() ->{
            for (int i = 0; i < 20; i++) tickets.sale();
        }).start();

    }

}

// 资源类，OOP（只包括属性和方法）: Lock锁
class Tickets2{
    private int nums = 50;

    // 锁对象
    private Lock lock = new ReentrantLock();

    // 售票
    public void sale(){
        try{
            // 加锁
            lock.lock();
            // 业务代码
            if (nums > 0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "买到了第 " + (nums--) + " 张票");
            }
        }finally {
            // 解锁
            lock.unlock();
        }
    }

}

// 资源类，OOP（只包括属性和方法）：synchronized的写法
class Tickets1{

    private int nums = 50;

    // 售票
    public synchronized void sale(){
        if (nums > 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "买到了第 " + (nums--) + " 张票");
        }
    }

}
