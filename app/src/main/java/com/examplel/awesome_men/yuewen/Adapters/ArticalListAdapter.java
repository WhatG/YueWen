package com.examplel.awesome_men.yuewen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.CusViews.CircleImage;
import com.examplel.awesome_men.yuewen.DataClass.Artical;
import com.examplel.awesome_men.yuewen.DataClass.Book;
import com.examplel.awesome_men.yuewen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longer on 2017/4/15.
 */

public class ArticalListAdapter extends BaseAdapter{
    private List<Artical> articalList;
    private Context context;
    private boolean noUserinfo = false;

    public ArticalListAdapter(Context context,List<Artical> data){
        this.context = context;
        this.articalList = data;
    }

    public void setNoUserinfo(){
        this.noUserinfo = true;
    }

    @Override
    public int getCount() {
        return articalList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artical artical  = articalList.get(position);
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.home_artical_item,parent,false);
            holder = new ViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.artical_item_title);
            holder.desc = (TextView)convertView.findViewById(R.id.artical_item_desc);
            holder.userName = (TextView)convertView.findViewById(R.id.artical_item_username);
            holder.time = (TextView)convertView.findViewById(R.id.artical_item_time);
            holder.userIcon = (ImageView)convertView.findViewById(R.id.artical_item_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.setArtical(artical);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private class ViewHolder{
        TextView title;
        TextView userName;
        TextView time;
        TextView desc;
        ImageView userIcon;
        void setArtical(Artical artical){
            this.title.setText(artical.getTitle());
            this.desc.setText(artical.getDesc()==null?artical.getContent():artical.getDesc());
            this.time.setText(artical.getTime());
            if(noUserinfo){
                userName.setVisibility(View.GONE);
                userIcon.setVisibility(View.GONE);
            }else{
                this.userName.setText(artical.getUname()!=null?artical.getUname():artical.getContent());
                Picasso.with(context).load(artical.getUicon()).into(this.userIcon);
            }

        }
    }
}
