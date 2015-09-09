package com.greenorange.gooutdoor.framework.Model.Interface;

import com.greenorange.gooutdoor.entity.LocationDBData;
import com.greenorange.gooutdoor.entity.SportsDetailDBData;

/**
 * Created by JasWorkSpace on 15/4/28.
 */
public interface iMapUIControl {

    public boolean addLocationDB(LocationDBData locationDBData, int sportsState);

    public boolean addSportsDetailDBData(SportsDetailDBData sportsDetailDBData);

    public int  getMapType();

    public Object getLine();

    public Object getBuilder();

    public boolean hasBuilded();

    public boolean hasLine();

    public float  getLineWidth();

    public int    getLineColor();
}
