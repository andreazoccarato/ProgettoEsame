package com.example.andrea.progettoesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int STUDENTE = 1;
    private static final int DOCENTE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    /*
    private void loginToServer(final String username, final String password) {
        //this is the url where you want to send the request
        String url = "http://192.168.1.104:8000/api/login";

        JSONObject json=new JSONObject();
        try {
            json.put("username",username);
            json.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("------------RISPOSTA------------");
                        System.out.println("-----------------------------------------------------------------"+response.toString());
                        try {
                            String ruolo = response.getString("Ruolo");
                            correctLogin = true;
                            Toast.makeText(MainActivity.this, ruolo, Toast.LENGTH_SHORT).show();
                            if (response.equals("Studente")) {
                                isStudente = true;
                            } else if (response.equals("Docente")) {
                                isStudente = false;
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
                        correctLogin = false;
                    }
                });

        // Adding the request to the queue along with a unique string tag
        jsObjRequest.setTag("postRequest");
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
    */


    private void loginToServer(final String username, final String password) {
        String url = "http://192.168.1.104:8000/api/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println("----------------------------------------"+response);
                        JSONObject json= null;
                        String ruolo="";
                        try {
                            json = new JSONObject(response);
                            ruolo=json.getString("Ruolo");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println(ruolo);
                        Toast.makeText(MainActivity.this, ruolo, Toast.LENGTH_SHORT).show();
                        if (ruolo.equals("Studente")) {
                            Studente();
                        } else if (ruolo.equals("Docente")) {
                            Docente();
                        }else{
                            Toast.makeText(MainActivity.this, "Username o/e password non corretti", Toast.LENGTH_SHORT).show();
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(postRequest);
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

