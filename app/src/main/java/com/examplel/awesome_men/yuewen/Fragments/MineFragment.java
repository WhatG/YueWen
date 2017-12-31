package com.examplel.awesome_men.yuewen.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Activitys.EditActicalActivity;
import com.examplel.awesome_men.yuewen.Activitys.MyDataDisplay;
import com.examplel.awesome_men.yuewen.Activitys.UserIconPickAct;
import com.examplel.awesome_men.yuewen.CusViews.CircleImage;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by longer on 2017/4/18.
 */

public class MineFragment extends Fragment{
    Handler handler = null;
    ImageView circle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case HttpUtils.FILE_NOT_FOUND:
                        Log.d("UploadFile","未找到文件");
                        break;
                    case HttpUtils.FILE_UPLOAD_FAILED:
                        AppUtils.toast(getActivity(),"文件上传失败");
                        break;
                    case HttpUtils.FILE_UPLOAD_SUCCESS:
                       AppUtils.toast(getActivity(),"文件上传成功");
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,container,false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.mine_myartical:
                        Intent intent1 = new Intent(getActivity(), MyDataDisplay.class);
                        intent1.putExtra("type",MyDataDisplay.ARTICAL);
                        startActivity(intent1);
                        break;
                    case R.id.mine_mydraft:
                        Intent intent2 = new Intent(getActivity(), MyDataDisplay.class);
                        intent2.putExtra("type",MyDataDisplay.Draft);
                        startActivity(intent2);
                        break;
                    case R.id.mine_usericon:
                        Intent intent = new Intent(getActivity(), UserIconPickAct.class);
                        startActivityForResult(intent,AppUtils.ACTIVITY_PICK_IMAGE);
                        break;
                    case R.id.mine_mybook:
                        Intent intent4 = new Intent(getActivity(), MyDataDisplay.class);
                        intent4.putExtra("type",MyDataDisplay.Book);
                        startActivity(intent4);
                        break;

                }
            }
        };
        TextView uname = (TextView)view.findViewById(R.id.mine_username);
        uname.setText(YueWenApplication.currentUserName);

        View myartical = view.findViewById(R.id.mine_myartical);
        myartical.setOnClickListener(listener);
//        myartical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getActivity(), MyDataDisplay.class);
//                startActivity(intent1)
//            }
//        });

        View mydraft = view.findViewById(R.id.mine_mydraft);
        mydraft.setOnClickListener(listener);
//
        circle = (ImageView) view.findViewById(R.id.mine_usericon);
        Picasso.with(getActivity()).load(YueWenApplication.currentUserIcon).into(circle);
        circle.setOnClickListener(listener);
//        circle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent,AppUtils.ACTIVITY_PICK_IMAGE);
//            }
//        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==AppUtils.ACTIVITY_PICK_IMAGE)
            {
                String uri = data.getStringExtra("url");
                Picasso.with(getActivity()).load(uri).into(circle);
                HashMap<String,Object> map = new HashMap<>();
                map.put("method","usericon");
                map.put("uid",YueWenApplication.getCurrentUserId());
                map.put("url",uri);
                HttpUtils.getInstance().httpPost(YueWenApplication.USER_SERVER_PATH,map,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case HttpUtils.HTTP_FAILED:
                                AppUtils.toast(getActivity(),"上传失败");
                                break;
                            case HttpUtils.HTTP_SUCCESS:
                                String dataS = (String)msg.obj;
                                try{
                                    JSONObject dataJ = new JSONObject(dataS);
                                    int error = dataJ.getInt("error");
                                    String resultMsg = dataJ.getString("msg");
                                    switch (error){
                                        case 0:
                                            YueWenApplication.currentUserIcon = ((JSONObject)dataJ.getJSONArray("data").get(0)).getString("uicon");
                                            AppUtils.toast(getActivity(),resultMsg);
                                            break;
                                        default:
                                            AppUtils.toast(getActivity(),resultMsg);
                                            break;
                                    }
                                }catch (JSONException je){
                                    AppUtils.toast(getActivity(),"解析json数据失败");
                                }
                                break;
                        }
                    }
                });
            }

        }
    }
}
