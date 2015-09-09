package com.greenorange.gooutdoor.framework.Dao;

import android.content.Context;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.entity.UserDBData;

import java.io.File;

/**
 * Created by JasWorkSpace on 15/4/17.
 */
public interface ApplicationDao {

    public UserDBData getUser();

    public boolean LoginUser(UserDBData userData);
    public boolean checkUser(UserDBData userData);
    public boolean saveUserDBDataToDB();

    public SportsDBData getNewSports(Context context,int type);

    public int getSportsSTATE();

    public int setSportsSTATE(int state);

    public File getBaseDir();

    public File getScreenShotDir();

    public String getScreenShotFileName(String sportId);
}
