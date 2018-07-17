package com.example.administrator.myrestfulapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myrestfulapp.model.Result;
import com.example.administrator.myrestfulapp.model.Student;
import com.example.administrator.myrestfulapp.network.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Student> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get_all_student).setOnClickListener(this);
        findViewById(R.id.get_student_by_id).setOnClickListener(this);
        findViewById(R.id.update_student).setOnClickListener(this);
        findViewById(R.id.create_student).setOnClickListener(this);
        findViewById(R.id.delete_student).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_all_student:
                getAllStudents();
                break;
            case R.id.get_student_by_id:
                getStudentById();
                break;
            case R.id.update_student:
                updateStudent();
                break;
            case R.id.create_student:
                createStudent();
                break;
            case R.id.delete_student:
                deleteStudent();
                break;

        }
    }

    private void deleteStudent() {
        Network.getStudentAPI()
                .deleteStudentById(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Toast.makeText(MainActivity.this, responseBody.string(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createStudent() {
        Network.getStudentAPI()
                .createStudent("贺三伟")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Toast.makeText(MainActivity.this, responseBody.string(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void updateStudent() {
        if (studentList != null && studentList.size() > 0) {
            Student student = studentList.get(0);
            student.name = "测试数据";
            Network.getStudentAPI()
                    .updateStudent(student.ID, student)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Result>() {
                        @Override
                        public void accept(Result result) throws Exception {
                            Log.d("MainActivity", "" + result.toString());
                            //JSONObject jsonObject = new JSONObject(responseBody.string());

                            if (result.status == 200) {
                                Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d("MainActivity", "error--->" + throwable.getMessage());
                        }
                    });
        }
    }

    private void getStudentById() {
        Network.getStudentAPI()
                .getStudentById(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) throws Exception {
                        Toast.makeText(MainActivity.this, student.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "获取学生失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getAllStudents() {
        Network.getStudentAPI()
                .getAllStudents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Student>>() {
                    @Override
                    public void accept(List<Student> students) throws Exception {
                        studentList.clear();
                        studentList.addAll(students);
                        for (Student student : students) {
                            Log.d("MainActivity", student.toString());
                        }
                        Log.d("MainActivity", students.size() + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("MainActivity", throwable.getMessage());
                    }
                });
    }
}
