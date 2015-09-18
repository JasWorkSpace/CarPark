package com.greenorange.gooutdoor.framework.Utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.SharePreference;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsState;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.Dao.SharePreferenceDao;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;

/**
 * Created by JasWorkSpace on 15/4/25.
 */
public class SportUtil {

    public static boolean isSportingState(int state){
        return state == SportsState.STATE_PAUSE || state == SportsState.STATE_RECORD;
    }
    public static String getSportStateString(int state){
        switch(state){
            case SportsState.STATE_RECORD:return "STATE_RECORD";
            case SportsState.STATE_FINISH:return "STATE_FINISH";
            case SportsState.STATE_PAUSE:return "STATE_PAUSE";
            case SportsState.STATE_STOP:return "STATE_STOP";
            case SportsState.STATE_UNKNOW:return "STATE_UNKNOW";
            case SportsState.STATE_MAX:return "STATE_MAX";
        }
        return "state " + state + " is unknow  state. where get it!!!!!!!!!";
    }
    public static boolean isSportsState(int state){
        return state > SportsState.STATE_UNKNOW && state < SportsState.STATE_MAX;
    }
    public static boolean isSportsType(int type){
        return type > SportsTYPE.SPORT_TYPE_UNKNOW && type < SportsTYPE.SPORT_TYPE_MAX;
    }
    public static double getGaodedistance(LocationDBData from, LocationDBData to){
        if(from != null && to != null){
            return AMapUtils.calculateLineDistance(new LatLng(from.getLatitude(), from.getLongitude()) ,
                                                   new LatLng(to.getLatitude(), to.getLongitude()) );
        }
        return 0;
    }
    public static double getCalorie(double diatance, int sportType){
        return diatance * 60 * (1.336 + sportType/10.0f);//kg * distance * 1.336
    }

    public static synchronized boolean setLastLocation(double latitude, double longitude){
        String data = latitude + SharePreference.SPLIT + longitude;
        SharePreferenceDao sharePreferenceDao = (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
        return sharePreferenceDao.setValue(SharePreference.KEY_LAST_LOCATION, data);
    }
    public static synchronized double[] getLastLocation(){
        SharePreferenceDao sharePreferenceDao = (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
        String data = (String) sharePreferenceDao.getValue(SharePreference.KEY_LAST_LOCATION, "");
        if(TextUtils.isEmpty(data))return null;
        String[] datas = data.split(SharePreference.SPLIT);
        if(datas != null && datas.length == 2){
            return new double[]{
                    Double.parseDouble(datas[0]), Double.parseDouble(datas[1])
            };
        }
        return null;
    }

    public static int getSportsColor(Context context, int sportsType){
        switch (sportsType){
            case SportsTYPE.SPORT_TYPE_BICYCLE: return context.getResources().getColor(R.color.floatcontrol_circulor_bicycle);
            case SportsTYPE.SPORT_TYPE_RUN: return context.getResources().getColor(R.color.floatcontrol_circulor_run);
            case SportsTYPE.SPORT_TYPE_SKI: return context.getResources().getColor(R.color.floatcontrol_circulor_ski);
            case SportsTYPE.SPORT_TYPE_WALK: return context.getResources().getColor(R.color.floatcontrol_circulor_walk);
        }
        return Color.TRANSPARENT;
    }

    public static int getSportsTypeColorIcon(int type){
        switch (type){
            case SportsTYPE.SPORT_TYPE_BICYCLE: return R.drawable.bt_bike_small;
            case SportsTYPE.SPORT_TYPE_RUN:     return R.drawable.bt_run_small;
            case SportsTYPE.SPORT_TYPE_SKI:     return R.drawable.bt_ski_small;
            case SportsTYPE.SPORT_TYPE_WALK:    return R.drawable.bt_walk_small;
        }
        throw new ApplicationException("unknow icon for type " + type);
    }
    public static int getSportsTypeIcon(int type){
        switch (type){
            case SportsTYPE.SPORT_TYPE_BICYCLE: return R.drawable.icon_cards_bike;
            case SportsTYPE.SPORT_TYPE_RUN:     return R.drawable.icon_cards_run;
            case SportsTYPE.SPORT_TYPE_SKI:     return R.drawable.icon_cards_ski;
            case SportsTYPE.SPORT_TYPE_WALK:    return R.drawable.icon_cards_walk;
        }
        throw new ApplicationException("unknow icon for type " + type);
    }
    public static int getSportsTypeSportingLab(int type){
        switch (type){
            case SportsTYPE.SPORT_TYPE_BICYCLE: return R.string.layout_map_data_flog_bicycling;
            case SportsTYPE.SPORT_TYPE_RUN:     return R.string.layout_map_data_flog_runing;
            case SportsTYPE.SPORT_TYPE_SKI:     return R.string.layout_map_data_flog_skiing;
            case SportsTYPE.SPORT_TYPE_WALK:    return R.string.layout_map_data_flog_walking;
        }
        throw new ApplicationException("unknow Lab for type " + type);
    }
    public static int getSportsTypeLab(int type){
        switch (type){
            case SportsTYPE.SPORT_TYPE_BICYCLE: return R.string.layout_map_data_flog_bicycle;
            case SportsTYPE.SPORT_TYPE_RUN:     return R.string.layout_map_data_flog_run;
            case SportsTYPE.SPORT_TYPE_SKI:     return R.string.layout_map_data_flog_ski;
            case SportsTYPE.SPORT_TYPE_WALK:    return R.string.layout_map_data_flog_walk;
        }
        throw new ApplicationException("unknow Lab for type " + type);
    }
}
