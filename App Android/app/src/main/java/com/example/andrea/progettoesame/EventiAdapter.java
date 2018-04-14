package com.example.andrea.progettoesame;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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

public class EventiAdapter extends RecyclerView.Adapter<EventiAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Evento> eventi;

    public EventiAdapter(Activity context, ArrayList eventi) {
        this.context = context;
        this.eventi = eventi;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_evento, parent, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Evento ev = eventi.get(position);
        holder.setItem(ev);
    }

    @Override
    public int getItemCount() {
        return eventi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Evento evento;
        private TextView materia;
        private TextView data;
        private TextView descrizione;

        public ViewHolder(View itemView) {
            super(itemView);
            data = (TextView) itemView.findViewById(R.id.eventi_data);
            materia = (TextView) itemView.findViewById(R.id.eventi_materia);
            descrizione = (TextView) itemView.findViewById(R.id.eventi_descrizione);
        }

        void setItem(Evento evento) {
            this.evento = evento;
            materia.setText(evento.getMateria());
            data.setText(evento.getData());
            descrizione.setText(evento.getDescrizione());
        }

    }

}
