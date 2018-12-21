package com.bzh.floodview.model.test;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/12/21 14:28
 */
public class Test {
    /**
     * name : 张三
     * age : 20
     */

    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
