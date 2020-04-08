package com.example.java8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream
 *
 * 1、创建Stream
 *
 */
public class StreamTest01 {

    // 创建Stream
    @Test
    public void test1(){
        // 1.通过Collocation系列集合提供的stream()/parallelStream()方法创建
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        Stream<String> stream1 = list.parallelStream();

        // 2.通过Arrays类中的静态方法stream()获取数组流
        int[] arr = new int[2];
        IntStream stream2 = Arrays.stream(arr);

        // 3.通过Stream类中的静态方法of()获取
        Stream<Integer> stream3 = Stream.of(1, 2, 3, 4);

        // 4.创建无限流--迭代
        Stream<Integer> stream4 = Stream.iterate(10, (x) -> x + 2);
        stream4.limit(10)
                .forEach(System.out::println);

        // 4.创建无限流--生成
        Stream<Integer> stream5 = Stream.generate(() -> (int) (Math.random() * 100));
        stream5.limit(5)
                .forEach(System.out::println);
    }
}
