package com.examplel.awesome_men.yuewen.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Activitys.ArticalDetailActivity;
import com.examplel.awesome_men.yuewen.Activitys.UniversalActivity;
import com.examplel.awesome_men.yuewen.Adapters.ArticalListAdapter;
import com.examplel.awesome_men.yuewen.Adapters.BookListAdapter;
import com.examplel.awesome_men.yuewen.CusViews.CircleImage;
import com.examplel.awesome_men.yuewen.DataClass.Artical;
import com.examplel.awesome_men.yuewen.DataClass.Book;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.Utils.HttpUtils;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by longer on 2017/4/18.
 */

public class BookFragment extends Fragment {
    ArrayList<Book> books = null;
    Handler handler;
    BookListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        books = new ArrayList<Book>();
        HashMap<String,Object> map = new HashMap<>();
       // map.put("uid",YueWenApplication.getCurrentUserId());
        map.put("method","getbooklist");
        if(getArguments()!=null&&getArguments().getString("type",null)!=null){
            map.put("uid",YueWenApplication.getCurrentUserId());
        }
        HttpUtils.getInstance().httpPost(YueWenApplication.BOOK_SERVER_PATH,map,new BookHandler());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.book_fragment,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.book_frag_toolbar);
        toolbar.setTitle("已分享的书籍");
        toolbar.setNavigationIcon(R.drawable.add);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UniversalActivity.class);
                intent.putExtra("fragment","editbook");
                startActivityForResult(intent,0x123);
            }
        });
        adapter = new BookListAdapter(getContext(),books);
        ListView listView = (ListView) view.findViewById(R.id.book_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UniversalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("book",books.get(position));
                bundle.putString("fragment","bookdetail");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private  class BookHandler extends Handler{
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
                        Log.e("BookFragment",(String)msg.obj);
                        switch (error){
                            case 0:
                                JSONArray data = dataJ.getJSONArray("data");
                                for(int i = 0;i<data.length();i++){
                                    Book book = new Book();
                                    book.setContent(data.getJSONObject(i));
                                    books.add(book);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0x123){
            if(resultCode== Activity.RESULT_OK){
                books.clear();
                HashMap<String,Object> map  = new HashMap<>();
                map.put("method","getbooklist");
                if(getArguments()!=null&&getArguments().getString("type",null)!=null){
                    map.put("uid",YueWenApplication.getCurrentUserId());
                }
                HttpUtils.getInstance().httpPost(YueWenApplication.BOOK_SERVER_PATH,map,new BookHandler());

            }
        }
    }
}
