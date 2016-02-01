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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.drawable.ic_marker);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAddPlace = (Button)findViewById(R.id.btnAddPlace);
        btnShowPlace = (Button)findViewById(R.id.btnShowPlace);

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


    }
}
