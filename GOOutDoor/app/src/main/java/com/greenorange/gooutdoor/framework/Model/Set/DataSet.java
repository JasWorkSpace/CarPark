package com.greenorange.gooutdoor.framework.Model.Set;


import com.greenorange.gooutdoor.framework.Model.Interface.iDataSetChangeListener;
import com.greenorange.gooutdoor.framework.Model.ModelListener;

/**
 * Created by JasWorkSpace on 15/3/19.
 */
public class DataSet extends ModelListener implements iDataSetChangeListener {

    private final static int FLAG_DATA_CHANGE = 1;

    @Override
    public boolean DataSetChange(DataSet dataSet) {
        pendNotifyListener(FLAG_DATA_CHANGE,dataSet);
        return true;
    }

    @Override
    public boolean notifyListener(int flag, Object listener, Object... objects) {
        switch(flag){
            case FLAG_DATA_CHANGE:{
                return ((iDataSetChangeListener)listener).DataSetChange((DataSet)objects[0]);
            }
        }
        return false;
    }
}
