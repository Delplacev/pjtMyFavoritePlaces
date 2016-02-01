package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentShowFavPlaces extends Fragment {
    private FavPlaceDataSource datasource;
    ListView listAddr;
    MonAdapteur Monadapter;
    List<FavPlace> l;

    public FragmentShowFavPlaces() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_show_fav_places, container, false);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        listAddr = (ListView) getActivity().findViewById(R.id.listViewShowFavPlaces);

        datasource = new FavPlaceDataSource(getActivity());
        datasource.open();

        List<FavPlace> values = datasource.getAllComments();

        this.l = new ArrayList(Arrays.asList(new FavPlace[]{}));
        for(int i=0; i<values.size(); i++) {
            this.l.add(values.get(i));
        }


        Monadapter = new MonAdapteur(getActivity(), R.layout.list_view_addr, this.l);

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
