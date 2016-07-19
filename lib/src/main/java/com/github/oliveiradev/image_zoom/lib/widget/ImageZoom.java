package com.github.oliveiradev.image_zoom.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ImageButton;

import com.github.oliveiradev.image_zoom.lib.animation.ZoomAnimation;

/**
 * Created by felipe on 22/04/16.
 */
public class ImageZoom extends ImageButton {


    private Drawable mForegroundDrawable;
    private Context context;

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
    }

    public ImageZoom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
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

    @Override
    public boolean performClick() {
        if (context instanceof ContextThemeWrapper)
            performZoom(((ContextThemeWrapper)context).getBaseContext());
        else
            performZoom(context);
        return super.performClick();
    }


    private void performZoom(Context context){
        ZoomAnimation.zoom(this,(Activity) context);
    }
}
