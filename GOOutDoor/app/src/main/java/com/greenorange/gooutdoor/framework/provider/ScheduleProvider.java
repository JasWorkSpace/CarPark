package com.greenorange.gooutdoor.framework.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by JasWorkSpace on 15/4/15.
 */
public class ScheduleProvider extends ContentProvider {
    //private final static String TAG = "ScheduleProvider";
    private final static String TAG = "Jas";

    private ScheduleDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int USER = 100;
    private static final int USER_ID = 101;

    private static final int SPORTS = 200;
    private static final int SPORTS_ID = 201;

    private static final int LOCATION = 300;
    private static final int LOCATION_ID = 301;

    private static final int SPORTSTYPE = 400;
    private static final int SPORTSTYPE_ID = 401;

    private static final int SPORTSTYPEDETAIL = 500;
    private static final int SPORTSTYPEDETAIL_ID = 501;

    private static final int MY_SCHEDULE = 600;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScheduleContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ScheduleContract.Tables.USER, USER);
        matcher.addURI(authority, ScheduleContract.Tables.USER + "/#", USER_ID);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTS, SPORTS);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTS + "/#", SPORTS_ID);
        matcher.addURI(authority, ScheduleContract.Tables.LOCATION, LOCATION);
        matcher.addURI(authority, ScheduleContract.Tables.LOCATION + "/#", LOCATION_ID);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTSTYPE, SPORTSTYPE);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTSTYPE + "/#", SPORTSTYPE_ID);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTSTYPEDETAIL, SPORTSTYPEDETAIL);
        matcher.addURI(authority, ScheduleContract.Tables.SPORTSTYPEDETAIL + "/#", SPORTSTYPEDETAIL_ID);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new ScheduleDatabase(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        final int match = sUriMatcher.match(uri);
        Log.i(TAG, "query(uri=" + uri + ", match=" + match + ", proj=" + Arrays.toString(projection) +
                    ", selection=" + selection + ", args=" + Arrays.toString(selectionArgs) + ", sortOrder="+sortOrder+")");
        switch (match) {
            default: {
                // Most cases are handled with simple SelectionBuilder
                final SelectionBuilder builder = buildExpandedSelection(uri, match);
                Log.d(TAG, "SelectionBuilder="+builder.toString());
                boolean distinct = !TextUtils.isEmpty(
                        uri.getQueryParameter(ScheduleContract.QUERY_PARAMETER_DISTINCT));
                Cursor cursor = builder
                        .where(selection, selectionArgs)
                        .query(db, distinct, projection, sortOrder, null);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case USER:
                return ScheduleContract.User.CONTENT_TYPE;
            case USER_ID:
                return ScheduleContract.User.CONTENT_ITEM_TYPE;
            case SPORTS:
                return ScheduleContract.Sports.CONTENT_TYPE;
            case SPORTS_ID:
                return ScheduleContract.Sports.CONTENT_ITEM_TYPE;
            case LOCATION:
                return ScheduleContract.Location.CONTENT_TYPE;
            case LOCATION_ID:
                return ScheduleContract.Location.CONTENT_ITEM_TYPE;
            case SPORTSTYPE:
                return ScheduleContract.SportsTYPE.CONTENT_TYPE;
            case SPORTSTYPE_ID:
                return ScheduleContract.SportsTYPE.CONTENT_ITEM_TYPE;
            case SPORTSTYPEDETAIL:
                return ScheduleContract.SportsTYPEDetail.CONTENT_TYPE;
            case SPORTSTYPEDETAIL_ID:
                return ScheduleContract.SportsTYPEDetail.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }
    /** Returns a tuple of question marks. For example, if count is 3, returns "(?,?,?)". */
    private String makeQuestionMarkTuple(int count) {
        if (count < 1) {
            return "()";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(?");
        for (int i = 1; i < count; i++) {
            stringBuilder.append(",?");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
         Log.i(TAG, "insert{uri=" + uri + ", values=" + values.toString() +" }");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        //boolean syncToNetwork = !ScheduleContract.hasCallerIsSyncAdapterParameter(uri); //we don't want to aync to network
        switch (match){
            case USER: {
                return insertTable(db, uri, values, ScheduleContract.Tables.USER);
            }
            case SPORTS: {
                return insertTable(db, uri, values, ScheduleContract.Tables.SPORTS);
            }
            case LOCATION: {
                return insertTable(db, uri, values, ScheduleContract.Tables.LOCATION);
            }
            case SPORTSTYPEDETAIL:{
                return insertTable(db, uri, values, ScheduleContract.Tables.SPORTSTYPEDETAIL);
            }
            case SPORTSTYPE:{
                return insertTable(db, uri, values, ScheduleContract.Tables.SPORTSTYPE);
            }
            default:
                throw new UnsupportedOperationException("insert fail Unknown uri: " + uri);
        }
        //return null;
    }
    private Uri insertTable(SQLiteDatabase db , Uri uri, ContentValues values, String table){
        long rowid = db.insertOrThrow(table, null, values);
        notifyChange(uri);
        return getUri(uri, rowid);
    }
    private Uri getUri(Uri uri, long rowid){
        if(rowid <= 0)return null;
        if(uri.getPathSegments().size() != 1){
            throw  new IllegalArgumentException("Invalid URI:"+uri);
        }
        return ContentUris.withAppendedId(uri, rowid);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete(uri=" + uri + ", selection=" + selection + ", selectionArgs="+ Arrays.toString(selectionArgs) + ")");
        if (uri.equals(ScheduleContract.BASE_CONTENT_URI)) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri);
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSimpleSelection(uri);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri);
        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update(uri=" + uri + ", values=" + (values== null ? "null" : values.toString()));
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final SelectionBuilder builder = buildSimpleSelection(uri);

        int retVal = builder.where(selection, selectionArgs).update(db, values);
        notifyChange(uri);
        return retVal;
    }
    private SelectionBuilder buildSimpleSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "buildSimpleSelection match="+match+", uri="+uri);
        switch (match) {
            case USER: {
                return builder.table(ScheduleContract.Tables.USER);
            }
            case USER_ID: {
                final String userId = ScheduleContract.User.getUserId(uri);
                return builder.table(ScheduleContract.Tables.USER)
                        .where(ScheduleContract.User.USER_ID + "=?", userId);
            }
            case SPORTS:{
                return builder.table(ScheduleContract.Tables.SPORTS);
            }
            case SPORTSTYPE:{
                return builder.table(ScheduleContract.Tables.SPORTSTYPE);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }
    private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {
            case USER: {
                return builder.table(ScheduleContract.Tables.USER);
            }
            case USER_ID: {
                final String userId = ScheduleContract.User.getUserId(uri);
                return builder.table(ScheduleContract.Tables.USER)
                        .where(ScheduleContract.User.USER_ID + "=?", userId);
            }
            case SPORTS:{
                return builder.table(ScheduleContract.Tables.SPORTS);
            }
            case SPORTS_ID:{
                final String sportId = ScheduleContract.Sports.getSportsId(uri);
                return builder.table(ScheduleContract.Tables.SPORTS)
                        .where(ScheduleContract.Sports.SPORTS_ID + "=?", sportId);
            }
            case SPORTSTYPE:{
                return builder.table(ScheduleContract.Tables.SPORTSTYPE);
            }
            case SPORTSTYPEDETAIL:{
                return builder.table(ScheduleContract.Tables.SPORTSTYPEDETAIL);
            }
            case LOCATION:{
                return builder.table(ScheduleContract.Tables.LOCATION);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }
    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        Context context = getContext();
        ScheduleDatabase.deleteDatabase(context);
        mOpenHelper = new ScheduleDatabase(getContext());
    }
    private void notifyChange(Uri uri) {
        // We only notify changes if the caller is not the sync adapter.
        // The sync adapter has the responsibility of notifying changes (it can do so
        // more intelligently than we can -- for example, doing it only once at the end
        // of the sync instead of issuing thousands of notifications for each record).
        if (!ScheduleContract.hasCallerIsSyncAdapterParameter(uri)) {
            Context context = getContext();
            context.getContentResolver().notifyChange(uri, null);
        }
    }

}
