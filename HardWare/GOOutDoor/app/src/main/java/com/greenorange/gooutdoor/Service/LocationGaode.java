package com.greenorange.gooutdoor.Service;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;

/**
 * Created by JasWorkSpace on 15/4/18.
 */
public class LocationGaode extends LocationControl implements AMapLocationListener {

    public LocationGaode(Context context){
        super(context);
    }

    @Override
    public LocationDBData getDebugLocationDBData() {
        LocationDBData locationDBData = new LocationDBData(new AMapLocation("gaodedebug"));
        locationDBData.setLatitude(GOConfig.DEFAULT_LOCATION_LATITUDE);
        locationDBData.setLongitude(GOConfig.DEFAULT_LOCATION_LONGITUDE);
        return locationDBData;
    }

    @Override
    public boolean openLocation() {
        super.openLocation();
        if(mLocationManagerProxy == null){
            mLocationManagerProxy = LocationManagerProxy.getInstance(mContext);
            mLocationManagerProxy.setGpsEnable(true);
            mLocationManagerProxy.requestLocationData(LocationManagerProxy.GPS_PROVIDER,
                    mLocationConfig.frq, mLocationConfig.mindistance, this);
            isOpen = true;
        }
        return isOpen;
    }

    @Override
    public boolean closeLocation() {
        super.closeLocation();
        if(mLocationManagerProxy != null){
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
            mLocationManagerProxy = null;
            isOpen = false;
        }
        return isOpen;
    }

    @Override
    public int getLocationMapType() {
        return MAPTYPE.MAPTYPE_GAODE;
    }

    /////////////////////
    private LocationManagerProxy mLocationManagerProxy;

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(isOpen() && mLocationManagerProxy != null){
            notifityLocationChange(new LocationDBData(aMapLocation));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
