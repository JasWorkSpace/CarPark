package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.greenorange.gooutdoor.Bean.UserSportsData;
import com.greenorange.gooutdoor.Service.LocationControl;
import com.greenorange.gooutdoor.Service.LocationService;
import com.greenorange.gooutdoor.Service.MapUIControl;
import com.greenorange.gooutdoor.Service.SportsControl;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.Dao.SportsDao;
import com.greenorange.gooutdoor.framework.Log;
import com.greenorange.gooutdoor.framework.Utils.Util;

/**
 * Created by JasWorkSpace on 15/4/18.
 */
public class SportsDaoImpl implements SportsDao {
    private Context mContext;
    private LocationService mLocationService = null;

    public SportsDaoImpl(Context context){
        mContext = context;
        bindLocationService();//bind it first
    }

    @Override
    public LocationService getLocationService() {
        return mLocationService;
    }

    @Override
    public void bindLocationService() {
        Intent intent = new Intent(mContext.getApplicationContext(), LocationService.class);
        mContext.bindService(intent, mLocationServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unBindLocationService() {
        if(mLocationService != null){
            mLocationService = null;//we reset it null first. for we can't receiver onServiceDisconnected?????
        }
        mContext.unbindService(mLocationServiceConnection);
    }

    @Override
    public void startNewSports(int sportsType) {
        SportsControl sportsControl = getSportsControl();
        if(sportsControl != null){
            sportsControl.startNewSports(sportsType);
        }
    }

    @Override
    public int getCurrentSportsState() {
        SportsControl sportsControl = getSportsControl();
        if(sportsControl != null){
            return sportsControl.getSportsState();
        } else {
            Log.d("getCurrentSportsState null for sportsControl is null !!!!!!!!!! ");
        }
        return SportsState.STATE_UNKNOW;
    }

    @Override
    public int getCurrentSportsType() {
        SportsControl sportsControl = getSportsControl();
        if(sportsControl != null){
            return sportsControl.getSportsTYPE();
        } else {
            Log.d("getCurrentSportsType null for sportsControl is null !!!!!!!!!! ");
        }
        return SportsTYPE.SPORT_TYPE_UNKNOW;
    }

    @Override
    public UserSportsData getCurrentUserSportsData() {
        SportsControl sportsControl = getSportsControl();
        if(sportsControl != null){
            return sportsControl.getUserSportsData();
        } else {
            Log.d("getCurrentUserSportsData null for sportsControl is null !!!!!!!!!! ");
        }
        return null;
    }

    @Override
    public MapUIControl getMapUIControl() {
        UserSportsData userSportsData = getCurrentUserSportsData();
        if(userSportsData != null){
            return userSportsData.mapUIControl;
        } else {
            Log.d("getMapUIControl null for userSportsData is null !!!!!!!!!! ");
        }
        return null;
    }

    @Override
    public LocationControl getLocationControl() {
        if(mLocationService != null){
            return mLocationService.getLocationControl();
        } else {
            Log.d("getLocationControl null for mLocationService is null !!!!!!!!!! ");
        }
        return null;
    }

    @Override
    public int getCurrentMapType() {
        LocationControl locationControl = getLocationControl();
        if(locationControl != null){
            return locationControl.getLocationMapType();
        } else {
            Log.d("getCurrentMapType null for sportsControl is null !!!!!!!!!! ");
        }
        return MAPTYPE.MAPTYPE_UNKNOW;
    }

    @Override
    public boolean reStartLocation() {
        if(mLocationService == null){
            bindLocationService();
            return false;
        }
        LocationControl locationControl = getLocationControl();
        if(locationControl != null){
            locationControl.openLocation();
            return true;
        }
        mLocationService.initLocationControl();//we should start it.
        return false;
    }

    @Override
    public boolean closeLocation() {
        if(mLocationService == null){ return true; }
        LocationControl locationControl = getLocationControl();
        if(locationControl != null){
            locationControl.closeLocation();
        }
        unBindLocationService();
        return true;
    }

    @Override
    public SportsControl getSportsControl() {
        if(mLocationService != null){
            return mLocationService.getSportsControl();
        } else {
            Log.d("getSportsControl null for  mLocationService is null !!!!!!!!!! ");
        }
        return null;
    }

    private ServiceConnection mLocationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SportsDaoImpl mLocationServiceConnection onServiceConnected");
            mLocationService = ((LocationService.LocationBinder)service).getService();
            notifyLocationServiceStateChange();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SportsDaoImpl mLocationServiceConnection onServiceDisconnected");
            mLocationService = null;
            notifyLocationServiceStateChange();
        }
    };

    private void notifyLocationServiceStateChange(){
        Log.d("notifyLocationServiceStateChange = " + (mLocationService != null));
        Util.notifyLocationServiceConnectionState(mLocationService != null);
    }
}
