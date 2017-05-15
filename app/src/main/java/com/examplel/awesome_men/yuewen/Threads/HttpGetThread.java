package com.examplel.awesome_men.yuewen.Threads;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by longer on 2017/4/13.
 */

public class HttpGetThread extends Thread{
    private String TAG = "HttpGetThread";
    private String urlS = null;
    private Handler handler = null;
    public HttpGetThread(String urlS, Handler handler){
        this.urlS = urlS;
        this.handler = handler;
    }
    @Override
    public void run() {
        URL url;
        HttpURLConnection huc=null;
        try{
            url = new URL(this.urlS);
            huc = (HttpURLConnection)url.openConnection();
            huc.setRequestMethod("GET");
            huc.setConnectTimeout(3000);
            huc.connect();

            Message msg = Message.obtain();
            if(huc.getResponseCode()!=200){
                msg.what = HttpUtils.HTTP_FAILED;
                msg.obj = huc.getResponseCode()+huc.getResponseMessage();
                handler.sendMessage(msg);
                return;
            }
            InputStream in = huc.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            while(in.read(data)!= -1){
                bos.write(data);
            }
            String hmsg = new String(bos.toByteArray());

            msg.obj = hmsg;
            msg.what = HttpUtils.HTTP_SUCCESS;
            handler.sendMessage(msg);
        }catch (MalformedURLException MUE){
            MUE.printStackTrace();
        }catch(IOException IOE){
            IOE.printStackTrace();
        }finally {
            if(huc!=null){
                huc.disconnect();
            }
        }
    }
}
