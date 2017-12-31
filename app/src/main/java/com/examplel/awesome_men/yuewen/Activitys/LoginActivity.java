package com.examplel.awesome_men.yuewen.Activitys;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Objects;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by longer on 2017/5/3.
 */

public class LoginActivity extends AppCompatActivity{
    private TextInputEditText username_edit;
    private TextInputEditText password_edit;
    private Handler handler;
    private Button login;
    SharedPreferences sp;
    private Handler tokenHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_edit = (TextInputEditText) findViewById(R.id.edit_username);
        password_edit = (TextInputEditText) findViewById(R.id.edit_password);
        login = (Button) findViewById(R.id.login);
        TextView register = (TextView) findViewById(R.id.register);
        ImageView icon = (ImageView)findViewById(R.id.login_usericon);



        sp = getPreferences(MODE_PRIVATE);
        username_edit.setText(sp.getString("uname",""));
        password_edit.setText(sp.getString("upassword",""));
        Picasso.with(this).load(sp.getString("icon",null)).into(icon);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                login.setClickable(true);
                switch(msg.what){
                    case HttpUtils.HTTP_FAILED:
                        Toast.makeText(LoginActivity.this,"无法连接到服务器",Toast.LENGTH_SHORT).show();
                        break;
                    case HttpUtils.HTTP_SUCCESS:
                        String dataS = (String)msg.obj;
                        try{
                            JSONObject dataJ = new JSONObject(dataS);
                            int error = dataJ.getInt("error");
                            String resultMsg = dataJ.getString("msg");
                            switch (error){
                                case 0:
                                    JSONObject jsonObject = (JSONObject) dataJ.getJSONArray("data").get(0);
                                    YueWenApplication.currentUserId = jsonObject.getString("uid");
                                    YueWenApplication.currentUserName = jsonObject.getString("uname");
                                    YueWenApplication.currentUserIcon = jsonObject.getString("uicon");
                                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(YueWenApplication.currentUserId,
                                            YueWenApplication.currentUserName,
                                            Uri.parse(YueWenApplication.currentUserIcon)));
                                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                                    Toast.makeText(LoginActivity.this,resultMsg,Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("uname",username_edit.getText().toString());
                                    editor.putString("upassword",password_edit.getText().toString());
                                    editor.putString("icon",YueWenApplication.currentUserIcon);
                                    editor.apply();

                                    getUserToken(YueWenApplication.getCurrentUserId());
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this,resultMsg,Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }catch (JSONException je){
                            Toast.makeText(LoginActivity.this,"解析JSON失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        tokenHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                login.setClickable(true);
                switch(msg.what){
                    case HttpUtils.HTTP_FAILED:
                        Toast.makeText(LoginActivity.this,"获取用户Token失败",Toast.LENGTH_SHORT).show();
                        break;
                    case HttpUtils.HTTP_SUCCESS:
                        Log.e("Token",(String)msg.obj);
                        String dataS = (String)msg.obj;
                        try{
                            JSONObject dataJ = new JSONObject(dataS);
                            int code = dataJ.getInt("code");
                            if(code==200){
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("usertoken",dataJ.getString("token"));
                                editor.apply();
                                YueWenApplication.RONGIM_USER_TOKEN = dataJ.getString("token");
                                connectToRongIM();
                            }else{
                                AppUtils.toast(LoginActivity.this,"获取用户Token失败");
                                return;
                            }
                        }catch (JSONException je){
                            Toast.makeText(LoginActivity.this,"获取用户Token失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username_edit.getText().toString();
                String password = password_edit.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"请输入登录密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("method","login");
                map.put("name",name);
                map.put("password", password);

                login.setClickable(false);
                HttpUtils.getInstance().httpPost(YueWenApplication.USER_SERVER_PATH,map,handler);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }
    private void getUserToken(String userid){
        HashMap<String,Object> map = new HashMap<>();
        map.put("method","getusertoken");
        map.put("uid",userid);
        HttpUtils.getInstance().httpPost(YueWenApplication.USER_SERVER_PATH,map,tokenHandler);
    }
    private void connectToRongIM(){
        RongIM.connect(YueWenApplication.RONGIM_USER_TOKEN, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                AppUtils.toast(LoginActivity.this,"用户Token过期");
                getUserToken(YueWenApplication.getCurrentUserId());
            }

            @Override
            public void onSuccess(String s) {
                AppUtils.toast(LoginActivity.this,"已连接到聊天服务器");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                AppUtils.toast(LoginActivity.this,"连接聊天服务器失败");
            }
        });
    }
}
