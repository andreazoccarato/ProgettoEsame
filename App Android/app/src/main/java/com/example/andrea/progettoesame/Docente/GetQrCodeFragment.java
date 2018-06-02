package com.example.andrea.progettoesame.Docente;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andrea.progettoesame.InterazioneServer;
import com.example.andrea.progettoesame.MySingleton;
import com.example.andrea.progettoesame.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetQrCodeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<String> classi;
    private ArrayAdapter<String> adapter;

    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0xFF000000;
    public final static int WIDTH = 350;

    private String username;
    private String password;

    private OnFragmentInteractionListener mListener;

    public GetQrCodeFragment() {

    }

    public static GetQrCodeFragment newInstance(String param1, String param2) {
        GetQrCodeFragment fragment = new GetQrCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_get_qr_code, container, false);

        mListener.onFragmentInteraction(null);
        try {
            classi = new ArrayList<>();

            final Spinner spinner = (Spinner) view.findViewById(R.id.getQrcode_spinner_classi);
            adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, classi);
            spinner.setAdapter(adapter);

            getClassi();

            DocenteActivity activity = (DocenteActivity) getActivity();
            Pair<String, String> myDataFromActivity = activity.getData();
            this.username = myDataFromActivity.first;
            this.password = myDataFromActivity.second;

            Button mButton = (Button) view.findViewById(R.id.getQrcode);
            final ImageView imageView = (ImageView) view.findViewById(R.id.qrCodeImage);

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    try {
                        final String clSez = spinner.getSelectedItem().toString();
                        if (clSez.equals("")) {
                            Toast.makeText(getContext(), "Nessuna Classe selezionata", Toast.LENGTH_SHORT).show();
                        } else {
                            String url = "http://" + InterazioneServer.URL_SERVER + "/api/qrCode";
                            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject json = null;
                                            String requestQrCode = "";
                                            try {
                                                json = new JSONObject(response);
                                                requestQrCode = json.getString("qrCodeRequest");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                Bitmap bitmap = encodeAsBitmap(requestQrCode);
                                                imageView.setImageBitmap(bitmap);
                                            } catch (WriterException e) {
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
                                    params.put("classe", clSez);
                                    return params;
                                }
                            };
                            postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Nessuna Classe selezionata", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Nessuna Classe selezionata", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }

    public void getClassi() {

        String url = "http://" + InterazioneServer.URL_SERVER + "/api/getClassi";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("------------RISPOSTA------------");
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray array = json.getJSONArray("Classi");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject classe = array.getJSONObject(i);
                                String sezione = classe.getString("Sezione");
                                classi.add(sezione);
                            }
                            adapter.notifyDataSetChanged();
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
                Pair p = ((DocenteActivity) getActivity()).getData();
                params.put("username", (String) p.first);
                params.put("password", (String) p.second);
                return params;
            }
        };
        //imposto un numero di tentativi in caso di com.android.volley.TimeoutError
        postRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }

}
