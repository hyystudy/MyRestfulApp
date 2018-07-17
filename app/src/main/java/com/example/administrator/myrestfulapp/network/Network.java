package com.example.administrator.myrestfulapp.network;

import com.example.administrator.myrestfulapp.network.api.StudentAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/7/15/015.
 */

public class Network {

    public static StudentAPI studentAPI;
    public static String STUDENT_API_BASE_URL = "http://www.hyyfamily.com/data/";
    public static OkHttpClient okHttpClient = new OkHttpClient();
    public static GsonConverterFactory converterFactory = GsonConverterFactory.create();

    public static StudentAPI getStudentAPI(){
        if (studentAPI == null) {
            //通过builder mode 先设置retrofit的各个必要参数
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(STUDENT_API_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            //当retrofit 调用create 方法的时候 通过反射创建对象 同时通过解析方法上注解以及参数上注解
            // 拼接URL 获取请求方式
            studentAPI = retrofit.create(StudentAPI.class);
        }
        return studentAPI;
    }
}
