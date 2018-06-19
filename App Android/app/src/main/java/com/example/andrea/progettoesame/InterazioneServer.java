package com.example.andrea.progettoesame;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.Studente.StudenteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrea on 16/05/2018.
 */

public class InterazioneServer {

    public static final String URL_SERVER = "192.168.1.104:8000";
    private Context context;
    private String username;
    private String password;

    public InterazioneServer(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    public void loginToServer() {
        String url = "http://" + URL_SERVER + "/api/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String ruolo = "";
                        try {
                            json = new JSONObject(response);
                            ruolo = json.getString("Ruolo");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println(ruolo);
                        Toast.makeText(context, ruolo, Toast.LENGTH_SHORT).show();
                        if (ruolo.equals("Studente")) {
                            ((MainActivity) context).Studente();
                        } else if (ruolo.equals("Docente")) {
                            ((MainActivity) context).Docente();
                        } else {
                            Toast.makeText(context, "Username o/e password non corretti", Toast.LENGTH_SHORT).show();
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void modificaProfilo(final String newUsername, final String newPassword) {
        String url = "http://" + InterazioneServer.URL_SERVER + "/api/setProfilo";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String risultato = "";
                        try {
                            json = new JSONObject(response);
                            risultato = json.getString("RisultatoModifica");
                            Toast.makeText(context, risultato, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("newPassword", newPassword);
                params.put("newUsername", newUsername);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void aggiungiVoto(final String voto, final String materia, final String descrizione, final String cfStud) {
        String url = "http://" + InterazioneServer.URL_SERVER + "/api/addVoto";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String risultato = "";
                        try {
                            json = new JSONObject(response);
                            risultato = json.getString("RisultatoVoto");
                            Toast.makeText(context, risultato, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("voto", voto);
                params.put("materia", materia);
                params.put("descrizione", descrizione);
                params.put("cfStud", cfStud);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void setPresenza(final String type, final String codF) {
        String url = "http://" + InterazioneServer.URL_SERVER + "/api/setPresenzaAssenza";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String risultato = "";
                        try {
                            json = new JSONObject(response);
                            risultato = json.getString("qrCodeCheck");
                            Toast.makeText(context, risultato, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("tipologia", type);
                params.put("cfStud", codF);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void giustifica(final int idAssenza, final String descrizione, final String tipologia) {
        String url = "http://" + URL_SERVER + "/api/giustificaAssenza";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String ris = "";
                        try {
                            json = new JSONObject(response);
                            ris = json.getString("RisultatoGiustifica");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, ris, Toast.LENGTH_SHORT).show();
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
                params.put("idAssenza", "" + idAssenza);
                params.put("descrizione", descrizione);
                params.put("username", username);
                params.put("password", password);
                params.put("tipologia", tipologia);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void addEvento(final String data, final String materia, final String descrizione, final String idClasse) {
        String url = "http://" + URL_SERVER + "/api/aggiungiEvento";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String ris = "";
                        try {
                            json = new JSONObject(response);
                            ris = json.getString("Risultato");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, ris, Toast.LENGTH_SHORT).show();
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
                params.put("data", data);
                params.put("materia", materia);
                params.put("descrizione", descrizione);
                params.put("idClasse", idClasse);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void firma(final String materia, final String descrizione, final String idClasse, final String nOre) {
        String url = "http://" + URL_SERVER + "/api/firma";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String ris = "";
                        try {
                            json = new JSONObject(response);
                            ris = json.getString("RispostaFirma");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, ris, Toast.LENGTH_SHORT).show();
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
                params.put("materia", materia);
                params.put("descrizione", descrizione);
                params.put("idClasse", idClasse);
                params.put("nOre", nOre);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void cancellaFirma() {
        String url = "http://" + URL_SERVER + "/api/cancellaFirma";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        String ris = "";
                        try {
                            json = new JSONObject(response);
                            ris = json.getString("RispostaCancellaFirma");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, ris, Toast.LENGTH_SHORT).show();
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }
}
