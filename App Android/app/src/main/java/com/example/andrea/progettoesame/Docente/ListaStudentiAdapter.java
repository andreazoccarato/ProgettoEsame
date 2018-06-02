package com.example.andrea.progettoesame.Docente;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrea.progettoesame.R;

import java.util.ArrayList;

/**
 * Created by andrea on 16/04/2018.
 */

public class ListaStudentiAdapter extends RecyclerView.Adapter<ListaStudentiAdapter.ViewHolder> {

    private static ArrayList<Studente> studenti;
    private Context context;

    public ListaStudentiAdapter(Context context, ArrayList<Studente> studenti) {
        this.context = context;
        this.studenti = studenti;
    }

    @Override
    public ListaStudentiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_lista_studenti, parent, false);
        return new ListaStudentiAdapter.ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ListaStudentiAdapter.ViewHolder holder, int position) {
        Studente stud = studenti.get(position);
        holder.setItem(stud, (position + 1));
    }

    @Override
    public int getItemCount() {
        return studenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView position;
        private TextView nomininativo;
        private TextView data_nascita;

        public ViewHolder(View itemView) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.lista_studenti_pos);
            nomininativo = (TextView) itemView.findViewById(R.id.lista_studenti_nome);
            data_nascita = (TextView) itemView.findViewById(R.id.lista_studenti_data);
            itemView.setOnClickListener(this);
        }

        public void setItem(Studente studente, int pos) {
            this.position.setText("" + pos);
            this.nomininativo.setText(studente.getCognome() + " " + studente.getNome());
            this.data_nascita.setText(studente.getDataNascita());
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position

            FragmentManager manager = ((DocenteActivity) context).getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("param1", "Gestione_studente");
            bundle.putString("param2", "" + studenti.get(position).getCodF());
            bundle.putString("param3", "" + studenti.get(position).getCognome() + " " + studenti.get(position).getNome());
            bundle.putString("param4", "");
            MenuAzioniFragment dialogFragment = new MenuAzioniFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(manager, "DIALOG");
        }
    }
}
