package com.example.andrea.progettoesame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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


public class ProfiloFragment extends Fragment {

    public ProfiloFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilo, container, false);

        final TextView nome = (TextView) view.findViewById(R.id.profilo_nome);
        final TextView cognome = (TextView) view.findViewById(R.id.profilo_cognome);
        final TextView dataNascita = (TextView) view.findViewById(R.id.profilo_dataNascita);

        String url = "http://192.168.1.104:8000/api/getProfilo";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println("----------------------------------------" + response);
                        JSONObject json = null;
                        String info = "";
                        try {
                            json = new JSONObject(response);
                            info = json.getString("Info");
                            info = info.replaceAll("/", "");
                            info = info.replaceAll("\"", "");
                            info = info.replaceAll(":", ",");
                            info = info.replaceAll("\\}]", " ");
                            String app[] = info.split(",");
                            nome.setText(app[1]);
                            cognome.setText(app[3]);
                            dataNascita.setText(app[5]);
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
                Map<String, String> params = new HashMap<>();
                Pair p = ((StudenteActivity) getActivity()).getData();
                params.put("username", (String) p.first);
                params.put("password", (String) p.second);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);

        TextView textView = (TextView) view.findViewById(R.id.profilo_modifica);

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ModificaProfiloFragment dialogFragment = new ModificaProfiloFragment();
                dialogFragment.show(getFragmentManager(), "Modifica Profilo");
            }
        });
        return view;
    }

}
