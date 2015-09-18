package com.greenorange.gooutdoor.framework;

/**
 * Created by JasWorkSpace on 15/4/10.
 */
public class Feature {

    public static boolean isUmengSupport(){
        return true;
    }
    //foint set support. support v4 < 21.0.3 is true.  22.2.0 is false
    public static boolean isFointSupport(){ return false;}
}
