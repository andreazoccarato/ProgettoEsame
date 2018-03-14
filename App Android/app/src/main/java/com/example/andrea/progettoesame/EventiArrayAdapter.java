package com.example.andrea.progettoesame;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 14/03/2018.
 */

public class EventiArrayAdapter extends ArrayAdapter<Evento> {

    private Activity context;
    private ArrayList<Evento> eventi;

    private static final String TAG = "ViewHolder";

    // this object will be tag
    public static class ViewHolder {
        public TextView materia;
        public TextView data;
        public TextView description;
    }

    public EventiArrayAdapter(Activity context, ArrayList eventi) {
        super(context, R.layout.row_layout_evento ,eventi);
        this.context=context;
        this.eventi=eventi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        EventiArrayAdapter.ViewHolder viewHolder;

        Evento e = eventi.get(position);

        // reuse view: ViewHolder pattern
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_evento, null);
            // configure view holder
            viewHolder = new EventiArrayAdapter.ViewHolder();
            viewHolder.materia = (TextView) rowView.findViewById(R.id.eventi_materia);
            viewHolder.data = (TextView) rowView.findViewById(R.id.eventi_data);
            viewHolder.description = (TextView) rowView.findViewById(R.id.eventi_descrizione);
            // take memory of the view
            rowView.setTag(viewHolder);
            // Log steTag()
        } else {
            // reuse the object
            viewHolder = (EventiArrayAdapter.ViewHolder) rowView.getTag();
            // Log steTag()
            Log.d(TAG, "getTag() for object in position: " + position);
        }
        viewHolder.materia.setText(e.getMateria());
        viewHolder.data.setText(e.getData());
        viewHolder.description.setText(e.getDescrizione());
        return rowView;
    }
}
