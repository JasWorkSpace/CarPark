package com.greenorange.gooutdoor.Util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/7/29.
 */
public class ActivityUtils {

    public static int getDefaultActivityInAnimation(){
        return R.anim.activity_slide_right_in;
    }
    public static int getDefaultActivityOutAnimation(){
        return R.anim.activity_slide_left_out;
    }
    public static void setActivityAnimation(Activity activity){
        setActivityAnimation(activity, getDefaultActivityInAnimation(), getDefaultActivityOutAnimation());
    }
    public static void setActivityAnimation(Activity activity, int in, int out){
        activity.overridePendingTransition(in, out);
    }
    public static void startActivityWithAnimation(Activity activity, Class<?> cls){
        startActivityWitchAnimation(activity, new Intent(activity, cls));
    }
    public static void startActivityWitchAnimation(Activity activity, Intent intent){
        startActivityWitchAnimation(activity, intent, getDefaultActivityInAnimation(), getDefaultActivityOutAnimation());
    }
    public static void startActivityWitchAnimation(Activity activity, Intent intent, int in, int out){
        activity.startActivity(intent);
        setActivityAnimation(activity,in,out);
    }
    public static boolean isDialogFragmentExit(FragmentActivity activity, String tag){
        return activity.getSupportFragmentManager().findFragmentByTag(tag) != null;
    }
}
