package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public class MapDataInfo1 extends LinearLayout implements View.OnClickListener {

    public final static int ID_CLICK_ROTATION = 1;
    public final static int ID_CLICK_CAMERA   = 2;

    private ImageView mSportsTypeImageView;
    private TextView  mSportsStateTextView;
    public MapDataInfo1(Context context) {
        this(context, null);
    }
    public MapDataInfo1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MapDataInfo1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_mapdatainfo, this);
        setBackgroundResource(R.color.toolbar_bg_color_1);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSportsStateTextView = (TextView)  findViewById(R.id.sporttextview);
        mSportsTypeImageView = (ImageView) findViewById(R.id.sporttype);
        findViewById(R.id.rotation).setOnClickListener(this);
        findViewById(R.id.camera).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rotation:{
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_MapDataInfo,
                        v, ID_CLICK_ROTATION));
            }break;
            case R.id.camera:{
                Util.postEvent(Util.produceEventClick(EventID.ID_CLICK_MapDataInfo,
                        v, ID_CLICK_CAMERA));
            }break;
        }
    }
    public void setSportTypeAndState(int type, int state){
        if(SportUtil.isSportsType(type)){
            mSportsTypeImageView.setBackgroundResource(SportUtil.getSportsTypeIcon(type));
            mSportsStateTextView.setText(SportUtil.getSportsTypeSportingLab(type));
        }
    }
}
