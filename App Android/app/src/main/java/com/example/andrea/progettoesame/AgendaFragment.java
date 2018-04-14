package com.example.andrea.progettoesame;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class AgendaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Evento> eventi;
    private RecyclerView recyclerView;


    public static AgendaFragment newInstance(String param1, String param2) {
        AgendaFragment fragment = new AgendaFragment();
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
        final View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.eventi_recyclerView);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 12);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(14f, 24f, 14f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .build();

        getEventi(view, Calendar.getInstance());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
                getEventi(view, date);
            }
        });

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

    private void getEventi(View view, Calendar date) {

        final String giornoSelezionato = (String) DateFormat.format("dd/MM/yyyy", date);
        final TextView textViewLezioni = (TextView) view.findViewById(R.id.agenda_fragment_textView_lezioni);
        eventi = new ArrayList<>();

        String url = "http://192.168.1.104:8000/api/getEventi";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println(response);
                        JSONObject json = null;
                        String risp = "";
                        String dat = "";
                        String desc = "";
                        String mat = "";

                        EventiAdapter adapter = new EventiAdapter(getActivity(), eventi);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(adapter);
                        try {
                            json = new JSONObject(response);
                            risp = json.getString("Eventi");
                            if (risp.equals("[\"Lezioni\",[],\"Compiti\",[]]")) {
                                textViewLezioni.setText("Nessun evento");
                                adapter.notifyDataSetChanged();
                            } else {
                                textViewLezioni.setText("");
                                risp = risp.replaceAll("Compiti", "Lezioni");
                                String primoSplit[] = risp.split("Lezioni");

                                String lezioni = primoSplit[1];
                                lezioni = lezioni.replaceAll("\\[", "");
                                lezioni = lezioni.replaceAll("\\]", "");
                                lezioni = lezioni.substring(2, lezioni.length() - 2);
                                String splitLezioni[] = lezioni.split("\\{");

                                textViewLezioni.setText("Eventi");

                                recyclerView.addItemDecoration(
                                        new DividerItemDecoration(getActivity().getDrawable(R.drawable.compiti),
                                                true, true));

                                for (int i = 0; i < splitLezioni.length; i++) {
                                    if (!splitLezioni[i].equals("")) {
                                        String secondoSplitLezioni[] = splitLezioni[i].split(",");
                                        String materia = secondoSplitLezioni[3];
                                        materia = materia.replaceAll("\"", "");
                                        String descrizione = secondoSplitLezioni[4];
                                        descrizione = descrizione.replaceAll("\"", "");
                                        String data = secondoSplitLezioni[1];
                                        data = data.replaceAll("\"", "");
                                        data = data.replaceAll("/", "");
                                        Evento ev = new Evento(materia, data, descrizione);
                                        eventi.add(ev);
                                    }
                                }

                                String compiti = primoSplit[2];
                                compiti = compiti.replaceAll("\\[", "");
                                compiti = compiti.replaceAll("\\]", "");
                                compiti = compiti.substring(2);
                                String splitCompiti[] = compiti.split("\\{");

                                recyclerView.addItemDecoration(
                                        new DividerItemDecoration(getActivity().getDrawable(R.drawable.compiti),
                                                true, true));

                                for (int i = 0; i < splitCompiti.length; i++) {
                                    if (!splitCompiti[i].equals("")) {
                                        String secondoSplitCompiti[] = splitCompiti[i].split(",");
                                        String materia = secondoSplitCompiti[2];
                                        materia = materia.replaceAll("\"", "");
                                        String descrizione = secondoSplitCompiti[3];
                                        descrizione = descrizione.replaceAll("\"", "");
                                        String data = secondoSplitCompiti[1];
                                        data = data.replaceAll("\"", "");
                                        data = data.replaceAll("/", "");
                                        Evento ev = new Evento(materia, data, descrizione);
                                        eventi.add(ev);
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
                params.put("data", giornoSelezionato);
                return params;
            }
        };
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

}
