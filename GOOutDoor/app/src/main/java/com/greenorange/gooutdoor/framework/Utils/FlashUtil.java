package com.greenorange.gooutdoor.framework.Utils;

import com.greenorange.gooutdoor.framework.Dao.Interface.FlashTYPE;

/**
 * Created by JasWorkSpace on 15/9/1.
 */
public class FlashUtil {

    public static String getFlashModeString(int type){
        switch(type){
            case FlashTYPE.FLASH_TYPE_CLOSED: return"FLASH_TYPE_CLOSED";
            case FlashTYPE.FLASH_TYPE_FLASH: return "FLASH_TYPE_FLASH";
            case FlashTYPE.FLASH_TYPE_OPENALWAY: return "FLASH_TYPE_OPENALWAY";
            case FlashTYPE.FLASH_TYPE_UNKNOW: return "FLASH_TYPE_UNKNOW";
            case FlashTYPE.FLASH_TYPE_MAX: return "FLASH_TYPE_MAX";
        }
        return "type " + type + " is unknow  state. where get it!!!!!!!!!";
    }

    public static boolean isFlashMode(int type){
        return FlashTYPE.FLASH_TYPE_UNKNOW < type && type < FlashTYPE.FLASH_TYPE_MAX;
    }
}
