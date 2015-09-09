package com.greenorange.gooutdoor.framework.Model.Interface;

import android.graphics.Bitmap;

/**
 * Created by JasWorkSpace on 15/8/6.
 */
public interface iMapScreenShotListener {

    public boolean onScreenShotSuccess(int mapType, Bitmap bitmap, String screenshotfile);

    public boolean onScreenShotFail(int mapType, String result);
}
