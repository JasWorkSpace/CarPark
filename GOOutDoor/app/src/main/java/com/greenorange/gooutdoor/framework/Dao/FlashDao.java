package com.greenorange.gooutdoor.framework.Dao;

/**
 * Created by JasWorkSpace on 15/4/12.
 */
public interface FlashDao {
    public final static int MSG_FLASH_OPENFAIL = 1;

    public void open();

    public void close();

    public boolean isFlash();

    public boolean isOpen();

    public int getCurrentFlashMode();

    public boolean setFlashMode(int type);

    public int getNextFlashMode();
}
