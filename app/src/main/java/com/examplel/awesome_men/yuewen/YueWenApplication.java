package com.examplel.awesome_men.yuewen;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import io.rong.imkit.RongIM;

/**
 * Created by longer on 2017/5/5.
 */

public class YueWenApplication extends Application{
    public  static String currentUserId = null;
    public  static String currentUserName = null;
    public  static String currentUserIcon = null;
    public static String getCurrentUserId(){
        return currentUserId;
    }

    public static final String ServerAddr = "http://192.168.1.112:8081";
    public static final String BOOK_SERVER_PATH = ServerAddr+"/book.php";
    public static final String ARTICAL_SERVER_PATH = ServerAddr+"/artical.php";
    public static final String USER_SERVER_PATH = ServerAddr+"/user.php";
    public static final String IMAGE_SERVER_PATH = ServerAddr+"/images.php";
    public static  String RONGIM_USER_TOKEN = null;
    public void setCurrentUserId(String userId){
        currentUserId = userId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        RongIM.init(this);
    }

    public static String getUserIcon(String path){
        return ServerAddr+path;
    }
}
