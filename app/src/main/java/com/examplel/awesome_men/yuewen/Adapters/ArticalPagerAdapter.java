package com.examplel.awesome_men.yuewen.Adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.examplel.awesome_men.yuewen.DataClass.Artical;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by longer on 2017/5/11.
 */

public class ArticalPagerAdapter extends PagerAdapter{
    private ArrayList<View> pagers = null;
    private String[] titles = {"推荐","短文","系列","更多"};

    public void setPagers(ArrayList<View> pagers){
        this.pagers = pagers;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = pagers.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagers.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
