package com.example.java8.stream;

import com.example.java8.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream
 *
 * 2、中间操作
 *  筛选与切片
 *      filter：过滤
 *      limit：截取
 *      skip(n)：跳过指定个数元素，返回剩余元素
 *      distinct: 去重
 *
 *  映射
 *      map：映射
 *      flatMap：散列
 *  排序
 *      sorted：自然排序
 *      sorted(Comparator com)：自定义排序
 *
 *  规约
 *      reduce
 *
 */
public class StreamTest02 {

    List<Employee> emps = Arrays.asList(
            new Employee(1001, "AA", 20, 2000.22),
            new Employee(1002, "CC", 30, 8000.88),
            new Employee(1003, "RR", 20, 5000.55),
            new Employee(1004, "BB", 60, 4000.44),
            new Employee(1005, "DD", 50, 1000.11),
            new Employee(1005, "DD", 50, 1000.11),
            new Employee(1005, "DD", 50, 1000.11),
            new Employee(1006, "FF", 18, 3000.33)
    );

    // reduce: 规约
    @Test
    public void test8(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> reduce1 = list.stream().reduce((v1, v2) -> v1 + v2);
        System.out.println(reduce1.orElseGet(null));

        System.out.println("=========");

        Integer reduce2 = list.stream().reduce(0, (v1, v2) -> v1 + v2);
        System.out.println(reduce2);

        System.out.println("==========");
        Optional<Double> reduce3 = emps.stream().map(Employee::getSal).reduce(Double::sum);
        System.out.println("所有员工的工资总和为：" + reduce3.orElseGet(null));



    }

    // sorted: 排序
    @Test
    public void test7(){
        List<String> list = Arrays.asList("DD", "BB", "CC", "AA");
        Stream<String> stream = list.stream().sorted();
        stream.forEach(System.out::println);

        System.out.println("=============");

        Stream<Employee> stream1 = emps.stream().sorted((e1, e2) -> {
            return e1.getAge()-e2.getAge();
        });
        stream1.forEach(System.out::println);
    }

    // map:将元素映射成其他元素
    @Test
    public void test5(){
        List<String> list = Arrays.asList("AA", "BB", "CC", "DD");
        Stream<String> stream = list.stream().map(String::toLowerCase);
        stream.forEach(System.out::println);

        System.out.println("======================");

        Stream<String> stream1 = emps.stream().distinct().map(Employee::getName);
        stream1.forEach(System.out::println);
    }

    // flatMap: 扁平化
    @Test
    public void test6(){
        List<String> list = Arrays.asList("AA", "BB", "CC", "DD");
        Stream<String> stream = list.stream().flatMap(e -> Arrays.stream(e.split("")));
        stream.forEach(System.out::println);

    }


    // filter: 过滤出年龄大于2
    @Test
    public void test1(){
        Stream<Employee> stream = emps.stream().filter((e) -> e.getAge() >= 30);
        stream.forEach((e)-> System.out.println(e.toString()));
    }

    // limit：取前两条数据
    @Test
    public void test2(){
        Stream<Employee> stream = emps.parallelStream().limit(2);
        stream.forEach(System.out::println);
    }

    // skip：跳过前2个元素，返回后边的剩余元素
    @Test
    public void test3(){
        Stream<Employee> stream = emps.stream().skip(2);
        stream.forEach(System.out::println);
    }

    // distinct: 按照姓名去重
    @Test
    public void test4(){
        Stream<Employee> stream = emps.stream().distinct();
        stream.forEach(System.out::println);
    }
}
