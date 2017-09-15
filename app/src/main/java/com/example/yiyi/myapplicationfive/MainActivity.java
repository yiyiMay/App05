package com.example.yiyi.myapplicationfive;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textview );


        // 使用线程编程步骤:

        // 1、首先实现Java的Runnable接口，并重写run()方法，在run()方法中实现耗时运算。
        //       final Runnable myWorker = new Runnable() {
        //         @Override
        //            public void run() {
        //             耗时运算  } };

        // 2、然后创建Thread对象，将Runnable对象传递给Thread对象
        //       Thread workThread = new Thread(null, myWorker, "WorkThread");

        // 3、最后调用Thread.start()方法启动线程，当run()返回时，该线程即结束
        //        workThread.start();

        // 在线程中每秒产生一个数字，然后通过Hander.sendMessage(Message)
        // 将消息发送给主线程，在主线程Handler.handleMessage()中接收并处理该消息


        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {  // Handler的主要用法是在新线程中发送消息，然后在主线程中处理消息
                textView.setText(msg.arg1+"");
            }
        };


        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while(progress <= 100){
                    Message msg = new Message();
                    msg.arg1 = progress;
                    handler.sendMessage(msg);
                    progress += 5;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }
        };

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });

    }
}
