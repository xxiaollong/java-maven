package com.example.juc.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式3：DCL懒汉式（双重检测锁+volatile（避免指令重排））
 *
 */
public class SingletonTest03 {

    // 私有构造器
    private SingletonTest03() {
        System.out.println(Thread.currentThread().getName() + " 调用了");
    }

    // 避免指令重排
    private volatile static SingletonTest03 SINGLETON_TEST;

    // 双重检测锁模式
    public static SingletonTest03 getInstance(){
        if (SINGLETON_TEST == null){
            synchronized (SingletonTest03.class){
                if (SINGLETON_TEST == null){
                    // 这不是原子性操作，在指令重排的影响下，极端情况下会返回空对象
                    SINGLETON_TEST = new SingletonTest03();
                }
            }
        }
        return SINGLETON_TEST;
    }


    // 反射可以破坏单例
    public static void main(String[] args) throws Exception {
        SingletonTest03 instance1 = SingletonTest03.getInstance();
        // 通过反射获取空参构造器
        Constructor<SingletonTest03> constructor = SingletonTest03.class.getDeclaredConstructor(null);
        // 忽略私有构造器
        constructor.setAccessible(true);
        SingletonTest03 instance2 = constructor.newInstance();

        System.out.println(instance1);
        System.out.println(instance2);

    }
}
