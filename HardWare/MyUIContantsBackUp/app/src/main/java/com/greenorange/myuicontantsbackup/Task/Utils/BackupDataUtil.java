package com.greenorange.myuicontantsbackup.Task.Utils;

import android.text.TextUtils;
import com.greenorange.myuicontantsbackup.Service.V2.ServiceHelper;
import java.io.IOException;

/**
 * Created by JasWorkSpace on 15/11/19.
 */
public class BackupDataUtil {
    /////////
    public final static int currentDataVersion = 2;
    ///////////////////////////////////////////////////////////////////////////
    public static String getEncrypt(String string) throws Exception {
        return getEncrypt(string, currentDataVersion);
    }
    public static String getEncrypt(String string, int version) throws Exception {
        switch(version){
            case 2:{
                return getEncryptV2(string);
            }
            default:{
                return getEncryptV1(string);
            }
        }
    }
    private static String getEncryptV1(String data) throws Exception {
        //we don't support it so return it.
        return new V1DESPlus().encrypt(data);
    }
    private static String getEncryptV2(String data) throws IOException {
        if(TextUtils.isEmpty(data))return "";
        return ServiceHelper.getEncodeParam(ServiceHelper.getZipString(data));
    }
    /////////////////////////////////////////////////////////////////////////////////////
    public static String getDecrypt(String string) throws Exception {
        return getDecrypt(string, currentDataVersion);
    }
    public static String getDecrypt(String string, int version) throws Exception {
        switch(version){
            case 2:{
                return getDecryptV2(string);
            }
            default:{
                return getDecryptV1(string);
            }
        }
    }
    private static String getDecryptV1(String string) throws Exception {
        return new V1DESPlus().decrypt(string);
    }
    private static String getDecryptV2(String string)throws Exception{
        if(TextUtils.isEmpty(string))return "";
        return ServiceHelper.getUnZipString(ServiceHelper.getDecodeParam(string));
    }
}
