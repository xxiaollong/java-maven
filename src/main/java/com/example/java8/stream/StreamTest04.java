package com.example.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 练习：
 *
 */
public class StreamTest04 {

    @Test
    public void test1(){
        User04 u1 = new User04(1, "aa", 21);
        User04 u2 = new User04(2, "bb", 22);
        User04 u3 = new User04(3, "cc", 23);
        User04 u4 = new User04(4, "dd", 24);
        User04 u5 = new User04(6, "ee", 25);

        List<User04> list = Arrays.asList(u1, u2, u3, u4, u5);

        // 1、ID必须为偶数
//        list.stream().filter((e) -> e.getId()%2 == 0).forEach(System.out::println);

        // 2、年龄必须大于23岁
//        list.stream().filter((e) -> {return e.getAge() > 23;}).forEach(System.out::println);

        // 3、用户名转为大写
//        list.stream().map((e) -> {
//            e.setName(e.getName().toUpperCase());
//            return e;
//        }).forEach(System.out::println);

        // 4、用户名倒序
//        list.stream().sorted((e1, e2) -> e2.getName().compareTo(e1.getName())).forEach(System.out::println);

        // 5、只输出1个用户
//        list.stream().limit(1).forEach(System.out::println);
    }

}

class User04{
    private int id;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User04{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public User04(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}