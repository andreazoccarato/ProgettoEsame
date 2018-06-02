package com.example.andrea.progettoesame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.Docente.DocenteActivity;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificaProfiloFragment extends DialogFragment {

    public static final String ARG_PARAM_TYPE = "tipologia";

    private String tipologia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipologia = getArguments().getString(ARG_PARAM_TYPE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Modifica Profilo");
        alertDialog.setView(R.layout.fragment_modifica_profilo);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Pair p = ((StudenteActivity) getActivity()).getData();
                        String currentPassword = ((EditText) getDialog().findViewById(R.id.Modifica_Current_password)).getText().toString();
                        final String newPassword = ((EditText) getDialog().findViewById(R.id.Modifica_New_Password)).getText().toString();
                        String confirmPassword = ((EditText) getDialog().findViewById(R.id.Modifica_Confirm_Password)).getText().toString();
                        final String newUsername = ((EditText) getDialog().findViewById(R.id.Modifica_New_Username)).getText().toString();
                        if (currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("") || newUsername.equals("")) {
                            Toast.makeText(getContext(), "I campi non possono essere vuoti", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else if (!currentPassword.equals(p.second)) {
                            Toast.makeText(getContext(), "Le password corrente non è corretta", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else if (!confirmPassword.equals(newPassword)) {
                            Toast.makeText(getContext(), "Le password sono diverse", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Pair pair;
                            if (tipologia.equals("studente")) {
                                pair = ((StudenteActivity) getActivity()).getData();
                            } else {
                                pair = ((DocenteActivity) getActivity()).getData();
                            }
                            InterazioneServer interazioneServer = new InterazioneServer(getContext(), (String) pair.first, (String) pair.second);
                            interazioneServer.modificaProfilo(newUsername, newPassword);
                        }
                    }
                }
        );
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
