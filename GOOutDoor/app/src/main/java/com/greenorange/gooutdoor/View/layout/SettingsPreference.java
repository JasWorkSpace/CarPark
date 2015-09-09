package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.greenorange.outdoorhelper.R;


/**
 * Created by JasWorkSpace on 15/8/7.
 */
public class SettingsPreference extends LinearLayout implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

    private ImageView mIconImageView;
    private TextView  mTitleTextView;
    private Switch    mSwitch;
    private String    mKey;

    private int     mIconImageView_id   = -1;
    private String  mTitleTextView_text = "";
    private boolean mSwitch_enable      = false;

    private boolean mSwitchChecked = false;
    public SettingsPreference(Context context) {
        this(context, null);
    }
    public SettingsPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SettingsPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_settings_preference, this);
        loadAttributeSet(context, attrs);
    }
    private void loadAttributeSet(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.settings_preference);
        mIconImageView_id = typedArray.getResourceId(R.styleable.settings_preference_preference_icon, -1);
        int textid     = typedArray.getResourceId(R.styleable.settings_preference_preference_title, -1);
        mTitleTextView_text   = textid > 0 ? context.getString(textid) : "";
        mSwitch_enable = typedArray.getBoolean(R.styleable.settings_preference_preference_switch, false);
        mKey           = typedArray.getString(R.styleable.settings_preference_preference_key);
        if(TextUtils.isEmpty(mKey))throw new IllegalStateException("SettingsPreference why key is null !!!!!");
        typedArray.recycle();
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIconImageView = (ImageView) findViewById(R.id.preference_icon);
        mTitleTextView = (TextView)  findViewById(R.id.preference_titleview);
        mSwitch        = (Switch)    findViewById(R.id.preference_switch);
        mSwitch.setOnCheckedChangeListener(this);
        findViewById(R.id.preference_root).setOnClickListener(this);
        updateAllUI();
    }
    private void updateAllUI(){
        mIconImageView.setImageResource(mIconImageView_id);//it will throw exception when id < 0
        mTitleTextView.setText(mTitleTextView_text);
        mSwitch.setVisibility(mSwitch_enable ? VISIBLE : GONE);
        interChangeSwitchState(mSwitchChecked);
    }
    private boolean switchchangeInter = false;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(switchchangeInter)return;
        if(mChangedListener != null){
            if(!mChangedListener.onCheckedChanged(SettingsPreference.this, buttonView, isChecked)){
                interChangeSwitchState(isChecked ? false : true);
            }
        }
    }
    private synchronized void interChangeSwitchState(boolean newstate){
        switchchangeInter = true;
        mSwitch.setChecked(newstate);
        switchchangeInter = false;
    }
    private ChangedListener   mChangedListener;
    private TreeClickListener mTreeClickListener;

    public boolean setonChangedListener(ChangedListener listener){
        mChangedListener = listener;
        return true;
    }
    public boolean setonTreeClickListener(TreeClickListener listener){
        mTreeClickListener = listener;
        return true;
    }
    public boolean removeAllListener(){
        mChangedListener   = null;
        mTreeClickListener = null;
        return true;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.preference_root:{
                TreeClick();
            }break;
        }
    }
    private void TreeClick(){
        if(mTreeClickListener != null && mTreeClickListener.onTreeClick(SettingsPreference.this))return;
        if(mSwitch_enable)mSwitch.toggle();//
    }
    public interface ChangedListener{
        public boolean onCheckedChanged(SettingsPreference settingsPreference, CompoundButton buttonView, boolean isChecked);
    }
    public interface TreeClickListener{
        public boolean onTreeClick(SettingsPreference settingsPreference);
    }
    public void setSwitchChecked(boolean ischecked){
        interChangeSwitchState(ischecked);
    }
    public boolean getSwitchChecked(){
        return mSwitch.isChecked();
    }
    public String  getKey(){return mKey;}

    @Override
    public void setOnClickListener(OnClickListener l) {
        //make sure its no use
    }
}
