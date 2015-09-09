package com.greenorange.gooutdoor.framework.Model.Set;

import com.greenorange.gooutdoor.framework.Model.Interface.iWindowStateChangeLinstener;
import com.greenorange.gooutdoor.framework.Model.ModelListener;

/**
 * Created by JasWorkSpace on 15/8/24.
 */
public class WindowSet extends ModelListener implements iWindowStateChangeLinstener {

    public final static int FLAG_WINDOW_VISIBILITY_CHANGE = 2;
    public final static int FLAG_WINDOW_ATTACHED          = 3;
    public final static int FLAG_WINDOW_DETACHED          = 4;


    @Override
    public boolean notifyListener(int flag, Object listener, Object... objects) {
        switch(flag){
            case FLAG_WINDOW_VISIBILITY_CHANGE:{
                return ((iWindowStateChangeLinstener)listener).onWindowVisibilityChanged((Integer)objects[0]);
            }
            case FLAG_WINDOW_ATTACHED:{
                return ((iWindowStateChangeLinstener)listener).onAttachedToWindow();
            }
            case FLAG_WINDOW_DETACHED:{
                return ((iWindowStateChangeLinstener)listener).onDetachedFromWindow();
            }
        }
        return false;
    }

    @Override
    public boolean onWindowVisibilityChanged(int visibility) {
        pendNotifyListener(FLAG_WINDOW_VISIBILITY_CHANGE,visibility);
        return true;
    }

    @Override
    public boolean onAttachedToWindow() {
        pendNotifyListener(FLAG_WINDOW_ATTACHED);
        return true;
    }

    @Override
    public boolean onDetachedFromWindow() {
        pendNotifyListener(FLAG_WINDOW_DETACHED);
        return true;
    }
}
