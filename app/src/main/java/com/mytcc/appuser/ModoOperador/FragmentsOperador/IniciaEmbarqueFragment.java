package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytcc.appuser.R;

public class IniciaEmbarqueFragment extends Fragment {
    public static final String TAG = "IniciaEmbarqueFragment";

    private TextView tvData, tvTitle;

    public IniciaEmbarqueFragment() {
        Log.d(TAG, "IniciaEmbarqueFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "IniciaEmbarqueFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.statsfragment, container, false);

        return rootView;
    }
}