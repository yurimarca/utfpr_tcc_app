package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.ViagemActivity;
import com.mytcc.appuser.ModoPassageiro.MainPassageioActivity;
import com.mytcc.appuser.R;

import java.util.ArrayList;

public class ListaPassageirosEmbarcadosFragment extends Fragment {
    public static final String TAG = "ListaPassEmbFragment";

    private ListView listView;
    private ArrayList<String> nomePassageiros;

    private ButtonRectangle btnIniciarViagem;
    private ButtonFlat btnRetornar;

    public ListaPassageirosEmbarcadosFragment() {
        Log.d(TAG, "ListaPassageirosEmbarcadosFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ListaPassageirosEmbarcadosFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_listapassageiros, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_listapassageiros);

        btnIniciarViagem = (ButtonRectangle) rootView.findViewById(R.id.btnIniciarViagem);
        btnRetornar = (ButtonFlat) rootView.findViewById(R.id.btnRetornar);

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            try {
                nomePassageiros = new ArrayList<String>();
                nomePassageiros = ((MainOperadorActivity) act).getListaNomePassageirosEmbarcados();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, nomePassageiros);
                listView.setAdapter(adapter);

                clickListeners();
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

    private void clickListeners() {
        btnIniciarViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainOperadorActivity) {
                    ((MainOperadorActivity)getActivity()).IniciaViagem();
                }
            }
        });

        btnRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainOperadorActivity) {
                    ((MainOperadorActivity)getActivity()).switchFragments(EmbarqueFragment.TAG);
                }
            }
        });
    }
}