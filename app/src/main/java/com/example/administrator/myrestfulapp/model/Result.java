package com.example.administrator.myrestfulapp.model;

/**
 * Created by Administrator on 2018/7/16/016.
 */

public class Result {
    public int status;
    public String msg;

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
