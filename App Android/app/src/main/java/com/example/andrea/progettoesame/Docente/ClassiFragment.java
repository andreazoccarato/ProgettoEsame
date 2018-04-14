package com.example.andrea.progettoesame.Docente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;
import com.example.andrea.progettoesame.Studente.StudenteActivity;
import com.example.andrea.progettoesame.Studente.VotiArrayAdapter;
import com.example.andrea.progettoesame.Studente.Voto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassiFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayList<Classe> classi;

    public ClassiFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classi, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        classi = new ArrayList<>();

        /*
        String url = "http://192.168.1.104:8000/api/getVoti";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
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
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
        */
        classi.add(new Classe("5ID", "Informatica", "Zuccante"));
        classi.add(new Classe("5EA", "Elettronica", "Zuccante"));
        classi.add(new Classe("5IC", "Informatica", "Zuccante"));
        classi.add(new Classe("3AA", "Automazione", "Zuccante"));
        ClassiAdapter adapter = new ClassiAdapter(getActivity(), classi);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
