package com.examplel.awesome_men.yuewen.Utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.examplel.awesome_men.yuewen.Activitys.LoginActivity;
import com.examplel.awesome_men.yuewen.Threads.HttpGetThread;
import com.examplel.awesome_men.yuewen.YueWenApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by longer on 2017/4/13.
 */

public class HttpUtils {
    public static final int HTTP_SUCCESS = 0x01;
    public static final int HTTP_FAILED = 0x02;
    public static final int FILE_NOT_FOUND = 0x03;
    public static final int FILE_UPLOAD_FAILED = 0x04;
    public static final int FILE_UPLOAD_SUCCESS = 0x05;


    private static HttpUtils instance = null;
    private HttpUtils(){
        super();
    }
    public  static HttpUtils getInstance(){
        if(instance==null){
            instance = new HttpUtils();
            return instance;
        }
        return instance;
    }

    private static String getFileType(String filename){
        String[] args = filename.split("\\.");
        String type = args[args.length-1];
        if(type.equals("png")||type.equals("jpeg")||type.equals("jpg")||type.equals("gif")) {
            return "image/" + type;
        }
        return "file/"+type;
    }

    public void httpGet(final String urlS, final HashMap<String,String> arguments, @Nullable final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try{
                    StringBuilder urlBuilder = new StringBuilder(urlS);
                    urlBuilder.append("/");
                    for(HashMap.Entry entry:arguments.entrySet()){
                        urlBuilder.append(entry.getKey()+"="+entry+"&");
                    }
                    urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&"));
                    URL url = new URL(urlS);

                    conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setDoInput(true);
                    conn.connect();

                    if(conn.getResponseCode()!=200){
                        if (handler!=null){
                            handler.sendEmptyMessage(HTTP_FAILED);
                        }
                        return;
                    }

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    StringBuilder string = new StringBuilder();
                    char[] chars = new char[128];
                    int len = -1;
                    if((len = in.read(chars))!=-1){
                        string.append(chars,0,len);
                    }
                    in.close();

                   if(handler!=null){
                       Message msg = Message.obtain();
                       msg.what = HTTP_SUCCESS;
                       msg.obj = string.toString();
                       handler.sendMessage(msg);
                   }

                }catch (MalformedURLException mal){
                    mal.printStackTrace();
                }
                catch (IOException io){
                    if (handler!=null){
                        handler.sendEmptyMessage(HTTP_FAILED);
                    }

                }finally {
                    if(conn!=null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    public void httpPost(final String urlS,final HashMap<String,Object> arguments,@Nullable final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                String postArg;
                try{
                    StringBuilder urlBuilder = new StringBuilder();
                    for(HashMap.Entry entry:arguments.entrySet()){
                        urlBuilder.append(entry.getKey()+"="+entry.getValue()+"&");
                    }
                    urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&"));

                    URL url = new URL(urlS);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.connect();

                    OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                    out.write(urlBuilder.toString());
                    out.flush();
                    out.close();
                    if(conn.getResponseCode()!=200){
                        if(handler!=null){
                            handler.sendEmptyMessage(HTTP_FAILED);
                        }
                        return;
                    }

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    char[] chars = new char[128];
                    StringBuilder builder = new StringBuilder();
                    int len = -1;
                    while((len=in.read(chars))!=-1){
                        builder.append(chars,0,len);
                    }
                    in.close();

                    if(handler!=null){
                        Message msg = Message.obtain();
                        msg.what = HTTP_SUCCESS;
                        msg.obj = builder.toString();
                        handler.sendMessage(msg);
                    }

                }catch (MalformedURLException mal){
                    mal.printStackTrace();
                }catch (IOException io){
                    if (handler!=null){
                        handler.sendEmptyMessage(HTTP_FAILED);
                    }
                }finally {
                    if(conn!=null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    public void uploadFile(final String urls,final String filePath,@Nullable final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String PREFIX = "--",LINE_END = "\r\n";
                final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
                final String contentType = "multipart/form-data";
                final String dataKey = "\"uploadfile\"";
                File file = new File(filePath);
                if(!file.exists()){
                    if(handler!=null){
                        handler.sendEmptyMessage(FILE_NOT_FOUND);
                    }
                    return;
                }
                try{
                    URL url = new URL(urls);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5*1000);
                    conn.setReadTimeout(5*1000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);
                    conn.setRequestProperty("ChatSet","utf-8");
                    conn.setRequestProperty("connection","keep-alive");
                    conn.setRequestProperty("Content-Type",contentType+";boundary="+BOUNDARY);
                    conn.connect();

                    DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    sb.append("Content-Disposition: form-data; name="+dataKey+"; filename=\""+file.getName()+"\""+LINE_END);
                    sb.append("Content-Type: "+getFileType(file.getName())+"; charset=utf-8"+LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());

                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while((len=is.read(bytes))!=-1)
                    {
                        dos.write(bytes, 0, len);
                    }
                    is.close();
                    dos.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();

                    dos.write(end_data);
                    dos.flush();
                    dos.close();

                    int code = conn.getResponseCode();
                    if(code!=200){
                        if(handler!=null){
                            handler.sendEmptyMessage(FILE_UPLOAD_FAILED);
                        }
                        return;
                    }

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    char[] chars = new char[128];
                    StringBuilder builder = new StringBuilder();
                    int len1 = -1;
                    while((len1=in.read(chars))!=-1){
                        builder.append(chars,0,len1);
                    }
                    in.close();
                    String result = builder.toString();

                    if(handler!=null){
                        handler.sendEmptyMessage(FILE_UPLOAD_SUCCESS);
                    }


                }
                catch(MalformedURLException mal){
                    if(handler!=null){
                        handler.sendEmptyMessage(FILE_UPLOAD_FAILED);
                    }
                }catch (IOException io){
                    if(handler!=null){
                        handler.sendEmptyMessage(FILE_UPLOAD_FAILED);
                    }
                }
            }
        }).start();
    }
}