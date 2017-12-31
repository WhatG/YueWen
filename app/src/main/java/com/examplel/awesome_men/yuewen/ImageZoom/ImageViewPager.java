package com.examplel.awesome_men.yuewen.ImageZoom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ImageViewPager extends ViewPager {
   private ImageZoomState currentZoomState;
   private float lastX;

   public ImageViewPager(Context context) {
      super(context);
   }
   public ImageViewPager(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      return  super.onTouchEvent(event);
   }

   @Override
   public boolean onInterceptTouchEvent(MotionEvent event) {
      if(currentZoomState==null){
         return super.onInterceptTouchEvent(event);
      }
      float x = event.getX();
      float dx = 0;
      int pointCount = event.getPointerCount();
      if(pointCount > 1||event.getPointerId(0)!=0){
         return false;
      }
      switch (event.getAction()){
         case MotionEvent.ACTION_DOWN:
            lastX = x;
         case MotionEvent.ACTION_MOVE:
            dx = x - lastX;
            lastX = x;
            if(currentZoomState.getmZoom()<1.0f){
               return false;
            }
            if(currentZoomState.getInsideViewX()){
               return super.onInterceptTouchEvent(event);
            }
            if(currentZoomState.getWayPanXMove()==ImageZoomView.PAN_NORMAL){
               return false;
            }
            else if(currentZoomState.getWayPanXMove()==ImageZoomView.PAN_ONLY_SUB){
               if(dx>0)
                  return false;
               return super.onInterceptTouchEvent(event);
            }
            else if(currentZoomState.getWayPanXMove() == ImageZoomView.PAN_ONLY_PLUS)
               if(dx<0)
                  return false;
            return super.onInterceptTouchEvent(event);
         case MotionEvent.ACTION_UP:
            if(!currentZoomState.getInsideViewX()){
               return super.onInterceptTouchEvent(event);
            }
      }
      return super.onInterceptTouchEvent(event);

   }

   public void setCurrentImageState(ImageZoomState zoomState) {
      currentZoomState = zoomState;
   }
}