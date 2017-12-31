package com.examplel.awesome_men.yuewen.ImageZoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/10/28.
 */
public class ImageZoomView extends View implements Observer {

   private Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

   private Rect mRectSrc = new Rect();

   private Rect mRectDst = new Rect();

   private float mAspectQuotient;

   public static int PAN_NORMAL = 0;

   public static int PAN_ONLY_PLUS = 1;

   public static int PAN_ONLY_SUB = 2;

   private float sx = 0;
   private float sy = 0;

   private Bitmap mBitmap;

   private ImageZoomState mZoomState;



   public ImageZoomView(Context context, AttributeSet attrs) {

      super(context, attrs);

   }
   public ImageZoomView(Context context){
      super(context);
   }



   @Override

   public void update(Observable observable, Object data) {

      this.invalidate();

   }

//   @Override
//   public boolean dispatchTouchEvent(MotionEvent event) {
//      float x = event.getX();
//      float y = event.getY();
//      int action = event.getAction();
//      float dX = x-sx;
//      if(event.getPointerCount() == 2){
//         return super.dispatchTouchEvent(event);
//      }
//      switch (action){
//         case MotionEvent.ACTION_DOWN:
//            sx = x;
//            sy = y;
//            if(mZoomState.getInsideViewX()){
//               return false;
//            }
//            if(mZoomState.getWayPanXMove()==ImageZoomView.PAN_NORMAL){
//               return super.dispatchTouchEvent(event);
//            }
//            return super.dispatchTouchEvent(event);
//         case MotionEvent.ACTION_MOVE:
//            if(mZoomState.getInsideViewX()){
//               return false;
//            }
//            if(mZoomState.getWayPanXMove()==ImageZoomView.PAN_NORMAL){
//               getParent().requestDisallowInterceptTouchEvent(false);
//               return super.dispatchTouchEvent(event);
//
//            }
//            else if(mZoomState.getWayPanXMove()==ImageZoomView.PAN_ONLY_SUB){
//               if(dX<0) {
//                  return false;
//               }//当图片右边缘与屏幕右边缘重合时，拦截向左滑动的事件。此时Viewpager
//               else{return true;}
//            }
//            else if(mZoomState.getWayPanXMove() == ImageZoomView.PAN_ONLY_PLUS)
//               if(dX>0){
//                  return false;
//               }else{
//                  return true;
//               }
//
//      }
//      return super.dispatchTouchEvent(event);
//   }

   @Override

   protected void onDraw(Canvas canvas) {

      if (mBitmap != null && mZoomState != null) {

         int viewWidth = this.getWidth();

         int viewHeight = this.getHeight();

         int bitmapWidth = mBitmap.getWidth();

         int bitmapHeight = mBitmap.getHeight();



         float panX = mZoomState.getmPanX();

         float panY = mZoomState.getmPanY();

         float zoomX = mZoomState.getZoomX(mAspectQuotient) * viewWidth

                 / bitmapWidth;// 相当于viewHeight/bitmapHeight*mZoom

         float zoomY = mZoomState.getZoomY(mAspectQuotient) * viewHeight

                 / bitmapHeight;// 相当于viewWidth/bitmapWidth*mZoom



         // Setup source and destination rectangles

         // 这里假定图片的高和宽都大于显示区域的高和宽，如果不是在下面做调整

         mRectSrc.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));

         mRectSrc.top = (int) (panY * bitmapHeight - viewHeight

                 / (zoomY * 2));

         mRectSrc.right = (int) (mRectSrc.left + viewWidth / zoomX);

         mRectSrc.bottom = (int) (mRectSrc.top + viewHeight / zoomY);



         mRectDst.left = this.getLeft();

         mRectDst.top = this.getTop();

         mRectDst.right = this.getRight();

         mRectDst.bottom = this.getBottom();


         // Adjust source rectangle so that it fits within the source image.
         // 如果图片宽或高小于显示区域宽或高（组件大小）或者由于移动或缩放引起的下面条件成立则调整矩形区域边界
         if(bitmapWidth*zoomX>viewWidth){
            mZoomState.setInsideViewX(false);
            if (mRectSrc.left < 0) {
               if(mZoomState.getWayPanXMove()!=PAN_ONLY_PLUS){
                  mZoomState.setWayPanXMove(PAN_ONLY_PLUS);
               }
               mZoomState.setmPanX( (viewWidth-bitmapWidth*zoomX/2)/viewWidth,false);
               mRectSrc.left = 0;
               mRectSrc.right = (int)(viewWidth/zoomX);
            }
            else if (mRectSrc.right > bitmapWidth) {
               if(mZoomState.getWayPanXMove()!=PAN_ONLY_SUB){
                  mZoomState.setWayPanXMove(PAN_ONLY_SUB);
               }
               mZoomState.setmPanX(bitmapWidth*zoomX/2/viewWidth,false);
               mRectSrc.right = bitmapWidth;
               mRectSrc.left = (int)(bitmapWidth - viewWidth / zoomX);
            }
            else{
               if(mZoomState.getmPanX()!=PAN_NORMAL){
                  mZoomState.setWayPanXMove(PAN_NORMAL);
               }
            }

         }
         else{
            mZoomState.setInsideViewX(true);
            if (mRectSrc.left < 0) {


               mRectDst.left += -mRectSrc.left * zoomX;

               mRectSrc.left = 0;



            }

            if (mRectSrc.right > bitmapWidth) {


               mRectDst.right -= (mRectSrc.right - bitmapWidth) * zoomX;

               mRectSrc.right = bitmapWidth;


            }
         }



         if(bitmapHeight*zoomY>viewHeight){
            mZoomState.setInsideViewY(false);
            if (mRectSrc.top < 0) {
               if(mZoomState.getWayPanYMove()!=PAN_ONLY_PLUS){
                  mZoomState.setWayPanYMove(PAN_ONLY_PLUS);
               }
               mZoomState.setmPanY((viewHeight - bitmapHeight*zoomY/2)/viewHeight,false);
               mRectSrc.top = 0;
               mRectSrc.bottom = (int)(viewHeight/zoomY);
            }
            else if (mRectSrc.bottom > bitmapHeight) {
               if(mZoomState.getWayPanYMove()!=PAN_ONLY_SUB){
                  mZoomState.setWayPanYMove(PAN_ONLY_SUB);
               }
               mZoomState.setmPanY(bitmapHeight*zoomY/2/viewHeight,false);
               mRectSrc.bottom = bitmapHeight;
               mRectSrc.top = (int)(bitmapHeight - viewHeight / zoomX);
            }
            else{
               if(mZoomState.getWayPanYMove()!=PAN_NORMAL){
                  mZoomState.setWayPanYMove(PAN_NORMAL);
               }
            }
         }
         else{
            mZoomState.setInsideViewY(true);
            if (mRectSrc.top < 0) {

               mRectDst.top += -mRectSrc.top * zoomY;

               mRectSrc.top = 0;

            }

            if (mRectSrc.bottom > bitmapHeight) {

               mRectDst.bottom -= (mRectSrc.bottom - bitmapHeight) * zoomY;

               mRectSrc.bottom = bitmapHeight;

            }
         }

         // 把bitmap的一部分(就是src所包括的部分)绘制到显示区中dst指定的矩形处.关键就是dst,它确定了bitmap要画的大小跟位置
         // 注：两个矩形中的坐标位置是相对于各自本身的而不是相对于屏幕的。
         canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint);

      }

   }



   @Override

   protected void onLayout(boolean changed, int left, int top, int right,

                           int bottom) {

      super.onLayout(changed, left, top, right, bottom);
      this.calculateAspectQuotient();

   }



   public void setImageZoomState(ImageZoomState zoomState) {

      if (mZoomState != null) {

         mZoomState.deleteObserver(this);

      }

      mZoomState = zoomState;

      mZoomState.addObserver(this);

      invalidate();

   }



   public void setImage(Bitmap bitmap) {
      mBitmap = bitmap;
      this.calculateAspectQuotient();
      invalidate();
   }



   private void calculateAspectQuotient() {

      if (mBitmap != null) {

         mAspectQuotient = (float) (((float) mBitmap.getWidth() / mBitmap

                 .getHeight()) / ((float) this.getWidth() / this.getHeight()));

      }

   }

   @Override
   public boolean dispatchTouchEvent(MotionEvent event) {
      return super.dispatchTouchEvent(event);
   }

}