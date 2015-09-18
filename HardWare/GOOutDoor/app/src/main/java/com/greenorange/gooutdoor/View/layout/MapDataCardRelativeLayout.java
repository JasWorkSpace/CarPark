package com.greenorange.gooutdoor.View.layout;

import android.content.Context;
import android.util.AttributeSet;
import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Util.FormatUtils;
import com.greenorange.gooutdoor.Util.Utils;
import com.greenorange.gooutdoor.View.Card.DataItemCard;
import com.greenorange.gooutdoor.View.Card.DataItemSpeedCard;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class MapDataCardRelativeLayout extends AutoUpdateRelativeLayout {
    private DataItemCard      mTimeCard;
    private DataItemCard      mDistanceCard;
    private DataItemSpeedCard mSpeedCard;
    private DataItemCard      mCalorieCard;
    public  MapDataCardRelativeLayout(Context context) {
        super(context);
    }
    public  MapDataCardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public  MapDataCardRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTimeCard     = (DataItemCard) findViewById(R.id.card_time);
        mDistanceCard = (DataItemCard) findViewById(R.id.card_distance);
        mSpeedCard    = (DataItemSpeedCard) findViewById(R.id.card_speed);
        mCalorieCard  = (DataItemCard) findViewById(R.id.card_calorie);
    }
    private long lastUpdateTime = 0;
    public void UpdateViewData(){
        //do nothing here. it should be abstract
        long nowtime = System.currentTimeMillis();
        if(nowtime - lastUpdateTime < 1000)return;
        lastUpdateTime = nowtime;
        UserSportsData userSportsData = Utils.getUserSportsData(getContext());
        if(userSportsData != null){
            long totleTime     = userSportsData.getSportTotalTime();
            long totleDistance = userSportsData.getSportTotleDistance();
            double calorie     = userSportsData.getSportTotleCalorie();
            setTimeCard(totleTime);
            setDistanceCard(totleDistance);
            setCalorie(calorie);
            setSpeedCard(totleTime, totleDistance);
        }
    }
//    @Override
//    public void setVisibility(int visibility) {
//        if(getVisibility() == visibility)return;
//        super.setVisibility(visibility);
//        this.clearAnimation();
//        this.startAnimation(AnimationUtils.loadAnimation(getContext(),
//                visibility == VISIBLE ? R.anim.actionbar_top_in : R.anim.actionbar_top_out));
//    }
    private void setTimeCard(long time){
        mTimeCard.setValue(FormatUtils.getMillSecTime(getContext(),time));
    }
    private void setDistanceCard(long distance){
        mDistanceCard.setValue(FormatUtils.getFloat3to1(getContext(), distance / 1000.0f));
    }
    private void setCalorie(double calorie){
        mCalorieCard.setValue(FormatUtils.getDouble3to1(getContext(), calorie / 1000.0f));
    }
    private void setSpeedCard(long time,long dis){
        mSpeedCard.setValue(time, dis);
    }

}
