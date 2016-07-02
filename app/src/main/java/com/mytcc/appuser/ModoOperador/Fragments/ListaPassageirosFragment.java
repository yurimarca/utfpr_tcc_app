package com.mytcc.appuser.ModoOperador.Fragments;

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
import com.mytcc.appuser.ModoOperador.ListViewAdapter.ListaViagensAdapter;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.PassageiroSimples;
import com.mytcc.appuser.R;

import java.util.ArrayList;

public class ListaPassageirosFragment extends Fragment {
    public static final String TAG = "ListaPassFragment";

    private ListView listView;
    private ArrayList<PassageiroSimples> passageiros;
    private ArrayList<String> nomePassageiros;

    public ListaPassageirosFragment() {
        Log.d(TAG, "ListaPassageirosFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ListaPassageirosFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_listapassageiros, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_listapassageiros);

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            passageiros = ((MainOperadorActivity) act).getListaPassageiros();

            try {
                nomePassageiros = new ArrayList<String>();
                for (int i = 0; i < passageiros.size(); i++) {
                    String newname = passageiros.get(i).getNomeCompleto();
                    nomePassageiros.add(newname);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, nomePassageiros);
                listView.setAdapter(adapter);
            }
            catch (Exception ex) {
                Dialog d = new Dialog(getActivity(), "Erro", "Erro ao tentar acessar Banco de Dados. Tente novamente!");
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