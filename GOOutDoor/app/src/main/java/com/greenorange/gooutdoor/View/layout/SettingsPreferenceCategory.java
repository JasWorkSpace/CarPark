package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/8/7.
 */
public class SettingsPreferenceCategory extends LinearLayout {
    private TextView mTitleView;

    private String mTitleView_text = "";
    public SettingsPreferenceCategory(Context context) {
        this(context, null);
    }
    public SettingsPreferenceCategory(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SettingsPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_settings_preference_category, this);
        loadAttributeSet(context, attrs);
    }
    private void loadAttributeSet(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.settings_preference_category);
        int textid = typedArray.getResourceId(R.styleable.settings_preference_category_category_title, 0);
        mTitleView_text = textid>0 ? context.getString(textid) : "";
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleView = (TextView) findViewById(R.id.preferencecategory_titleview);
        updateAllUI();
    }

    private void updateAllUI(){
        mTitleView.setText(mTitleView_text);
    }
}
