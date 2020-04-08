package com.example.java8.lambda;

/**
 * 员工类
 */
public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Double sal;
    private Integer dept;

    public Employee(Integer id, String name, Integer age, Double sal, Integer dept) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sal = sal;
        this.dept = dept;
    }

    public Employee(Integer id, String name, Integer age, Double sal) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sal = sal;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return name != null ? name.equals(employee.name) : employee.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sal=" + sal +
                ", dept=" + dept +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }
}
