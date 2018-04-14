package com.example.andrea.progettoesame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificaProfiloFragment extends DialogFragment {

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
                            String url = "http://192.168.1.104:8000/api/setProfilo";
                            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.println("------------RISPOSTA------------");
                                            System.out.println("----------------------------------------" + response);
                                            JSONObject json = null;
                                            String risultato = "";
                                            try {
                                                json = new JSONObject(response);
                                                risultato = json.getString("RisultatoModifica");
                                                Toast.makeText(getContext(), risultato, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    Pair p = ((StudenteActivity) getActivity()).getData();
                                    params.put("username", (String) p.first);
                                    params.put("password", (String) p.second);
                                    params.put("newPassword", newPassword);
                                    params.put("newUsername", newUsername);
                                    return params;
                                }
                            };
                            postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
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
