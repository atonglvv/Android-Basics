package com.example.innovation.handlertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/*
* 简单的Handler更新UI Demo
* @author Ubi.Innovation
* @since 1.0
*
* Remarks:
* 原来是可以在子线程中极限更新UI的
* http://blog.csdn.net/xyh269/article/details/52728861
*/
public class MainActivity extends AppCompatActivity {

    public static final int UPDATE_TEXT = 1;
    TextView textView;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case  UPDATE_TEXT:
                    textView.setText("I'm Handler!!!");
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message); // 将Message对象发送出去
            }
        }).start();
    }


//    Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = UPDATE_TEXT;
//            handler.sendMessage(message); // 将Message对象发送出去
//        }
//    });


}
