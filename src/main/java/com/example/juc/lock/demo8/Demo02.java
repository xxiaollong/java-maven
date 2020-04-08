package com.example.juc.lock.demo8;

import java.util.concurrent.TimeUnit;

/**
 *  普通方法调用不受锁的影响
 *  两个对象，两个同步方法，调用相互不受影响
 */
public class Demo02 {
    public static void main(String[] args) {
        // 资源对象
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();
        new Thread(phone1::send, "P1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(phone2::call, "P2").start();

        new Thread(phone2::hello, "P2").start();
    }

}

class Phone2{

    // synchronized 锁的对象是方法的调用者；
    // 两个方法用的是同一个锁，谁先拿到谁执行；
    public synchronized void send(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 有锁--发送信息");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName() + " 有锁--拨打电话");
    }

    public void hello(){
        System.out.println(Thread.currentThread().getName() + " 普通无锁方法");
    }

}

