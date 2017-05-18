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
import com.examplel.awesome_men.yuewen.CusViews.CircleImage;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by longer on 2017/4/18.
 */

public class MineFragment extends Fragment {
    Handler handler = null;
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
        CircleImage circle = (CircleImage) view.findViewById(R.id.mine_usericon);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,AppUtils.ACTIVITY_PICK_IMAGE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==AppUtils.ACTIVITY_PICK_IMAGE)
            {
                Uri uri = data.getData();
                String filepath = AppUtils.getPath(getActivity(),uri);
                HttpUtils.getInstance().uploadFile("http://192.168.1.117:8081/images.php",filepath,handler);
            }

        }
    }
}
