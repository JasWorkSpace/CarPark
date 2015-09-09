package com.greenorange.gooutdoor.framework.Dao.impl;

import android.content.Context;
import com.amap.api.maps.MapsInitializer;
import com.greenorange.gooutdoor.GOApplication;
import com.greenorange.gooutdoor.GOConfig;
import com.greenorange.gooutdoor.framework.Config;
import com.greenorange.outdoorhelper.R;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;
import com.greenorange.gooutdoor.framework.Dao.MapDao;
import com.greenorange.gooutdoor.framework.Dao.SharePreferenceDao;
import com.greenorange.gooutdoor.framework.Dao.Interface.Dao;
import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationConfig;
import com.greenorange.gooutdoor.framework.Model.DATA.LocationFilterConfig;
import com.greenorange.gooutdoor.framework.Model.Exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class MapDaoImpl implements MapDao{

    private int   mDefaultMapType = MAPTYPE.MAPTYPE_GAODE;
    private HashMap<Integer, LocationConfig> mMAPLocationConfig;
    private HashMap<Integer, HashMap<Integer, LocationFilterConfig>> mMAPFliterConfig;
    private HashMap<Integer, LocationFilterConfig>    mDefaultMAPFliterConfig;
    public  MapDaoImpl(Context context){
        init(context);
        initApiKey(context);
    }
    private void initApiKey(Context context) {
        LocationConfig gaode = mMAPLocationConfig.get(MAPTYPE.MAPTYPE_GAODE);
        if(gaode.enable){
            MapsInitializer.setApiKey(GOConfig.DEBUG_LOCATION ? Config.AMAP_KEY_DEV : Config.AMAP_KEY_RELEASE);
        }
    }
    public boolean init(Context context) {
        mDefaultMapType = Integer.parseInt(context.getString(R.string.config_default_maptype));
        //// for map location config
        mMAPLocationConfig = new HashMap<Integer, LocationConfig>();
        String[] locationconfigs = context.getResources().getStringArray(R.array.config_maps);
        if(locationconfigs != null && locationconfigs.length > 0) {
            for (String locationconfig : locationconfigs) {
                String[] config = locationconfig.split(";");
                int maptype = Integer.parseInt(config[0]);
                if (isMAPTYPEExsit(maptype)) {
                    LocationConfig locationConfig = new LocationConfig();
                    locationConfig.maptype = maptype;
                    locationConfig.enable = Integer.parseInt(config[1]) != 0;
                    locationConfig.frq = Integer.parseInt(config[2]);
                    locationConfig.mindistance = Integer.parseInt(config[3]);
                    mMAPLocationConfig.put(maptype, locationConfig);
                    continue;
                }
                throw new ApplicationException("config_maps unaviable !!!!");
            }
        }
        mMAPFliterConfig = new HashMap<Integer, HashMap<Integer,LocationFilterConfig>>();
        String[] fliterconfigs   = context.getResources().getStringArray(R.array.config_maps_filter);
        if(fliterconfigs != null && fliterconfigs.length > 0){
            for(String fliterconfig : fliterconfigs){
                String[] config = fliterconfig.split(";");
                int maptype = Integer.parseInt(config[0]);
                int sportstype = Integer.parseInt(config[1]);
                if(isMAPTYPEExsit(maptype)
                        && (SportsTYPE.SPORT_TYPE_UNKNOW < sportstype && sportstype < SportsTYPE.SPORT_TYPE_MAX)){
                    LocationFilterConfig locationFilterConfig = new LocationFilterConfig();
                    locationFilterConfig.maptype     = maptype;
                    locationFilterConfig.sportstype  = sportstype;
                    locationFilterConfig.mindistance = Integer.parseInt(config[2]);
                    locationFilterConfig.maxdistance = Integer.parseInt(config[3]);
                    HashMap<Integer,LocationFilterConfig> configHashMap = new HashMap<Integer,LocationFilterConfig>();
                    if(mMAPFliterConfig.containsKey(maptype)){
                        configHashMap = mMAPFliterConfig.get(maptype);
                    }
                    configHashMap.put(sportstype, locationFilterConfig);
                    mMAPFliterConfig.put(maptype, configHashMap);
                    continue;
                }
                throw new ApplicationException("config_maps_filter unaviable !!!!");
            }
        }
        // for default
        mDefaultMAPFliterConfig = new HashMap<Integer, LocationFilterConfig>();
        String[] defaultfliterconfigs   = context.getResources().getStringArray(R.array.config_maps_filter_default);
        if(defaultfliterconfigs != null && defaultfliterconfigs.length > 0){
            for(String fliterconfig : defaultfliterconfigs){
                String[] config = fliterconfig.split(";");
                int maptype = Integer.parseInt(config[0]);
                {
                    LocationFilterConfig locationFilterConfig = new LocationFilterConfig();
                    locationFilterConfig.maptype     = maptype;
                    locationFilterConfig.sportstype  = SportsTYPE.SPORT_TYPE_UNKNOW;
                    locationFilterConfig.mindistance = Integer.parseInt(config[1]);
                    locationFilterConfig.maxdistance = Integer.parseInt(config[2]);
                    if(mDefaultMAPFliterConfig.containsKey(maptype)){
                        throw new ApplicationException("config_maps_filter_default unaviable !!!!");
                    }
                    mDefaultMAPFliterConfig.put(maptype, locationFilterConfig);
                }
            }
        }
        return true;
    }
    public boolean isMAPTYPEExsit(int maptype){
        return maptype > MAPTYPE.MAPTYPE_UNKNOW && maptype < MAPTYPE.MAPTYPE_MAX;
    }
    @Override
    public int getDefaultMAPTYPE() {
        return mDefaultMapType;
    }
    @Override
    public ArrayList<Integer> getSupportMAPTYPE() {
        Set<Integer> integers = mMAPLocationConfig.keySet();
        ArrayList<Integer> support = new ArrayList<Integer>();
        for(Iterator<Integer> iterator = integers.iterator(); iterator.hasNext(); ){
            LocationConfig locationConfig = mMAPLocationConfig.get(iterator.next());
            if(locationConfig != null && locationConfig.enable){
                support.add(locationConfig.maptype);
            }
        }
        return support;
    }

    @Override
    public boolean isSupportMAPTYPE(int maptype) {
        return getSupportMAPTYPE().contains(maptype);
    }

    @Override
    public int getCurrentMAPTYPE() {
        SharePreferenceDao sharePreferenceDao = (SharePreferenceDao) GOApplication.getDaoManager().getManager(Dao.SharePerferenceDao);
        int currentMapType = (Integer)sharePreferenceDao.getValue(MAPTYPE.KEY_Current_MAPTYPE, getDefaultMAPTYPE());
        if(isSupportMAPTYPE(currentMapType))return currentMapType;
        return getDefaultMAPTYPE();
    }
    @Override
    public String getMAPTYPEString(int maptype) {
        switch(maptype){
            case MAPTYPE.MAPTYPE_BAIDU:return "MAPTYPE_BAIDU";
            case MAPTYPE.MAPTYPE_GAODE:return "MAPTYPE_GAODE";
            case MAPTYPE.MAPTYPE_MAX:return "MAPTYPE_MAX";
            case MAPTYPE.MAPTYPE_UNKNOW:return "MAPTYPE_UNKNOW";
        }
        return "maptype " + maptype + " is uncase maptype, where you get it !!!!!";
    }
    @Override
    public LocationConfig getMAPLocationConfig(int maptype) {
        return mMAPLocationConfig.get(maptype);
    }
    @Override
    public LocationFilterConfig getMAPFliterConfig(int maptype, int sportType) {
        HashMap<Integer, LocationFilterConfig> configHashMap = mMAPFliterConfig.get(maptype);
        if(configHashMap != null){
            return configHashMap.get(sportType);
        }
        return null;
    }
    @Override
    public LocationFilterConfig getDefaultFliterConfig() {
        return mDefaultMAPFliterConfig.get(getCurrentMAPTYPE());
    }
    public String toString(){
        StringBuffer sp = new StringBuffer();
        sp.append("MapDao{")
                .append("mDefaultMapType=" + mDefaultMapType)
                .append(", mMAPLocationConfig=" + mMAPLocationConfig.toString())
                .append(", mMAPFliterConfig=" + mMAPFliterConfig.toString())
                .append(", getCurrentMAPTYPE=" + getCurrentMAPTYPE())
                .append(", getCurrentMAPTYPEString=" + getMAPTYPEString(getCurrentMAPTYPE()))
                .append(", mDefaultMAPFliterConfig=" + mDefaultMAPFliterConfig.toString())
                .append("}");
        return sp.toString();
    }
}
