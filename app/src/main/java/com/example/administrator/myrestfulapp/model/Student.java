package com.example.administrator.myrestfulapp.model;

/**
 * Created by Administrator on 2018/7/15/015.
 */

public class Student {
    public int ID;
    public String name;
    public boolean sex;
    public int age;
    public String address;

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

}
