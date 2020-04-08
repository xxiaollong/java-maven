package com.example.juc.safedemo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 获取安全的集合的方法
 *
 *  对ArrayList进行并发操作时会出现：java.util.ConcurrentModificationException（并发操作异常）
 *
 *
 */
public class SafeListTest02 {
    public static void main(String[] args) {
        // 出现java.util.ConcurrentModificationException（并发操作异常）
//        List<String> list = new ArrayList<>();

        // 使用Vector<>()集合，Vector在JDK1.0中出现，ArrayList在1.2中出现
//        List<String> list = new Vector<>();

        // Collections.synchronizedList()将转为安全集合
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        // JUC方案：CopyOnWriteArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        // Set集合
        Set<Object> set1 = new HashSet<>();
        Set<Object> set2 = Collections.synchronizedSet(new HashSet<>());
        Set<Object> set3 = new CopyOnWriteArraySet<>();

        // Map集合
        Map<Object, Object> map1 = new HashMap<>();
        Map<Object, Object> map2 = Collections.synchronizedMap(new HashMap<>());
        Map<Object, Object> map3 = new ConcurrentHashMap<>();


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }


    }


}
