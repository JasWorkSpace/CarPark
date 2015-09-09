package com.greenorange.gooutdoor.Test;

import android.content.Context;
import android.net.Uri;

import com.greenorange.gooutdoor.entity.UserDBData;
import com.greenorange.gooutdoor.framework.DB.UserDBHelper;
import com.greenorange.gooutdoor.framework.Log;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/4/16.
 */
public class Test_DB_UserData implements Runnable{
    private UserDBHelper mUserDBHelper;
    private Context mContext;
    public void startUserDataTest(Context context){
        mContext = context;
        new Thread(this).run();
    }

    @Override
    public void run() {
        Log.d("startUserDataTest");
        mUserDBHelper = new UserDBHelper();
        Log.d("write or read +++++++++++++++++++++++++++++++++++++++");
        Write();
        read();
        readAll();
        Log.d("deluserData 1 +++++++++++++++++++++++++++++++++++++++");
        mUserDBHelper.deluserData(mContext, "1");
        readAll();
        Log.d("update 2  +++++++++++++++++++++++++++++++++++++++");
        mUserDBHelper.updateUserData(mContext, getuserData("2", "update2name2"));
        readAll();
        Log.d("update 3  +++++++++++++++++++++++++++++++++++++++");
        mUserDBHelper.updateUserData(mContext, getuserData("1", "update2name2"));
        readAll();
        Log.d("update 4  +++++++++++++++++++++++++++++++++++++++");
        Write();
        mUserDBHelper.delAlluserData(mContext);
        readAll();
        Log.d("update 5  +++++++++++++++++++++++++++++++++++++++");
        mUserDBHelper.updateUserData(mContext, getuserData("3","name3"));
        readAll();
        Log.d("end end end end   +++++++++++++++++++++++++++++++++++++++");
    }
    private boolean readAll(){
        Log.d("readAll UserData start");
        List<UserDBData> userDatas = mUserDBHelper.getAllUserData(mContext);
        for(UserDBData userData : userDatas){
            Log.d("UserData-->"+(userData == null ? " null !!!! " : userData.toString()));
        }
        Log.d("readAll UserData end");
        return userDatas.size() > 0;
    }

    private boolean read(){
        UserDBData userData = mUserDBHelper.getUserDataByUserId(mContext,"1");
        Log.d("getUserDataByUserId(1) return -->"+(userData == null ? "fail !!" : userData.toString()));
        userData = mUserDBHelper.getUserDataByUserId(mContext, "2");
        Log.d("getUserDataByUserId(2) return -->"+(userData == null ? "fail !!" : userData.toString()));
        return userData != null;
    }

    private boolean Write(){
        UserDBData userData1 = getuserData("1","name1");
        Uri uri = mUserDBHelper.insertUserData(mContext, userData1);
        Log.d("Write 1--> "+(uri==null ? "fail " : uri));
        UserDBData userData2 = getuserData("2","name2");
        uri = mUserDBHelper.insertUserData(mContext, userData2);
        Log.d("Write 2--> "+(uri==null ? "fail " : uri));
        return uri != null;
    }

    private UserDBData getuserData(String userId, String username){
        UserDBData userData = new UserDBData();
        userData.setUserid(userId);
        userData.setUsername(username);
        userData.setUserpassword("password"+username);
        userData.setLastlogintime(System.currentTimeMillis());
        userData.setTotal_count(Integer.parseInt(userId));
        userData.setTotal_typecount("setTotal_typecount");
        userData.setTotal_distance(123.123);
        userData.setTotal_time(System.currentTimeMillis());
        userData.setTotal_calorie(123.456);
        return userData;
    }
}
