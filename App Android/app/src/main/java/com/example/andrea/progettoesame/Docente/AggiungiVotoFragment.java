package com.example.andrea.progettoesame.Docente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

public class AggiungiVotoFragment extends Fragment {

    private static final String ARG_PARAM = "CodiceFiscale";

    private String codF;

    public AggiungiVotoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codF = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungi_voto, container, false);

        final Spinner spinner = (Spinner) view.findViewById(R.id.aggiungi_voto_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.voti_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText materia = (EditText) view.findViewById(R.id.aggiungi_voto_materia);
        final EditText descrizione = (EditText) view.findViewById(R.id.aggiungi_voto_descrizione);
        Button button = (Button) view.findViewById(R.id.aggiungi_voto_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = materia.getText().toString();
                String d = descrizione.getText().toString();
                String voto = spinner.getSelectedItem().toString();
                if (voto.equals("")) {
                    Toast.makeText(getContext(), "Nessun voto selezionato", Toast.LENGTH_SHORT).show();
                } else if (m.equals("")) {
                    Toast.makeText(getContext(), "Nessun materia impostata", Toast.LENGTH_SHORT).show();
                } else {
                    Pair pair = ((DocenteActivity) getActivity()).getData();
                    InterazioneServer interazioneServer = new InterazioneServer(getContext(), (String) pair.first, (String) pair.second);
                    if (d.equals("")) {
                        interazioneServer.aggiungiVoto(voto, m, "Nessuna Descrizione", codF);
                    } else {
                        interazioneServer.aggiungiVoto(voto, m, d,codF);
                    }
                }
            }
        });

        return view;
    }

}
