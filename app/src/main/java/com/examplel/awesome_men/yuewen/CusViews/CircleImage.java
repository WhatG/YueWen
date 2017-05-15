package com.examplel.awesome_men.yuewen.CusViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.examplel.awesome_men.yuewen.R;
import com.examplel.awesome_men.yuewen.Utils.AppUtils;

/**
 * Created by longer on 2017/4/16.
 */

public class CircleImage extends View {
    private Context context;
    private String username;
    private Bitmap usericon;
    private int iconSize;
    private Paint mPaint;
    private RectF ovalRect;
    private Rect srcRect;
    private Rect dstRect;
    private static final Xfermode MASK_XFERMODE;
    private Bitmap layerBitmap;

    static{
        PorterDuff.Mode localMode = PorterDuff.Mode.SRC_IN;
        MASK_XFERMODE = new PorterDuffXfermode(localMode);
    }

    public CircleImage(Context context){
        super(context,null);
    }
    public CircleImage(Context context, AttributeSet set){
        super(context,set);
        this.context = context;
        usericon = BitmapFactory.decodeResource(context.getResources(), R.drawable.jay);
        ovalRect = new RectF();
    }

    public void setUserName(String username){
        this.username = username;
        this.invalidate();
    }

    public void setUsericon(Bitmap usericon){
        this.usericon = usericon;
        this.layerBitmap = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            width = usericon.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
//            float textHeight = mBounds.height();
//            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = usericon.getHeight();
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(layerBitmap==null){
            this.drawBitmap();
        }
        canvas.drawBitmap(layerBitmap,srcRect,dstRect,null);
    }

    private void drawBitmap(){
        layerBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(layerBitmap);
        ovalRect.top = 0.0f;
        ovalRect.left = 0.0f;
        ovalRect.bottom = getHeight();
        ovalRect.right = getWidth();
        if(mPaint==null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }

        mPaint.setColor(Color.parseColor("#167772"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        canvas.drawRoundRect(ovalRect,getWidth()/9,getWidth()/9,mPaint);
        mPaint.setXfermode(MASK_XFERMODE);

        srcRect = new Rect(0,0,usericon.getWidth(),usericon.getHeight());
        dstRect = new Rect(0,0,getWidth(),getHeight());

        canvas.drawBitmap(usericon,srcRect,dstRect,mPaint);
    }
}
