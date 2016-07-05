package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.Viagem;
import com.mytcc.appuser.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class IniciaEmbarqueFragment extends Fragment {
    public static final String TAG = "IniciaEmbarqueFragment";

    private TextView tvId, tvData, tvHora, tvOrigem, tvDestino;
    private EditText etPlaca, etCNPJ;
    private ButtonRectangle btnIniciarEmbarque;

    private Viagem viagem;

    public IniciaEmbarqueFragment() {
        Log.d(TAG, "IniciaEmbarqueFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "IniciaEmbarqueFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_iniciaembarque, container, false);

        tvId = (TextView)rootView.findViewById(R.id.tvId);
        tvData = (TextView)rootView.findViewById(R.id.tvData);
        tvHora = (TextView)rootView.findViewById(R.id.tvHora);
        tvOrigem = (TextView)rootView.findViewById(R.id.tvOrigem);
        tvDestino = (TextView)rootView.findViewById(R.id.tvDestino);

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            viagem = ((MainOperadorActivity) act).getViagemOperador();

            tvId.setText("Identificador: " + viagem.getId());

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            tvData.setText("Data de Partida: " + viagem.getPartidaString(df));

            df = new SimpleDateFormat("HH:mm");
            tvHora.setText("Horário de Partida: " + viagem.getPartidaString(df));

            tvOrigem.setText("Origem: " + viagem.getOrigem());
            tvDestino.setText("Destino: " + viagem.getDestino());
        }

        etPlaca = (EditText)rootView.findViewById(R.id.etPlaca);
        etCNPJ = (EditText)rootView.findViewById(R.id.etCNPJ);

        btnIniciarEmbarque = (ButtonRectangle)rootView.findViewById(R.id.btnIniciarEmbarque);
        btnIniciarEmbarque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (etPlaca.getText().length() > 0) && (etCNPJ.getText().length() > 0) ) {

                    Activity act = getActivity();
                    if (act instanceof MainOperadorActivity) {
                        ((MainOperadorActivity) act).switchFragments(EmbarqueFragment.TAG);
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Preecha os campos para iniciar o embarque.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}