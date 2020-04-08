package com.example.java8.lambda;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 四大核心函数式接口
 *
 */
public class Core4Test01 {

    /**
     * Supplier: 供给型接口，没有入参，只有一个出参
     */
    @Test
    public void test4(){
        Supplier<String> s1 = new Supplier<String>() {
            @Override
            public String get() {
                return "AAA";
            }
        };
        System.out.println(s1.get());

        // Lambda简写
        Supplier<String> s2 = () -> "AAA";
        System.out.println(s2.get());

    }

    /**
     * Consumer: 消费型接口，有一个入参，没有出参
     */
    @Test
    public void test3(){
        Consumer<String> c1 = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str);
            }
        };
        c1.accept("hello");

        // Lambda简写
        Consumer<String> c2 = (str) -> System.out.println(str);
        c2.accept("hello");

        Consumer<String> c3 = System.out::println;
        c3.accept("hello");

    }

    /**
     * Predicate: 断定型接口，一个入参，返回值是bool值
     */
    @Test
    public void test2(){
        Predicate<String> p1 = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.startsWith("AA");
            }
        };
        System.out.println(p1.test("AABB"));

        // Lambda简写
        Predicate<String> p2 = (str) -> str.startsWith("AA");
        System.out.println(p2.test("AABB"));
    }

    /**
     * Function: 函数式接口，一个入参，一个出参
     */
    @Test
    public void test1(){
        Function<String, String> f1 = new Function<String, String>() {
            @Override
            public String apply(String str) {
                return str;
            }
        };
        System.out.println(f1.apply("abc"));

        // Lambda简写
        Function<String, String> f2 = (str) -> str;
        System.out.println(f2.apply("efg"));
    }


}
