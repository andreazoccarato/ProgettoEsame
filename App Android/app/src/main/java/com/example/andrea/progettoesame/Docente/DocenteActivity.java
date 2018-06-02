package com.example.andrea.progettoesame.Docente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrea.progettoesame.Docente.GetQrCodeFragment;
import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.MainFragment;
import com.example.andrea.progettoesame.ProfiloFragment;
import com.example.andrea.progettoesame.R;


public class DocenteActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener,
        GetQrCodeFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    public String username;
    public String password;

    public TextView textView;

    public static final int QRCODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        this.username = bundle.getString("username");
        this.password = bundle.getString("password");

        System.out.println(username);
        System.out.println(password);
        
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new MainFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.docente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.action_settings) {
            fragment = new ProfiloFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tipologia", "docente");
            fragment.setArguments(bundle);
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_qrCode) {
            fragment = new GetQrCodeFragment();
        } else if (id == R.id.nav_classi) {
            fragment = new ClassiFragment();
        } else if (id == R.id.nav_profilo) {
            fragment = new ProfiloFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tipologia", "docente");
            fragment.setArguments(bundle);
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public Pair<String, String> getData() {
        Pair p = new Pair(username, password);
        return p;
    }

    public void onClickAzione(View view) {
        int id = view.getId();
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        MenuAzioniFragment dialog = (MenuAzioniFragment) fm.findFragmentByTag("DIALOG");
        dialog.dismiss();
        InterazioneServer interazioneServer = new InterazioneServer(this, username, password);
        String codF = dialog.getCodFStud();
        Bundle bundle = new Bundle();
        bundle.putString("CodiceFiscale", codF);
        switch (id) {
            case R.id.azioni_studente_voto:
                fragment = new AggiungiVotoFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.azioni_studente_assenza:
                interazioneServer.setPresenza("presenza", codF);
                break;
            case R.id.azioni_studente_presenza:
                interazioneServer.setPresenza("assenza", codF);
                break;
            case R.id.azioni_studente_giustifica:
                fragment = new GiustificaFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.azioni_generali_evento:
                fragment = new AggiungiEventoFragment();
                bundle.putString("codiceClasse", dialog.getCodClasse());
                fragment.setArguments(bundle);
                break;
            case R.id.azioni_generali_firma:
                fragment = new FirmaFragment();
                fragment.setArguments(bundle);
                bundle.putString("codiceClasse", dialog.getCodClasse());
                break;
            case R.id.azioni_generali_cancella_firma:
                interazioneServer.cancellaFirma();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }
    }
}
