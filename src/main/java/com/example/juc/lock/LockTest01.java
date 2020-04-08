package com.example.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock(可重入锁)实现同步买票
 */
public class LockTest01 {
    public static void main(String[] args) {
        BuyTicket ticket = new BuyTicket();
        new Thread(ticket).start();
        new Thread(ticket).start();
        new Thread(ticket).start();

    }
}

class BuyTicket implements Runnable{
    int ticketNums = 10;

    // 显式的定义锁
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try{
                // 加锁
                lock.lock();

                // 不安全的代码块
                if (ticketNums <= 0){
                    break;
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(ticketNums--);
                }
            }finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
