package com.greenorange.gooutdoor.UI.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.greenorange.gooutdoor.View.layout.MapDataInfo;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Model.Event.EventClick;
import com.greenorange.gooutdoor.framework.Model.Event.EventID;
import com.greenorange.gooutdoor.framework.Model.Interface.iMapScreenShotListener;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.Service.MapUIControl;
import com.greenorange.gooutdoor.View.layout.MapDataCardRelativeLayout;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Model.Event.EventMSG;
import com.greenorange.gooutdoor.framework.Model.Event.EventStateChange;
import com.squareup.otto.Subscribe;

/**
 * Created by JasWorkSpace on 15/3/19.
 */
public class FragmentGaodeMap extends BaseMapFragment implements View.OnClickListener, AMap.OnMapTouchListener, AMap.OnMapScreenShotListener {
    public  MapView mapView;
    public  AMap    aMap;
    private Marker  mStartMarket;
    private Marker  mCurrentMarker;
    @Override
    public int getMapType() {
        return MAPTYPE.MAPTYPE_GAODE;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_gaode,null);//don't have any group
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView   = (MapView) view.findViewById(R.id.map);
        aMap = mapView.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnMapTouchListener(this);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    //////////////////////////////////////////////////////////////
    // for data view
    private MapDataCardRelativeLayout mMapDataCardRelativeLayout;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
    ////////////////////////////////////////////////////////////////////////////
    // for Base fragment
    @Override
    protected boolean onLocationChange(MapUIControl mapUIControl) {
        return updateLocationUI(mapUIControl);
    }
    @Override
    protected boolean onAdaptionMapView(MapUIControl mapUIControl) {
        return adaptionMapView(mapUIControl);
    }
    @Override
    protected boolean onClear() {
        aMap.clear();
        mStartMarket   = null;
        mCurrentMarker = null;
        return true;
    }
    @Override
    protected boolean onMove(double latitude, double longitude) {
        try{
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(new LatLng(latitude, longitude), 18, 30 , 0)));
            return true;
        }catch (Throwable e){Log.d("FragmentGaodeMap onMove fail -->" + e.toString());}
        return false;
    }
    /////////////////////////////////////////////////////////////////////
    // for screen shot
    @Override
    public synchronized boolean getMaoScreenShot(iMapScreenShotListener listener) {
        if(super.getMaoScreenShot(listener)){
            if(aMap != null){
                aMap.getMapScreenShot(FragmentGaodeMap.this);
                return true;
            }
            onScreenShotFail(getMapType(), "Gaode Map unuse. its null !!!!");
        }
        return false;
    }

    private boolean updateLocationUI(MapUIControl mapUIControl){
        if(mapUIControl.hasLine()){
            aMap.addPolyline((PolylineOptions) mapUIControl.getLine());
        }
        updateLocationUIStartMakers(mapUIControl);
        updateLocationUICurrentMakers(mapUIControl);
        return true;
    }
    private boolean adaptionMapView(MapUIControl mapUIControl){
        try {
            if(mapUIControl.hasBuilded()){
                aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                        ((LatLngBounds.Builder) mapUIControl.getBuilder()).build(), 50
                ));
            }else if(mapUIControl.mCurrentLocationData != null){
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mapUIControl.mCurrentLocationData.getLatitude(), mapUIControl.mCurrentLocationData.getLongitude())
                        , getZoom(mapUIControl)
                ));
            }
            return true;
        }catch (Throwable e){Log.d("FragmentGaodeMap adaptionMapView fail -->" + e.toString());}
        return false;
    }
    private float getZoom(MapUIControl mapUIControl){
        return mapUIControl.getLineWidth() * (600.0f);
    }
    private void updateLocationUIStartMakers(MapUIControl mapUIControl){
        if(mapUIControl.mFirstLocationData == null)return;
        if(mStartMarket == null ){
            mStartMarket = addMakers(mapUIControl.mFirstLocationData , getStartMarkerDrawableId() , 0.5f, 0.5f);
        }
    }
    private void updateLocationUICurrentMakers(MapUIControl mapUIControl){
        if(mapUIControl.mCurrentLocationData == null)return;
        if(mCurrentMarker == null){
            mCurrentMarker = addMakers(mapUIControl.mCurrentLocationData , getCurrentMarkerDrawableId() , 0.5f, 0.5f);
        }else{
            mCurrentMarker.setPosition(new LatLng(mapUIControl.mCurrentLocationData.getLatitude(), mapUIControl.mCurrentLocationData.getLongitude()));
        }
    }
    private Marker addMakers(LocationDBData data,int bitmapId,float X, float Y){
        return aMap.addMarker( new MarkerOptions().anchor(X, Y)
                                    .icon(BitmapDescriptorFactory.fromResource(bitmapId))
                                    .position(new LatLng(data.getLatitude(), data.getLongitude()))
                                    .draggable(true) );
    }
    @Subscribe
    public void EventMSGListener(EventMSG eventMSG){
        super.EventMSGListener(eventMSG);
    }
    @Subscribe
    public void EventStateChangeListener(EventStateChange eventStateChange){
        super.EventStateChangeListener(eventStateChange);
    }
    @Subscribe
    public void EventClickListener(EventClick eventClick){
        super.EventClickListener(eventClick);
    }
    @Override
    public void onTouch(MotionEvent motionEvent) {
        super.onTouchMap(motionEvent);
    }
    @Override
    public void onMapScreenShot(Bitmap bitmap) {
        onScreenShotSuccess(getMapType(), bitmap, null);
    }
}
