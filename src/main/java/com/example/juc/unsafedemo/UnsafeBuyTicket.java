package com.example.juc.unsafedemo;

/**
 * 线程不安全案例一：买票
 */
public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket ticket = new BuyTicket();

        new Thread(ticket, "AA").start();
        new Thread(ticket, "CC").start();
        new Thread(ticket, "BB").start();
    }

}

class BuyTicket implements Runnable{

    // 票
    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            buy();
        }
    }

    // 售票的方法
    private void buy(){
        if (ticketNums <= 0){
            flag = false;
            return;
        }

        // 模拟延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 出票
        System.out.println(Thread.currentThread().getName()+" 拿到了第 "+ ticketNums-- +"张票");
    }
}
