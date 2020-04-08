package com.example.juc.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：多个线程可以同时读，但是多个线程不能同时写
 *
 *  共享锁（读锁）：多个线程可以同时占有
 *  独占锁/排它锁（写锁）：一次只能被一个线程占有
 *
 */
public class ReadWriteLockTest01 {
    public static void main(String[] args) {

        // 资源类
//        MyCache01 myCache = new MyCache01();
        MyCache02 myCache = new MyCache02();

        // 写入
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(String.valueOf(temp), temp);
            }, String.valueOf(i)).start();
        }

        // 读取
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(String.valueOf(temp));
            }, String.valueOf(i)).start();
        }

    }


}

// 缓存(读写锁)
class MyCache02{
    private volatile Map<String, Object> map = new HashMap<>();

    // 创建读写锁
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    // 存，写
    public void put(String key, Object value){
        try{
            // 开启写入锁
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " 写入了 "+key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入了 "+key + " 完成");
        }finally {
            lock.writeLock().unlock();
        }
    }

    // 取，读
    public void get(String key){
        try{
            // 开启读锁
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " 读取 "+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取 "+key +"--"+ o + " 完成");
        }finally {
            lock.readLock().unlock();
        }
    }

}

// 缓存(无锁)
class MyCache01{

    private volatile Map<String, Object> map = new HashMap<>();

    // 存，写
    public void put(String key, Object value){
        System.out.println(Thread.currentThread().getName() + " 写入了 "+key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + " 写入了 "+key + " 完成");
    }

    // 取，读
    public void get(String key){
        System.out.println(Thread.currentThread().getName() + " 读取 "+key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + " 读取 "+key +"--"+ o + " 完成");
    }

}