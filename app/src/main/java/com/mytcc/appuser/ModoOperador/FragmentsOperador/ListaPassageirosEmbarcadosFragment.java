package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.R;

import java.util.ArrayList;

public class ListaPassageirosEmbarcadosFragment extends Fragment {
    public static final String TAG = "ListaPassEmbFragment";

    private ListView listView;
    private ArrayList<String> nomePassageiros;

    public ListaPassageirosEmbarcadosFragment() {
        Log.d(TAG, "ListaPassageirosEmbarcadosFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ListaPassageirosEmbarcadosFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_listapassageiros, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_listapassageiros);

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            try {
                nomePassageiros = new ArrayList<String>();
                nomePassageiros = ((MainOperadorActivity) act).getListaNomePassageirosEmbarcados();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, nomePassageiros);
                listView.setAdapter(adapter);
            }
            catch (Exception ex) {
                Dialog d = new Dialog(getActivity(), "Erro", "Efetue o processo novamente!");
                d.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainOperadorActivity)getActivity()).switchFragments(MainOperadorFragment.TAG);
                    }
                });
                d.create();
                d.getButtonAccept().setText("OK");
                d.show();
            }
        }

        return rootView;
    }
}