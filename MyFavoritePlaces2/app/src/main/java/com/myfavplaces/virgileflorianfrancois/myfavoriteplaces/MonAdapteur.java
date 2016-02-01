package com.myfavplaces.virgileflorianfrancois.myfavoriteplaces;

/**
 * Created by virgiledelplace on 28/01/16.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class MonAdapteur extends ArrayAdapter<FavPlace> {
    List<FavPlace> l;
    Context c;
    int r;

    public MonAdapteur(Context context, int resource, List<FavPlace> objects) {
        super(context, resource, objects);
        this.l = objects;
        this.c = context;
        this.r = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)this.c).getLayoutInflater();
        View v = inflater.inflate(this.r, parent, false);
        //String elt = (String)this.l.get(position);

        TextView tvAddr = (TextView)v.findViewById(R.id.textViewAddr);
        tvAddr.setText(this.l.get(position).getAdresseFavPlace());
        TextView tvVille = (TextView)v.findViewById(R.id.textViewVille);
        tvVille.setText(this.l.get(position).getVilleAdresse() + " - "+(Integer.toString(this.l.get(position).getCPAdresse())));
        TextView tvCP = (TextView)v.findViewById(R.id.textViewCP);
        tvCP.setText(this.l.get(position).getDate());

        return v;
    }
}
