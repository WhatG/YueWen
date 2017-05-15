package com.examplel.awesome_men.yuewen.Fragments;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.examplel.awesome_men.yuewen.Activitys.EditActicalActivity;
import com.examplel.awesome_men.yuewen.Adapters.ArticalListAdapter;
import com.examplel.awesome_men.yuewen.Adapters.ArticalPagerAdapter;
import com.examplel.awesome_men.yuewen.R;

import java.util.ArrayList;

/**
 * Created by longer on 2017/4/14.
 */

public class ArticalFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artical_fragment,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.artical_viewpager);
        ArticalPagerAdapter pagerAdapter = new ArticalPagerAdapter();

        ArrayList<View> pagers= new ArrayList<View>();
        for(int i = 0;i<4;i++){
            ListView listView = new ListView(getContext());
            listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            listView.setAdapter( new ArticalListAdapter(container.getContext()));
            pagers.add(listView);
        }
        pagerAdapter.setPagers(pagers);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.artical_tabLayout);
        tabLayout.setTabTextColors(0xff8a8a8a,0xffffffff);
        tabLayout.setupWithViewPager(viewPager);

        View add = view.findViewById(R.id.art_bar_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditActicalActivity.class);
                startActivity(intent);
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


}
