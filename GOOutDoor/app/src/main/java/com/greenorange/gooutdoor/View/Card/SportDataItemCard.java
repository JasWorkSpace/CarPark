package com.greenorange.gooutdoor.View.Card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class SportDataItemCard extends RelativeLayout {

    public final static int STATE_V1 = 1;
    public final static int STATE_V2 = 2;

    private View     mColorView;
    private TextView mDataTextView;
    private TextView mDataUnitTextView;
    private TextView mTitle;
    private TextView mSubTitle;

    protected String mTitleText      = null;
    protected String mSubTitleText   = null;
    protected String mDataUnitTextView_Text1 = null;
    protected String mDataUnitTextView_Text2 = null;
    protected String mDefaultValueText = null;

    private int mColorViewColor = -1;
    private int mTitleViewColor = -1;
    private int mSubTitleViewColor = -1;

    private int STATE = STATE_V1;//1 or 2

    public int getSTATE() {
        return STATE;
    }
    public void changeSTATE(int state){
        STATE = state;
        if(STATE == STATE_V1){
            setTextView(mDataUnitTextView, mDataUnitTextView_Text1);
            setTextView(mTitle, mTitleText);
            setTextView(mSubTitle, mSubTitleText);
        }else if(STATE == STATE_V2){
            setTextView(mDataUnitTextView, mDataUnitTextView_Text2);
            setTextView(mTitle, mSubTitleText);
            setTextView(mSubTitle, mTitleText);
        }
    }
    private void setValue(String value){
        setTextView(mDataTextView, value);
    }
    private void setTextView(TextView textView, String text){
        if(textView != null){
            if(text != null){
                textView.setText(text);
            }
            textView.setVisibility(text != null ? VISIBLE : GONE);
        }
    }
    private void setTextViewColor(TextView view, int color){
        if(view != null && color != -1){
            view.setTextColor(color);
        }
    }
    private void setColorView(){
        if(mColorView != null){
            if(mColorViewColor != -1){
                mColorView.setBackgroundColor(mColorViewColor);
            }
            mColorView.setVisibility(mColorViewColor != -1 ? VISIBLE : GONE);
        }
    }
    public SportDataItemCard(Context context) {
        super(context);
    }

    public SportDataItemCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public SportDataItemCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public void initAttrs(Context context, AttributeSet attrs){
        if(attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DataItemCard);
        mColorViewColor          = typedArray.getColor(R.styleable.DataItemCard_viewcolor, mColorViewColor);
        mDefaultValueText        = typedArray.getString(R.styleable.DataItemCard_defaultValue);
        mDataUnitTextView_Text1  = typedArray.getString(R.styleable.DataItemCard_unittext1);
        mDataUnitTextView_Text2  = typedArray.getString(R.styleable.DataItemCard_unittext2);
        mTitleViewColor          = typedArray.getColor(R.styleable.DataItemCard_titlecolor, mTitleViewColor);
        mTitleText               = typedArray.getString(R.styleable.DataItemCard_titletext);
        mSubTitleViewColor       = typedArray.getColor(R.styleable.DataItemCard_subtitlecolor, mSubTitleViewColor);
        mSubTitleText            = typedArray.getString(R.styleable.DataItemCard_subtitletext);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mColorView        = findViewById(R.id.colorview);
        mDataTextView     = (TextView) findViewById(R.id.v1_1);
        mDataUnitTextView = (TextView) findViewById(R.id.v1_2);
        mTitle            = (TextView) findViewById(R.id.v2_1);
        mSubTitle         = (TextView) findViewById(R.id.v2_2);
        //initView();
    }
    public void initView(){
        setColorView();
        setTextViewColor(mTitle, mTitleViewColor);
        setTextViewColor(mSubTitle, mSubTitleViewColor);
        setTextView(mDataTextView,mDefaultValueText);
        changeSTATE(STATE_V1);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("DataItemCard{")
                .append("STATE="+STATE)
                .append(", mTitleText="+mTitleText)
                .append(", mSubTitleText="+mSubTitleText)
                .append(", mDataUnitTextView_Text1="+mDataUnitTextView_Text1)
                .append(", mDataUnitTextView_Text2="+mDataUnitTextView_Text2)
                .append(", mDefaultValueText="+mDefaultValueText)
                .append(", mColorViewColor="+mColorViewColor)
                .append(", mTitleViewColor="+mTitleViewColor)
                .append(", mSubTitleViewColor="+mSubTitleViewColor)
                .append("}");
        return sb.toString();
    }

    public void animationShow(){
        loadAnimation(R.anim.card_show);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mAnimation = null;
                SportDataItemCard.this.setVisibility(VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        SportDataItemCard.this.startAnimation(mAnimation);
    }
    public void animationHide(){
        loadAnimation(R.anim.card_hide);
        SportDataItemCard.this.setVisibility(INVISIBLE);
        SportDataItemCard.this.startAnimation(mAnimation);
    }
    private Animation mAnimation;
    private synchronized  void loadAnimation(int anim){
        if(mAnimation != null){
            mAnimation.cancel();
            mAnimation = null;
        }
        mAnimation = AnimationUtils.loadAnimation(getContext(), anim);
    }
}
