package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

/**
 * Created by virgiledelplace on 28/01/16.
 */
public class FavPlace {
    private int idFavPlace;
    private String adresseFavPlace;
    private String villeAdresse;
    private int CPAdresse;
    private Double latitude;
    private Double longitude;
    private String description;
    private String date;

    public  FavPlace(){}
    public FavPlace(String prmAdresseFavPlace, String prmVilleAdresse, int prmCPAdresse, Double prmLatitude, Double prmLongitude,String prmDescription,String prmDate){
        adresseFavPlace = prmAdresseFavPlace;
        villeAdresse = prmVilleAdresse;
        CPAdresse = prmCPAdresse;
        latitude = prmLatitude;
        longitude = prmLongitude;
        description = prmDescription;
        date = prmDate;
    }

    public int getIdFavPlace() {
        return idFavPlace;
    }

    public String getAdresseFavPlace() {
        return adresseFavPlace;
    }

    public String getVilleAdresse() {
        return villeAdresse;
    }

    public int getCPAdresse() {
        return CPAdresse;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setIdFavPlace(int idFavPlace) {
        this.idFavPlace = idFavPlace;
    }

    public void setAdresseFavPlace(String adresseFavPlace) { this.adresseFavPlace = adresseFavPlace;}

    public void setCPAdresse(int CPAdresse) {
        this.CPAdresse = CPAdresse;
    }

    public void setVilleAdresse(String villeAdresse) {
        this.villeAdresse = villeAdresse;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {this.longitude = longitude;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}