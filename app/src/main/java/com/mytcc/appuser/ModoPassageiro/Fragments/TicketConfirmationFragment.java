package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.mytcc.appuser.ModoPassageiro.MainPassageioActivity;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.ModoPassageiro.Passagem;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TicketConfirmationFragment extends Fragment {
    public static final String TAG = "TicketConfirmationFrag";

    private MyApplication myApp;

    private TextView textViewOrigem,textViewDestino,textViewPartida,textViewChegada,
            textViewClasse, textViewPreco;
    private ButtonRectangle btnComprar;
    private ButtonFlat btnCancelar;

    private ParseObject newTicket;

    public TicketConfirmationFragment() {
        Log.d(TAG, "TicketConfirmationFrag()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "TicketConfirmationFrag.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_ticketconfirmation, container, false);

        myApp = (MyApplication)getActivity().getApplication();

        textViewOrigem = (TextView) rootView.findViewById(R.id.textViewOrigem);
        textViewDestino = (TextView) rootView.findViewById(R.id.textViewDestino);
        textViewPartida = (TextView) rootView.findViewById(R.id.textViewPartida);
        textViewChegada = (TextView) rootView.findViewById(R.id.textViewChegada);
        textViewClasse = (TextView) rootView.findViewById(R.id.textViewClasse);
        textViewPreco = (TextView) rootView.findViewById(R.id.textViewPreco);

        btnComprar = (ButtonRectangle) rootView.findViewById(R.id.btnComprar);
        btnCancelar = (ButtonFlat) rootView.findViewById(R.id.btnCancelar);

        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Activity act = getActivity();
        if (act instanceof MainPassageioActivity) {
            newTicket = ((MainPassageioActivity) act).getNewTicket();
            textViewOrigem.setText(newTicket.get("Origem").toString());
            textViewDestino.setText(newTicket.get("Destino").toString());
            textViewPartida.setText(df.format(newTicket.get("Partida")));
            textViewChegada.setText(df.format(newTicket.get("PrevisaoChegada")));
            textViewClasse.setText(newTicket.get("Classe").toString());
            textViewPreco.setText(newTicket.get("Preco").toString());

            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("idViagem", newTicket.getObjectId().toString());
                    param.put("poltrona", "1");

                    ParseCloud.callFunctionInBackground("compraViagem", param, new FunctionCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject obj, ParseException e) {
                            if (e == null) {
                                Passagem ticket = new Passagem(newTicket.get("Origem").toString(),
                                        newTicket.get("Destino").toString(),
                                        (Date) newTicket.get("Partida"),
                                        (Date) newTicket.get("PrevisaoChegada"),
                                        newTicket.get("Classe").toString(),
                                        newTicket.get("Preco").toString(),
                                        obj.get("Poltrona").toString(),
                                        newTicket.getObjectId(),
                                        obj.getObjectId() );

                                ParsePush.subscribeInBackground(newTicket.getObjectId().toString());

                                myApp.addTicketToUser(ticket);

                                Activity act = getActivity();
                                if (act instanceof MainPassageioActivity) {
                                    ((MainPassageioActivity) act).setNewTicketViagem(ticket);
                                    ((MainPassageioActivity) act).switchFragments(TicketFragment.TAG);
                                }
                            } else {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }

        btnCancelar.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity act = getActivity();
                        if (act instanceof MainPassageioActivity) {
                            ((MainPassageioActivity) act).clearNewTicketsFound();
                            ((MainPassageioActivity) act).switchFragments(NewTicketFragment.TAG);
                        }
                    }
                });

        return rootView;
    }

}
