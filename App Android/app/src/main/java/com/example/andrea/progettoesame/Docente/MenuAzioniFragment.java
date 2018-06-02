package com.example.andrea.progettoesame.Docente;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.andrea.progettoesame.R;


public class MenuAzioniFragment extends DialogFragment {

    private static final String ARG_PARAM = "param1";
    private static final String ARG_PARAM_COD = "param2";
    private static final String ARG_PARAM_NOM = "param3";
    private static final String ARG_PARAM_COD_CLASSE = "param4";

    private String tipologia;
    private String cfStud;
    private String classe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipologia = getArguments().getString(ARG_PARAM);
            cfStud = getArguments().getString(ARG_PARAM_COD);
            classe = getArguments().getString(ARG_PARAM_COD_CLASSE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        if (tipologia.equals("Gestione_studente")) {
            String nome = getArguments().getString(ARG_PARAM_NOM);
            String text = nome;
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent));
            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(text);
            ssBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    text.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            alertDialog.setMessage(ssBuilder);
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
            String titleText = "Gestione";
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorAccent));
            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
            ssBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    titleText.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            alertDialog.setTitle(ssBuilder);
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

    public String getCodFStud() {
        return cfStud;
    }

    public String getCodClasse() {
        System.out.println("_______________________________________________---");
        System.out.println(classe);
        return classe;
    }
}