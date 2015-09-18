package com.greenorange.gooutdoor.framework.Model;


import com.greenorange.gooutdoor.framework.Model.Interface.iListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/3/19.
 */
public abstract class Listener implements iListener {

    public List<Object> mListeners = new ArrayList<Object>();

    @Override
    public boolean addListener(Object listener) {
        synchronized (mListeners){
            if(!mListeners.contains(listener)){
                return mListeners.add(listener);
            }
        }
        return false;
    }

    @Override
    public boolean removeListener(Object listener) {
        synchronized (mListeners){
            return mListeners.remove(listener);
        }
    }
}
