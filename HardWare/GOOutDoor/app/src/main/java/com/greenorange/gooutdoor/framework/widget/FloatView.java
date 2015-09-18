package com.greenorange.gooutdoor.framework.widget;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import com.greenorange.gooutdoor.entity.SmileData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class FloatView extends RelativeLayout {
	public static final String IMAGE_BODY = "<body style='\"'margin: 0px; padding: 0px; text-align:center;'\"'><img src='\"'{0}'\"' width='\"'{1}'dp\"' height='\"'{2}'dp\"'/></body>";
	//	public static final String IMAGE_BODY = "<body style='\"'margin: 0px; padding: 0px; text-align:center;'\"'><div style=\"background-image:{0} width='\"'{1}'dp\"' height='\"'{2}'dp\"'></div></body>";
	public static final String REDIRECT_URI = "REDIRECT_URI";

	public static final String HIDE_BORDER = "<style>* { -webkit-tap-highlight-color: rgba(0,0,0,0);} img {width:100%;height:100%}</style>";
	//	public static final String HIDE_BORDER = "<style>* { -webkit-tap-highlight-color: rgba(0,0,0,0) }</style>";

	private   boolean       mAnimation;
	private   SmileData     mResponse;
	private   WebSettings   mWebSettings;
	private   Context       mContext = null;
	private   WebView       mfirstWebView;
	private   static Method mWebView_SetLayerType;
	private   static Field  mWebView_LAYER_TYPE_SOFTWARE;

	public FloatView(final Context context, final SmileData response){
		this(context, response, true);
	}

	public FloatView(final Context context, final SmileData response, final boolean animation) {
		super(context);
		mResponse = response;
		mContext = context;
		this.mAnimation = animation;
	}

	private WebView createWebView(final Context context) {
//		final WebView webView = new WebView(context) {
//			@Override
//			public void draw(final Canvas canvas) {
//				if (this.getWidth() > 0 && this.getHeight() > 0)
//					super.draw(canvas);
//			}
//		};
		WebView webView = new WebView(context);
		webView.setBackgroundColor(Color.TRANSPARENT);
		mWebSettings = webView.getSettings();
		mWebSettings.setJavaScriptEnabled(true);
		setLayer(webView);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(final WebView view,
					final String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		return webView;
	}

	private void doOpenUrl(final String url) {
		notifyAdClicked();
		if ((url.startsWith("http://") || url.startsWith("https://"))) {
			if(url.endsWith(".mp4")){
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setDataAndType(Uri.parse(url), "video/mp4");
				getContext().startActivity(i);
			}
		} 
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		getContext().startActivity(intent);
	}

	static {
		initCompatibility();
	};

	private static void initCompatibility() {
		try {
			for(Method m:WebView.class.getMethods()){
				if(m.getName().equals("setLayerType")){
					mWebView_SetLayerType = m;
					break;
				}
			}
			mWebView_LAYER_TYPE_SOFTWARE = WebView.class.getField("LAYER_TYPE_SOFTWARE");
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
	}

	private void setLayer(WebView webView){
		if (mWebView_SetLayerType != null && mWebView_LAYER_TYPE_SOFTWARE !=null) {
			try {
				mWebView_SetLayerType.invoke(webView, mWebView_LAYER_TYPE_SOFTWARE.getInt(WebView.class), null);
			} catch (InvocationTargetException ite) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
	}
	
	private void buildBannerView(){
		if(mfirstWebView != null){
			//mfirstWebView.startAnimation(AnimationUtils.GetExitTranslateAnimation(mTranslateAnimationType));
			FloatView.this.removeView(mfirstWebView);
			mfirstWebView = null;
		}
		mfirstWebView = createWebView(mContext);
		setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT
				,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		final LayoutParams params = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(mfirstWebView, params);
	}
	
	public void Show(){
		Show(mResponse);
	}
	public void Show(final SmileData ad) {
		if(ad == null)return;
        mResponse  = ad; 
        //if(AdSDKManagerCompat.isAviableResponse(mResponse)){
        	initCompatibility();
        	buildBannerView();
        	showContent();
        //}
	}

	private void notifyAdClicked() {
		Log.d("PMP","notifyAdClicked");
		//AdSDKManagerCompat.reportClick(mResponse);
	}
	private void notifyLoadAdSucceeded() {
		Log.d("PMP","notifyLoadAdSucceeded");
		//AdSDKManagerCompat.reportImp(mResponse);
	}
	private void showContent() {
		  try {
			{
//					String text = mResponse.GetCreative_res_url();
//					text = Uri.encode(Const.HIDE_BORDER + text);
//					mfirstWebView.loadData(text, "text/html", Const.ENCODING);

            	  String text = MessageFormat.format(IMAGE_BODY,mResponse.getUrl() ,null,null);
        		  text = Uri.encode(HIDE_BORDER + text);
        		  mfirstWebView.loadData(text, "text/html","UTF-8");
			  notifyLoadAdSucceeded();
			}
            if(mAnimation){
            	//mfirstWebView.startAnimation(AnimationUtils.GetTranslateAnimation(mTranslateAnimationType));
            }
		} catch (Throwable t) {}
	}

}
