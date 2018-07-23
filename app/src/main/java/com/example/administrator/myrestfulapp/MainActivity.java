package com.example.administrator.myrestfulapp;

import android.support.v4.os.AsyncTaskCompat;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Sink;
import okio.Timeout;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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
        findViewById(R.id.btn_rxjava).setOnClickListener(this);
        findViewById(R.id.btn_okhttp).setOnClickListener(this);
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
            case R.id.btn_rxjava:
                showRxJavaDemo();
                break;
            case R.id.btn_okhttp:
                requestByOkhttp();
                break;

        }
    }

    private void requestByOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        //Toast.makeText(MainActivity.this, "拦截器执行了", Toast.LENGTH_SHORT).show();
//                        Log.d("MainActivity", "intercept: ");
//                        Request request = chain.request();
//
//                        return chain.proceed(request);
//                    }
//                })
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url("http://www.hyyfamily.com/data/students/")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", response.body().string());
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRxJavaDemo() {
        //通过create 创建observable 一般不用
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                    //模拟耗时操作
//                    Thread.sleep(1000);
//                    e.onNext("next1");
//                    Thread.sleep(1000);
//                    e.onNext("next2");
//                    Thread.sleep(1000);
//                    e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//            }
//        });

        
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
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
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
