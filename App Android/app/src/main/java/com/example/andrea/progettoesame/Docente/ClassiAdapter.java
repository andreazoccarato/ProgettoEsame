package com.example.andrea.progettoesame.Docente;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.VotiArrayAdapter;
import com.example.andrea.progettoesame.Studente.Voto;

import java.util.ArrayList;

/**
 * Created by andrea on 12/04/2018.
 */

public class ClassiAdapter extends ArrayAdapter<Classe> {

    private Activity context;
    private ArrayList<Classe> classi;

    private static final String TAG = "ViewHolder";

    public static class ViewHolder {
        public TextView clsez;
        public TextView indirizzo;
        public TextView intermezzo;
        public TextView scuola;
    }

    public ClassiAdapter(Activity context, ArrayList classi) {
        super(context, R.layout.row_layout_classi, classi);
        this.context = context;
        this.classi = classi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ClassiAdapter.ViewHolder viewHolder;

        Classe c = classi.get(position);

        // reuse view: ViewHolder pattern
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_classi, null);
            // configure view holder
            viewHolder = new ClassiAdapter.ViewHolder();
            viewHolder.clsez = (TextView) rowView.findViewById(R.id.classi_clsez);
            viewHolder.indirizzo = (TextView) rowView.findViewById(R.id.classi_indirizzo);
            viewHolder.intermezzo = (TextView) rowView.findViewById(R.id.classi_intermezzo);
            viewHolder.scuola = (TextView) rowView.findViewById(R.id.classi_scuola);
            // take memory of the view
            rowView.setTag(viewHolder);
            // Log steTag()
            Log.d(TAG, "setTag() for object in position: " + position + "; product name: " + c.getClsez());
        } else {
            // reuse the object
            viewHolder = (ClassiAdapter.ViewHolder) rowView.getTag();
            // Log steTag()
            Log.d(TAG, "getTag() for object in position: " + position);
        }
        if((position % 2) == 0){
            viewHolder.intermezzo.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.intermezzo.setBackgroundColor(Color.CYAN);
        }
        viewHolder.clsez.setText(c.getClsez());
        viewHolder.indirizzo.setText(c.getIndirizzo());
        viewHolder.scuola.setText(c.getScuola());
        return rowView;
    }
}
