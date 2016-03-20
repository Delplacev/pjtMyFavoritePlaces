package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

/**
 * Created by virgiledelplace on 28/01/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavPlaceDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COL_ID,MySQLiteHelper.COL_ADRESSE,MySQLiteHelper.COL_VILLE,MySQLiteHelper.COL_CP,MySQLiteHelper.COL_LAT,MySQLiteHelper.COL_LONG,MySQLiteHelper.COL_DESCR,MySQLiteHelper.COL_DATE };

    public FavPlaceDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();


    }

    public void close() {
        dbHelper.close();
    }

    public FavPlace createFavPlace(String adresse,String ville,int CP, Double latitude, Double longitude,String description,String prmDate) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_ADRESSE, adresse);
        values.put(MySQLiteHelper.COL_VILLE, ville);
        values.put(MySQLiteHelper.COL_CP, CP);
        values.put(MySQLiteHelper.COL_LAT, latitude);
        values.put(MySQLiteHelper.COL_LONG, longitude);
        values.put(MySQLiteHelper.COL_DESCR,description);
        values.put(MySQLiteHelper.COL_DATE,prmDate);
        long insertId = database.insert(MySQLiteHelper.TABLE_FAV_PLACES, null,
                values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                allColumns, MySQLiteHelper.COL_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FavPlace newComment = cursorFavPlace(cursor);
        cursor.close();
        return newComment;
    }

    public void updateFavPlace(Integer id, String adresse,String ville,int CP, Double latitude, Double longitude,String description,String prmDate) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_ADRESSE, adresse);
        values.put(MySQLiteHelper.COL_VILLE, ville);
        values.put(MySQLiteHelper.COL_CP, CP);
        values.put(MySQLiteHelper.COL_LAT, latitude);
        values.put(MySQLiteHelper.COL_LONG, longitude);
        values.put(MySQLiteHelper.COL_DESCR,description);
        values.put(MySQLiteHelper.COL_DATE,prmDate);
        long insertId = database.update(MySQLiteHelper.TABLE_FAV_PLACES,
                values, MySQLiteHelper.COL_ID + " = " + id, null );

    }

    public void deleteFavPlace(FavPlace comment) {
        long id = comment.getIdFavPlace();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FAV_PLACES, MySQLiteHelper.COL_ID
                + " = " + id, null);
    }

    public List<FavPlace> getFavPlace(String idFavPlace) {
        List<FavPlace> comments = new ArrayList<FavPlace>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                allColumns, MySQLiteHelper.COL_ID + " = " + idFavPlace, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavPlace comment = cursorFavPlace(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;

    }

    public List<FavPlace> getAllFavPlaces(String trie) {
        List<FavPlace> comments = new ArrayList<FavPlace>();
        Cursor cursor = null;
        if(trie == "") {
             cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                    allColumns, null, null, null, null, null);
        }
        if(trie == "A") {
             cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                    allColumns, null, null, null, null, MySQLiteHelper.COL_VILLE +" ASC");
        }
        if(trie == "Z") {
            cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                    allColumns, null, null, null, null, MySQLiteHelper.COL_VILLE+" DESC");
        }
        if(trie == "Date ASC") {
             cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                    allColumns, null, null, null, null, MySQLiteHelper.COL_DATE+" ASC");
        }
        if(trie == "Date DSC") {
            cursor = database.query(MySQLiteHelper.TABLE_FAV_PLACES,
                    allColumns, null, null, null, null, MySQLiteHelper.COL_DATE+" DESC");
        }


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavPlace comment = cursorFavPlace(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private FavPlace cursorFavPlace(Cursor cursor) {
        FavPlace comment = new FavPlace();
        comment.setIdFavPlace(cursor.getInt(0));
        comment.setAdresseFavPlace(cursor.getString(1));
        comment.setVilleAdresse(cursor.getString(2));
        comment.setCPAdresse(cursor.getInt(3));
        comment.setLatitude(cursor.getDouble(4));
        comment.setLongitude(cursor.getDouble(5));
        comment.setDescription(cursor.getString(6));
        comment.setDate(cursor.getString(7));
        return comment;
    }
}
