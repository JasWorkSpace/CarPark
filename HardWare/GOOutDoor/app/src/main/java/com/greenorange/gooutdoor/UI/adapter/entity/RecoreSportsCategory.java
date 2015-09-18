package com.greenorange.gooutdoor.UI.adapter.entity;

import android.content.Context;

import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.outdoorhelper.R;


/**
 * Created by JasWorkSpace on 15/9/14.
 */
public class RecoreSportsCategory extends Category{
    private SportsDBData mSportsDBData;
    private String       headString;
    public  SportsDBData getSportsDBData() {
        return mSportsDBData;
    }
    public  RecoreSportsCategory(SportsDBData sportsDBData){
        mSportsDBData = sportsDBData;
    }
    public RecoreSportsCategory(Context context, SportsDBData sportsDBData){
        this(sportsDBData);
        headString = context.getString(R.string.fragment_recordsports_header_text,
                sportsDBData.getSports_time_year(), sportsDBData.getSports_time_month());
        Key = headString.hashCode();//use hashcode for key.
    }
    @Override
    public String getHeader() {
        return headString;
    }

    @Override
    public SportsDBData getItem() {
        return mSportsDBData;
    }
}
