package com.example.andrea.progettoesame;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 14/03/2018.
 */

public class EventiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    private Activity context;
    private ArrayList<Evento> eventi;

    public EventiAdapter(Activity context, ArrayList eventi) {
        this.context = context;
        this.eventi = eventi;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            view = inflater.inflate(R.layout.header_item, parent, false);
            return new ViewHolderHeader(view);
        } else {
            view = inflater.inflate(R.layout.row_layout_evento, parent, false);
            return new ViewHolderItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
                Evento evento = eventi.get(position);
                holderHeader.setTitle(evento.descrizione);
                break;
            case 1:
                ViewHolderItem holderItem = (ViewHolderItem) holder;
                Evento ev = eventi.get(position);
                holderItem.setItem(ev);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return eventi.size();
    }

    @Override
    public int getItemViewType(int position) {
        return eventi.get(position).itemType;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {

        private Evento evento;
        private TextView materia;
        private TextView data;
        private TextView descrizione;

        public ViewHolderItem(View itemView) {
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

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {

        private TextView titolo;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            titolo = (TextView) itemView.findViewById(R.id.eventi_titolo);
        }

        void setTitle(String title) {
            titolo.setText(title);
        }

    }

}
