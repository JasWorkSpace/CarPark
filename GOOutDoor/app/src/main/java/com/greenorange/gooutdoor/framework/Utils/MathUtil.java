package com.greenorange.gooutdoor.framework.Utils;

import java.util.Arrays;

/**
 * Created by JasWorkSpace on 15/4/11.
 */
public class MathUtil {

    public static int StringToInt(String string){
        return Integer.parseInt(string);
    }
    public static String IntToString(int data){
        return String.valueOf(data);
    }
    public static String toString(int[] data){
        return Arrays.toString(data);
    }

    
}
