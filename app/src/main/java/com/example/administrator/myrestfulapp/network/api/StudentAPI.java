package com.example.administrator.myrestfulapp.network.api;

import com.example.administrator.myrestfulapp.model.Result;
import com.example.administrator.myrestfulapp.model.Student;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/7/15/015.
 */

public interface StudentAPI {

    @GET("students/")
    Observable<List<Student>> getAllStudents();

    @GET("students/{id}/")
    Observable<Student> getStudentById(@Path("id") int id);

    @PATCH("students/{id}/")
    Observable<Result> updateStudent(@Path("id") int id, @Body Student student);

}
