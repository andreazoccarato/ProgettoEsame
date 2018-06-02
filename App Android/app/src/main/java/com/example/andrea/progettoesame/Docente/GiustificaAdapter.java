package com.example.andrea.progettoesame.Docente;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.Assenza;

import java.util.ArrayList;

/**
 * Created by andrea on 18/05/2018.
 */

public class GiustificaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Assenza> assenze;
    private Context context;

    public GiustificaAdapter(ArrayList assenze, Context context) {
        this.assenze = assenze;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_layout_assenze, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holderItem = (ViewHolder) holder;
        Assenza as = assenze.get(position);
        holderItem.setItem(as);
        holderItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((DocenteActivity) context).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("idGiustifica", assenze.get(position).getId());
                bundle.putString("tipologia", assenze.get(position).getTipologia());
                bundle.putInt("position", position);
                GiustificaDialogFragment dialogFragment = new GiustificaDialogFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(manager, "Giustifica Dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return assenze.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Assenza assenza;
        private TextView tipo;
        private TextView data;
        private TextView descrizione;

        public ViewHolder(View itemView) {
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
                    tipo.setBackgroundResource(R.drawable.circle_orange);
                    break;
                case "A":
                    tipo.setBackgroundResource(R.drawable.circle_red);
                    break;
                case "U":
                    tipo.setBackgroundResource(R.drawable.circle_orange);
                    break;
            }
            data.setText(assenza.getData());
            descrizione.setText(assenza.getDescrizione());
        }
    }
}
