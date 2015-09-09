package com.greenorange.gooutdoor.Service;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Utils.SportUtil;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public class MapUIGaode extends MapUIControl {

    public  LatLngBounds.Builder mBuilder    = new LatLngBounds.Builder();
    private PolylineOptions mPolylineOptions = null;
    public  MapUIGaode(){
        mLocationType = MAPTYPE.MAPTYPE_GAODE;
        isBuilded     = false;
    }
    private PolylineOptions getPolylineOptions(){
        if(mPolylineOptions == null){
            mPolylineOptions = new PolylineOptions().width(getLineWidth()).color(getLineColor());
        }
        return mPolylineOptions;
    }
    @Override
    public boolean addLocationDB(LocationDBData locationDBData, int sportsState) {
        if(super.addLocationDB(locationDBData, sportsState)){
            if (SportUtil.isSportingState(sportsState)) {
                LatLng latLng = new LatLng(locationDBData.getLatitude(), locationDBData.getLongitude());
                updateBuilder(latLng);
                updateLineOptions(latLng);
            }
            return true;
        }
        return false;
    }
    private void updateBuilder(LatLng latLng){
        if(latLng != null){
            mBuilder.include(latLng);
            isBuilded = true;
        }
    }
    private void updateLineOptions(LatLng latLng){
        if(latLng != null){
            if(!hasLines && mFirstLocationData != null){//we should add start point.
                mPolylineOptions = getPolylineOptions().add( new LatLng(mFirstLocationData.getLatitude(), mFirstLocationData.getLongitude()) );
            }
            mPolylineOptions = getPolylineOptions().add( latLng );
            hasLines = true;
        }
    }
    @Override
    public Object getLine() {
        return getPolylineOptions();
    }

    @Override
    public Object getBuilder() {
        return mBuilder;
    }
}
