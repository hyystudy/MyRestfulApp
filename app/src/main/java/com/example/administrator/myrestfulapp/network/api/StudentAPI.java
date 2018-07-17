package com.example.administrator.myrestfulapp.network.api;

import com.example.administrator.myrestfulapp.model.Result;
import com.example.administrator.myrestfulapp.model.Student;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/7/15/015.
 */

public interface StudentAPI {

    //获取student list
    @GET("students/")
    Observable<List<Student>> getAllStudents();

    //通过id 查询一个student对象
    @GET("students/{id}/")
    Observable<Student> getStudentById(@Path("id") int id);

    //通过id 更新一个student对象 url 为 BASE_URL/students/{id}/
    @PATCH("students/{id}/")
    Observable<Result> updateStudent(@Path("id") int id, @Body Student student);

    //创建一个student对象  @Field注解 要和 @FormUrlEncoded 一块使用
    @FormUrlEncoded
    @POST("students/")
    Observable<ResponseBody> createStudent(@Field("name") String name);

    //通过id 删除一个student
    @DELETE("students/{id}/")
    Observable<ResponseBody> deleteStudentById(@Path("id") int id);

}
