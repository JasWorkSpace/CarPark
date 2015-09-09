package com.greenorange.gooutdoor.framework.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.TextUtils;


/////////////////////////////
//
/**
 * Created by JasWorkSpace on 15/4/15.
 */
public class ScheduleContract {

    public final static String DB_NAME = "GOOUT.db";
    public final static int    DB_VERSION = 3;

    public static final String QUERY_PARAMETER_DISTINCT = "distinct";
    public static final String OVERRIDE_ACCOUNTNAME_PARAMETER = "overrideAccount";

    public interface UserColumns{
        String USER_ID                      = "user_id";
        String USER_NAME                    = "user_name";
        String USER_PASSWORD                = "user_password";
        String USER_LASTLOGINTIME           = "user_lastlogintime";
        String USER_SPORTS_TOTAL_COUNT      = "user_sports_total_count";
        String USER_SPORTS_TOTAL_TYPECOUNT  = "user_sports_total_typecount";
        String USER_SPORTS_TOTAL_DISTANCE   = "user_sports_total_distance";
        String USER_SPORTS_TOTAL_TIME       = "user_sports_total_time";
        String USER_SPORTS_TOTLE_CALORIE    = "user_sports_totle_calorie";
    }

    public interface SportsColumns{
        String SPORTS_ID             = "sports_id";
        String SPORTS_USERID         = "sports_userid";
        String SPORTS_TYPE           = "sports_type";
        String SPORTS_TIME           = "sports_time";
        String SPORTS_TOTAL_TIME     = "sports_total_time";
        String SPORTS_TOTAL_DISTANCE = "sports_total_distance";
        String SPORTS_TOTAL_CALORIE  = "sports_totle_calorie";
        String SPORTS_RECORD_STATE   = "sports_record_state";
        //for special
        String SPORTS_TIME_YEAR        = "sports_time_year";
        String SPORTS_TIME_MONTH       = "sports_time_month";
        String SPORTS_TIME_DAYOFMONTH  = "sports_time_dayofmonth";
        String SPORTS_TIME_DAYOFWEEK   = "sports_time_dayofweek";
        String SPORTS_TIME_WEEKOFYEAR  = "sports_time_weekofyear";
        String SPORTS_TIME_WEEKOFMONTH = "sports_time_weekofmonth";
        String SPORTS_TIME_HOUROFDAY   = "sports_time_hourofday";
        String SPORTS_TIME_MINUTE      = "sports_time_minute";
    }

    public interface LocationColumns{
        String LOCATION_ID           = "location_id";
        String LOCATION_USERID       = "location_userid";
        String LOCATION_SPORTSID     = "location_sportsid";
        String LOCATION_TYPE         = "location_type";
        String LOCATION_LATITUDE     = "location_latitude";
        String LOCATION_LONGITUDE    = "location_longitude";
        String LOCATION_CURRENTSPEED = "location_currentSpeed";
        String LOCATION_ADDRESS      = "location_address";
        String LOCATION_ALTITUDE     = "location_altitude";
        String LOCATION_PROVIDER     = "location_provider";
        String LOCATION_CREATETIME   = "location_createtime";
        String LOCATION_SPORTSTATE   = "location_sportstate";
        String LOCATION_SPORTTYPE    = "location_sporttype";
    }

    public interface SportsTYPEColumns{
        String SPORTS_ID             = "sports_id";
        String SPORTS_USERID         = "sports_userid";
        String SPORTS_TYPE           = "sports_type";
        String SPORTS_TOTAL_COUNT    = "sports_total_count";
        String SPORTS_TOTAL_TIME     = "sports_total_time";
        String SPORTS_TOTAL_DISTANCE = "sports_total_distance";
        String SPORTS_TOTAL_CALORIE  = "sports_totle_calorie";
    }
    public interface SportsTYPEDetailColumns{
        String SPORTS_ID             = "sports_id";
        String SPORTS_USERID         = "sports_userid";
        String SPORTS_TYPE           = "sports_type";
        String SPORTS_TIME           = "sports_time";
        String SPORTS_SPEED          = "sports_speed";
        String SPORTS_CALORIE        = "sports_calorie";
        String SPORTS_DISTANCE       = "sports_distance";
    }
    ///////////////////// for control
    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(
                ContactsContract.CALLER_IS_SYNCADAPTER, "true").build();
    }
    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        return TextUtils.equals("true",
                uri.getQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER));
    }
    public static Uri addOverrideAccountName(Uri uri, String accountName) {
        return uri.buildUpon().appendQueryParameter(
                OVERRIDE_ACCOUNTNAME_PARAMETER, accountName).build();
    }

    public static String getOverrideAccountName(Uri uri) {
        return uri.getQueryParameter(OVERRIDE_ACCOUNTNAME_PARAMETER);
    }

    public interface Tables{
        String USER       = "user";
        String SPORTS     = "sports";
        String LOCATION   = "location";
        String SPORTSTYPE = "sportstype";
        String SPORTSTYPEDETAIL = "sportsdetail";
    }

    public static final String CONTENT_AUTHORITY = "com.greenorange.gooutdoor.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class User implements UserColumns, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Tables.USER).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.greenorangegooutdoor.user";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.greenorangegooutdoor.user";

        public static Uri buildUsersUri() {
            return CONTENT_URI;
        }

        public static Uri buildUserUri(String userId) {
            return CONTENT_URI.buildUpon().appendPath(userId).build();
        }

        public static String getUserId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Sports implements SportsColumns, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Tables.SPORTS).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.greenorangegooutdoor.sports";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.greenorangegooutdoor.sports";

        public static Uri buildSportsUri() {
            return CONTENT_URI;
        }

        public static Uri buildSportsUri(String userId) {
            return CONTENT_URI.buildUpon().appendPath(userId).build();
        }

        public static String getSportsId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class SportsTYPE implements SportsTYPEColumns, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Tables.SPORTSTYPE).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.greenorangegooutdoor.sportstype";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.greenorangegooutdoor.sportstype";

        public static Uri buildSportsTYPEUri() {
            return CONTENT_URI;
        }

        public static Uri buildSportsTYPEUri(String sportTYPEId) {
            return CONTENT_URI.buildUpon().appendPath(sportTYPEId).build();
        }

        public static String getSportsTYPEId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Location implements LocationColumns, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Tables.LOCATION).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.greenorangegooutdoor.location";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.greenorangegooutdoor.location";

        public static Uri buildLocationUri() {
            return CONTENT_URI;
        }

        public static Uri buildLocationUri(String locationId) {
            return CONTENT_URI.buildUpon().appendPath(locationId).build();
        }

        public static String getLocationId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class SportsTYPEDetail implements SportsTYPEDetailColumns, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(Tables.SPORTSTYPEDETAIL).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.greenorangegooutdoor.sportsdetail";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.greenorangegooutdoor.sportsdetail";
    }
}
