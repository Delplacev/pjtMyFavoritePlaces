package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

/**
 * Created by virgiledelplace on 28/01/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FAV_PLACES = "table_fav_places";
    public static final String COL_ID = "ID";
    public static final String COL_ADRESSE = "Adresse";
    public static final String COL_VILLE = "ville";
    public static final String COL_CP = "CP";
    public static final String COL_LAT = "latitude";
    public static final String COL_LONG = "longitude";
    public static final String COL_DESCR = "description";
    public static final String COL_DATE = "date";

    public static final String DATABASE_NAME = "favPlace.db";
    public static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAV_PLACES + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_ADRESSE + " TEXT NOT NULL, "
            + COL_CP + " TEXT NOT NULL, "+ COL_LAT + " TEXT NOT NULL, "+ COL_LONG + " TEXT NOT NULL, "+ COL_VILLE + " TEXT NOT NULL, "+COL_DESCR+" TEXT NOT NULL, "+COL_DATE+" TEXT NOT NULL);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV_PLACES);
        onCreate(db);
    }

}