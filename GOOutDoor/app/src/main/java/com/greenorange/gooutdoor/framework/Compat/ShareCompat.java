package com.greenorange.gooutdoor.framework.Compat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.greenorange.gooutdoor.framework.Utils.AndroidUtils;
import com.greenorange.gooutdoor.mode.Share;

/**
 * Created by JasWorkSpace on 15/8/6.
 */
public class ShareCompat extends Share {

    public ShareCompat(Context context) {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {
        Intent intent = getTakeScreenShotIntent();
        if(AndroidUtils.hasSeriice(context, intent)){

        }
        return super.doInBackground(params);
    }
    private Intent getTakeScreenShotIntent(){
        ComponentName componentName = new ComponentName("com.android.systemui","com.android.systemui.TakeScreenshotService");
        return new Intent().setComponent(componentName);
    }
}
