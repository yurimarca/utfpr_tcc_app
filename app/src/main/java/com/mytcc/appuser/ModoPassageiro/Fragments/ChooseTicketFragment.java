package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoPassageiro.MainActivity;
import com.mytcc.appuser.ModoPassageiro.ListViewAdapter.ChooseTicketAdapter;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseTicketFragment extends Fragment {

    public static final String TAG = "ChooseTicketFragment";

    private ArrayList<ParseObject> newTicketsFound;

    private TextView tvTitle, tvData;
    private ListView listView;
    private ChooseTicketAdapter adapter;

    private DateFormat df;

    public ChooseTicketFragment() {
        Log.d(TAG, "ChooseTicketFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ChooseTicketFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_chooseticket, container, false);

        tvTitle = (TextView)rootView.findViewById(R.id.chooseticket_text_title);
        tvData = (TextView)rootView.findViewById(R.id.chooseticket_text_data);

        listView = (ListView)rootView.findViewById(R.id.listView_chooseticket);

        Activity act = getActivity();
        if (act instanceof MainActivity) {
            newTicketsFound = ((MainActivity) act).getNewTicketsFound();

            df = new SimpleDateFormat("dd/MM/yyyy");

            tvTitle.setText(newTicketsFound.get(0).get("Origem").toString() + " para " + newTicketsFound.get(0).get("Destino").toString());
            tvData.setText(df.format(newTicketsFound.get(0).get("Partida")));

            String partida[] = new String[newTicketsFound.size()];
            String chegada[] = new String[newTicketsFound.size()];
            String classe[] = new String[newTicketsFound.size()];
            String preco[] = new String[newTicketsFound.size()];

            df = new SimpleDateFormat("HH:mm");

            for(int i = 0; i < newTicketsFound.size(); i++) {
                try {
                    partida[i] = df.format(newTicketsFound.get(i).get("Partida"));
                    chegada[i] = df.format(newTicketsFound.get(i).get("PrevisaoChegada"));
                    classe[i] = newTicketsFound.get(i).get("Classe").toString();
                    preco[i] = newTicketsFound.get(i).get("Preco").toString();
                }
                catch (Exception ex) {
                    Log.e(TAG, "Ocorreu algum erro com essa passagem");
                    Dialog d = new Dialog(getActivity(), "Erro", "Ocorreu um erro durante esta requisição");
                    d.setOnAcceptButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             Activity act = getActivity();
                            ((MainActivity) act).switchFragments(MainFragment.TAG);
                        }
                    });
                    d.create();
                    d.getButtonAccept().setText("OK");
                    d.show();
                }
            }

            adapter = new ChooseTicketAdapter(getActivity(),
                    partida, chegada, classe, preco, R.layout.list_item_chooseticket);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("idViagem", newTicketsFound.get(position).getObjectId());

                    Activity act = getActivity();
                    if (act instanceof MainActivity)
                        ((MainActivity) act).setNewTicket(newTicketsFound.get(position));

                    ParseCloud.callFunctionInBackground("listaPoltronas", param, new FunctionCallback<ArrayList<String>>() {
                        @Override
                        public void done(ArrayList<String> objs, ParseException e) {
                            if (e == null) {
                                if (objs != null) {
                                    Activity act = getActivity();
                                    if (act instanceof MainActivity) {
                                        ((MainActivity) act).setListPoltronas(objs);
                                        ((MainActivity) act).switchFragments(TicketConfirmationFragment.TAG);
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }

        return rootView;
    }
}