package com.examplel.awesome_men.yuewen.ImageZoom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.examplel.awesome_men.yuewen.Utils.AppUtils;

/**
 * Created by Administrator on 2016/10/31.
 */
public class CirclePointBar extends View {
   private String TAG = "CirclePointBar";
   private Paint paint = new Paint();
   boolean selected = false;
   private Context context;
   private int pointCount = 4;//小圆点的数量。
   private int pointRadius = 6;//小原点的半径 单位是半径。
   private int pointColor = Color.WHITE;//被选中时的颜色。
   private int pointColorUnSelect = Color.GRAY;//为被选中时的颜色。
   private int selectedPoint = 0;//被选中的点。
   private int pointDx;//点与点之间的距离。
   public CirclePointBar(Context context){
      this(context,null);

   }
   public CirclePointBar(Context context, AttributeSet set){
      this(context,set,0);

   }
   public CirclePointBar(Context context, AttributeSet set, int style){
      super(context,set,style);
      this.setBackgroundColor(Color.TRANSPARENT);
      this.context = context;
   }
   @Override
   protected void onDraw(Canvas canvas){
      if(pointCount == 0){
         Log.e(TAG,"");
      }
      pointDx = pointRadius*4;
      paint.setColor(pointColorUnSelect);
      paint.setAntiAlias(true);
      float x = pointRadius;
      float y = pointRadius;
      for(int i = 0; i<pointCount; i++){
         if(i == selectedPoint){
            paint.setColor(pointColor);
         }
         else{
            paint.setColor(pointColorUnSelect);
         }
         canvas.drawCircle(x,y,pointRadius,paint);
         x+= pointDx;
      }
      super.onDraw(canvas);

   }

   @Override
   protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      super.onLayout(changed, left, top, right, bottom);
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      int widthMode = MeasureSpec.getMode(widthMeasureSpec);
      int widthSize = MeasureSpec.getSize(widthMeasureSpec);
      int heightMode = MeasureSpec.getMode(heightMeasureSpec);
      int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      int width;
      int height;
      if(widthMode == MeasureSpec.EXACTLY){
         width = widthSize;
      }
      else{
         width = pointRadius*2*pointCount+(pointCount-1)*pointDx;
      }

      if(heightMode == MeasureSpec.EXACTLY){
         height = heightSize;
      }
      else{
         height = pointRadius*2;
      }
      setMeasuredDimension(width,height);
   }

   public void setPointCount(int pointCount){
      if(pointCount>9||pointCount<1){
         return;
      }
      this.pointCount = pointCount;
      this.invalidate();
   }
   public void setSelectedPoint(int pointNum){
      if(pointNum>pointCount){
         Log.e(TAG,"setSelectedPoint:pointNum>PointCount");
         return;
      }
      this.selectedPoint = pointNum;
      this.invalidate();
   }
   public void setPointRadius(int radiusDIP){
      if(radiusDIP<1){
         Log.e(TAG,"invalid radiusDIP");
         return;
      }
      this.pointRadius = AppUtils.getRawSize(context,radiusDIP);
      this.invalidate();
   }

}
