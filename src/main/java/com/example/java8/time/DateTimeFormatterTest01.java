package com.example.java8.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * DateTimeFormatter: 线程安全的时间格式化处理
 *
 */
public class DateTimeFormatterTest01 {

    @Test
    public void test1() throws ExecutionException, InterruptedException {

        // 时间格式化方法
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 创建线程
        Callable<LocalDate> task = () -> LocalDate.parse("20200318", dtf);

        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        // 结果集合
        List<Future<LocalDate>> list = new ArrayList<>();

        // 获取结果
        for (int i = 0; i < 10; i++) {
            Future<LocalDate> future = pool.submit(task);
            list.add(future);
        }

        // 遍历结果
        for (Future<LocalDate> future : list) {
            System.out.println(future.get());
        }

        // 关闭线程池
        pool.shutdown();
    }

}
