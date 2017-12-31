package com.examplel.awesome_men.yuewen.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Activitys.UniversalActivity;
import com.examplel.awesome_men.yuewen.CusViews.CustomProgress;
import com.examplel.awesome_men.yuewen.ImageZoom.SingleImageCheckAct;
import com.examplel.awesome_men.yuewen.MultiImageSlector.MultiImageSelector;
import com.examplel.awesome_men.yuewen.MultiImageSlector.MultiImageSelectorActivity;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by longer on 2017/5/27.
 */

public class EditBookFragment extends Fragment {
    LinearLayout linear;
    Button addImage;
    ImageView[] imgvs;
    TextView imgsum;
    ArrayList<String> origin = new ArrayList<>();
    DisplayMetrics displayMetrics ;
    String btext = "0/4";
    Handler handler;
    int successCount = 0;
    EditText bname;
    EditText bauthor;
    EditText brepublic;
    EditText bdesc;
    CustomProgress customProgress;
    HashMap<String,Object> args;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = new HashMap<String, Object>();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case HttpUtils.HTTP_FAILED:
                        AppUtils.toast(getActivity(),"无法连接到服务器");
                        customProgress.dismiss();
                        break;
                    case HttpUtils.HTTP_SUCCESS:
                        customProgress.dismiss();
                        try{
                            JSONObject json = new JSONObject((String)msg.obj);
                            AppUtils.toast(getActivity(),json.getString("msg"));
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }catch (JSONException je){
                            je.printStackTrace();
                            AppUtils.toast(getActivity(),"解析数据失败");
                        }
                        break;
                    case HttpUtils.FILE_NOT_FOUND:
                        customProgress.dismiss();
                        AppUtils.toast(getActivity(),"上传图片失败");
                        break;
                    case HttpUtils.FILE_UPLOAD_FAILED:
                        customProgress.dismiss();
                        AppUtils.toast(getActivity(),"上传图片失败");
                        break;
                    case HttpUtils.FILE_UPLOAD_SUCCESS:
                        customProgress.dismiss();
                        try{
                            JSONObject json = new JSONObject((String)msg.obj);
                            if(json.getInt("error")==0){
                                JSONArray datas = json.getJSONArray("data");
                                JSONObject data = datas.getJSONObject(0);
                                args.put("bimage",data.getString("url"));
                               HttpUtils.getInstance().httpPost(YueWenApplication.BOOK_SERVER_PATH,args,handler);
                            }else{
                                customProgress.dismiss();
                                AppUtils.toast(getActivity(),"上传失败");
                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                            AppUtils.toast(getActivity(),"解析数据失败");

                        }
                        break;
                    default:
                        customProgress.dismiss();
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editbook_fragment,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.editbook_toolbar);
        toolbar.setTitle("发布书籍");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(0x45);
                getActivity().finish();
            }
        });
        displayMetrics = getResources().getDisplayMetrics();

        bname = (EditText)view.findViewById(R.id.editbook_bname);
        bauthor = (EditText)view.findViewById(R.id.editbook_author);
        brepublic = (EditText)view.findViewById(R.id.editbook_repub);
        bdesc = (EditText)view.findViewById(R.id.editbook_desc);

        imgsum = (TextView)view.findViewById(R.id.editbook_imgsum);
        imgsum.setText("0/4");
        addImage = (Button)view.findViewById(R.id.editbook_addimg);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector multiImageSelector = MultiImageSelector.create();
                multiImageSelector.origin(origin);
                multiImageSelector.multi();
                multiImageSelector.count(4);
                if(AppUtils.hasPermission(getActivity())) {
                    multiImageSelector.start(EditBookFragment.this,UniversalActivity.MULTI_PICK_IMAGE);
                    //startActivityForResult(new Intent(getActivity(),MultiImageSelectorActivity.class),UniversalActivity.MULTI_PICK_IMAGE);
                }else{
                    AppUtils.toast(getActivity(), "该应用没有访问存储设备的权限");
                }
            }
        });



        View submit = view.findViewById(R.id.editbook_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bname.getText().toString().isEmpty()){
                    AppUtils.toast(getActivity(),"缺少书名");
                    return;
                }
                args.put("bname",bname.getText().toString());
                if(bauthor.getText().toString().isEmpty()){
                    AppUtils.toast(getActivity(),"必须填写作者名称");
                    return;
                }args.put("bauthor",bauthor.getText().toString());
                if(brepublic.getText().toString().isEmpty()){
                    AppUtils.toast(getActivity(),"必须填写书籍的出版社");
                    return;
                }
                args.put("brepublic",brepublic.getText().toString());
                if(!bdesc.getText().toString().isEmpty()){
                    args.put("bdesc",bdesc.getText().toString());
                }
                args.put("method","addbook");
                args.put("uid",YueWenApplication.currentUserId);
                customProgress = new CustomProgress(getActivity(),R.style.dialog_slider);
                customProgress.setTitle("正在上传");
                customProgress.show();

                if(origin.size()==0){
                    HttpUtils.getInstance().httpPost(YueWenApplication.BOOK_SERVER_PATH,args,handler);
                }else{
                    HttpUtils.getInstance().uploadFile(YueWenApplication.IMAGE_SERVER_PATH,origin,handler);
                }

            }
        });
        linear = (LinearLayout) view.findViewById(R.id.editbook_imgs);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==UniversalActivity.MULTI_PICK_IMAGE){
            if(resultCode== Activity.RESULT_OK){
                final ArrayList<String> imagePaths = (ArrayList<String>)data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                origin = imagePaths;
                imgsum.setText(imagePaths.size()+"/4");
                if(origin.size()==4){
                    addImage.setText("更换图片");
                }
                for(int i = 0;i<imagePaths.size();i++){
                    final String path = imagePaths.get(i);
                    File file = new File(path);
                    int size = displayMetrics.widthPixels/4-10;
                    if(file.exists()){
                        ImageView imageview;
                        if(linear.getChildAt(i)==null){
                            imageview = new ImageView(getActivity());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);
                            if(i<3){
                                params.setMargins(0,0,10,0);
                            }
                            imageview.setLayoutParams(params);
                            imageview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), SingleImageCheckAct.class);
                                    intent.putExtra("imageUrl",(String)v.getTag());
                                    startActivity(intent);
                                }
                            });
                            linear.addView(imageview);
                        }else{
                            imageview = (ImageView)linear.getChildAt(i);
                        }
                        imageview.setTag(String.valueOf("file://"+path));
                        Picasso.with(getActivity()).load("file://"+path).resize(size,size).centerCrop().into(imageview);

                    }else{
                        AppUtils.toast(getActivity(),"图片不可用");
                    }
                }
                for(int i = imagePaths.size();i<linear.getChildCount();i++){
                    ((ImageView)linear.getChildAt(i)).setImageBitmap(null);
                }
            }
        }
    }
}
