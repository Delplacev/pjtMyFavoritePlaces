package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

import android.Manifest;

import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.myfavplaces.virgileflorianfrancois.myfavoriteplaces.FavPlace;
import com.myfavplaces.virgileflorianfrancois.myfavoriteplaces.FavPlaceDataSource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFavPlace extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private FavPlaceDataSource datasource;
    private Button btnAddFavPlace;
    private Button btnCurrentPlace;
    int count = 0;
    private EditText edtAdresse;
    private EditText edtVille;
    private EditText edtCP;
    private EditText edtDescr;

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    Location currentPos = null;
    FavPlace comment = null;

    LatLng pos = null;
    String idLieu ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fav_place);

        edtAdresse = (EditText) findViewById(R.id.editTextAdresse);
        edtVille = (EditText) findViewById(R.id.editTextVille);
        edtCP = (EditText) findViewById(R.id.editTextCP);
        edtDescr = (EditText) findViewById(R.id.editTextDescription);

        btnAddFavPlace = (Button) findViewById(R.id.btnAddFavPlace);
        btnCurrentPlace = (Button) findViewById(R.id.btnAddCurrentPos);
        datasource = new FavPlaceDataSource(this);
        datasource.open();

        btnCurrentPlace.setEnabled(false);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                idLieu= null;
            } else {
                idLieu = extras.getString("idlieu");
            }
        } else {
            idLieu= (String) savedInstanceState.getSerializable("idlieu");
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        btnAddFavPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAdresse.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Veuillez ajouter une adresse valide", Toast.LENGTH_LONG);
                    toast.show();
                }else if(edtVille.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Veuillez ajouter une ville valide", Toast.LENGTH_LONG);
                    toast.show();
                }else if(edtCP.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Veuillez ajouter un code postal valide", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    pos = getLocationFromAddress(edtAdresse.getText().toString() +" "+ edtVille.getText().toString() +"" + edtCP.getText().toString()); // récupération lat&long par apport a l'adresse
                    System.out.println(pos);
                    if(pos == null){
                        Toast toast = Toast.makeText(getApplicationContext(), "L'adresse saisie est invalide", Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formattedDate = df.format(c.getTime());

                        comment = datasource.createFavPlace(edtAdresse.getText().toString(), edtVille.getText().toString(), Integer.valueOf(edtCP.getText().toString()), pos.latitude, pos.longitude, edtDescr.getText().toString(), formattedDate);
                        if(idLieu != null){
                             datasource.updateFavPlace(Integer.parseInt(idLieu), edtAdresse.getText().toString(), edtVille.getText().toString(), Integer.valueOf(edtCP.getText().toString()), pos.latitude, pos.longitude, edtDescr.getText().toString(), formattedDate);
                            count++;
                            Toast toast = Toast.makeText(getApplicationContext(), "Modification effectué", Toast.LENGTH_LONG);
                            toast.show();
                        }else {
                            comment = datasource.createFavPlace(edtAdresse.getText().toString(), edtVille.getText().toString(), Integer.valueOf(edtCP.getText().toString()), pos.latitude, pos.longitude, edtDescr.getText().toString(), formattedDate);
                            count++;
                            Toast toast = Toast.makeText(getApplicationContext(), "Nouveau lieu ajouté", Toast.LENGTH_LONG);
                            toast.show();
                        }


                    }
                }

            }
        });

        btnCurrentPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> address = null;

                String postCode = "00000";
                String adresse = "";
                String ville = "";
                if (geoCoder != null) {
                    try {
                        address = geoCoder.getFromLocation(currentPos.getLatitude(), currentPos.getLongitude(), 1);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    if (address.size() > 0) {
                        postCode = address.get(0).getPostalCode();
                        adresse = address.get(0).getAddressLine(0);
                        ville = address.get(0).getLocality();
                    }
                }

                    comment = datasource.createFavPlace(adresse, ville, Integer.valueOf(postCode), currentPos.getLatitude(), currentPos.getLongitude(), edtDescr.getText().toString(), formattedDate);
                    count++;
                    Toast toast = Toast.makeText(getApplicationContext(), "Nouveau lieu ajouté", Toast.LENGTH_LONG);
                    toast.show();


            }
        });


        if(idLieu != null) {
            List<FavPlace> values = datasource.getFavPlace(idLieu);
            edtAdresse.setText(values.get(0).getAdresseFavPlace());
            edtVille.setText(values.get(0).getVilleAdresse());
            edtCP.setText(Integer.toString(values.get(0).getCPAdresse()));
            edtDescr.setText(values.get(0).getDescription());
            btnAddFavPlace.setText("Modifier");
            btnCurrentPlace.setVisibility(View.GONE);

        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {

        }
        else {
            handleNewLocation(location);
        };
    }

    private void handleNewLocation(Location location) {
        if(location == null){
            btnCurrentPlace.setEnabled(false);
        } else {
            btnCurrentPlace.setEnabled(true);
            Log.d(TAG, location.toString());
            currentPos = location; // Mise à jour de la position actuel
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    // Fonction qui récupère lat & long avec l'adresse , ville et CP
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}