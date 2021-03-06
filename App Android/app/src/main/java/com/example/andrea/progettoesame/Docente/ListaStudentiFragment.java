package com.example.andrea.progettoesame.Docente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListaStudentiFragment extends Fragment {

    private static final String ARG_PARAM_SEZIONE = "param1";
    private static final String ARG_PARAM_COD_CLASSE = "param2";

    private ArrayList<Studente> studenti;
    private RecyclerView recyclerView;
    private String sezioneClasse;
    protected String codiceClasse;

    public ListaStudentiFragment() {
        studenti = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sezioneClasse = getArguments().getString(ARG_PARAM_SEZIONE);
            codiceClasse = getArguments().getString(ARG_PARAM_COD_CLASSE);
            System.out.println("cod: " + codiceClasse);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_studenti, container, false);

        Button button = (Button) view.findViewById(R.id.lista_studenti_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("param1", "Gestione generale");
                bundle.putString("param2", "");
                bundle.putString("param3", "");
                bundle.putString("param4", "" + codiceClasse);
                MenuAzioniFragment dialogFragment = new MenuAzioniFragment();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "DIALOG");
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.lista_studenti_textView);
        textView.setText(sezioneClasse);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_studenti_recyclerView);

        final ListaStudentiAdapter adapter = new ListaStudentiAdapter(getContext(), studenti);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        String url = "http://" + InterazioneServer.URL_SERVER + "/api/getStudentiByClasse";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println(response);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray array = json.getJSONArray("Studenti");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject studente = array.getJSONObject(i);
                                String codF = studente.getString("CodiceFiscale");
                                String nome = studente.getString("Nome");
                                String cognome = studente.getString("Cognome");
                                String dataNascita = studente.getString("DataNascita");
                                studenti.add(new Studente(codF, nome, cognome, dataNascita));
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
                System.out.println("cod dentro: " + codiceClasse);
                params.put("codiceClasse", codiceClasse);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);

        return view;
    }


}
