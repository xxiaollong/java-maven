package com.example.juc.pool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程池的好处：
 *  1、降低资源的消耗；
 *  2、提高响应的速度；
 *  3、方便线程管理；
 *
 * 总结：线程复用、可以控制最大并发数、管理线程
 *
 * 线程池知识点：三大方法、七大参数、四种拒绝策略
 *
 */
public class PoolTest01 {

    /**
     * 线程池最大线程数如何设置？
     * 1、CPU密集型：最大线程数和机器CPU核心数保持一致，减少上下文切换时的性能损耗
     * 2、IO密集型：最大线程数 大于 程序中IO密集的线程数（建议2倍）
     *
     */
    @Test
    public void test4(){
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU核心数为：" + i);

    }

    /**
     *
     ThreadPoolExecutor.AbortPolicy :抛出一个 RejectedExecutionException
     ThreadPoolExecutor.CallerRunsPolicy :哪来的回哪去尝试执行
     ThreadPoolExecutor.DiscardPolicy :队列满了就丢掉任务，不会抛出异常
     ThreadPoolExecutor.DiscardOldestPolicy :队列满了，尝试去竞争执行，有机会则执行，否则丢掉任务，不会抛出异常
     */
    @Test
    public void test3() throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2,  //核心线程数为2
                5,  //最大线程数为3
                3,  //超时时间为3秒
                TimeUnit.SECONDS,   //超时时间单位为秒
                new LinkedBlockingDeque<>(3),   //使用容量为3的阻塞队列
                Executors.defaultThreadFactory(),   //默认工厂
//                new ThreadPoolExecutor.AbortPolicy()
//                new ThreadPoolExecutor.CallerRunsPolicy()
//                new ThreadPoolExecutor.DiscardPolicy()
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        // 当线程>最大线程数+阻塞队列容量值 时，有可能触发拒绝策略。
        for (int i = 0; i < 10; i++) {
            // 使用线程池来创建线程
            poolExecutor.execute(() ->{
                System.out.println(Thread.currentThread().getName());
            });
        }

        // 等待线程结束
        TimeUnit.SECONDS.sleep(3);

        // 关闭资源
        poolExecutor.shutdown();
    }


    /**
     * 七大参数
     *
     ThreadPoolExecutor(
         int corePoolSize,  //核心线程池大小
         int maximumPoolSize,   //最大核心线程池大小
         long keepAliveTime,    //超时没有调用就释放
         TimeUnit unit,     //超时单位
         BlockingQueue<Runnable> workQueue, //阻塞队列
         ThreadFactory threadFactory,   //线程工厂
         RejectedExecutionHandler handler   //拒绝策略
     )
     */
    @Test
    public void test2() throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2,  //核心线程数为2
                5,  //最大线程数为3
                3,  //超时时间为3秒
                TimeUnit.SECONDS,   //超时时间单位为秒
                new LinkedBlockingDeque<>(3),   //使用容量为3的阻塞队列
                Executors.defaultThreadFactory(),   //默认工厂
                new ThreadPoolExecutor.AbortPolicy()    //被拒绝的任务抛出RejectedExecutionException
        );

        // 当线程>最大线程数+阻塞队列容量值 时，有可能触发拒绝策略。
        for (int i = 0; i < 10; i++) {
            // 使用线程池来创建线程
            poolExecutor.execute(() ->{
                System.out.println(Thread.currentThread().getName());
            });
        }

        // 等待线程结束
        TimeUnit.SECONDS.sleep(3);

        // 关闭资源
        poolExecutor.shutdown();

    }


    /**
     * 创建线程池: 三大创建方法
     * 三大创建方法的本质是：ThreadPoolExecutor
     *
     */
    @Test
    public void test1() throws InterruptedException {
        // 创建单个容量的线程池
//        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        // 创建指定容量的线程池
//        ExecutorService threadExecutor = Executors.newFixedThreadPool(5);
        // 创建可伸缩容量的线程池
        ExecutorService threadExecutor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            // 使用线程池来创建线程
            threadExecutor.execute(() ->{
                System.out.println(Thread.currentThread().getName());
            });
        }

        // 等待线程结束
        TimeUnit.SECONDS.sleep(5);

        // 关闭资源
        threadExecutor.shutdown();
    }

}
