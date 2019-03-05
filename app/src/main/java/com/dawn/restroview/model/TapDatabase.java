package com.dawn.restroview.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TapDatabase {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "tapdetails.db";

    //table name
    private static final String TABLE_FOOD = "food_table";
    private static final String TABLE_SERVICE = "service_table";

    private static final String FOOD_COL_ID = "_id";
    private static final String FOOD_COL_TAP = "tap_name";
    private static final String FOOD_COL_TAP_DATE = "tap_date";
    private static final String FOOD_COL_TAP_TIME = "tap_time";

    private static final String SERVICE_COL_ID = "_id";
    private static final String SERVICE_COL_TAP = "tap_name";
    private static final String SERVICE_COL_TAP_DATE = "tap_date";
    private static final String SERVICE_COL_TAP_TIME = "tap_time";

    private static final String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + " ("
            + FOOD_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOOD_COL_TAP + " TEXT, "
            + FOOD_COL_TAP_TIME + " TEXT, "
            + FOOD_COL_TAP_DATE + " TEXT"
            + ")";

    private static final String CREATE_SERVICE_TABLE = "CREATE TABLE " + TABLE_SERVICE + " ("
            + SERVICE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SERVICE_COL_TAP + " TEXT, "
            + SERVICE_COL_TAP_TIME + " TEXT, "
            + SERVICE_COL_TAP_DATE + " TEXT"
            + ")";

    private SQLiteDatabase sqLiteDatabase;
    private TapDBHelper tapDBHelper;

    public TapDatabase(Context context) {
        tapDBHelper = new TapDBHelper(context);
        sqLiteDatabase = tapDBHelper.getWritableDatabase();
    }

    //open a database
    public void openDB() throws SQLException {
        sqLiteDatabase = tapDBHelper.getWritableDatabase();
    }

    //close a database
    public void closeDB() {
        sqLiteDatabase.close();
    }

    //inserting food tap data
    public void insertFoodTapData(String tapName){
        ContentValues cv = new ContentValues();
        cv.put(FOOD_COL_TAP, tapName);
        cv.put(FOOD_COL_TAP_TIME, getCurrentTime());
        cv.put(FOOD_COL_TAP_DATE, getCurrentDate());

        openDB();
        sqLiteDatabase.insert(TABLE_FOOD, null, cv);
        closeDB();
    }

    //inserting food tap data
    public void insertServiceTapData(String tapName){
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_COL_TAP, tapName);
        cv.put(SERVICE_COL_TAP_TIME, getCurrentTime());
        cv.put(SERVICE_COL_TAP_DATE, getCurrentDate());

        openDB();
        sqLiteDatabase.insert(TABLE_SERVICE, null, cv);
        closeDB();
    }

    //getting current time
    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Date date = new Date();
        return timeFormat.format(date);
    }

    //getting current date
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }

    //retrieving food love data
    public float getFoodLoveData(){
        float counter;
        String query = "SELECT " + FOOD_COL_TAP + " FROM " + TABLE_FOOD + " WHERE " + FOOD_COL_TAP_DATE + " = date('now', 'localtime')" + " AND "
                + FOOD_COL_TAP + " = 'love'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        counter = cursor.getCount();
        cursor.close();
        return counter;
    }
    //retrieving food sad data
    public float getFoodSadData(){
        float counter;
        String query = "SELECT " + FOOD_COL_TAP + " FROM " + TABLE_FOOD + " WHERE " + FOOD_COL_TAP_DATE + " = date('now', 'localtime')" + " AND "
                + FOOD_COL_TAP + " = 'sad'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        counter = cursor.getCount();
        cursor.close();
        return counter;
    }

    //retrieving service love data
    public float getServiceLoveData(){
        float counter;
        String query = "SELECT " + SERVICE_COL_TAP + " FROM " + TABLE_SERVICE + " WHERE " + SERVICE_COL_TAP_DATE + " = date('now', 'localtime')" + " AND "
                + SERVICE_COL_TAP + " = 'love'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        counter = cursor.getCount();
        cursor.close();
        return counter;
    }
    //retrieving service sad data
    public float getServiceSadData(){
        float counter;
        String query = "SELECT " + SERVICE_COL_TAP + " FROM " + TABLE_SERVICE + " WHERE " + SERVICE_COL_TAP_DATE + " = date('now', 'localtime')" + " AND "
                + SERVICE_COL_TAP + " = 'sad'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        counter = cursor.getCount();
        cursor.close();
        return counter;
    }

    //helper class
    public class TapDBHelper extends SQLiteOpenHelper {

        public TapDBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_FOOD_TABLE);
            db.execSQL(CREATE_SERVICE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
            onCreate(db);
        }
    }
}