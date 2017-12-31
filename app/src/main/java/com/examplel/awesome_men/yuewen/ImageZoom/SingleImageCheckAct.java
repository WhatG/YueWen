package com.examplel.awesome_men.yuewen.ImageZoom;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by longer on 2017/5/29.
 */

public class SingleImageCheckAct extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.image_check_pager_item);
        final ImageZoomView zoomView = (ImageZoomView)findViewById(R.id.image_zoom_view);
        final ProgressBar progress = (ProgressBar)findViewById(R.id.image_loading_bar);
        final TextView fail = (TextView)findViewById(R.id.image_load_failed);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.image_check_backfloat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleImageCheckAct.this.finish();
            }
        });
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.with(this).load(imageUrl).resize(1024,1024).centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                zoomView.setImage(bitmap);
                SimpleImageZoomListener zoomListener = new SimpleImageZoomListener();
                ImageZoomState zoomState = new ImageZoomState();
                zoomListener.setZoomState(zoomState);
                zoomView.setImageZoomState(zoomState);
                zoomView.setOnTouchListener(zoomListener);
                progress.setVisibility(View.GONE);

            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                fail.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        zoomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleImageCheckAct.this.finish();
            }
        });


    }
}
