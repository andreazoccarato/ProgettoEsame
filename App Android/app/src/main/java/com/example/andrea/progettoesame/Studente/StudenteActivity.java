package com.example.andrea.progettoesame.Studente;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.AgendaFragment;
import com.example.andrea.progettoesame.MainFragment;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.ProfiloFragment;
import com.example.andrea.progettoesame.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudenteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        VisualizzaVotiFragment.OnFragmentInteractionListener,
        ScanQrCodeFragment.OnFragmentInteractionListener,
        AgendaFragment.OnFragmentInteractionListener{

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
            Toast.makeText(StudenteActivity.this, scanResult, Toast.LENGTH_LONG).show();
            setPresenza();
        }

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
        getMenuInflater().inflate(R.menu.studente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.action_settings) {
            fragment = new ProfiloFragment();
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
        // Handle navigation view item clicks here.
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setPresenza() {
        String url = "http://192.168.1.104:8000/api/setPresenza";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        System.out.println("----------------------------------------" + response);
                        JSONObject json = null;
                        String risultato = "";
                        try {
                            json = new JSONObject(response);
                            risultato = json.getString("Risultato");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(StudenteActivity.this, risultato, Toast.LENGTH_LONG).show();
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
                params.put("scanResult", scanResult);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

}
