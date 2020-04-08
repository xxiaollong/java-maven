package com.example.juc.assist;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞式队列：BlockingQueue
 *      四组存取元素的API
 *
 */
public class BlockingQueueTest01 {
    public static void main(String[] args) {

    }

    /**
     * 阻塞：超时等待
     *      offer  poll
     */
    @Test
    public void test4() throws InterruptedException {
        // 创建队列，并指定大小
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加元素
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.size());
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        // 当队列已满时，等待超时后返回false
        System.out.println("添加元素等待超时，不等了，不添加了");

        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.size());
        // 当队列为空时，等待超时后返回null
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println("获取元素等待超时，不等了，不要了");
    }

    /**
     * 阻塞：一直等待
     *      put   take
     */
    @Test
    public void test3() throws InterruptedException {
        // 创建队列，并指定大小
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加元素
        blockingQueue.put("A");
        blockingQueue.put("B");
        blockingQueue.put("C");
        System.out.println(blockingQueue.size());

        // 当队列已满时，开始进入阻塞，直到队列有容量时继续添加
//        blockingQueue.put("D");

        // 获取元素
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.size());

        // 当队列为空时，开始进入阻塞，直到队列中有元素可以继续获取
//        System.out.println(blockingQueue.take());


    }


    /**
     * 不抛出异常的存取
     *      offer  poll  peek
     */
    @Test
    public void test2(){
        // 创建队列，并指定大小
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加元素
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
        System.out.println(blockingQueue.size());
        // 当队列已满继续添加元素时，返回false
        System.out.println(blockingQueue.offer("D"));

        // 查看队首元素
        System.out.println("队首元素是：" + blockingQueue.peek());

        // 获取元素
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        // 查看队首元素,队列为空时返回null
        System.out.println("队首元素是：" + blockingQueue.peek());

        // 当队列为空时，继续获取元素，则返回null
        System.out.println(blockingQueue.poll());

    }

    /**
     * 抛出异常的存取
     *      add  remove  element
     */
    @Test
    public void test1(){
        // 创建队列，并指定大小
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加元素
        System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.add("C"));
        System.out.println(blockingQueue.size());

        // 查看队首元素
        System.out.println("队首元素是：" + blockingQueue.element());

        // 当队列满时继续添加元素，则抛出java.lang.IllegalStateException: Queue full异常
//        System.out.println(blockingQueue.add("D"));

        // 从头开始移除并返回元素
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.size());

        // 查看队首元素,队列为空时，抛出java.util.NoSuchElementException异常
//        System.out.println("队首元素是：" + blockingQueue.element());

        // 当队列为空时继续移除，则抛出java.util.NoSuchElementException异常
//        System.out.println(blockingQueue.remove());


    }

}
