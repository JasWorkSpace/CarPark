package com.greenorange.gooutdoor.Service;

import com.greenorange.gooutdoor.framework.Dao.Interface.MAPTYPE;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public class MapUIBaidu extends MapUIControl {

    @Override
    public int getMapType() {
        return MAPTYPE.MAPTYPE_BAIDU;
    }

    @Override
    public Object getLine() {
        return null;
    }
}
