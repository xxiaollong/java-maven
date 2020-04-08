package com.example.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 一、Lambda表达式的基础语法
 *
 * -> : 箭头操作符,将lambda表达式拆分成两部分:
 *          左侧: 参数列表
 *          右侧: 表达式中执行的功能,即lambda体
 *
 * 总结：
 *      1、左右遇一括号省；
 *      2、左右推断类型省；
 *
 * 二、Lambda表达式需要"函数式接口"的支持
 *      函数式接口：只有一个抽象方法的接口，可以用@FunctionalInterface修饰
 */
public class LambdaTest01 {

    // 1、无参无表达式
    @Test
    public void test1(){
        // 开启线程
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello Runnable");
            }
        };
        r1.run();

        System.out.println("====================");

        Runnable r2 = () -> System.out.println("hello Lambda");
        r2.run();
    }

    // 2、有一个参数，无返回值
    @Test
    public void test2(){
        Consumer<String> c1 = (x) -> System.out.println(x);
        c1.accept("hello lambda c1");

        Consumer<String> c2 = x -> System.out.println(x);
        c2.accept("hello lambda c2");

        Consumer<String> c3 = System.out::println;
        c2.accept("hello lambda c3");
    }

    // 3、有多个参数，并且有返回值
    @Test
    public void test3(){
        Comparator<Integer> c1 = (x, y) -> Integer.compare(x, y);
        System.out.println(c1.compare(1, 2));
        System.out.println("============");

        Comparator<Integer> c11 = Integer::compare;
        System.out.println(c11.compare(1, 2));
        System.out.println("============");

        // Lambda体中有多条语句，需要用{}包括，有返回值则需要return
        Comparator<Integer> c2 = (x, y) -> {
            System.out.println("多条语句");
            return Integer.compare(x, y);
        };
        System.out.println(c2.compare(1, 2));
    }


}
