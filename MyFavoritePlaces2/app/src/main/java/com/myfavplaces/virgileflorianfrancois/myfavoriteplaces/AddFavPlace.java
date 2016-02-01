package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddFavPlace extends AppCompatActivity implements
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

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        btnAddFavPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());


                FavPlace comment = null;
                LatLng pos = getLocationFromAddress(edtAdresse.getText().toString() +" "+ edtVille.getText().toString() +"" + edtCP.getText().toString()); // récupération lat&long par apport a l'adresse
                comment = datasource.createComment(edtAdresse.getText().toString(), edtVille.getText().toString(), Integer.valueOf(edtCP.getText().toString()), pos.latitude, pos.longitude, edtDescr.getText().toString(), formattedDate);
                count++;
                Toast toast = Toast.makeText(getApplicationContext(), "ajout", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        btnCurrentPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                FavPlace comment = null;
                comment = datasource.createComment(edtAdresse.getText().toString(), edtVille.getText().toString(), Integer.valueOf(edtCP.getText().toString()), currentPos.getLatitude(), currentPos.getLongitude(), edtDescr.getText().toString(), formattedDate);
                count++;
                Toast toast = Toast.makeText(getApplicationContext(), "ajout", Toast.LENGTH_LONG);
                toast.show();
            }
        });

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
