package com.greenorange.gooutdoor.framework.Dao;

import com.greenorange.gooutdoor.entity.SmileData;

import java.util.List;

/**
 * Created by JasWorkSpace on 15/8/11.
 */
public interface SmileDao {

    public List<SmileData> getAllSmileData();

    public boolean isSmileDataInited();

    public SmileData     getSelectionSmileData();

    public boolean setSelectionSmileData(SmileData smileData);
}
