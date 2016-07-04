package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.ModoPassageiro.MainPassageioActivity;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewTicketFragment extends Fragment {
    public static final String TAG = "NewTicketFragment";

    private ButtonRectangle tBtnIda, tBtnIdaVolta, btnBuscar;
    private boolean toggleBtnIdaVolta;

    private EditText  etDataIda, etDataVolta;
    private AutoCompleteTextView etOrigem, etDestino;

    private static String destinos[];

    private int mYear, mMonth, mDay;

    public NewTicketFragment() {
        Log.d(TAG, "NewTicketFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "NewTicketFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_newticket, container, false);

        InitDestinos();

        tBtnIda = (ButtonRectangle) rootView.findViewById(R.id.tBtnIda);
        tBtnIdaVolta = (ButtonRectangle) rootView.findViewById(R.id.tBtnIdaVolta);

        etOrigem = (AutoCompleteTextView) rootView.findViewById(R.id.etOrigem);
        etDestino = (AutoCompleteTextView) rootView.findViewById(R.id.etDestino);
        etDataIda = (EditText) rootView.findViewById(R.id.etDataIda);
        etDataVolta = (EditText) rootView.findViewById(R.id.etDataVolta);

        etOrigem.setDropDownBackgroundResource(R.color.myDrawerBackground);
        etDestino.setDropDownBackgroundResource(R.color.myDrawerBackground);

        btnBuscar = (ButtonRectangle) rootView.findViewById(R.id.btnBuscar);

        //Botão Somente Ida Selecionado
        toggleBtnIdaVolta = false;
        tBtnIda.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
        tBtnIdaVolta.setBackgroundColor(getResources().getColor(R.color.myAccentColor));
        etDataVolta.setEnabled(false);

//        etOrigem.setText("Curitiba");
//        etDestino.setText("São Paulo");
//        etDataIda.setText("11/11/2015");

        tBtnIda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBtnIdaVolta = false;
                tBtnIda.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
                tBtnIdaVolta.setBackgroundColor(getResources().getColor(R.color.myAccentColor));
                etDataVolta.setEnabled(false);
            }
        });

        tBtnIdaVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBtnIdaVolta = true;
                tBtnIdaVolta.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
                tBtnIda.setBackgroundColor(getResources().getColor(R.color.myAccentColor));
                etDataVolta.setEnabled(true);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggleBtnIdaVolta)
                {

                }
                else
                {
                    String origemIda = etOrigem.getText().toString();
                    String destinoIda = etDestino.getText().toString();
                    String dataIda = etDataIda.getText().toString();

                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date = format.parse(dataIda);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> param = new HashMap<>();
                    param.put("origem", origemIda);
                    param.put("destino", destinoIda);
                    param.put("data", date);

                    ParseCloud.callFunctionInBackground("listaViagens", param, new FunctionCallback<ArrayList<ParseObject>>() {
                        @Override
                        public void done(ArrayList<ParseObject> objs, ParseException e) {
                            if (e == null) {
                                if (objs != null) {
                                    if (objs.size() != 0) {
                                        Activity act = getActivity();
                                        if (act instanceof MainPassageioActivity) {
                                            ((MainPassageioActivity) act).pushNewTicketsFound(objs);
                                            ((MainPassageioActivity) act).switchFragments(ChooseTicketFragment.TAG);
                                        }
                                    } else {
                                        Dialog d = new Dialog(getActivity(),
                                                getString(R.string.newticket_dialog_noTickets_title),
                                                getString(R.string.newticket_dialog_noTickets_text));
                                        d.create();
                                        d.getButtonAccept().setText(getString(R.string.dialog_ok));
                                        d.show();
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        etDataIda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDataIda.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        etDataVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDataVolta.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        return rootView;
    }

    private void InitDestinos() {
       Map<String, Object> param = new HashMap<>();

        ParseCloud.callFunctionInBackground("listaDestinos", param, new FunctionCallback<ArrayList<ParseObject>>() {
            @Override
            public void done(ArrayList<ParseObject> objs, ParseException e) {
                if (e == null) {
                    destinos = objs.toArray(new String[objs.size()]);

                    ArrayAdapter<String> adapter_destinos =
                            new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, destinos);
                    etOrigem.setAdapter(adapter_destinos);
                    etDestino.setAdapter(adapter_destinos);
                }
            }
        });
    }
}
