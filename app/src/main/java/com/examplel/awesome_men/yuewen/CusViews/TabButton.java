package com.examplel.awesome_men.yuewen.CusViews;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.examplel.awesome_men.yuewen.R;

/**
 * Created by longer on 2017/4/14.
 */

public class TabButton extends LinearLayout{
    private String tabName = "书籍";
    private int imagePressId = R.drawable.ic_book;
    private int imageUnPressId = R.drawable.ic_book;
    private ImageView image;
    private TextView textview;
    private Context context;
    private OnTabClickListener listener;

    private  int indexInActivity = -1;

    public TabButton(Context context){
        super(context,null);
    }
    public TabButton(Context context, AttributeSet set){
        super(context,set);
        this.context = context;
        initView();
    }

    public void setIndex(int indexInActivity){
        this.indexInActivity = indexInActivity;
    }
    public int getIndex(){
        return indexInActivity;
    }

    public void setContent(String tabName,int imageId,int imagePressId){
        this.tabName = tabName;
        this.imageUnPressId = imageId;
        this.imagePressId = imagePressId;
        textview.setText(tabName);
        image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),imageId,null));
        this.invalidate();

    }

    private void initView(){
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER);
        textview = new TextView(this.context);
        image = new ImageView(context);
        LayoutParams lp = new LayoutParams(AppUtils.getRawSize(context,25),AppUtils.getRawSize(context,25));
        image.setLayoutParams(lp);
        image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),imageUnPressId,null));
        LayoutParams lp1 = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(lp1);
        textview.setText(tabName);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        textview.setTextColor(0xFF000000);
        this.addView(image);
        this.addView(textview);
        this.invalidate();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabButton)v).setSelected();
                if(listener!=null){
                    listener.onTabClick(indexInActivity);
                }
            }
        });
    }


    public void clearSelected(){
        textview.setTextColor(0xFF000000);
        this.image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),imageUnPressId,null));
        this.invalidate();
    }

    public void setSelected(){
        textview.setTextColor(0xFF3F51B5);
        image.setImageDrawable(ResourcesCompat.getDrawable(getResources(),imagePressId,null));
        textview.invalidate();
    }

    public  interface OnTabClickListener{
        void onTabClick(int index);
    }

    public void setOnTabClickListener(OnTabClickListener listener){
        this.listener = listener;
    }
}
