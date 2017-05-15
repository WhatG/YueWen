package com.examplel.awesome_men.yuewen.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Adapters.ArticalListAdapter;
import com.examplel.awesome_men.yuewen.Adapters.BookListAdapter;
import com.examplel.awesome_men.yuewen.CusViews.CircleImage;
import com.examplel.awesome_men.yuewen.DataClass.Book;
import com.examplel.awesome_men.yuewen.R;

import java.util.ArrayList;

/**
 * Created by longer on 2017/4/18.
 */

public class BookFragment extends Fragment {
    ArrayList<Book> books1 = null;
    ArrayList<Book> books2 = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.book_fragment,container,false);
        ListView listView = (ListView) view.findViewById(R.id.book_list);
        listView.setAdapter(new BookListAdapter(getContext()));
        return view;
    }
}
