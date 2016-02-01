package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

/**
 * Created by virgiledelplace on 27/01/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavPlaceBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "client.db";

    private static final String TABLE_FAV_PLACES = "table_fav_places";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ADRESSE = "Adresse";
    private static final int NUM_COL_ADRESSE = 1;
    private static final String COL_VILLE = "ville";
    private static final int NUM_COL_VILLE = 2;
    private static final String COL_CP = "CP";
    private static final int NUM_COL_CP = 3;
    private static final String COL_LAT = "latitude";
    private static final int NUM_COL_LAT = 4;
    private static final String COL_LONG = "longitude";
    private static final int NUM_COL_LONG = 5;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public FavPlaceBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
        maBaseSQLite.onUpgrade(bdd,1,2);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public List<FavPlace> getAllFavPlace() {
        List<FavPlace> comments = new ArrayList<FavPlace>();

        Cursor cursor = bdd.query(TABLE_FAV_PLACES,
                new String[] {COL_ID, COL_ADRESSE, COL_VILLE,COL_CP,COL_LAT,COL_LONG}, null, null, null, null, null);


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // Votre code
            FavPlace comment = cursorToFavPlace(cursor);
            comments.add(comment);
        }
        cursor.close();


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            FavPlace comment = cursorToFavPlace(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }


    public long insertLivre(FavPlace favPlaces){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ADRESSE, favPlaces.getAdresseFavPlace());
        values.put(COL_CP, favPlaces.getCPAdresse());
        values.put(COL_VILLE, favPlaces.getVilleAdresse());
        values.put(COL_LAT, favPlaces.getLatitude());
        values.put(COL_LONG, favPlaces.getLongitude());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_FAV_PLACES, null, values);
    }

    public int updateLivre(int id, FavPlace favPlaces){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ADRESSE, favPlaces.getAdresseFavPlace());
        values.put(COL_CP, favPlaces.getCPAdresse());
        values.put(COL_VILLE, favPlaces.getVilleAdresse());
        values.put(COL_LAT, favPlaces.getLatitude());
        values.put(COL_LONG, favPlaces.getLongitude());
        return bdd.update(TABLE_FAV_PLACES, values, COL_ID + " = " +id, null);
    }

    public int removeLivreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_FAV_PLACES, COL_ID + " = " +id, null);
    }

    public FavPlace getLivreWithTitre(String ville){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_FAV_PLACES, new String[] {COL_ID, COL_ADRESSE, COL_VILLE,COL_CP,COL_LAT,COL_LONG}, COL_VILLE + " LIKE \"" + ville +"\"", null, null, null, null);
        return cursorToFavPlace(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private FavPlace cursorToFavPlace(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        FavPlace favPlaces = new FavPlace();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        favPlaces.setIdFavPlace(c.getInt(NUM_COL_ID));
        favPlaces.setAdresseFavPlace(c.getString(NUM_COL_ADRESSE));
        favPlaces.setVilleAdresse(c.getString(NUM_COL_VILLE));
        favPlaces.setCPAdresse(c.getInt(NUM_COL_CP));
        favPlaces.setLatitude(c.getDouble(NUM_COL_LAT));
        favPlaces.setLongitude(c.getDouble(NUM_COL_LONG));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return favPlaces;
    }
}
