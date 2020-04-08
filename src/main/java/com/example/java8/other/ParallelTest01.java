package com.example.java8.other;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * 并行流，串行流
 *
 */
public class ParallelTest01 {


    // 并行流
    @Test
    public void test2(){
        Instant start = Instant.now();

        long reduce = LongStream
                .range(0, 1000000000L)
                .parallel()
                .reduce(0, Long::sum);
        System.out.println(reduce);

        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

    // 串行流
    @Test
    public void test1(){
        Instant start = Instant.now();

        long reduce = LongStream
                .range(0, 1000000000L)
                .sequential()
                .reduce(0, Long::sum);
        System.out.println(reduce);

        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

}
