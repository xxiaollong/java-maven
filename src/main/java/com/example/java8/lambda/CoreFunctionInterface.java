package com.example.java8.lambda;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8 内置的4大核心函数式接口
 *  Consumer<T>: 消费型接口（无返回值） : void accept(T t);
 *  Supplier<T>: 供给型接口（无入参）   : T get();
 *  Function<T, R>: 函数型接口（一入一出）: R apply(T t);
 *  Predicate<T>: 断言型接口（返回值是bool类型）: boolean test(T t);
 */
public class CoreFunctionInterface {

    // Consumer<T>: 消费型接口
    public void happy(Double d, Consumer<Double> consumer){
        consumer.accept(d);
    }

    @Test
    public void test1(){
        happy(100.0, (d) -> System.out.println("吃饭每次消费：" + d + "元"));
    }

    // Supplier<T>: 供给型接口
    // 产生指定个数的整数，并返回
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(sup.get());
        }

        return list;
    }

    @Test
    public void test2(){
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        numList.forEach(System.out::println);
    }

    // Function<T, R>: 函数型接口
    // 用于处理字符串的方法
    public String getNewStr(String str, Function<String, String> fun){
        return fun.apply(str);
    }

    @Test
    public void test3(){
        String str = "  aaaBBB  ";
        // 去掉前后空格
        System.out.println(getNewStr(str, String::trim));
        // 去掉前后空格并大写
        System.out.println(getNewStr(getNewStr(str, String::toUpperCase), String::trim));
    }


    // Predicate<T>: 断言型接口
    // 将满足条件的字符串提取出来
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        ArrayList<String> newList = new ArrayList<>();

        for (String str : list) {
            if (pre.test(str)){
                newList.add(str);
            }
        }

        return newList;
    }

    @Test
    public void test4(){
        List<String> list = Arrays.asList(
                "AAA",
                "aAA",
                "bbb",
                "Ccc"
        );

        List<String> newList = filterStr(list, (str) -> str.matches("^[A-Z].*?"));
        newList.forEach(System.out::println);

    }


}
