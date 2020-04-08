package com.example.juc.singleton;

/**
 * 单例模式4: 静态内部类, 线程安全，但是因为反射的缘故，单例这种方式也不安全
 *
 */
public class SingletonTest04 {
    // 私有构造方法
    private SingletonTest04(){}

    // 获取对象的方法
    public static SingletonTest04 getInstace(){
        return InnerClass.SINGLETON_TEST;
    }

    public static class InnerClass{
        private static final SingletonTest04 SINGLETON_TEST = new SingletonTest04();
    }


}
