package com.example.andrea.progettoesame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private URL urlServer;
    private boolean isStudente;
    private static final String STUDENTE = "STUDENTE";
    private static final String DOCENTE = "DOCENTE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.isStudente = true;
        try {
            this.urlServer = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        if (loginToServer(username, password)) {

            if (isStudente == true) {
                //apri parte studente
            } else {
                //apri parte docente
            }

        } else {
            Toast.makeText(this, "Username o/e password non corretti", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loginToServer(String username, String password) {

        try {
            HttpURLConnection client = (HttpURLConnection) urlServer.openConnection();
            String datiPost = URLEncoder.encode(username, "UTF-8")
                    + URLEncoder.encode(password, "UTF-8");

            client.setDoOutput(true);
            client.setChunkedStreamingMode(0);

            BufferedWriter out = new BufferedWriter(new PrintWriter(client.getOutputStream()));
            out.write(datiPost, 0, datiPost.length());
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String risp = in.readLine();
            if (risp.equals("Utente Valido")) {
                risp = in.readLine();
                if (risp.equals(STUDENTE)) {
                    this.isStudente = true;
                } else {
                    this.isStudente = false;
                }
                in.close();
                out.close();
                return true;
            } else {
                in.close();
                out.close();
                return false;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
