package com.example.andrea.progettoesame.Studente;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrea.progettoesame.R;

import java.util.ArrayList;

/**
 * Created by andrea on 19/04/2018.
 */

public class AssenzaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    private ArrayList<Assenza> assenze;

    public AssenzaAdapter(ArrayList assenze) {
        this.assenze = assenze;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            view = inflater.inflate(R.layout.header_item, parent, false);
            return new com.example.andrea.progettoesame.EventiAdapter.ViewHolderHeader(view);
        } else {
            view = inflater.inflate(R.layout.row_layout_assenze, parent, false);
            return new com.example.andrea.progettoesame.EventiAdapter.ViewHolderItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderHeader holderHeader = (ViewHolderHeader) holder;
                Assenza assenza = assenze.get(position);
                holderHeader.setTitle(assenza.getDescrizione());
                break;
            case 1:
                ViewHolderItem holderItem = (ViewHolderItem) holder;
                Assenza as = assenze.get(position);
                holderItem.setItem(as);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return assenze.size();
    }

    @Override
    public int getItemViewType(int position) {
        return assenze.get(position).itemType;
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {

        private Assenza assenza;
        private TextView tipo;
        private TextView data;
        private TextView descrizione;

        public ViewHolderItem(View itemView) {
            super(itemView);
            data = (TextView) itemView.findViewById(R.id.assenza_giorno);
            tipo = (TextView) itemView.findViewById(R.id.assenza_giustificato);
            descrizione = (TextView) itemView.findViewById(R.id.assenza_descrizione);
        }

        void setItem(Assenza assenza) {
            this.assenza = assenza;
            tipo.setText(assenza.getTipologia());
            switch (assenza.getTipologia()) {
                case "R":
                    tipo.setBackgroundColor(R.drawable.circle_orange);
                    break;
                case "A":
                    tipo.setBackgroundColor(R.drawable.circle_red);
                    break;
                case "U":
                    tipo.setBackgroundColor(R.drawable.circle_orange);
                    break;
            }
            data.setText(assenza.getData());
            descrizione.setText(assenza.getDescrizione());
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

