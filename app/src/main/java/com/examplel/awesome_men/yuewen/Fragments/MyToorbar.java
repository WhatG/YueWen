package com.examplel.awesome_men.yuewen.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.examplel.awesome_men.yuewen.Adapters.ArticalListAdapter;
import com.examplel.awesome_men.yuewen.DataClass.Artical;

import java.util.ArrayList;

/**
 * Created by longer on 2017/5/26.
 */

public class MyToorbar extends Fragment{
    ArrayList<Artical> myarticals = new ArrayList<Artical>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listview = new ListView(getActivity());
        ArticalListAdapter adapter = new ArticalListAdapter(getActivity(),myarticals);
        adapter.setNoUserinfo();
        listview.setAdapter(adapter);
        return listview;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
