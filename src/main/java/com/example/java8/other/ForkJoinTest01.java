package com.example.java8.other;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 三种求和计算
 *
 */
public class ForkJoinTest01 {

    // 总和为：500000000500000000 耗时为：795
    @Test
    public void test3(){
        long start = System.currentTimeMillis();

        long sum = LongStream.range(1L, 10_0000_0000L + 1).parallel().reduce(0L, Long::sum);

        long end = System.currentTimeMillis();
        System.out.println("总和为：" +sum+" 耗时为："+(end-start));
    }

    // 总和为：500000000500000000 耗时为：10673
    // 临界值设置不当容易出现:java.lang.StackOverflowError
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyForkJoin forkJoin = new MyForkJoin(1L, 10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(forkJoin);
        Long sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("总和为：" +sum+" 耗时为："+(end-start));
    }

    // 总和为：500000000500000000 耗时为：11097
    @Test
    public void test1(){
        Long sum = 0L;
        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("总和为：" +sum+" 耗时为："+(end-start));

    }

}

class MyForkJoin extends RecursiveTask<Long>{
    private Long start;
    private Long end;
    private Long temp = 10000L;

    public MyForkJoin(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) < temp){
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            long middle = (end - start) / 2;
            // 拆分任务
            MyForkJoin task1 = new MyForkJoin(start, middle);
            task1.fork();
            MyForkJoin task2 = new MyForkJoin(middle + 1, end);
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}