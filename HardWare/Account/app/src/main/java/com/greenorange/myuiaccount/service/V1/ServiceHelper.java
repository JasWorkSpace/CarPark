package com.greenorange.myuiaccount.service.V1;

import com.google.gson.Gson;
import com.greenorange.myuiaccount.Log;
import com.greenorange.myuiaccount.service.V1.Response.BaseResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class ServiceHelper {

    public static BaseResponse getServiceBaseResponse(String url) throws Exception {
        InputStream inputStream = null;
        try{
            inputStream = NetworkUtilities.getHttpInputStream(url);
            return new Gson().fromJson(ConvertStream2String(inputStream), BaseResponse.class);
        }catch (Throwable e){
            e.printStackTrace();
            Log.d("getServiceBaseResponse fail -->"+e.toString());
            throw new Exception("unkonw exception");
        }finally {
            if(inputStream != null)try{inputStream.close();}catch(Throwable e){}
        }
    }
    public static String ConvertStream2String(InputStream inputStream){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        }catch (Throwable e){e.printStackTrace(); Log.d("ConvertStream2String fail-->" + e.toString());}finally {
            if(in !=null) try {in.close();} catch (IOException e) {e.printStackTrace();}
        }
        return null;
    }
}
