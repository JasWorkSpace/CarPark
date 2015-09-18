package com.greenorange.gooutdoor.Service;

import android.content.Context;

import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;

/**
 * Created by JasWorkSpace on 15/4/22.
 */
public class LocationBaidu extends LocationControl {
    public LocationBaidu(Context context){
        super(context);
    }

    @Override
    public LocationDBData getDebugLocationDBData() {
        return null;
    }

    @Override
    public boolean openLocation() {
        return false;
    }

    @Override
    public boolean closeLocation() {
        return false;
    }

    @Override
    public int getLocationMapType() {
        return MAPTYPE.MAPTYPE_BAIDU;
    }
}
