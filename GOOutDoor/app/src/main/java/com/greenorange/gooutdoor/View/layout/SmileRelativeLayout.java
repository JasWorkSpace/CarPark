package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.greenorange.gooutdoor.entity.SmileData;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/8/11.
 */
public class SmileRelativeLayout extends RelativeLayout {
    private WebView   mWebView;
    private SmileData mSmileData;
    public SmileRelativeLayout(Context context) {
        this(context, null);
    }
    public SmileRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SmileRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_smilerelativelayout, this);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWebView = (WebView) findViewById(R.id.webview);
        initWebView(mWebView);
        loadSmileData(mSmileData);
    }
    public void loadSmileData(SmileData smileData){
        if(smileData != null){
            mSmileData = smileData;
            loadUri(smileData.getUrl());
        }
    }
    public void loadUri(String uri){
        if(TextUtils.isEmpty(uri) || mWebView== null)return;
        try{
            mWebView.loadUrl(uri);
            mWebView.setVisibility(VISIBLE);
            return;
        }catch (Throwable e){e.printStackTrace();}
        mWebView.setVisibility(INVISIBLE);
    }
    private void initWebView(WebView webView){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setScrollbarFadingEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }
}
