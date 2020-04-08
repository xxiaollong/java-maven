package com.example.juc.lock.demo8;

import java.util.concurrent.TimeUnit;

/**
 * 一个对象，一个静态同步方法，一个同步方法，相互不会影响
 *
 */
public class Demo04 {
    public static void main(String[] args) {
        // 资源对象
        Phone4 phone = new Phone4();
        new Thread(()->{phone.send();}).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {phone.call();}).start();
    }

}

class Phone4{

    // 锁的是Class类
    public static synchronized void send(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送信息");
    }

    // 锁的是调用对象
    public synchronized void call(){
        System.out.println("拨打电话");
    }

}
