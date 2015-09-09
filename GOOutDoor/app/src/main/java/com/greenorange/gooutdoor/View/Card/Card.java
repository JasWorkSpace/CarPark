package com.greenorange.gooutdoor.View.Card;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenorange.outdoorhelper.R;

/**
 * Created by JasWorkSpace on 15/8/14.
 */
public class Card extends RelativeLayout {

    public final static int STATE_V1 = 1;
    public final static int STATE_V2 = 2;


    private View     mColorView;
    private TextView mDataTextView;
    private TextView mDataUnitTextView;
    private TextView mTitle;
    private TextView mSubTitle;
    private TextView mSq;

    protected boolean mCilckable     = false;
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
    public boolean setSTATE(int state){
        if(state == STATE)return false;
        changeSTATE(state);
        return true;
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
    public void setValue(String value){
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
    public Card(Context context) {
        this(context, null);
    }
    public Card(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_card, this);
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
        mCilckable               = typedArray.getBoolean(R.styleable.DataItemCard_cliclable, mCilckable);
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
        mSq               = (TextView) findViewById(R.id.v2_sq);
        initView();
    }
    public void initView(){
        setColorView();
        setTextViewColor(mTitle, mTitleViewColor);
        setTextViewColor(mSubTitle, mSubTitleViewColor);
        setTextView(mDataTextView,mDefaultValueText);
        mSq.setVisibility(TextUtils.isEmpty(mSubTitleText) ? INVISIBLE : VISIBLE);
        changeSTATE(STATE_V1);
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("DataItemCard{")
                .append("id=" + id)
                .append(", STATE="+STATE)
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
    private int id = -1;
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
}
