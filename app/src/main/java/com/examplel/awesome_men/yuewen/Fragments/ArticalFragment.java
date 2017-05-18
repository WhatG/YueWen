package com.examplel.awesome_men.yuewen.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.examplel.awesome_men.yuewen.Activitys.ArticalDetailActivity;
import com.examplel.awesome_men.yuewen.Activitys.EditActicalActivity;
import com.examplel.awesome_men.yuewen.Activitys.LoginActivity;
import com.examplel.awesome_men.yuewen.Adapters.ArticalListAdapter;
import com.examplel.awesome_men.yuewen.DataClass.Artical;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * Created by longer on 2017/4/14.
 */

public class ArticalFragment extends Fragment {
    List<Artical> articals = new ArrayList<>();
    ArticalListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArticalListAdapter(getActivity(),articals);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HashMap<String,Object> map = new HashMap<>();
        map.put("method","getarticallist");
        HttpUtils.getInstance().httpPost(YueWenApplication.ServerAddr+"/articals/artical.php",map,new ArticalHandler());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.artical_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.artical_fragment_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artical.
                Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
                intent.putExtra("title",)
                startActivity(intent);

            }
        });
        View add = view.findViewById(R.id.art_bar_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (YueWenApplication.currentUserId == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,EditActicalActivity.TO_LOGIN);
                }
                else{
                    Intent intent = new Intent(getActivity(), EditActicalActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == EditActicalActivity.TO_LOGIN){
                Intent intent = new Intent(getActivity(),EditActicalActivity.class);
                startActivity(intent);
            }
        }
    }

    private  class ArticalHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HttpUtils.HTTP_FAILED:
                    AppUtils.toast(getActivity(),"无法连接到服务器");
                    break;
                case HttpUtils.HTTP_SUCCESS:
                    String dataS = (String)msg.obj;
                    try{
                        JSONObject dataJ = new JSONObject(dataS);
                        int error = dataJ.getInt("error");
                        String resultMsg = dataJ.getString("msg");
                        switch (error){
                            case 0:
                                JSONArray data = dataJ.getJSONArray("data");
                                for(int i = 0;i<data.length();i++){
                                    Artical artical = new Artical();
                                    artical.setContent(data.getJSONObject(i));
                                    articals.add(artical);
                                }
                                adapter.notifyDataSetChanged();
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
    }
}
