package com.example.andrea.progettoesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StudenteActivity extends AppCompatActivity {

    private static final int VOTI=0;
    private static final int QRCODE=1;
    private static final int MODIFICA_PROFILO=2;
    private static final int ASSENZE=3;

    public String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);
        Bundle bundle=getIntent().getExtras();
        this.username=bundle.getString("username");
        this.password=bundle.getString("password");
    }

    public void voti(View view){

    }

    public void qrCode(View view){

    }

    public void modificaProfilo(View view){

    }

    public void assenze(View view){

    }

    public void logOut(View view){

    }

    public void startActivityVoti(){

    }

    public void startActivityQrCode(){

    }

    public void startActivityAssenze(){

    }

    public void startActivityModificaProfilo(){

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOTI) {
            if (resultCode == RESULT_OK) {
                System.out.println("From Voti");
            }
        }else if(requestCode == QRCODE){
            if (resultCode == RESULT_OK) {
                System.out.println("From QrCode");
            }
        }else if(requestCode == ASSENZE){
            if (resultCode == RESULT_OK) {
                System.out.println("From Assenze");
            }
        }else if(requestCode == MODIFICA_PROFILO){
            if (resultCode == RESULT_OK) {
                System.out.println("From Modifica_Profilo");
            }
        }
    }
}
