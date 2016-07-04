package com.mytcc.appuser.ModoOperador.FragmentsViagem;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mytcc.appuser.R;

public class MainViagemFragment extends Fragment {
    public static final String TAG = "MainViagemFragment";

    public MainViagemFragment() {
        Log.d(TAG, "MainViagemFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MainViagemFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.statsfragment, container, false);

        return rootView;
    }
}