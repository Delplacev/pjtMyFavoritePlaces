package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowFavPlace extends AppCompatActivity {
    private FavPlaceDataSource datasource;
    ListView listAddr;
    Button btnTrieAZ;
    Button btnTrieDate;
    MonAdapteur Monadapter;
    List<FavPlace> l;
    private String val = "Z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fav_place);

        listAddr = (ListView)findViewById(R.id.listViewAddr);
        registerForContextMenu(listAddr);
        btnTrieAZ = (Button)findViewById(R.id.btAZ);
        btnTrieDate = (Button)findViewById(R.id.btDate);
        datasource = new FavPlaceDataSource(this);
        datasource.open();

        List<FavPlace> values = datasource.getAllFavPlaces("");

        l = new ArrayList(Arrays.asList(new FavPlace[]{}));
        for(int i=0; i<values.size(); i++) {
            l.add(values.get(i));
        }


        Monadapter = new MonAdapteur(this, R.layout.list_view_addr, this.l);

        listAddr.setAdapter(Monadapter);



        listAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mapsItent = new Intent(ShowFavPlace.this, MapsActivity.class);
                FavPlace favPlace = null;
                favPlace = (FavPlace) listAddr.getAdapter().getItem(i);
                mapsItent.putExtra("FavPlaceSelected", Integer.toString(favPlace.getIdFavPlace()));
                startActivity(mapsItent);

            }
        });



        btnTrieAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (val == "A") {
                    val = "Z";
                } else {
                    val = "A";
                }

                List<FavPlace> values = datasource.getAllFavPlaces(val);

                l = new ArrayList(Arrays.asList(new FavPlace[]{}));
                for (int i = 0; i < values.size(); i++) {
                    l.add(values.get(i));
                }


                Monadapter = new MonAdapteur(ShowFavPlace.this, R.layout.list_view_addr, l);

                listAddr.setAdapter(Monadapter);

                Monadapter.notifyDataSetChanged();
            }
        });

        btnTrieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val == "Date ASC") {
                    val = "Date DSC";
                } else {
                    val = "Date ASC";
                }


                List<FavPlace> values = datasource.getAllFavPlaces(val);

                l = new ArrayList(Arrays.asList(new FavPlace[]{}));
                for (int i = 0; i < values.size(); i++) {
                    l.add(values.get(i));
                }


                Monadapter = new MonAdapteur(ShowFavPlace.this, R.layout.list_view_addr, l);

                listAddr.setAdapter(Monadapter);
                Monadapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listViewAddr) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final long iditem = info.id;
        switch(item.getItemId()) {
            case R.id.edit:
                Intent update = new Intent(ShowFavPlace.this,AddFavPlace.class);
                FavPlace favPlace = null;
                favPlace = (FavPlace) listAddr.getAdapter().getItem((int)iditem);
                update.putExtra("idlieu", Integer.toString(favPlace.getIdFavPlace()));
                startActivity(update);
                return true;
            case R.id.delete:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowFavPlace.this);
                builder1.setMessage("Voulez vous supprimer cette endroit ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FavPlace favPlace = null;
                                favPlace = (FavPlace) listAddr.getAdapter().getItem((int)iditem);
                                datasource.deleteFavPlace(favPlace);
                                Monadapter.remove(favPlace);
                                Monadapter.notifyDataSetChanged();
                                Toast toast = Toast.makeText(getApplicationContext(), "Lieu supprimé avec succès", Toast.LENGTH_LONG);
                                toast.show();

                            }
                        });

                builder1.setNegativeButton(
                        "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
