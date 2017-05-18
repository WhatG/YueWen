package com.examplel.awesome_men.yuewen.Activitys;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by longer on 2017/5/3.
 */

public class LoginActivity extends AppCompatActivity{
    private TextInputEditText username_edit;
    private TextInputEditText password_edit;
    private Handler handler;
    private Button login;
    private TextView register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_edit = (TextInputEditText) findViewById(R.id.edit_username);
        password_edit = (TextInputEditText) findViewById(R.id.edit_password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);


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
                                    YueWenApplication.currentUserId = ((JSONObject)(dataJ.getJSONArray("data").get(0))).getString("uid");
                                    Toast.makeText(LoginActivity.this,resultMsg,Toast.LENGTH_SHORT).show();
                                    LoginActivity.this.setResult(RESULT_OK);
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
                HttpUtils utils = HttpUtils.getInstance();
                utils.httpPost("http://192.168.1.117:8081/users/user.php",map,handler);
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
}
