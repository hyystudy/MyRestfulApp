package com.example.administrator.myrestfulapp.network.api;

import com.example.administrator.myrestfulapp.model.Student;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/7/15/015.
 */

public interface StudentAPI {

    @GET("students/")
    Observable<List<Student>> getAllStudents();

    @GET("students/{id}/")
    Observable<Student> getStudentById(@Path("id") int id);
}
