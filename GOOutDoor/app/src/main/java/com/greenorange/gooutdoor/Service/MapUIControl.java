package com.greenorange.gooutdoor.Service;

import android.graphics.Color;

import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.entity.SportsDetailDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;
import com.greenorange.gooutdoor.framework.Model.Interface.iMapUIControl;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public abstract class MapUIControl implements iMapUIControl {
    public int mLocationType = MAPTYPE.MAPTYPE_UNKNOW;
    protected boolean isBuilded = false;
    protected boolean hasLines  = false;
    ////////////////////////////////////////////
    // for market
    public LocationDBData mFirstLocationData;
    public LocationDBData mCurrentLocationData;
    ///////////////////////////////////////////
    // for
    public List<SportsDetailDBData> mList = new ArrayList<SportsDetailDBData>();
    @Override
    public boolean addLocationDB(LocationDBData locationDBData, int sportsState) {
        if(locationDBData != null){
            if(mLocationType != locationDBData.getMaptype())
                throw new ApplicationException("mLocationType is " + mLocationType + " why add LocationDBData which is " + locationDBData.getMaptype());
            mCurrentLocationData  = new LocationDBData(locationDBData);
            if(mFirstLocationData == null
                    && (SportUtil.isSportingState(sportsState))){
                mFirstLocationData = new LocationDBData(locationDBData);
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean addSportsDetailDBData(SportsDetailDBData sportsDetailDBData){
        return mList.add(sportsDetailDBData);
    }
    @Override
    public int getMapType() {
        return mLocationType;
    }

    @Override
    public Object getLine() {
        return null;
    }

    @Override
    public Object getBuilder() {
        return null;
    }

    @Override
    public boolean hasBuilded() {
        return isBuilded;
    }

    @Override
    public boolean hasLine() {
        return hasLines;
    }
    @Override
    public float getLineWidth() {
        return 30.0f;
    }
    @Override
    public int getLineColor() {
        return Color.BLUE;
    }
}
