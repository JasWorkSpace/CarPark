package com.greenorange.gooutdoor.framework.Model.DATA;

import com.greenorange.gooutdoor.entity.LocationDBData;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public interface LocationCallBack {

    public boolean onLocationChange(LocationDBData locationData);

}
