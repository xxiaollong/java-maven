package com.example.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS ：compareAndSet(比较并交换)
 *  CAS是CPU的并发原语
 *
 */
public class CASTest01 {

    public static void main(String[] args) {

        // 原子应用解决ABA问题
        AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(1, 1);


        AtomicInteger num = new AtomicInteger(2020);

        boolean b1 = num.compareAndSet(2020, 2021);
        System.out.println(b1 +"  "+num);

        boolean b2 = num.compareAndSet(2020, 2021);
        System.out.println(b2 +"  "+num);

        System.out.println(num.get());

    }
}
