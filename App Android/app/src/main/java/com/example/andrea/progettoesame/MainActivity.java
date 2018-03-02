package com.example.andrea.progettoesame;

import android.content.Intent;
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
import com.example.andrea.progettoesame.Docente.DocenteActivity;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

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
                            Studente(username,password);
                        } else if (ruolo.equals("Docente")) {
                            Docente(username,password);
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
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(this).addToRequestQueue(postRequest);
    }


    public void Docente(String username,String password){
        Intent i=new Intent(MainActivity.this, DocenteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("username",username);
        bundle.putString("password",password);
        i.putExtras(bundle);
        startActivityForResult(i,DOCENTE);
    }

    public void Studente(String username,String password){
        Intent i=new Intent(MainActivity.this, StudenteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("username",username);
        bundle.putString("password",password);
        bundle.putString("scanResult","");
        i.putExtras(bundle);
        startActivityForResult(i,STUDENTE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STUDENTE) {
            if (resultCode == RESULT_OK) {
                System.out.println("From Studente");
            }
        }else if(requestCode == DOCENTE){
            if (resultCode == RESULT_OK) {
                System.out.println("From Docente");
            }
        }
    }

}

