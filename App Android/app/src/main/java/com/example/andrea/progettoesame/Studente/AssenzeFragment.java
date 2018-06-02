package com.example.andrea.progettoesame.Studente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.Evento;
import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssenzeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private ArrayList<Assenza> assenze;

    public AssenzeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assenze, container, false);
        assenze = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.assenze_recyclerView);
        getEventi();
        return view;
    }

    private void getEventi() {
        String url = "http://" + InterazioneServer.URL_SERVER + "/api/getAssenze";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println(response);
                        JSONObject json = null;

                        AssenzaAdapter adapter = new AssenzaAdapter(assenze);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(adapter);

                        try {
                            json = new JSONObject(response);
                            JSONArray array = json.getJSONArray("Assenze");

                            JSONObject giustificate = array.getJSONObject(0);
                            JSONArray arrayGiustificate = giustificate.getJSONArray("Giustificate");

                            JSONObject nonGiustificate = array.getJSONObject(1);
                            JSONArray arrayNonGiustificate = nonGiustificate.getJSONArray("NonGiustificate");

                            if (arrayGiustificate.length() == 0 && arrayNonGiustificate.length() == 0) {
                                Assenza assenza_header = new Assenza("", "Nessuna Assenza", true, "", AssenzaAdapter.VIEW_TYPE_HEADER);
                                assenze.add(assenza_header);
                                adapter.notifyDataSetChanged();
                            } else {
                                if (arrayGiustificate.length() != 0) {
                                    Assenza assenza_header = new Assenza("", "Giustificate", true, "", AssenzaAdapter.VIEW_TYPE_HEADER);
                                    assenze.add(assenza_header);
                                    for (int i = 0; i < arrayGiustificate.length(); i++) {
                                        JSONObject jsonAssenzaGiustificata = arrayGiustificate.getJSONObject(i);
                                        String data = jsonAssenzaGiustificata.getString("Data");
                                        String descrizione = jsonAssenzaGiustificata.getString("Descrizione");
                                        String tipologia = jsonAssenzaGiustificata.getString("TipologiaGiustifica");
                                        Assenza assenza_item = new Assenza(data, descrizione, true, tipologia, AssenzaAdapter.VIEW_TYPE_ITEM);
                                        assenze.add(assenza_item);
                                    }
                                }
                                if (arrayNonGiustificate.length() != 0) {
                                    Assenza assenza_header = new Assenza("", "Da giustificare", true, "", AssenzaAdapter.VIEW_TYPE_HEADER);
                                    assenze.add(assenza_header);
                                    for (int i = 0; i < arrayNonGiustificate.length(); i++) {
                                        JSONObject jsonAssenzaNonGiustificata = arrayNonGiustificate.getJSONObject(i);
                                        String data = jsonAssenzaNonGiustificata.getString("Data");
                                        String orario = jsonAssenzaNonGiustificata.getString("Orario");
                                        String tipologia = getTipologiaGiustifica(orario);
                                        Assenza assenza_item = new Assenza(data, "Nessuna Descrizione", false, tipologia, AssenzaAdapter.VIEW_TYPE_ITEM);
                                        assenze.add(assenza_item);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

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
                return params;
            }
        };

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
