package com.example.andrea.progettoesame;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.Docente.AggiungiVotoFragment;
import com.example.andrea.progettoesame.Docente.DocenteActivity;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int STUDENTE = 1;
    private static final int DOCENTE = 2;

    private String username;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText userName = (EditText) findViewById(R.id.username);
        EditText passWord = (EditText) findViewById(R.id.password);
        username = userName.getText().toString();
        password = passWord.getText().toString();

        if (username.equals("") || password.equals("")) {
            userName.setError("Campo Necessario");
            passWord.setError("Campo Necessario");
        }
        InterazioneServer interazioneServer = new InterazioneServer(this, username, password);
        interazioneServer.loginToServer();
    }

    public void Docente() {
        Intent i = new Intent(MainActivity.this, DocenteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        i.putExtras(bundle);
        startActivityForResult(i, DOCENTE);
    }

    public void Studente() {
        Intent i = new Intent(MainActivity.this, StudenteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("scanResult", "");
        i.putExtras(bundle);
        startActivityForResult(i, STUDENTE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STUDENTE) {
            if (resultCode == RESULT_OK) {
                System.out.println("From Studente");
            }
        } else if (requestCode == DOCENTE) {
            if (resultCode == RESULT_OK) {
                System.out.println("From Docente");
            }
        }
    }

    public Pair<String, String> getData() {
        Pair p = new Pair(username, password);
        return p;
    }
}

