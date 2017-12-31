package com.examplel.awesome_men.yuewen.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.DataClass.Artical;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;

/**
 * Created by longer on 2017/5/16.
 */

public class ArticalDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.artical_detail_toolbar);
        toolbar.setTitle("文章详情");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticalDetailActivity.this.finish();
            }
        });



        Intent intent = getIntent();
        Artical artical = intent.getExtras().getParcelable("artical");

        TextView title = (TextView)findViewById(R.id.artical_detail_title);
        title.setText(artical.getTitle());

        TextView uname = (TextView)findViewById(R.id.artical_detail_username);
        uname.setText(artical.getUname());

        TextView date = (TextView)findViewById(R.id.artical_detail_date);
        date.setText(artical.getTime());

        TextView content = (TextView)findViewById(R.id.artical_detail_content);
        content.setText(artical.getContent());
    }
}
