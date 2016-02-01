package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowFavPlace extends AppCompatActivity {
    private FavPlaceDataSource datasource;
    ListView listAddr;
    MonAdapteur Monadapter;
    List<FavPlace> l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fav_place);

        listAddr = (ListView)findViewById(R.id.listViewAddr);

        datasource = new FavPlaceDataSource(this);
        datasource.open();

        List<FavPlace> values = datasource.getAllComments();

        this.l = new ArrayList(Arrays.asList(new FavPlace[]{}));
        for(int i=0; i<values.size(); i++) {
            this.l.add(values.get(i));
        }


        Monadapter = new MonAdapteur(this, R.layout.list_view_addr, this.l);

        listAddr.setAdapter(Monadapter);



        listAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FavPlace favPlace = null;
                favPlace = (FavPlace) listAddr.getAdapter().getItem(i);
                datasource.deleteComment(favPlace);
                Monadapter.remove(favPlace);
                Monadapter.notifyDataSetChanged();
            }
        });

    }
}
