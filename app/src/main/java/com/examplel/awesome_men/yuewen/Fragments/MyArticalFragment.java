package com.examplel.awesome_men.yuewen.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.examplel.awesome_men.yuewen.Activitys.ArticalDetailActivity;
import com.examplel.awesome_men.yuewen.Activitys.EditActicalActivity;
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


/**
 * Created by longer on 2017/5/26.
 */

public class MyArticalFragment extends Fragment {
    ArrayList<Artical> myarticals = new ArrayList<Artical>();
    Handler handler;
    ArticalListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
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
                                        myarticals.add(artical);
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
        };
        HashMap<String,Object> map = new HashMap<>();
        map.put("uid", YueWenApplication.getCurrentUserId());
        if(getArguments()==null||!getArguments().getBoolean("isdraft")){
            map.put("method","getarticallist");
        }else{
            map.put("method","getdraftlist");
        }


        HttpUtils.getInstance().httpPost(YueWenApplication.ARTICAL_SERVER_PATH,map,handler);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view,container,false);

        ListView listview = (ListView)view.findViewById(R.id.mydata_list);
        if(adapter==null){
            adapter = new ArticalListAdapter(getActivity(),myarticals);
        }
        adapter.setNoUserinfo();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(getArguments()!=null&&getArguments().getBoolean("isdraft")){
                    intent = new Intent(getActivity(), EditActicalActivity.class);
                }else{
                    intent = new Intent(getActivity(), ArticalDetailActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("artical",myarticals.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
