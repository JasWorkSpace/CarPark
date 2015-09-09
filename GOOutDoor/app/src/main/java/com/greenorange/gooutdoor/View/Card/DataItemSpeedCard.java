package com.greenorange.gooutdoor.View.Card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.framework.Dao.Interface.SharePreference;
import com.greenorange.gooutdoor.framework.Utils.SharePerferenceUtils;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class DataItemSpeedCard extends DataItemCard implements View.OnClickListener {
    public DataItemSpeedCard(Context context) {
        super(context);
        initData(context);
    }
    public DataItemSpeedCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }
    public DataItemSpeedCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.layout_map_data_item_card).setOnClickListener(this);
        updateState();
    }
    private void initData(Context context){
        mTitleText    = context.getString(R.string.layout_map_data_item_card_value_v1_2_1);
        mSubTitleText = context.getString(R.string.layout_map_data_item_card_value_v2_2_1);
        mDataUnitTextView_Text1 = context.getString(R.string.layout_map_data_item_card_value_t_2_1);
        mDataUnitTextView_Text2 = context.getString(R.string.layout_map_data_item_card_value_t1_2_1);
    }
    @Override
    public void onClick(View v) {
        if(STATE_V1 == getSTATE()){
            setState(STATE_V2);
        }else if(STATE_V2 == getSTATE()){
            setState(STATE_V1);
        }
        updateState();
    }
    public void setValue(long time, long distance){//distance is m. time is ms
        if(STATE_V1 == getSTATE()){
            float v1 = (time > 0 ? (60.0f*distance/time) : 0.0f);
            setValue(FormatUtils.getFloat3to1(getContext(), v1));
        } else {
            float v2 = (distance > 0 ? (time/distance/60.0f) : 0.0f);
            setValue(FormatUtils.getFloat3to1(getContext(), v2));
        }
    }
    private void setState(int state){
        if(state == STATE_V1 || state == STATE_V2){
            SharePerferenceUtils.setValue(SharePreference.KEY_DATA_ITEM_SPEEDCARD, state);
        }
    }
    private void updateState(){
        int state = (Integer)SharePerferenceUtils.getValue(SharePreference.KEY_DATA_ITEM_SPEEDCARD, STATE_V1);
        if(state != STATE_V1 && state != STATE_V2){
            state = STATE_V1;
            setState(state);
        }
        if(state != getSTATE()){
            changeSTATE(state);
        }
    }
}
