package com.example.andrea.progettoesame.Docente;

import android.content.Context;
import android.net.Uri;
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

public class FirmaFragment extends Fragment {

    public static final String ARG_PARAM_ID_CLASSE = "codiceClasse";

    private String idClasse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idClasse = getArguments().getString(ARG_PARAM_ID_CLASSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_firma, container, false);

        final Spinner spinner = (Spinner) view.findViewById(R.id.firma_nOre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ore_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText editTextMateria = (EditText) view.findViewById(R.id.inserisci_firma_materia);
        final EditText editTextDesc = (EditText) view.findViewById(R.id.inserisci_firma_descrizione);

        Button button = (Button) view.findViewById(R.id.inserisci_firma_bottone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String materia = editTextMateria.getText().toString().trim();
                    String descrizione = editTextDesc.getText().toString().trim();
                    System.out.println(materia);
                    System.out.println(descrizione);
                    String nOre = spinner.getSelectedItem().toString();
                    System.out.println("--------------------------------------------------------------------------------");
                    System.out.println(nOre);
                    if (materia.equals("") || descrizione.equals("") || nOre.equals("")) {
                        Toast.makeText(getContext(), "I campi non possono essere vuoti", Toast.LENGTH_SHORT).show();
                    } else {
                        Pair<String, String> pair = ((DocenteActivity) getActivity()).getData();
                        InterazioneServer interazioneServer = new InterazioneServer(getContext(), (String) pair.first, (String) pair.second);
                        interazioneServer.firma(materia, descrizione, idClasse, nOre);
                    }
                } catch (Exception ex) {
                    Toast.makeText(getContext(), "Impossibile aggiungere la firma", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}
