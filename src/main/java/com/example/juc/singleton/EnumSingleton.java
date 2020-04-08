package com.example.juc.singleton;

/**
 * 单例模式5: 枚举
 *
 * 枚举：本身是一个Class类
 */
public enum  EnumSingleton {

    INSTANCE;

    private EnumSingleton getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        EnumSingleton instance = EnumSingleton.INSTANCE;
    }

}
