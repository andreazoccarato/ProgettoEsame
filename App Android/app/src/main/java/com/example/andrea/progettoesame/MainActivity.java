package com.example.andrea.progettoesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private boolean isStudente;
    private boolean correctLogin;
    private static final int STUDENTE = 1;
    private static final int DOCENTE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.isStudente = false;
        correctLogin=false;
    }

    public void login(View view) {
        EditText userName = (EditText) findViewById(R.id.username);
        EditText passWord = (EditText) findViewById(R.id.password);
        String username = userName.getText().toString();
        String password = passWord.getText().toString();

        if (username.equals("") || password.equals("")) {
            userName.setError("Campo Necessario");
            passWord.setError("Campo Necessario");
        }

        loginToServer(username,password);
        if (correctLogin) {

            if (isStudente == true) {
                Studente();
            } else {
                Docente();
            }

        } else {
            Toast.makeText(this, "Username o/e password non corretti", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginToServer(final String username, final String password) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        //this is the url where you want to send the request
        String url = "http://192.168.1.104:8000/api/login";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        correctLogin=true;
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                        if(response.equals("Studente")){
                            isStudente=true;
                        }else if(response.equals("Docente")){
                            isStudente=false;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                correctLogin=false;
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public void Docente(){
        Intent i=new Intent(MainActivity.this,DocenteActivity.class);
        startActivityForResult(i,DOCENTE);
    }

    public void Studente(){
        Intent i=new Intent(MainActivity.this,StudenteActivity.class);
        startActivityForResult(i,STUDENTE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STUDENTE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("isOK"), Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == DOCENTE){
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra("isOK"), Toast.LENGTH_SHORT).show();
            }
        }
    }

}

