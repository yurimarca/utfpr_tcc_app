package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;

public class MainOperadorFragment extends Fragment {
    public static final String TAG = "MainOperadorFragment";

    private MyApplication myApp;

    private TextView textView;
    private ButtonRectangle btnEmbarque, btnBagagem;

    public MainOperadorFragment() {
        Log.d(TAG, "MainOperadorFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MainOperadorFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_mainoperador, container, false);

        textView = (TextView)rootView.findViewById(R.id.textView);
        btnEmbarque = (ButtonRectangle) rootView.findViewById(R.id.btnEmbarque);
        btnBagagem = (ButtonRectangle) rootView.findViewById(R.id.btnBagagem);

        final Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            String NomeOperador = ((MainOperadorActivity) act).getNomeOperador();

            textView.setText("Olá Sr(a)." + NomeOperador + ", Bem-vindo!");

            btnEmbarque.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((MainOperadorActivity) act).checkViagens()) {
                                ((MainOperadorActivity) act).setModoFuncionamento(MainOperadorActivity.MODO_REALIZAR_EMBARQUE);
                                ((MainOperadorActivity) act).switchFragments(ListaViagensFragment.TAG);
                            } else {
                                Toast.makeText(act.getApplicationContext(), getString(R.string.mainoperator_text_error), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );

            btnBagagem.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainOperadorActivity) act).switchFragments(CadastroEtiquetaFragment.TAG);
                        }
                    }
            );

            /*btnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MainOperadorActivity) act).checkViagens()) {
                        ((MainOperadorActivity) act).setModoFuncionamento(MainOperadorActivity.MODO_ENVIAR_NOTIFICATION);
                        ((MainOperadorActivity) act).switchFragments(ListaViagensFragment.TAG);
                    } else {
                        Toast.makeText(act.getApplicationContext(), getString(R.string.mainoperator_text_error), Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MainOperadorActivity) act).checkViagens()) {
                        ((MainOperadorActivity) act).setModoFuncionamento(MainOperadorActivity.MODO_LISTAR_PASSAGEIROS);
                        ((MainOperadorActivity) act).switchFragments(ListaViagensFragment.TAG);
                    } else {
                        Toast.makeText(act.getApplicationContext(), getString(R.string.mainoperator_text_error), Toast.LENGTH_LONG).show();
                    }
                }
            });*/

        }

        return rootView;
    }
}