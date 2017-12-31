package com.examplel.awesome_men.yuewen.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Created by longer on 2017/4/14.
 */

public class ArticalFragment extends Fragment {
    List<Artical> articals ;
    ArticalListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(articals==null){
           articals = new ArrayList<>();
            HashMap<String,Object> map = new HashMap<>();
            map.put("method","getarticallist");
            HttpUtils.getInstance().httpPost(YueWenApplication.ARTICAL_SERVER_PATH,map,new ArticalHandler());
        }
        if(adapter==null){
            adapter = new ArticalListAdapter(getActivity(),articals);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("artical",articals.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        TextView textView = new TextView(getActivity());
        textView.setTextSize(AppUtils.getRawSize(getActivity(),35));
        textView.setTextColor(0xffb5b5b5);
        textView.setText("空");
        listView.setEmptyView(textView);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.artical_frag_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("已分享的文章");
        toolbar.setNavigationIcon(R.drawable.add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditActicalActivity.class);
                startActivityForResult(intent,0x567);
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
        if (requestCode==0x567&&resultCode == RESULT_OK) {
            articals.clear();
            HashMap<String,Object> map = new HashMap<>();
            map.put("method","getarticallist");
            HttpUtils.getInstance().httpPost(YueWenApplication.ARTICAL_SERVER_PATH,map,new ArticalHandler());
            adapter.notifyDataSetChanged();
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
