package com.greenorange.gooutdoor.Util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by JasWorkSpace on 15/7/30.
 */
public class AnimatorUtils {
    private final static int LAG_BETWEEN_ITEMS = 20;
    public static android.animation.ObjectAnimator getObjectAnimator(View view, int dur, boolean show){
        return getObjectAnimator(view, dur,
                getAlaphaPropertyValuesHolder(view, show ? 0 : 1, show ? 1 : 0),
                getScaleXPropertyValuesHolder(view, show ? 0 : 1, show ? 1 : 0),
                getScaleYPropertyValuesHolder(view, show ? 0 : 1, show ? 1 : 0));
    }
    public static android.animation.ObjectAnimator getObjectAnimator(View view, int dur, PropertyValuesHolder... propertyValuesHolders){
        return getObjectAnimator(view, dur, getOpenTimeInterpolator(), propertyValuesHolders);
    }
    public static android.animation.ObjectAnimator getObjectAnimator(View view, int dur, TimeInterpolator timeInterpolator,PropertyValuesHolder... propertyValuesHolders){
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolders);
        animation.setDuration(dur);
        if(timeInterpolator != null)animation.setInterpolator(timeInterpolator);
        return animation;
    }
    public static TimeInterpolator getOpenTimeInterpolator(){
        return new OvershootInterpolator(0.9f);
    }
    public static TimeInterpolator getCloseTimeInterpolator(){
        return new AccelerateDecelerateInterpolator();
    }
    public static PropertyValuesHolder getAlaphaPropertyValuesHolder(View view, float start, float end){
        view.setAlpha(start);
        return PropertyValuesHolder.ofFloat(View.ALPHA, end);
    }
    public static PropertyValuesHolder getScaleXPropertyValuesHolder(View view, float start, float end){
        view.setScaleX(start);
        return PropertyValuesHolder.ofFloat(View.SCALE_X, end);
    }
    public static PropertyValuesHolder getScaleYPropertyValuesHolder(View view, float start, float end){
        view.setScaleY(start);
        return PropertyValuesHolder.ofFloat(View.SCALE_Y, end);
    }
    public static PropertyValuesHolder getRotationPropertyValuesHolder(View view, float start, float end){
        view.setRotation(start);
        return PropertyValuesHolder.ofFloat(View.ROTATION, end);
    }
    public static void startAnimation(Animator.AnimatorListener animatorListener, Animator... animators){
        if(animators == null)return;
        for(int i= (animators.length-1); i >= 0; i--){
            Animator animator = animators[i];
            if(animatorListener != null)animator.addListener(animatorListener);
            animator.setStartDelay(i * LAG_BETWEEN_ITEMS);
            animator.start();
        }
    }
    public static void startScaleAnimation(final boolean show, final int dur, View... views){
        if(views != null){
            for(int i = (views.length-1); i >= 0; i--){
                final View view = views[i];
                Animator animator = getObjectAnimator(views[i], dur, show);
                animator.setStartDelay(i * LAG_BETWEEN_ITEMS);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        if(show){
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                    }
                });
                animator.start();
            }
        }
    }


}
