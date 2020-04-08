package com.example.juc.singleton;

/**
 * 单例模式1：饿汉式
 */
public class SingletonTest01 {

    // 在这个对象没有被使用的情况下浪费内存
    private byte[] data = new byte[1024 * 1024];

    // 私有构造器
    private SingletonTest01() {}

    private final static SingletonTest01 SINGLETON_TEST = new SingletonTest01();

    // 获取示例的方法
    public static SingletonTest01 getInstance(){
        return SINGLETON_TEST;
    }
}
