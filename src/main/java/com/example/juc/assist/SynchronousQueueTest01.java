package com.example.juc.assist;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列：SynchronousQueue
 *      没有容量，进去一个元素，必须等待取出之后，才能再添加元素
 */
public class SynchronousQueueTest01 {

    @Test
    public void test1() throws InterruptedException {
        // 创建同步队列
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue();


        new Thread(() -> {
            try {
                System.out.println("添加了元素：A");
                synchronousQueue.put("A");

                System.out.println("添加了元素：B");
                synchronousQueue.put("B");

                System.out.println("添加了元素：C");
                synchronousQueue.put("C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        for (int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(1);
            new Thread(() -> {
                try {
                    System.out.println("获取了元素：" + synchronousQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }

}
