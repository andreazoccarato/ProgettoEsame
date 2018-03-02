package com.example.andrea.progettoesame.Studente;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrea.progettoesame.R;

import java.util.ArrayList;

/**
 * Created by andrea on 02/03/2018.
 */

public class VotiArrayAdapter extends ArrayAdapter<Voto> {

    private Activity context;
    private ArrayList<Voto> voti;

    private static final String TAG = "ViewHolder";

    // this object will be tag
    static class ViewHolder {
        public TextView voto;
        public TextView materia;
        public TextView data;
        public TextView description;
    }

    public VotiArrayAdapter(Activity context, ArrayList voti) {
        super(context, R.layout.row_layout_voti ,voti);
        this.context=context;
        this.voti=voti;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        Voto v = voti.get(position);

        // reuse view: ViewHolder pattern
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_voti, null);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.materia = (TextView) rowView.findViewById(R.id.voti_materia);
            viewHolder.data= (TextView) rowView.findViewById(R.id.voti_data);
            viewHolder.description = (TextView) rowView.findViewById(R.id.voti_descrizione);
            viewHolder.voto = (TextView) rowView.findViewById(R.id.voto);
            // take memory of the view
            rowView.setTag(viewHolder);
            // Log steTag()
            Log.d(TAG, "setTag() for object in position: " + position + "; product name: " + v.getVoto());
        } else {
            // reuse the object
            viewHolder = (ViewHolder) rowView.getTag();
            // Log steTag()
            Log.d(TAG, "getTag() for object in position: " + position);
        }
        String voto=v.getVoto();

        switch (voto){
            case "3":
                viewHolder.voto.setText("3");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "3+":
                viewHolder.voto.setText("3+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "3 1/2":
                viewHolder.voto.setText("3 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "3/4":
                viewHolder.voto.setText("3/4");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "4-":
                viewHolder.voto.setText("4-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "4":
                viewHolder.voto.setText("4");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "4+":
                viewHolder.voto.setText("4+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "4 1/2":
                viewHolder.voto.setText("4 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "4/5":
                viewHolder.voto.setText("4/5");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "5-":
                viewHolder.voto.setText("5-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "5":
                viewHolder.voto.setText("5");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "5+":
                viewHolder.voto.setText("5+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "5 1/2":
                viewHolder.voto.setText("5 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "5/6":
                viewHolder.voto.setText("5/6");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "6-":
                viewHolder.voto.setText("6-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_red);
                break;
            case "6":
                viewHolder.voto.setText("6");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "6+":
                viewHolder.voto.setText("6+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "6 1/2":
                viewHolder.voto.setText("6 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "6/7":
                viewHolder.voto.setText("6/7");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "7-":
                viewHolder.voto.setText("7-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "7":
                viewHolder.voto.setText("7");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "7+":
                viewHolder.voto.setText("7+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "7 1/2":
                viewHolder.voto.setText("7 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "7/8":
                viewHolder.voto.setText("7/8");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "8-":
                viewHolder.voto.setText("8-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "8":
                viewHolder.voto.setText("8");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "8+":
                viewHolder.voto.setText("8+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "8 1/2":
                viewHolder.voto.setText("8 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "8/9":
                viewHolder.voto.setText("8/9");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "9-":
                viewHolder.voto.setText("9-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "9":
                viewHolder.voto.setText("9");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "9+":
                viewHolder.voto.setText("9+");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "9 1/2":
                viewHolder.voto.setText("9 ½");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "9/10":
                viewHolder.voto.setText("9/10");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "10-":
                viewHolder.voto.setText("10-");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;
            case "10":
                viewHolder.voto.setText("10");
                viewHolder.voto.setBackgroundResource(R.drawable.circle_green);
                break;

        }
        viewHolder.materia.setText(v.getMateria());
        viewHolder.data.setText(v.getData());
        viewHolder.description.setText(v.getDescrizione());
        return rowView;
    }
}
