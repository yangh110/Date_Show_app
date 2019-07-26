package com.example.date_show;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    public static final int DEST_PORT = 30000;
    public static final String DEST_IP = "192.145.37.183";
    TextView tv1_0 , tv1_1 , tv1_2 , tv1_3 , tv1_4 , tv1_5;
    TextView tv2_0 , tv2_1 , tv2_2 , tv2_3 , tv2_4 , tv2_5;
    TextView tv3_0 , tv3_1 , tv3_2 , tv3_3 , tv3_4 , tv3_5;
    TextView tv4_0 , tv4_1 , tv4_2 , tv4_3 , tv4_4 , tv4_5;

    private Handler handler;
    Get_Data get_Data;
    String contents = null;
    String alls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tv1_1 = (TextView)findViewById(R.id.tv_1_1);
        tv1_2 = (TextView)findViewById(R.id.tv_1_2);
        tv1_3 = (TextView)findViewById(R.id.tv_1_3);
        tv1_4 = (TextView)findViewById(R.id.tv_1_4);
        tv1_5 = (TextView)findViewById(R.id.tv_1_5);

        tv2_1 = (TextView)findViewById(R.id.tv_2_1);
        tv2_2 = (TextView)findViewById(R.id.tv_2_2);
        tv2_3 = (TextView)findViewById(R.id.tv_2_3);
        tv2_4 = (TextView)findViewById(R.id.tv_2_4);
        tv2_5 = (TextView)findViewById(R.id.tv_2_5);

        tv3_1 = (TextView)findViewById(R.id.tv_3_1);
        tv3_2 = (TextView)findViewById(R.id.tv_3_2);
        tv3_3 = (TextView)findViewById(R.id.tv_3_3);
        tv3_4 = (TextView)findViewById(R.id.tv_3_4);
        tv3_5 = (TextView)findViewById(R.id.tv_3_5);

        tv4_1 = (TextView)findViewById(R.id.tv_4_1);
        tv4_2 = (TextView)findViewById(R.id.tv_4_2);
        tv4_3 = (TextView)findViewById(R.id.tv_4_3);
        tv4_4 = (TextView)findViewById(R.id.tv_4_4);
        tv4_5 = (TextView)findViewById(R.id.tv_4_5);


        Log.d("MainActivity" , "准备handler");
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {

                if( msg.what == 0x1 )
                {
                    //tv1.setText("\n" + msg.obj.toString());
                    contents = msg.obj.toString();
                    Process_String(contents);
                }

            }
        };

        get_Data = new Get_Data(handler);
        new Thread(get_Data).start();
    }


    //字符处理
    public void Process_String(String Alls)
    {
        String [] stringArr= Alls.split(",");

        tv1_1.setText( "一:" + stringArr[0]);
        tv1_2.setText("二:" + stringArr[1]);
        tv1_3.setText("三" + stringArr[2]);
        tv1_4.setText("四:" + stringArr[3]);
        tv1_5.setText(" 五:" + stringArr[4]);
        //Log.d("MainActivity" , stringArr[3]);

        tv2_1.setText( "一:" + stringArr[5]);
        tv2_2.setText("二:" + stringArr[6]);
        tv2_3.setText("三" + stringArr[7]);
        tv2_4.setText("四:" + stringArr[8]);
        tv2_5.setText(" 五:" + stringArr[9]);

        tv3_1.setText( "一:" + stringArr[10]);
        tv3_2.setText("二:" + stringArr[11]);
        tv3_3.setText("三" + stringArr[12]);
        tv3_4.setText("四:" + stringArr[13]);
        tv3_5.setText(" 五:" + stringArr[14]);
        //Log.d("MainActivity" , stringArr[13]);
        tv4_1.setText( "一:" + stringArr[15]);
        tv4_2.setText("二:" + stringArr[16]);
        tv4_3.setText("三" + stringArr[17]);
        tv4_4.setText("四:" + stringArr[18]);
        tv4_5.setText(" 五:" + stringArr[19]);

    }




    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
