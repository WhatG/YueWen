package com.examplel.awesome_men.yuewen.Activitys;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.examplel.awesome_men.yuewen.CusViews.EditableDialog;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by longer on 2017/5/11.
 */

public class EditActicalActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    private Handler handler;
    private TextView titleView;
    private TextView descView;
    private EditText contentView;
    private boolean titleChanged = false;
    private boolean descChanged = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_actical);


        Toolbar toolbar = (Toolbar) findViewById(R.id.act_editart_toolbar);
        toolbar.setTitle("编辑文章");
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.ic_arrow_down));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditActicalActivity.this.finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.artedit_menu_item_draft:
                        break;
                    case R.id.artedit_menu_item_release:
                        break;
                }
                return true;
            }
        });

        initView();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HttpUtils.HTTP_FAILED:
                        AppUtils.toast(EditActicalActivity.this,"无法连接到服务器");
                        break;
                    case HttpUtils.HTTP_SUCCESS:
                        String dataS = (String)msg.obj;
                        try{
                            JSONObject dataJ = new JSONObject(dataS);
                            int error = dataJ.getInt("error");
                            String resultMsg = dataJ.getString("msg");
                            switch (error){
                                case 0:
                                    YueWenApplication.currentUserId = ((JSONObject)(dataJ.getJSONArray("data").get(0))).getString("id");
                                    AppUtils.toast(EditActicalActivity.this,resultMsg);
                                    break;
                                default:
                                    AppUtils.toast(EditActicalActivity.this,resultMsg);
                                    break;
                            }
                        }catch (JSONException je){
                           AppUtils.toast(EditActicalActivity.this,"解析json数据失败");
                        }
                        break;
                }
            }
        };






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_art_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        AppUtils.toast(EditActicalActivity.this,"点击了menu");
        HashMap<String,Object> map = new HashMap<>();
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.artedit_menu_item_release:
                String title = titleView.getText().toString();
                String desc = descView.getText().toString();
                String content = contentView.getText().toString();
                map.put("method","newartical");
                map.put("title",title);
                map.put("desc",desc);
                map.put("content",content);
                HttpUtils.getInstance().httpPost(YueWenApplication.ServerAddr+"/articals/artical.php",map,handler);
                break;
            case R.id.artedit_menu_item_draft:
                break;
        }
        return false;
    }

    private void initView(){
        titleView = (TextView)findViewById(R.id.activity_artical_title);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(EditActicalActivity.this);
                if(titleChanged){
                    editText.setText(titleView.getText());
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActicalActivity.this);
                alert.setMessage("输入标题");
                alert.setView(editText);
                alert.setNegativeButton("放弃输入",null);
                alert.setPositiveButton("完成输入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText.getText().toString().isEmpty()){
                            return;
                        }
                        titleView.setText(editText.getText());
                        titleChanged = true;
                    }
                });
                alert.show();
            }
        });
        descView = (TextView)findViewById(R.id.activity_artical_desc);
        descView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(EditActicalActivity.this);
                if(descChanged){
                    editText.setText(descView.getText());
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActicalActivity.this);
                alert.setMessage("输入对文章的描述");
                alert.setView(editText);
                alert.setNegativeButton("放弃输入",null);
                alert.setPositiveButton("完成输入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editText.getText().toString().isEmpty()){
                            return;
                        }
                        descView.setText(editText.getText());
                        descChanged = true;
                    }
                });
                alert.show();
            }
        });
        contentView = (EditText) findViewById(R.id.activity_artical_content);
    }
}
