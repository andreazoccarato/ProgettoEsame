package com.example.andrea.progettoesame.Docente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AggiungiEventoFragment extends Fragment {

    public static final String ARG_PARAM_ID_CLASSE = "codiceClasse";

    private String idClasse;

    public AggiungiEventoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idClasse = getArguments().getString(ARG_PARAM_ID_CLASSE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungi_evento, container, false);

        final EditText dataEditText = (EditText) view.findViewById(R.id.inserisci_evento_data);
        final EditText materiaEditText = (EditText) view.findViewById(R.id.inserisci_evento_materia);
        final EditText descrizioneEditText = (EditText) view.findViewById(R.id.inserisci_evento_descrizione);

        Button button = (Button) view.findViewById(R.id.inserisci_evento_bottone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = dataEditText.getText().toString();
                String materia = materiaEditText.getText().toString();
                String descrizione = descrizioneEditText.getText().toString();

                if (data.equals("") || materia.equals("") || descrizione.equals("")) {
                    Toast.makeText(getContext(), "I campi non possono essere vuoti", Toast.LENGTH_SHORT).show();
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateFormat.setLenient(false);
                    Pair<String, String> pair = ((DocenteActivity) getActivity()).getData();
                    try {
                        dateFormat.parse(data);
                    } catch (ParseException e) {
                        Toast.makeText(getContext(), "Data non valida", Toast.LENGTH_SHORT).show();
                    }
                    InterazioneServer interazioneServer = new InterazioneServer(getContext(), (String) pair.first, (String) pair.second);
                    interazioneServer.addEvento(data, materia, descrizione, idClasse);
                }
            }
        });

        return view;
    }
}
