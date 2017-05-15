package com.examplel.awesome_men.yuewen;

import android.app.Application;

/**
 * Created by longer on 2017/5/5.
 */

public class YueWenApplication extends Application{
    public  static String currentUserId = null;
    public static String getCurrentUserId(){
        return currentUserId;
    }
    public static final String ServerAddr = "192.168.1.117:8081";

    public void setCurrentUserId(String userId){
        currentUserId = userId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
