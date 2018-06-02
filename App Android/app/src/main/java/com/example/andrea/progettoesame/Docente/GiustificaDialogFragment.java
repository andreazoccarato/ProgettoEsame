package com.example.andrea.progettoesame.Docente;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.R;

/**
 * Created by andrea on 18/05/2018.
 */

public class GiustificaDialogFragment extends DialogFragment {

    private static final String ARG_PARAM_ID = "idGiustifica";
    private static final String ARG_PARAM_TYPE = "tipologia";
    private static final String ARG_PARAM_POS = "position";

    private int idGiustifica;
    private String tipologia;
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGiustifica = getArguments().getInt(ARG_PARAM_ID);
            tipologia = getArguments().getString(ARG_PARAM_TYPE);
            pos = getArguments().getInt(ARG_PARAM_POS);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String titleText = "Giustifica Assenza";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(ssBuilder);
        alertDialog.setView(R.layout.fragment_dialog_giustifica);
        alertDialog.setPositiveButton("Giustifica", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText editText = (EditText) getDialog().findViewById(R.id.giustifica_dialog_editText);
                String descrizione = editText.getText().toString();
                if (descrizione.equals("")) {
                    Toast.makeText(getContext(), "Descrizione non valida", Toast.LENGTH_SHORT).show();
                } else {
                    Pair pair = ((DocenteActivity) getActivity()).getData();
                    InterazioneServer interazioneServer = new InterazioneServer(getContext(), (String) pair.first, (String) pair.second);
                    interazioneServer.giustifica(idGiustifica, descrizione, tipologia);
                }
                dialog.dismiss();
            }
        });

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

