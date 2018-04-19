package com.example.andrea.progettoesame.Docente;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrea.progettoesame.R;

import java.util.ArrayList;

/**
 * Created by andrea on 16/04/2018.
 */

public class ListaStudentiAdapter extends RecyclerView.Adapter<ListaStudentiAdapter.ViewHolder> {

    private static ArrayList<Studente> studenti;

    public ListaStudentiAdapter(ArrayList<Studente> studenti) {
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
        holder.setItem(stud, (position+1));
    }

    @Override
    public int getItemCount() {
        return studenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView position;
        private TextView nomininativo;
        private TextView data_nascita;

        public ViewHolder(View itemView) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.lista_studenti_pos);
            nomininativo = (TextView) itemView.findViewById(R.id.lista_studenti_nome);
            data_nascita = (TextView) itemView.findViewById(R.id.lista_studenti_data);
        }

        public void setItem(Studente studente, int pos) {
            this.position.setText(""+pos);
            this.nomininativo.setText(studente.getCognome() + " " + studente.getNome());
            this.data_nascita.setText(studente.getDataNascita());
        }

    }
}
