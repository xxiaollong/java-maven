package com.example.java8.stream;

import com.example.java8.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Stream
 *
 * 3、终止操作
 *  查找与匹配
 *      allMatch: 是否匹配所有元素
 *      anyMatch: 是否匹配至少一个元素
 *      noneMatch: 是否没有匹配所有元素
 *      findFirst: 返回第一个元素
 *      findAny: 返回流中任一元素(不适合随机获取元素)
 *      count: 返回流中元素的总个数
 *      max: 返回流中最大元素
 *      min: 返回流中最小元素
 *
 *  收集
 *      collect:
 *
 *
 */
public class StreamTest03 {

    List<Employee> emps = Arrays.asList(
            new Employee(1001, "AA", 20, 2000.22, 10),
            new Employee(1002, "CC", 30, 8000.88, 20),
            new Employee(1003, "RR", 20, 5000.55, 10),
            new Employee(1004, "BB", 60, 4000.44, 20),
            new Employee(1005, "DD", 50, 1000.11, 10),
            new Employee(1005, "DD", 50, 1000.11, 20),
            new Employee(1006, "FF", 18, 3000.33, 30)
    );

    // collect--连接
    @Test
    public void test8(){
        String strs = emps.stream().map(Employee::getName).collect(Collectors.joining("-","开始==》","《==结束"));
        System.out.println(strs);
    }

    // collect--分区
    @Test
    public void test7(){
        Map<Boolean, List<Employee>> collect = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getDept() == 10));
        System.out.println(collect);
    }

    // collect--分组
    @Test
    public void test6(){
        // 分组
        Map<Integer, List<Employee>> collect = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept));
        System.out.println(collect);

        System.out.println("=============================");

        // 多级分组
        Map<Integer, Map<String, List<Employee>>> collect1 = emps.stream()
                .collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.groupingBy((e) -> {
                            if (e.getAge() <= 30) {
                                return "青年";
                            } else if (e.getAge() > 30 && e.getAge() <= 50) {
                                return "中年";
                            } else {
                                return "老年";
                            }
                        })));
        System.out.println(collect1);

    }

    // collect--组函数
    @Test
    public void test5(){
        // 总数
        Long count = emps.stream().collect(Collectors.counting());
        System.out.println("count: " + count);

        System.out.println("=========================");
        // 平均数
        Double avg = emps.stream().collect(Collectors.averagingDouble(Employee::getSal));
        System.out.println("avg: " + avg);

        System.out.println("=========================");
        // 总和
        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSal));
        System.out.println("sum: " + sum);

        System.out.println("=========================");
        // 最大值
        Optional<Employee> max = emps.stream().collect(
                Collectors.maxBy((v1, v2) -> Double.compare(v1.getSal(), v2.getSal())));
        System.out.println("max: " + max.orElseGet(null));

        System.out.println("=========================");
        // 最小值
        Optional<Double> min = emps.stream()
                .map(Employee::getSal)
                .collect(Collectors.minBy(Double::compare));
        System.out.println("min: " + min.orElseGet(null));

        System.out.println("=========================");
        // 组函数的另一种实现
        DoubleSummaryStatistics collect = emps.stream().collect(Collectors.summarizingDouble(Employee::getSal));
        System.out.println("count: " + collect.getCount());
        System.out.println("sum: " + collect.getSum());
        System.out.println("avg: " + collect.getAverage());
        System.out.println("max: " + collect.getMax());
        System.out.println("min: " + collect.getMin());

    }

    // collect--1
    @Test
    public void test4(){
        // 收集到List集合中
        List<String> list1 = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(list1);

        System.out.println("===============");

        // 收集到Set集合中
        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        System.out.println(set);

        System.out.println("==============");

        // 收集到自定义集合中
        HashSet<String> hashSet = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        System.out.println(hashSet);
    }

    // count、max、min
    @Test
    public void test3(){
        long count = emps.stream().count();
        System.out.println("员工总数为："+count);

        Employee max = emps.stream().max((e1, e2) ->
                Double.compare(e1.getSal(), e2.getSal())
        ).orElseGet(null);
        System.out.println("工资最高的员工是："+max);

        Employee min = emps.stream().min((e1, e2) ->
                Double.compare(e1.getSal(), e2.getSal())
        ).orElseGet(null);
        System.out.println("工资最低的员工是："+min);

    }

    // findFirst、findAny
    @Test
    public void test2(){
        Optional<Employee> first = emps.stream().sorted((e1, e2) ->
             -Double.compare(e1.getSal(), e2.getSal())
        ).findFirst();
        System.out.println(first);

        System.out.println("===============");

        // 注意stream()和parallelStream()的区别
        Optional<Employee> any = emps.parallelStream()
                .filter(e -> e.getId().toString().startsWith("100")).findAny();
        System.out.println(any);
    }

    // allMatch、anyMatch、noneMatch
    @Test
    public void test1(){
        boolean match1 = emps.stream()
                .allMatch(e -> e.getId().toString().startsWith("100"));
        System.out.println("所有ID是个都以100开头: " + match1);

        boolean match2 = emps.stream()
                .allMatch(e -> e.getId().toString().startsWith("1001"));
        System.out.println("所有ID是个都以1001开头: " + match2);
        System.out.println("================");


        boolean match3 = emps.stream()
                .anyMatch(e -> e.getId().toString().startsWith("1001"));
        System.out.println("至少有一个ID是个都以1001开头: " + match3);

        boolean match4 = emps.stream()
                .anyMatch(e -> e.getId().toString().startsWith("200"));
        System.out.println("至少有一个ID是个都以200开头: " + match4);
        System.out.println("================");

        boolean match5 = emps.stream()
                .noneMatch(e -> e.getId().toString().startsWith("200"));
        System.out.println("没有ID是个都以200开头: " + match5);

        boolean match6 = emps.stream()
                .noneMatch(e -> e.getId().toString().startsWith("1002"));
        System.out.println("没有ID是个都以1002开头: " + match6);

    }

}
