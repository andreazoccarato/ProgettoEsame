package com.example.andrea.progettoesame.Studente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.andrea.progettoesame.R;

import java.util.ArrayList;

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

        assenze=new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.assenza_recyclerView);
        AssenzaAdapter adapter = new AssenzaAdapter(assenze);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
