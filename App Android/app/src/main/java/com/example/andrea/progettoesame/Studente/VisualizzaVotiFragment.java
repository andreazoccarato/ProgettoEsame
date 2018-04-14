package com.example.andrea.progettoesame.Studente;

import android.content.Context;
import android.net.Uri;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualizzaVotiFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ArrayList<Voto> voti;

    private OnFragmentInteractionListener mListener;

    public VisualizzaVotiFragment() {
    }

    public static VisualizzaVotiFragment newInstance(String param1, String param2) {
        VisualizzaVotiFragment fragment = new VisualizzaVotiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizza_voti, container, false);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        voti = new ArrayList<>();

        String url = "http://192.168.1.104:8000/api/getVoti";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        JSONObject json = null;
                        String risp = "";
                        String vot = "";
                        String dat = "";
                        String desc = "";
                        String mat = "";
                        try {
                            json = new JSONObject(response);
                            risp = json.getString("Voti");
                            if (risp.equals("")) {
                                Toast.makeText(getContext(), "Nessun Voto", Toast.LENGTH_SHORT).show();
                            } else {
                                String app[] = risp.split("Voto");
                                for (int i = 1; i < app.length; i++) {
                                    String app2[] = app[i].split("\\\"");
                                    for (int j = 0; j < app2.length; j++) {
                                        String app3[] = app2[j].split(",");
                                        switch (j) {
                                            case 2:
                                                vot = app3[0];
                                                break;
                                            case 6:
                                                mat = app3[0];
                                                break;
                                            case 10:
                                                dat = app3[0];
                                                break;
                                            case 14:
                                                desc = app3[0];
                                                break;
                                        }
                                    }
                                    Voto v = new Voto(vot, mat, desc, dat);
                                    voti.add(v);
                                    VotiArrayAdapter adapter = new VotiArrayAdapter(getActivity(), voti);
                                    //use this below for a correct initialization
                                    setListAdapter(adapter);
                                }
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
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);

        // connect to
        getListView().setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
