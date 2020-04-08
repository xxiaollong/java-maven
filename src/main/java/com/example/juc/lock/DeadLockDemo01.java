package com.example.juc.lock;

/**
 * 死锁：多个线程之间出现相互等待的情况
 */
public class DeadLockDemo01 {
    public static void main(String[] args) {
        Test t1 = new Test(0, "晓明");
        Test t2 = new Test(2, "小红");

        t1.start();
        t2.start();
    }
}

class AA{

}

class BB{

}

class Test extends Thread{
    // 这里必须要用static修饰，保证不同对象获取的是同一把锁
    private static AA aa = new AA();
    private static BB bb = new BB();

    private int index;


    public Test(int index, String name) {
        super(name);
        this.index = index;
    }

    @Override
    public void run() {
        try {
            test2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 出现死锁
    private void test1() throws InterruptedException {
        if (index <= 1){
            synchronized (aa){
                System.out.println(this.getName() + " 获得了aa锁");
                Thread.sleep(1000);
                synchronized (bb){
                    System.out.println(this.getName() + " 获得了bb锁");
                }
            }
        }else {
            synchronized (bb){
                System.out.println(this.getName() + " 获得了bb锁");
                Thread.sleep(2000);
                synchronized (aa){
                    System.out.println(this.getName() + " 获得了aa锁");
                }
            }
        }
    }

    // 不会出现死锁，只会出现等待
    private void test2() throws InterruptedException {
        if (index <= 1){
            synchronized (aa){
                System.out.println(this.getName() + " 获得了aa锁");
                Thread.sleep(1000);
            }
            synchronized (bb){
                System.out.println(this.getName() + " 获得了bb锁");
            }
        }else {
            synchronized (bb){
                System.out.println(this.getName() + " 获得了bb锁");
                Thread.sleep(2000);
            }
            synchronized (aa){
                System.out.println(this.getName() + " 获得了aa锁");
            }
        }
    }
}
