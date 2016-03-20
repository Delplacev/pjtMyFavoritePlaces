package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnAddPlace;
    Button btnShowPlace;
    Button btnPower;
    Button btnListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.drawable.ic_marker); //Ajout d'une icone à l'action bar
        getSupportActionBar().setDisplayUseLogoEnabled(true); // logo actif
        getSupportActionBar().setDisplayShowHomeEnabled(true); // activation de la fleche retour

        btnAddPlace = (Button)findViewById(R.id.btnAddPlace);
        btnShowPlace = (Button)findViewById(R.id.btnShowPlace);
        btnPower = (Button)findViewById(R.id.btnPower);
        btnListe = (Button)findViewById(R.id.btnListeFav);

        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFavPlace.class);
                startActivity(intent);
            }
        });

        btnShowPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        btnListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowFavPlace.class);
                startActivity(intent);
            }
        });

        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}
