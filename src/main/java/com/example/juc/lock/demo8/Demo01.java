package com.example.juc.lock.demo8;

import java.util.concurrent.TimeUnit;

/**
 * 深刻理解锁
 *
 *  // synchronized 锁的对象是方法的调用者；
 *  // 两个方法用的是同一个锁，谁先拿到谁执行；
 */
public class Demo01 {
    public static void main(String[] args) {
        // 资源对象
        Phone1 phone = new Phone1();
        new Thread(phone::send).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(phone::call).start();
    }

}

class Phone1{

    // synchronized 锁的对象是方法的调用者；
    // 两个方法用的是同一个锁，谁先拿到谁执行；
    public synchronized void send(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送信息");
    }

    public synchronized void call(){
        System.out.println("拨打电话");
    }

}
