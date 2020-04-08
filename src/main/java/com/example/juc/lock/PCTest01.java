package com.example.juc.lock;

/**
 * 生产消费问题：使用缓冲区
 *
 * 生产者，消费者，产品，缓冲区
 */
public class PCTest01 {
    public static void main(String[] args) {
        // 容器对象
        SynContainer container = new SynContainer();
        // 生产者开始生产
        new Producer(container).start();
        // 消费者开始消费
        new Consumer(container).start();

    }

}

// 生产者
class Producer extends Thread{
    private SynContainer container;

    public Producer(SynContainer container) {
        this.container = container;
    }

    // 生产产品
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            container.push(new Chicken(i));
            System.out.println("生产了第 "+i+" 只鸡");
        }
    }
}

// 消费者
class Consumer extends Thread{
    private SynContainer container;

    public Consumer(SynContainer container) {
        this.container = container;
    }

    // 消费产品

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费了第 "+container.get().id+" 只鸡");
        }
    }
}

// 产品
class Chicken{
    int id;

    public Chicken(int id) {
        this.id = id;
    }
}

// 缓冲区
class SynContainer{
    // 产品容器
    Chicken[] chickens = new Chicken[10];
    // 产品计数器
    int count = 0;

    // 生产者放入产品
    public synchronized void push(Chicken chicken){
        // 若容器已满，则等待
        if (count == chickens.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 添加到容器
        chickens[count] = chicken;
        count++;

        // 通知消费者消费
        this.notifyAll();
    }

    // 消费者拿走产品
    public synchronized Chicken get(){
        // 若容器为空，则等待
        if (count == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count--;
        Chicken chicken = chickens[count];

        // 通知生产者生产
        this.notifyAll();

        return chicken;
    }
}