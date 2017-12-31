package com.examplel.awesome_men.yuewen.Activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Fragments.BookFragment;
import com.examplel.awesome_men.yuewen.Fragments.MyArticalFragment;
import com.examplel.awesome_men.yuewen.R;

/**
 * Created by longer on 2017/5/26.
 */

public class MyDataDisplay extends AppCompatActivity{
    public static final int ARTICAL = 0;
    public static final int Draft = 1;
    public static final int Book = 2;

    private int type = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydata_display);
        Toolbar title = (Toolbar) findViewById(R.id.mydata_disply_title);
        title.setTitleTextColor(0xffffffff);
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataDisplay.this.finish();
            }
        });
        Intent intent = getIntent();
        type = intent.getIntExtra("type",-1);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fts = manager.beginTransaction();
        switch (type){
            case ARTICAL:
                title.setTitle("我的文章");
                fts.add(R.id.mydata_disply_content,new MyArticalFragment());
                break;
            case Draft:
                title.setTitle("我的草稿");
                MyArticalFragment maf = new MyArticalFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isdraft",true);
                maf.setArguments(bundle);
                fts.add(R.id.mydata_disply_content,maf);
                break;
            case Book:
                title.setTitle("我的书籍");
                BookFragment bf = new BookFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("type","mine");
                bf.setArguments(bundle1);
                fts.add(R.id.mydata_disply_content,bf);
                break;
        }
        fts.commit();
    }
}
