package com.example.andrea.progettoesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.io.InputStream;



public class MainActivity extends AppCompatActivity {

    private boolean isStudente;
    private static final int STUDENTE = 1;
    private static final int DOCENTE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.isStudente = true;
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
        if (loginToServer("192.168.1.103/ServerProgetto/routes/login",username, password)) {

            if (isStudente == true) {
                Studente();
            } else {
                Docente();
            }

        } else {
            Toast.makeText(this, "Username o/e password non corretti", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loginToServer(String url,String username, String password) {
        BufferedReader in = null;
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", username);
            jsonObject.accumulate("password",password);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            in=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            System.out.println("s");
            if(in != null){
                String result = in.readLine();
                if(result.equals("Studente")){
                    this.isStudente = true;
                    in.close();
                    return true;
                }else if(result.equals("Docente")){
                    this.isStudente=false;
                    in.close();
                    return true;
                }else return false;

            }
            else Toast.makeText(MainActivity.this,"Did not work!",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return false;
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

