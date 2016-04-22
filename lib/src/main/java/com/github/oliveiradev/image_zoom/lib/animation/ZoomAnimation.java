package com.github.oliveiradev.image_zoom.lib.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by felipe on 22/04/16.
 */
public final class ZoomAnimation {

    private static Animator mCurrentAnimator;
    private static ImageView mImageZoom;

    public static void zoom(View view , Activity activity){
        zoomImageFromThumb(view,activity);
    }


    private static void zoomImageFromThumb(final View thumbView, Activity activity) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        ContentFrameLayout container = (ContentFrameLayout)activity.findViewById(android.R.id.content);

        mImageZoom = new ImageView(thumbView.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageZoom.setLayoutParams(params);
        mImageZoom.setImageDrawable(((ImageButton)thumbView).getDrawable());

        container.addView(mImageZoom);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        mImageZoom.setVisibility(View.VISIBLE);

        mImageZoom.setPivotX(0f);
        mImageZoom.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(mImageZoom, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(mImageZoom, View.SCALE_Y, startScale, 1f));
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;


        final float startScaleFinal = startScale;
        mImageZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                mImageZoom.setBackgroundColor(0);
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(mImageZoom, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(mImageZoom, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(mImageZoom, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(mImageZoom, View.SCALE_Y, startScaleFinal));
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        mImageZoom.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        mImageZoom.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
