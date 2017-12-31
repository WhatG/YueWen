package com.examplel.awesome_men.yuewen.Fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.DataClass.Book;
import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.YueWenApplication;
import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by longer on 2017/5/31.
 */

public class BookDetailFragment extends Fragment{
    Book book;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_detail_fragment,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.book_detail_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        book = getArguments().getParcelable("book");
        if(book==null){
            return view;
        }
        TextView bname = (TextView)view.findViewById(R.id.book_detail_bname);
        bname.setText(book.getBname());
        TextView bauthor = (TextView)view.findViewById(R.id.book_detail_bauthor);
        bauthor.setText(book.getBauthor());
        TextView brep = (TextView)view.findViewById(R.id.book_detail_republic);
        brep.setText(book.getBrepublic());
        TextView buname = (TextView)view.findViewById(R.id.book_detail_uname);
        buname.setText(book.getUname());
        TextView date = (TextView)view.findViewById(R.id.book_detail_btime);
        date.setText(book.getTime());
        TextView desc = (TextView)view.findViewById(R.id.book_detail_desc);
        desc.setText(book.getBdesc());

        ImageView bimage = (ImageView)view.findViewById(R.id.book_detail_bimage);
        String fimage = book.getImages()==null?"":book.getImages().split(",")[0];
        Picasso.with(getActivity()).load(YueWenApplication.getUserIcon(fimage)).into(bimage);

        Button button = (Button)view.findViewById(R.id.book_detail_getbook);
        String currentUid = YueWenApplication.getCurrentUserId();
        if(book.getUid().equals(YueWenApplication.getCurrentUserId())){
           button.setText("你自己的书籍");
        }
        else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE,book.getUid(),book.getUname());
                }
            });
        }
        return  view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
