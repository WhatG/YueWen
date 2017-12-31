package com.examplel.awesome_men.yuewen.ImageZoom;

import java.util.Observable;

/**
 * Created by Administrator on 2016/10/28.
 */
public class ImageZoomState extends Observable {

   private float mZoom = 1.0f;// 控制图片缩放的变量，表示缩放倍数，值越大图像越大

   private float mZoomX = 1.0f;

   private float mZoomY = 1.0f;

   private float mPanX = 0.5f;// 控制图片水平方向移动的变量，值越大图片可视区域的左边界距离图片左边界越远，图像越靠左，值为0.5f时居中

   private float mPanY = 0.5f;// 控制图片水平方向移动的变量，值越大图片可视区域的上边界距离图片上边界越远，图像越靠上，值为0.5f时居中

   private float mMinZoom = 0.25f;

   private float mMaxZoom = 2.0f;

   private boolean insideViewY = false;//当图片缩放后的宽度小于view的宽度时无法x方向拖动

   private boolean insideViewX = false;//当图片缩放后的高度小于view的高度时无法y方向拖动

   private int wayPanXMove = 0;

   private int wayPanYMove = 0;

   public void setWayPanXMove(int way){
      this.wayPanXMove = way;
   }

   public void setWayPanYMove(int way){
      this.wayPanYMove = way;
   }
   public int getWayPanXMove(){
      return this.wayPanXMove;
   }
   public int getWayPanYMove(){
      return this.wayPanYMove;
   }



   void setInsideViewY(boolean insideViewY){
      this.insideViewY = insideViewY;
   }

   void setInsideViewX(boolean insideViewX){
      this.insideViewX = insideViewX;
   }
   boolean getInsideViewX(){
      return this.insideViewX;
   }


   float getmZoom() {

      return mZoom;

   }



   void setmZoom(float mZoom) {

      if (this.mZoom != mZoom) {
         this.mZoom = mZoom > mMaxZoom ? mMaxZoom : mZoom;
         //最大缩放比例。
         if (this.mZoom <= 1.0f) {
            this.mZoom = mZoom < mMinZoom ? mMinZoom : mZoom;//最小缩放比例。
            this.mPanX = 0.5f;
            this.mPanY = 0.5f;

         }
         this.setChanged();
      }

   }

   float getmPanX() {

      return mPanX;

   }


   void setmPanX(float mPanX,boolean changed) {

      if (insideViewX) {// 使图为缩小状态时不能移动
         return;

      }

      if (this.mPanX == mPanX) {
         return;
      }
      float panSub = mPanX-this.mPanX;
      switch (wayPanXMove){
         case 0:
            this.mPanX = mPanX;
            break;
         case 1:
            if(panSub>0){
               this.mPanX = mPanX;
            }
            break;
         case 2:
            if(panSub<0){
               this.mPanX = mPanX;
            }
      }
      if(changed){
         this.setChanged();
      }

   }



   float getmPanY() {

      return mPanY;

   }



   void setmPanY(float mPanY,boolean changed) {

      if(insideViewY) {// 使图为缩小状态时时不能移动
         return;
      }

      if (this.mPanY == mPanY) {
         return;
      }
      float panSub = mPanY-this.mPanY;
      switch (wayPanYMove){
         case 0:
            this.mPanY = mPanY;
            break;
         case 1:
            if(panSub>0){
               this.mPanY = mPanY;
            }
            break;
         case 2:
            if(panSub<0){
               this.mPanY = mPanY;
            }
      }
      if(changed){
         this.setChanged();
      }

   }



   float getZoomX(float aspectQuotient) {
      //return mZoom;
      return Math.min(mZoom, mZoom * aspectQuotient);
      //View的宽高比大于bitmap宽高比时 用zoom乘

   }


   float getZoomY(float aspectQuotient) {
      // return mZoom;
      return Math.min(mZoom, mZoom / aspectQuotient);
      //View的宽高比小于bitmap宽高比时 用Zoom乘

   }

}
