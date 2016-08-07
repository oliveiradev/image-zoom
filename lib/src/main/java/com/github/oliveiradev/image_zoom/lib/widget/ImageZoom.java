package com.github.oliveiradev.image_zoom.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ImageButton;

import com.github.oliveiradev.image_zoom.lib.R;
import com.github.oliveiradev.image_zoom.lib.animation.ZoomAnimation;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by felipe on 22/04/16.
 */
public class ImageZoom extends ImageButton {


    private Drawable mForegroundDrawable;
    private Context context;
    private boolean landScapeZoom = false;

    private Rect mCachedBounds = new Rect();

    public ImageZoom(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ImageZoom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        processAttributes(context, attrs);
    }

    public ImageZoom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
        processAttributes(context, attrs);
    }

    private void init() {
        setBackgroundColor(0);
        setPadding(0, 0, 0, 0);

        TypedArray a = getContext()
                .obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        mForegroundDrawable = a.getDrawable(0);
        mForegroundDrawable.setCallback(this);
        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mForegroundDrawable.isStateful()) {
            mForegroundDrawable.setState(getDrawableState());
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mForegroundDrawable.setBounds(mCachedBounds);
        mForegroundDrawable.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCachedBounds.set(0, 0, w, h);
    }

    public void setLandScapeZoom(boolean isLandScape) {
        this.landScapeZoom = isLandScape;
    }

    private void processAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageZoom);

        boolean defaultLandScapeRenderValue = array.getBoolean(R.styleable.ImageZoom_landScapeZoom, false);
        setLandScapeZoom(defaultLandScapeRenderValue);

        array.recycle();
    }

    @Override
    public boolean performClick() {
        if (((ContextThemeWrapper) this.context).getBaseContext() instanceof Activity)
            performZoom(((ContextThemeWrapper) this.context).getBaseContext());
        else
            performZoom(context);

        return super.performClick();
    }


    private void performZoom(final Context context) {
        ZoomAnimation.zoom(ImageZoom.this, (Activity) context, landScapeZoom);
    }
}
