package com.mytcc.appuser.ModoOperador.FragmentsViagem;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.ListaPassageirosEmbarcadosFragment;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.Viagem;
import com.mytcc.appuser.ModoOperador.ViagemActivity;
import com.mytcc.appuser.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainViagemFragment extends Fragment {
    public static final String TAG = "MainViagemFragment";

    private TextView tvId, tvData, tvHora, tvOrigem, tvDestino;
    private ButtonRectangle btnRegistroPercurso, btnFinalizar;//, btnNotification;

    public MainViagemFragment() {
        Log.d(TAG, "MainViagemFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "MainViagemFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_mainviagem, container, false);

        tvId = (TextView)rootView.findViewById(R.id.tvId);
        tvData = (TextView)rootView.findViewById(R.id.tvData);
        tvHora = (TextView)rootView.findViewById(R.id.tvHora);
        tvOrigem = (TextView)rootView.findViewById(R.id.tvOrigem);
        tvDestino = (TextView)rootView.findViewById(R.id.tvDestino);

        Activity act = getActivity();
        if (act instanceof ViagemActivity) {
            Viagem viagem = ((ViagemActivity) act).getViagem();

            tvId.setText("Identificador: " + viagem.getId());

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            tvData.setText("Data de Partida: " + viagem.getPartidaString(df));

            df = new SimpleDateFormat("HH:mm");
            tvHora.setText("Horário de Partida: " + viagem.getPartidaString(df));

            tvOrigem.setText("Origem: " + viagem.getOrigem());
            tvDestino.setText("Destino: " + viagem.getDestino());
        }

        btnRegistroPercurso = (ButtonRectangle)  rootView.findViewById(R.id.btnRegistroPercurso);
        btnRegistroPercurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof ViagemActivity) {
                    ((ViagemActivity) act).switchFragments(RegistroPercursoFragment.TAG);
                }
            }
        });

        /*btnNotification = (ButtonRectangle)  rootView.findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        btnFinalizar = (ButtonRectangle)  rootView.findViewById(R.id.btnFinalizar);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof ViagemActivity) {
                    ((ViagemActivity) act).FinalizarViagem();
                }
            }
        });

        return rootView;
    }
}