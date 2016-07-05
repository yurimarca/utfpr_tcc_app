package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mytcc.appuser.R;

public class RegistroOcorrenciaFragment extends Fragment {
    public static final String TAG = "RegistroOcorrenciaFrag";

    private ListView lvRegistros;
    private ButtonRectangle btnEnviar;

    public RegistroOcorrenciaFragment() {
        Log.d(TAG, "RegistroOcorrenciaFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "RegistroOcorrenciaFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_registroocorrencia, container, false);

        //lview_infos = (ListView) rootView.findViewById(R.id.listView_infos);

        return rootView;
    }
}