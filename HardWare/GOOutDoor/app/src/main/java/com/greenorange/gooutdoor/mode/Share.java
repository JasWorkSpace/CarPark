package com.greenorange.gooutdoor.mode;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.greenorange.gooutdoor.framework.Utils.ViewUtils;

import java.io.File;

/**
 * Created by JasWorkSpace on 15/8/6.
 */
public class Share extends AsyncTask<String, Void, String> {
    public Context context;
    public Share(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        if(!isCancelled() && context instanceof Activity){
            String filedir = params[0];
            if(!TextUtils.isEmpty(filedir)){
                return ViewUtils.savePicture(ViewUtils.takeScreenShot((Activity)context)
                        , filedir) ? filedir : null;
            }
            return null;
        }
        return null;
    }
    @Override
    protected void onPostExecute(String bitmapdir) {
        super.onPostExecute(bitmapdir);
        if(!(isCancelled() || TextUtils.isEmpty(bitmapdir))){
            File file = new File(bitmapdir);
            if(file.exists()) {
                ViewUtils.sharePicture(context, Uri.fromFile(file));
                return;
            }
        }
    }
}
