package com.examplel.awesome_men.yuewen.Activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.examplel.awesome_men.yuewen.R;

/**
 * Created by longer on 2017/6/2.
 */

public class ConversationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        String title =  getIntent().getData().getQueryParameter("title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.conversation_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConversationActivity.this.finish();
            }
        });
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(title);
    }

}