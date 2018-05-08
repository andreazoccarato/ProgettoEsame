package com.example.andrea.progettoesame.Studente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andrea.progettoesame.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrCodeFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private String username;
    private String password;

    public ScanQrCodeFragment() {

    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_qr_code, container, false);

        StudenteActivity activity = (StudenteActivity) getActivity();
        Pair<String, String> myDataFromActivity = activity.getData();
        this.username = myDataFromActivity.first;
        this.password = myDataFromActivity.second;

        Button mButton = (Button) view.findViewById(R.id.fragment_Get_qrCode_scan);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setContentView(mScannerView);
            }
        });
        return view;
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent i = new Intent(getContext(), StudenteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("scanResult", rawResult.getText());
        i.putExtras(bundle);
        startActivity(i);
    }

}
