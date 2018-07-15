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
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(STUDENT_API_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            studentAPI = retrofit.create(StudentAPI.class);
        }
        return studentAPI;
    }
}
