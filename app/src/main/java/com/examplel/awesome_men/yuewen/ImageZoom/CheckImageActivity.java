package com.examplel.awesome_men.yuewen.ImageZoom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CheckImageActivity extends Activity {
   private ImageViewPager viewPager;
   private CirclePointBar pointBar;
   public View currentView;
   private Handler handler;
   //private Bitmap[] bitmaps;
   private ImagePagerAdapter pagerAdapter;
   private String[] bitmapUrls;
   private ImageZoomState[] zoomStates;
   private final int LOAD_IMAGE_SUCCESS = 0;
   private final int LOAD_IMAGE_FIALED = -1;
   private final int LOAD_IMAGE_CANCEL = 1;
   @Override
   protected void onCreate(Bundle bundle) {
      super.onCreate(bundle);
      setContentView(R.layout.check_image_actiivity);

      Intent intent = this.getIntent();
      bitmapUrls = intent.getStringArrayExtra("imageUrls");
//      bitmaps = new Bitmap[bitmapUrls.length];
      zoomStates = new ImageZoomState[bitmapUrls.length];

      handler = new Handler(){
         @Override
         public void handleMessage(Message msg){
            switch(msg.what){
               case LOAD_IMAGE_SUCCESS:
                  //updatePageAfterLoaded(msg.arg1);
                  break;

               case LOAD_IMAGE_CANCEL:
                  break;

               case LOAD_IMAGE_FIALED:
//                  currentView.findViewById(R.id.image_loading_bar).setVisibility(View.GONE);
//                  currentView.findViewById(R.id.image_zoom_view).setVisibility(View.GONE);
//                  (currentView.findViewById(R.id.image_load_failed)).setVisibility(View.VISIBLE);
                  pagerAdapter.notifyDataSetChanged();
                  break;
            }
         }
      };


      viewPager = (ImageViewPager) findViewById(R.id.image_check_pager);
      viewPager.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            CheckImageActivity.this.finish();
         }
      });

      pointBar = (CirclePointBar) findViewById(R.id.image_check_bottom_point);
      pointBar.setPointRadius(5);
      pointBar.setPointCount(bitmapUrls.length);
      pointBar.setSelectedPoint(0);
      pointBar.invalidate();

      pagerAdapter = new ImagePagerAdapter(this,bitmapUrls);
      viewPager.setAdapter(pagerAdapter);
      viewPager.setCurrentItem(0,false);
      viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         }
         @Override
         public void onPageSelected(int position) {
            pointBar.setSelectedPoint(position);

         }

         @Override
         public void onPageScrollStateChanged(int state) {
            if(state == ViewPager.SCROLL_STATE_DRAGGING){

            }
         }
      });
   }
   class ImagePagerAdapter extends PagerAdapter {
      private String[] images;
      private LayoutInflater inflater;
      private Handler handler;
      private boolean firstInstantiate = true;



      public ImagePagerAdapter(Context context, String[] images){
         this.images = images;
         inflater = LayoutInflater.from(context);
         //this.handler = handler;
      }


      @Override
      public int getCount() {
         return images.length;
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
         return view == object;
      }

      @Override
      public Object instantiateItem(ViewGroup container,final int position) {

         Log.d("instantiateItem",""+position);
         final View view = inflater.inflate(R.layout.image_check_pager_item,container,false);
         final ImageZoomView zoomView = (ImageZoomView) view.findViewById(R.id.image_zoom_view);
         view.setTag(bitmapUrls[position]);
         Picasso.with(CheckImageActivity.this).load(bitmapUrls[position]).resize(1024,1024).centerInside().centerCrop().into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

               Log.d("instantiateItem","加载一张图片"+position);
               if(zoomStates[position]==null){
                  ImageZoomState zoomState = new ImageZoomState();
                  zoomStates[position] = zoomState;
               }
               zoomView.setImage(bitmap);
               viewPager.setCurrentImageState(zoomStates[position]);
               SimpleImageZoomListener zoomListener = new SimpleImageZoomListener();
               zoomListener.setZoomState(zoomStates[position]);
               zoomView.setImageZoomState(zoomStates[position]);
               zoomView.setOnTouchListener(zoomListener);
               view.findViewById(R.id.image_loading_bar).setVisibility(View.GONE);
               view.invalidate();

            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
               view.findViewById(R.id.image_load_failed).setVisibility(View.VISIBLE);
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
         });

         zoomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CheckImageActivity.this.finish();
            }
         });
         container.addView(view);
         return view;
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object){
         container.removeView(container.findViewWithTag(bitmapUrls[position]));
      }

      @Override
      public int getItemPosition(Object object) {
         return super.getItemPosition(object);
      }

      @Override
      public void startUpdate(ViewGroup container) {
         super.startUpdate(container);
      }



   }
   public void finishActivity(){
      this.finish();
   }

}
