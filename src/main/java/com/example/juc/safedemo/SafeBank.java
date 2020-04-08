package com.example.juc.safedemo;

/**
 * 线程安全案例二：取钱（同步代码块）
 *
 *  synchronized默认锁对象是this
 *  锁对象是变化的量，需要执行增删改操作
 */
public class SafeBank {
    public static void main(String[] args) {
        Account account = new Account(100, "买车存款");

        Drawing aa = new Drawing(account, 50, "AA");
        Drawing bb = new Drawing(account, 100, "BB");

        aa.start();
        bb.start();
    }


}

// 账户
class Account{
    int money;
    String name;

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

// 银行，模拟取款
class Drawing extends Thread{

    private Account account;        // 账户
    private int drawingMoney;       // 取了多少钱
    private int nowMoney;           // 手里多少钱

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    // 取钱

    @Override
    public void run() {
        // 同步代码块，锁对象是账户
        synchronized (account){
            if (account.money - drawingMoney < 0){
                System.out.println(this.getName() + " 余额不足，不能取钱");
                return;
            }

            // 模拟延时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 余额=余额-取出来的钱
            account.money = account.money - drawingMoney;

            // 手里的钱=手里的钱+取出来的钱
            nowMoney = nowMoney + drawingMoney;
        }

        // 状态
        System.out.println(account.name + " 余额为： " + account.money);
        System.out.println(this.getName() + " 手里的钱为： " + nowMoney);
    }
}
