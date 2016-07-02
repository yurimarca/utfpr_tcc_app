package com.mytcc.appuser.ModoOperador.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mytcc.appuser.R;

public class FinalizaEmbarqueFragment extends Fragment {
    public static final String TAG = "FinalizaEmbarFragment";

    public FinalizaEmbarqueFragment() {
        Log.d(TAG, "FinalizaEmbarqueFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "FinalizaEmbarqueFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.statsfragment, container, false);

        return rootView;
    }
}