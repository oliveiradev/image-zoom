package com.github.oliveiradev.image_zoom.lib.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.util.Pair;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by felipe on 22/04/16.
 */
public final class ZoomAnimation {

    private static final int ROTATION_DELAY = 500;
    private static Animator mCurrentAnimator;
    private static ImageView mImageZoom;
    private static boolean isLandScapeScreen;
    private static ContentFrameLayout container;

    public static void zoom(View view, Activity activity, boolean isLandScape) {
        zoomImageFromThumb(view, activity, isLandScape);
    }

    private static void zoomImageFromThumb(final View thumbView, final Activity activity, boolean isLandScape) {
        isLandScapeScreen = isLandScape;

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        container = (ContentFrameLayout) activity.findViewById(android.R.id.content);

        mImageZoom = new ImageView(thumbView.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageZoom.setLayoutParams(params);
        mImageZoom.setImageDrawable(((ImageButton) thumbView).getDrawable());

        container.addView(mImageZoom);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        final float startScale;
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

        final Pair<Rect, Rect> bounds = new Pair<>(startBounds, finalBounds);
        thumbView.setAlpha(0f);

        final float startScaleFinal = startScale;

        zoomIn(mImageZoom, bounds, startScale);

        mImageZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut(thumbView, (ImageView) v, bounds, startScaleFinal);
            }
        });

    }


    private static void zoomIn(final ImageView imageZoom, Pair<Rect, Rect> bounds, float startScale) {
        if (isLandScapeScreen)
            rotate(imageZoom, 90);

        imageZoom.setVisibility(View.VISIBLE);

        imageZoom.setPivotX(0f);
        imageZoom.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imageZoom, View.X, bounds.first.left,
                        bounds.second.left))
                .with(ObjectAnimator.ofFloat(imageZoom, View.Y, bounds.first.top,
                        bounds.second.top))
                .with(ObjectAnimator.ofFloat(imageZoom, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(imageZoom, View.SCALE_Y, startScale, 1f));
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
    }

    private static void zoomOut(final View thumb, final ImageView imageZoom, final Pair<Rect, Rect> bounds, final float scale) {
        if (isLandScapeScreen)
            rotate(imageZoom, 0);

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        imageZoom.setBackgroundColor(0);
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imageZoom, View.X, bounds.first.left))
                .with(ObjectAnimator.ofFloat(imageZoom, View.Y, bounds.first.top))
                .with(ObjectAnimator
                        .ofFloat(imageZoom, View.SCALE_X, scale))
                .with(ObjectAnimator
                        .ofFloat(imageZoom, View.SCALE_Y, scale));
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thumb.setAlpha(1f);
                imageZoom.setVisibility(View.GONE);
                mCurrentAnimator = null;
                removeView(container.getChildCount() - 1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                thumb.setAlpha(1f);
                imageZoom.setVisibility(View.GONE);
                mCurrentAnimator = null;
                removeView(container.getChildCount() - 1);
            }
        });
        set.start();
        mCurrentAnimator = set;
    }

    private static void rotate(ImageView imageView, float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(ROTATION_DELAY);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }

    private static void removeView(int index){
        container.removeViewAt(index);
    }
}
