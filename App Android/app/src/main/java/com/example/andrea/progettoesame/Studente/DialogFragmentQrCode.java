package com.example.andrea.progettoesame.Studente;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DialogFragmentQrCode extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Check");
        alertDialog.setView(R.layout.fragment_dialog_fragment_qr_code);
        alertDialog.setPositiveButton("Presenza",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setPresenza("presenza");
                        dialog.dismiss();
                    }
                }
        );
        alertDialog.setNegativeButton("Assenza",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setPresenza("assenza");
                        dialog.dismiss();
                    }
                }
        );
        return alertDialog.create();
    }

    private void setPresenza(final String type) {
        String url = "http://192.168.1.104:8000/api/setPresenza";
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
                            risultato = json.getString("qrCodeCheck");
                            Toast.makeText(getActivity(), risultato, Toast.LENGTH_LONG).show();
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
                String[] s = ((StudenteActivity) getActivity()).getResult();
                params.put("username", (String) p.first);
                params.put("password", (String) p.second);
                params.put("scuola", s[0]);
                params.put("'classe", s[1]);
                params.put("'data'", s[2]);
                params.put("'codice'", s[3]);
                params.put("presenzaOassenza", type);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);
    }

}
