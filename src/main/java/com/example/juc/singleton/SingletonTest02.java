package com.example.juc.singleton;

/**
 * 单例模式2：懒汉式
 *      在多线程的情况下存在问题
 */
public class SingletonTest02 {

    // 私有构造器
    private SingletonTest02() {
        System.out.println(Thread.currentThread().getName() + " 调用了");
    }

    private static SingletonTest02 SINGLETON_TEST;

    // 获取示例的方法
    public static SingletonTest02 getInstance(){
        if (SINGLETON_TEST == null){
            SINGLETON_TEST = new SingletonTest02();
        }
        return SINGLETON_TEST;
    }


    // 验证多线程下实例多次创建
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(SingletonTest02::getInstance).start();
        }
    }
}
