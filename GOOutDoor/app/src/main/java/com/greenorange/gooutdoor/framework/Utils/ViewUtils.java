package com.greenorange.gooutdoor.framework.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.framework.widget.dialogs.util.TypefaceHelper;
import com.greenorange.outdoorhelper.R;

import java.io.File;
import java.io.FileOutputStream;

public class ViewUtils {
    private static int width;
    private static int height;

    public static void init(Context context) {
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public static int getScreenWidth() {
        return width;
    };

    public static int getScreenHeight() {
        return height;
    };

    /**
     * change dip value to pixel value for certain screen size
     * 
     * @param dipValue
     * @param context
     * @return
     */
    public static int getPixels(int dipValue, Context context) {
        int result = 0;
        Resources res = context.getResources();
        result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, res.getDisplayMetrics());
        return result;
    }

    /**
     * change View to Bitmap Object
     * 
     * @param v
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    /**
     * compute sample size you want
     * 
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * compute bitmap initial sample size
     * 
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    public static int getStatusBarHeight(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
    public static Bitmap takeScreenShot(Activity activity){
        View view  = activity.getWindow().getDecorView();
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width, height-statusBarHeight);
        view.destroyDrawingCache();
        return bitmap;
    }
    public static Bitmap getBitmapFromView(View view){
        if(view != null){
            try{
                int width = view.getWidth();
                int height = view.getHeight();
                if(width>0 && height>0){
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    view.layout(0, 0, width, height);
                    view.draw(canvas);
                    return bitmap;
                }
            }catch (Throwable e){}
        }
        return null;
    }
    public static Bitmap createWaterMakerBitmap(Bitmap bitmap, Bitmap watermaker){
        return createWaterMakerBitmap(bitmap, watermaker, null);
    }
    public static Bitmap createWaterMakerBitmap(Bitmap bitmap, Bitmap watermaker, String title){
        Context context = GOApplication.getInstance();
        return createWaterMakerBitmap(bitmap, watermaker, title,
                TypefaceHelper.get(context, context.getString(R.string.config_default_font)));
    }
    public static Bitmap createWaterMakerBitmap(Bitmap bitmap, Bitmap watermaker, String title, Typeface typeface){
        if(!TextUtils.isEmpty(title)) {
            return createWaterMakerBitmap(bitmap, watermaker, title, getDefaultPaint(), typeface);
        }
        return createWaterMakerBitmap(bitmap, watermaker, null, null, null);
    }
    public static Bitmap createWaterMakerBitmap(Bitmap bitmap, Bitmap watermaker, String title, Paint paint,Typeface typeface){
        if(bitmap == null
                || (watermaker == null && TextUtils.isEmpty(title))) return bitmap;//nothing need todohere
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if(width>0 && height>0) {
            try {
                Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas    = new Canvas(newBitmap);
                canvas.drawBitmap(bitmap, 0, 0, null);//draw src
                if(watermaker != null){
                    canvas.drawBitmap(watermaker, 0, 0, null);
                }
                if(!TextUtils.isEmpty(title)){
                    Paint mpaint = paint;
                    if(mpaint == null){
                        mpaint = getDefaultPaint();
                    }
                    if(typeface != null){
                        mpaint.setTypeface(typeface);
                    }
                    mpaint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText(title, width/3, height*3/4, mpaint);
                }
                canvas.save(Canvas.ALL_SAVE_FLAG);
                canvas.restore();
                return newBitmap;
            }catch (Throwable e){}
        }
        return null;
    }
    public static Paint getDefaultPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffff6600"));// #141,141,141
        paint.setTextSize(100);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
        return paint;
    }
    public static boolean savePicture(Bitmap bitmap, String file){
        if(bitmap == null)return false;
        FileOutputStream fos = null;
        try {
            File localfile = new File(file);
            if(localfile.exists()){localfile.delete();}
            fos = new FileOutputStream(localfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return true;
        } catch (Throwable e) {}finally {
            if(fos != null) try {fos.close();}catch(Throwable e){}
        }
        return false;
    }
    public static void sharePicture(Context context, Uri file){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.goapplication_app_name));
        intent.putExtra(Intent.EXTRA_STREAM, file);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.toolbar_share)));
    }
    public static void hideWindow(Activity activity){
        Window win = activity.getWindow();
        WindowManager.LayoutParams wl = win.getAttributes();
        wl.buttonBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
        wl.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN |WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        wl.systemUiVisibility   = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        win.setAttributes(wl);
    }
    public static void showWindow(Activity activity){
        Window win = activity.getWindow();
        WindowManager.LayoutParams wl = win.getAttributes();
        wl.systemUiVisibility  &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        wl.systemUiVisibility  &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        win.setAttributes(wl);
    }
}
