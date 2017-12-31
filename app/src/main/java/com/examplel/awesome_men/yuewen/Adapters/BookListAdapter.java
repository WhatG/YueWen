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
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longer on 2017/4/18.
 */

public class BookListAdapter extends BaseAdapter{
    private List<Book> bookList;
    private Context context;

    public BookListAdapter(Context context,ArrayList<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }
    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.home_book_item,parent,false);
            holder.bname = (TextView)convertView.findViewById(R.id.book_name);
            holder.bauthor = (TextView)convertView.findViewById(R.id.book_bauthor);
            holder.brepublic = (TextView)convertView.findViewById(R.id.book_republic);
            holder.bimage = (ImageView)convertView.findViewById(R.id.book_bimage);
            holder.uicon = (ImageView)convertView.findViewById(R.id.book_uicon);
            holder.time = (TextView)convertView.findViewById(R.id.book_btime);
            holder.uname = (TextView)convertView.findViewById(R.id.book_uname);
            holder.desc = (TextView)convertView.findViewById(R.id.book_desc);
            holder.divider = convertView.findViewById(R.id.book_divider);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setBook(bookList.get(position));

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

    class ViewHolder{
        TextView bname;
        TextView bauthor;
        TextView brepublic;
        ImageView bimage;
        ImageView uicon;
        TextView uname;
        TextView desc;
        TextView time;
        View divider;

        void setBook(Book book){
            bname.setText(book.getBname());
            if(book.getBdesc()==null){
                desc.setText("");
                desc.setVisibility(View.INVISIBLE);
                divider.setVisibility(View.INVISIBLE);
            }else{
                desc.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
                desc.setText(book.getBdesc());
            }

            brepublic.setText(book.getTime());
            uname.setText(book.getUname());
            bauthor.setText(book.getBauthor());
            time.setText(book.getTime());
            brepublic.setText(book.getBrepublic());
            if(book.getImages()==null){
                bimage.setVisibility(View.GONE);
            }else{
                bimage.setVisibility(View.VISIBLE);
                String fimage = book.getImages()==null?"":book.getImages().split(",")[0];
                Picasso.with(context).load(YueWenApplication.ServerAddr+fimage).resize(400,400).centerInside().into(bimage);
            }
            Picasso.with(context).load(book.getUicon()).into(uicon);




        }

    }
}
