package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces ;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_FAV_PLACES = "table_fav_places";
    private static final String COL_ID = "ID";
    private static final String COL_ADRESSE = "Adresse";
    private static final String COL_VILLE = "ville";
    private static final String COL_CP = "CP";
    private static final String COL_LAT = "latitude";
    private static final String COL_LONG = "longitude";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_FAV_PLACES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ADRESSE + " TEXT NOT NULL, "
            + COL_CP + " TEXT NOT NULL, "+ COL_LAT + " TEXT NOT NULL, "+ COL_LONG + " TEXT NOT NULL, "+ COL_VILLE + " TEXT NOT NULL);";

    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_FAV_PLACES + ";");
        onCreate(db);
    }

}