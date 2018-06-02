package com.example.andrea.progettoesame.Docente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.Assenza;
import com.example.andrea.progettoesame.Studente.AssenzaAdapter;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GiustificaFragment extends Fragment {

    private static final String ARG_PARAM = "CodiceFiscale";

    private String codF;
    private RecyclerView recyclerView;
    private ArrayList<Assenza> assenze;
    private GiustificaAdapter adapter;

    private TextView textView;

    public GiustificaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codF = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giustifica, container, false);

        assenze = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.giustifica_recyclerView);
        textView = (TextView) view.findViewById(R.id.giustifica_textView);
        getAssenze();

        Button button = (Button) view.findViewById(R.id.giustifica_bottone_aggiorna);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assenze.clear();
                getAssenze();
            }
        });
        return view;
    }

    private void getAssenze() {
        String url = "http://" + InterazioneServer.URL_SERVER + "/api/getAssenzeNonGiustificate";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;

                        adapter = new GiustificaAdapter(assenze, getContext());

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(adapter);

                        try {
                            json = new JSONObject(response);
                            JSONArray array = json.getJSONArray("Assenze");
                            if (array.length() == 0) {
                                textView.setText("Nessuna assenza da giustificare");
                            } else {
                                textView.setText("Seleziona un'assenza da giustificare");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonAssenza = array.getJSONObject(i);
                                    int id = jsonAssenza.getInt("ID");
                                    String data = jsonAssenza.getString("Data");
                                    String orario = jsonAssenza.getString("Orario");
                                    String tipologia = getTipologiaGiustifica(orario);
                                    assenze.add(new Assenza(id, data, "Nessuna Descrizione", tipologia));
                                }
                            }
                            adapter.notifyDataSetChanged();
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
                params.put("codiceFiscaleStudente", codF);
                return params;
            }
        };
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }


    private String getTipologiaGiustifica(String orario) {
        if (orario.indexOf('0') < 0) {
            return "A";
        }
        char primo = orario.charAt(0);
        for (int i = 0; i < 24; i++) {
            if (primo != orario.charAt(i)) {
                return (primo == '1') ? "R" : "U";
            }
        }
        return "A";
    }
}
