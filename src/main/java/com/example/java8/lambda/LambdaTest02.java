package com.example.java8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Lambda表达式练习
 *
 *
 */
public class LambdaTest02 {

    List<Employee> emps = Arrays.asList(
            new Employee(1001, "AA", 20, 2000.22),
            new Employee(1002, "CC", 30, 8000.88),
            new Employee(1003, "RR", 20, 5000.55),
            new Employee(1004, "BB", 60, 4000.44),
            new Employee(1005, "DD", 50, 1000.11),
            new Employee(1006, "FF", 18, 3000.33)
    );

    // 按年龄排序，年龄相同安装姓名排
    @Test
    public void test1(){
        Collections.sort(emps, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });

        emps.forEach(e -> System.out.println(e.toString()));
    }

    // 处理字符串的方法
    public String strHandler(String str, MyFunction1 nf){
        return nf.getNewStr(str);
    }

    @Test
    public void test2(){
        String str = "  aa哈哈哈BB ";

        // 去除首位空格
        System.out.println(strHandler(str, (v) -> v.trim()));

        // 小写转大写
        System.out.println(strHandler(str, String::toUpperCase));
    }

    // 对两个lang类型的数据进行处理
    public void longHandler(Long l1, Long l2, MyFunction2<Long, Long> nf){
        System.out.println(nf.getValue(l1, l2));
    }

    @Test
    public void test3(){
        // Long 相加
        longHandler(1000L, 2000L, (l1, l2) -> l1 + l2);

        // Long 相减
        longHandler(1000L, 2000L, (l1, l2) -> l1 - l2);
    }


}
