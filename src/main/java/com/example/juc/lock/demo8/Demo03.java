package com.example.juc.lock.demo8;

import java.util.concurrent.TimeUnit;

/**
 *  静态方法锁的对象是对应类的Class
 *  两个方法用的是同一个锁，谁先拿到谁执行
 *  两个对象，两个静态同步方法，因为使用的同一个锁，所以谁先得到锁谁先执行
 */
public class Demo03 {
    public static void main(String[] args) {
        // 资源对象
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();
        new Thread(()->{phone1.send();}).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {phone2.call();}).start();
    }

}

class Phone3{

    // 这里锁的对象是Phone3.class,全局唯一，类加载完成后就有了；
    // 两个方法用的是同一个锁，谁先拿到谁执行；
    public static synchronized void send(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送信息");
    }

    public static synchronized void call(){
        System.out.println("拨打电话");
    }

}

