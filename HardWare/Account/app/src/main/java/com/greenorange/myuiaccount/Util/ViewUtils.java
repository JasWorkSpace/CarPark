package com.greenorange.myuiaccount.Util;

import android.content.Context;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.greenorange.myuiaccount.R;

/**
 * Created by JasWorkSpace on 15/10/20.
 */
public class ViewUtils {

    public static void showErrorOnEditView(Context context, EditText view, String message){
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
        if(!TextUtils.isEmpty(message))view.setError(message);
        view.selectAll();
        view.requestFocus();
    }
}
