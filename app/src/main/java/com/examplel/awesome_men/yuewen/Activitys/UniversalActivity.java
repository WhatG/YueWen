package com.examplel.awesome_men.yuewen.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.examplel.awesome_men.yuewen.Fragments.BookDetailFragment;
import com.examplel.awesome_men.yuewen.Fragments.EditBookFragment;
import com.examplel.awesome_men.yuewen.MultiImageSlector.MultiImageSelectorActivity;
import com.examplel.awesome_men.yuewen.R;

import java.util.List;

/**
 * Created by longer on 2017/5/27.
 */

public class UniversalActivity extends AppCompatActivity{
    public static final int MULTI_PICK_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_activity);
        Log.i("UniversalActivity","onCreate()");
        Intent intent = getIntent();
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        String fragment = intent.getStringExtra("fragment");
        if(fragment.equals("editbook")){
            fts.replace(R.id.universal_content,new EditBookFragment());
            fts.commit();
        }
        else if(fragment.equals("bookdetail")){
            BookDetailFragment fragment1 = new BookDetailFragment();
            Bundle bundle = intent.getExtras();
            fragment1.setArguments(bundle);
            fts.replace(R.id.universal_content,fragment1);
            fts.commit();

        }
    }
}
