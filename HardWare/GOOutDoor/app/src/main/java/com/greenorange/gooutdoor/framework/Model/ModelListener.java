package com.greenorange.gooutdoor.framework.Model;

import java.util.Iterator;

/**
 * Created by JasWorkSpace on 15/3/19.
 */
public abstract class ModelListener extends Listener{

    public void pendNotifyListener(int flag,Object...objects){
        synchronized (mListeners){
            for(Iterator<Object> iterator=mListeners.iterator(); iterator.hasNext(); ){
                 if(notifyListener(flag,iterator.next(),objects))break;
            }
        }
    }

    public abstract boolean notifyListener(int flag, Object listener, Object...objects);
}
