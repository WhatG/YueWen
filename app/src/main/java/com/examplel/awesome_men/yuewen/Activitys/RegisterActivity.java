package com.examplel.awesome_men.yuewen.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import static com.examplel.awesome_men.yuewen.Utils.HttpUtils.HTTP_FAILED;
import static com.examplel.awesome_men.yuewen.Utils.HttpUtils.HTTP_SUCCESS;

/**
 * Created by longer on 2017/5/5.
 */

public class RegisterActivity extends AppCompatActivity{
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText username = (EditText) findViewById(R.id.register_input_name);
        final EditText password = (EditText) findViewById(R.id.register_input_password);
        final EditText confirm = (EditText) findViewById(R.id.register_input_confirm);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HTTP_FAILED:
                        Toast.makeText(RegisterActivity.this,"无法连接到服务器",Toast.LENGTH_SHORT).show();
                        break;
                    case HTTP_SUCCESS:
                        String dataS = (String)msg.obj;
                        try{
                            JSONObject dataJ = new JSONObject(dataS);
                            int error = dataJ.getInt("error");
                            String resultMsg = dataJ.getString("msg");
                            switch (error){
                                case 0:
                                    Toast.makeText(RegisterActivity.this,resultMsg,Toast.LENGTH_SHORT).show();
                                    RegisterActivity.this.finish();
                                    break;
                                default:
                                    Toast.makeText(RegisterActivity.this,resultMsg,Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }catch (JSONException je){
                            Toast.makeText(RegisterActivity.this,"解析JSON失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        Button register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map = new HashMap<String, Object>();
                String name = username.getText().toString();
                String pw = password.getText().toString();
                String pwc = confirm.getText().toString();
                if(name.equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入你的用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.equals("")){
                    Toast.makeText(RegisterActivity.this,"请设置登录密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pw.equals(pwc)){
                    Toast.makeText(RegisterActivity.this,"登录密码与确认密码不一致",Toast.LENGTH_SHORT).show();
                    confirm.setText("");
                    password.setText("");
                    return;
                }
                map.put("method","register");
                map.put("name",name);
                map.put("password",pwc);

                HttpUtils utils = HttpUtils.getInstance();
                utils.httpPost("http://192.168.1.117:8081/users/user.php",map,handler);
            }
        });
    }
}
