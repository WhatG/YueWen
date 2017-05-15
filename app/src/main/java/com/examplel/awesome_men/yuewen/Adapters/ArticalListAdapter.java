package com.examplel.awesome_men.yuewen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.examplel.awesome_men.yuewen.DataClass.Book;
import com.examplel.awesome_men.yuewen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longer on 2017/4/15.
 */

public class ArticalListAdapter extends BaseAdapter{
    private List<Book> bookList = new ArrayList<>();
    private Context context;

    public ArticalListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_artical_item,(ViewGroup)null);
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
