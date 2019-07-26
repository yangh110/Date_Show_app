package com.example.date_show;

import android.os.Looper;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.lang.String;
import java.net.SocketTimeoutException;


public class Get_Data implements Runnable
{
    //关于udp传输时的信息
    public static final int DEST_PORT = 30000;
    public static final String DEST_IP = "192.145.37.183";
    private static final int DATA_LEN = 4096;
    //对于所需结构的声明
    private DatagramSocket ds = null;
    byte[] inBuff = new byte[DATA_LEN];
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    private DatagramPacket outPacket = null;

    //Handler 函数声明
    private Handler handler;
    public Handler recvHandler;


    public Get_Data(Handler handler)
    {
        this.handler = handler;
    }

    public void run()
    {
        try
        {

            new Thread()
            {
                public void run()
                {
                    try
                    {
                        String content = null;
                        ds = new DatagramSocket( 23333);
                        outPacket = new DatagramPacket(new byte[0] , 0 , InetAddress.getByName(DEST_IP) , DEST_PORT);
                        while (true)
                        {
                            ds.send(outPacket);
                            try
                            {
                                ds.setSoTimeout(1000);
                                ds.receive(inPacket);
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                                continue;
                            }

                            content = new String(inBuff , 0 , inPacket.getLength());
                            //将读到的数据传递进消息
                            Message msg = new Message();
                            msg.what = 0x1;
                            msg.obj = content;
                            //发送消息,进入消息队列
                            handler.sendMessage(msg);
                            Log.d("Get_Data", "接收成功\n" + content);
                            //sleep(10);
                        }

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        if( ds != null )
                        {
                            ds.close();
                        }
                    }

                }
            }.start();

            Looper.prepare();
            Looper.loop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}