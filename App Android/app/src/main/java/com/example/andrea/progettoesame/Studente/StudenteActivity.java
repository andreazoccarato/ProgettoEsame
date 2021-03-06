package com.example.andrea.progettoesame.Studente;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andrea.progettoesame.AgendaFragment;
import com.example.andrea.progettoesame.Docente.AggiungiVotoFragment;
import com.example.andrea.progettoesame.MainFragment;
import com.example.andrea.progettoesame.ProfiloFragment;
import com.example.andrea.progettoesame.R;


public class StudenteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        VisualizzaVotiFragment.OnFragmentInteractionListener,
        AgendaFragment.OnFragmentInteractionListener {

    public String username;
    public String password;
    public String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);

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
        this.scanResult = bundle.getString("scanResult");

        if (!scanResult.equals("")) {
            Toast.makeText(this, scanResult, Toast.LENGTH_LONG).show();
            DialogFragmentQrCode dialogFragment = new DialogFragmentQrCode();
            dialogFragment.show(getFragmentManager(), "ControlloPresenza");
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new MainFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.action_settings) {
            fragment = new ProfiloFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tipologia", "studente");
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
        if (id == R.id.nav_Scan) {
            fragment = new ScanQrCodeFragment();
        } else if (id == R.id.nav_Voti) {
            fragment = new VisualizzaVotiFragment();
        } else if (id == R.id.nav_assenze) {
            fragment = new AssenzeFragment();
        } else if (id == R.id.nav_agenda) {
            fragment = new AgendaFragment();
        } else if (id == R.id.nav_profilo) {
            fragment = new ProfiloFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tipologia", "studente");
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

    public Pair<String, String> getData() {
        Pair p = new Pair(username, password);
        return p;
    }

    public String[] getResult() {
        return scanResult.split("-");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
