package com.mytcc.appuser.ModoPassageiro.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.mytcc.appuser.ModoPassageiro.MainActivity;
import com.mytcc.appuser.ModoPassageiro.Passagem;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.QRCode;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TicketFragment extends Fragment {
    public static final String TAG = "TicketFragment";

    private TextView textViewOrigem,textViewDestino,textViewPartida;

    private ButtonFlat btnCancelar;

    private Passagem newTicket;

    private ImageView imgQRCode;

    private MyApplication myApp;

    public TicketFragment() {
        Log.d(TAG, "TicketFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "TicketFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);

        textViewOrigem = (TextView) rootView.findViewById(R.id.textViewOrigem);
        textViewDestino = (TextView) rootView.findViewById(R.id.textViewDestino);
        textViewPartida = (TextView) rootView.findViewById(R.id.textViewPartida);

        btnCancelar = (ButtonFlat) rootView.findViewById(R.id.btnCancelar);

        myApp = (MyApplication)getActivity().getApplication();

        Activity act = getActivity();
        if (act instanceof MainActivity) {
            newTicket = ((MainActivity) act).getNewTicketPassagem();
            textViewOrigem.setText(newTicket.getOrigem());
            textViewDestino.setText(newTicket.getDestino());
            textViewPartida.setText(newTicket.getPartidaString(new SimpleDateFormat("MM/dd/yyyy HH:mm")));

            imgQRCode = (ImageView) rootView.findViewById(R.id.imageView_barcode);
            generateBarcode(newTicket.getPassagemId());
        }

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> param = new HashMap<>();
                param.put("idPassagem", newTicket.getPassagemId());

                ParseCloud.callFunctionInBackground("cancelaPassagem", param, new FunctionCallback<Boolean>() {
                    @Override
                    public void done(Boolean b, ParseException e) {
                        if (e == null) {
                            if(b) {
                                myApp.removeTicketFromUser(newTicket);
                                Activity act = getActivity();
                                if (act instanceof MainActivity) {
                                    ((MainActivity) act).switchFragments(MyTicketsFragment.TAG);
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return rootView;
    }

    private void generateBarcode(String Serial) {
        Bitmap pallet = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(pallet);

        QRCode qrCode = new QRCode();
        qrCode.setData(Serial);

        qrCode.setProcessTilde(false);;
        qrCode.setBarcodeHeight(100f);
        qrCode.setBarcodeHeight(100f);

        qrCode.setUom(IBarcode.FNC1_NONE);

        qrCode.setX(10f);

        qrCode.setLeftMargin(40f);
        qrCode.setRightMargin(15f);
        qrCode.setTopMargin(15f);
        qrCode.setBottomMargin(15f);

        qrCode.setBarAlignment(1);
        qrCode.setResolution(720);

        RectF bounds = new RectF(0, 0, 0, 0);
        try {
            qrCode.drawBarcode(cv, bounds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int h = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //imgQRCode.setImageBitmap(Bitmap.createScaledBitmap(pallet, w, h/5, false));
        imgQRCode.setImageBitmap(Bitmap.createBitmap(pallet));
    }
}