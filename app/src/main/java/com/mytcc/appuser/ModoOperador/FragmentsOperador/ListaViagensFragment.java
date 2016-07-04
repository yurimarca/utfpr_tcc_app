package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.ModoOperador.ListViewAdapter.ListaViagensAdapter;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.Viagem;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaViagensFragment extends Fragment {
    public static final String TAG = "ListaViagensFragment";

    private ListView listView;
    private ListaViagensAdapter adapter;

    private ArrayList<Viagem> viagensOperador;

    public ListaViagensFragment() {
        Log.d(TAG, "ListaViagensFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ListaViagensFragment.onCreateView()");

        View rootView = inflater.inflate(R.layout.fragment_listaviagens, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_listaviagens);

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            viagensOperador = ((MainOperadorActivity) act).getViagensNovasOperador();

            adapter = new ListaViagensAdapter(getActivity(),
                    viagensOperador, R.layout.list_item_listaviagens);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Activity act = getActivity();
                    if (act instanceof MainOperadorActivity) {
                        ((MainOperadorActivity) act).setViagemOperador(viagensOperador.get(position));
                        ((MainOperadorActivity) act).listaPassageirosDaViagem();
                        try {
                            Thread.sleep(2000);
                        } catch (Exception ex) {
                            Log.e(TAG, "Erro sleep");
                        }
                        ((MainOperadorActivity) act).switchFragments(IniciaEmbarqueFragment.TAG);
                    }
                }
            });
        }

        return rootView;
    }

    /*private void switchModoFuncionamento() {
        Activity act = getActivity();
        switch (((MainOperadorActivity) act).getModoFuncionamento()) {
            case MainOperadorActivity.MODO_REALIZAR_EMBARQUE:
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Activity act = getActivity();
                        if (act instanceof MainOperadorActivity) {
                            ((MainOperadorActivity) act).setViagemOperador(viagensOperador.get(position));
                            ((MainOperadorActivity) act).listaPassageirosDaViagem();
                            try {
                                Thread.sleep(2000);
                            } catch (Exception ex) {
                                Log.e(TAG, "Erro sleep");
                            }
                            ((MainOperadorActivity) act).switchFragments(EmbarqueFragment.TAG);
                        }
                    }
                });
                break;
            case MainOperadorActivity.MODO_ENVIAR_NOTIFICATION:
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        sendNotification(viagensOperador.get(position));
                    }
                });
                break;
            case MainOperadorActivity.MODO_LISTAR_PASSAGEIROS:
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Activity act = getActivity();
                        if (act instanceof MainOperadorActivity) {
                            ((MainOperadorActivity) act).setViagemOperador(viagensOperador.get(position));
                            ((MainOperadorActivity) act).listaPassageirosDaViagem();
                            try {
                                Thread.sleep(2000);
                            } catch (Exception ex) {
                                Log.e(TAG, "Erro slepp");
                            }
                            ((MainOperadorActivity) act).switchFragments(ListaPassageirosFragment.TAG);
                        }
                    }
                });
                break;
            default:
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Activity act = getActivity();
                        if (act instanceof MainOperadorActivity) {
                            ((MainOperadorActivity) act).setViagemOperador(viagensOperador.get(position));
                            ((MainOperadorActivity) act).listaPassageirosDaViagem();
                            try {
                                Thread.sleep(2000);
                            } catch (Exception ex) {
                                Log.e(TAG, "Erro slepp");
                            }
                            ((MainOperadorActivity) act).switchFragments(EmbarqueFragment.TAG);
                        }
                    }
                });
                break;
        }
    }

    private void sendNotification(Viagem v) {
        Map<String, Object> param = new HashMap<>();
        param.put("idViagem", v.getId());
        param.put("tempo", 10);
        ParseCloud.callFunctionInBackground("alertaSaida", param, new FunctionCallback<Boolean>() {
            @Override
            public void done(Boolean ret, ParseException e) {
                if( (e == null) && (ret == true) ) {
                    Dialog d = new Dialog(getActivity(), "Notificação", "Alerta de 10min enviado com sucesso!");
                    d.setOnAcceptButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainOperadorActivity)getActivity()).switchFragments(MainOperadorFragment.TAG);
                        }
                    });
                    d.create();
                    d.getButtonAccept().setText("OK");
                    d.show();
                } else {
                    Dialog d = new Dialog(getActivity(), "Notificação", "Erro ao enviar!");
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
        });
    }*/
}