package com.example.andrea.progettoesame.Docente;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.andrea.progettoesame.R;


public class MenuAzioniFragment extends DialogFragment {

    private static final String ARG_PARAM = "param1";
    private static final String ARG_PARAM_COD = "param2";
    private static final String ARG_PARAM_NOM = "param3";

    private String tipologia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipologia = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        if (tipologia.equals("Gestione_studente")) {
            String nome = getArguments().getString(ARG_PARAM_NOM);
            alertDialog.setMessage(nome);
            alertDialog.setView(R.layout.fragment_menu_azioni_studente);
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }
            );
            return alertDialog.create();
        } else {
            alertDialog.setView(R.layout.fragment_menu_azioni_generali);
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    }
            );
            return alertDialog.create();
        }

    }

}
