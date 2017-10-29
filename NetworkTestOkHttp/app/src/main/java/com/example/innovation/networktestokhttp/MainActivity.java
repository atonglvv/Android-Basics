package com.example.innovation.networktestokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/*
    *OkHttp 3.0 简单网络请求操作
    *获取 www.qq.com 网络数据
    *@author Ubi.Innovation
    *@since 1.0
 */
public class MainActivity extends AppCompatActivity {

    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.tv_response);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithOkHttp();
            }
        });

    }

    /*
    * 如果是发起一条POST请求，先构建出一个RequestBody对象来存放待提交的参数 如下
    * RequestBody requestBody = new FormBody.Builder().add("username","Innovation").add("password","123456").build();
    * 然后在Request.Builder中调用post()方法，并将requestBody传入
    * Request request = new Request.Builder().url("http://www.qq.com").post(requestBody).build();
    */
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.qq.com").build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
