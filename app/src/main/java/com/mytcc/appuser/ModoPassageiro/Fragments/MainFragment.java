package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mytcc.appuser.R;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    public MainFragment() {
        Log.d(TAG, "MainFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MainFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }
}
