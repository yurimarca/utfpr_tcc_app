package com.mytcc.appuser.ModoOperador.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.widgets.Dialog;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoOperador.Viagem;
import com.mytcc.appuser.ModoPassageiro.Passageiro;
import com.mytcc.appuser.ModoPassageiro.Passagem;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmbarqueFragment extends Fragment
        implements SurfaceHolder.Callback, View.OnClickListener, Camera.AutoFocusCallback {

    public static final String TAG = "EmbarqueFragment";

    private Context mContext;
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    //private AlertDialog mDialog;
    private Dialog mDialog;

    private TextView tvData, tvTitle;
    private ButtonFlat btnFinalizar;

    private MyApplication myApp;

    private Viagem viagem;

    private int auxiliarTesteEmbarqueItem;
    private long auxiliarTesteEmbarqueInterval;

    public EmbarqueFragment() {
        Log.d(TAG, "EmbarqueFragment()");

        auxiliarTesteEmbarqueItem = 1;
        auxiliarTesteEmbarqueInterval = System.currentTimeMillis();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "EmbarqueFragment.onCreateView()");

        View rootView = inflater.inflate(R.layout.fragment_embarque, container, false);

        mContext = getActivity().getApplicationContext();
        mSurfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        mSurfaceView.setOnClickListener(this);

        mDialog = new Dialog(getActivity(), getString(R.string.embarque_dialog_title), getString(R.string.embarque_dialog_message));

        tvData = (TextView) rootView.findViewById(R.id.tvData);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);

        btnFinalizar = (ButtonFlat) rootView.findViewById(R.id.btnFinalizar);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainOperadorActivity) {
                    ((MainOperadorActivity) act).switchFragments(ListaPassageirosEmbarcadosFragment.TAG);
                }
            }
        });

        Activity act = getActivity();
        if (act instanceof MainOperadorActivity) {
            viagem = ((MainOperadorActivity) act).getViagemOperador();

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            tvData.setText(viagem.getPartidaString(df));

            tvTitle.setText(viagem.getOrigem() + " para " + viagem.getDestino());
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Camera Open
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        mCamera.setPreviewCallback(_previewCallback);

        Camera.Parameters parameters = mCamera.getParameters();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size previewSize = previewSizes.get(0);
        parameters.setPreviewSize(previewSize.width, previewSize.height);

        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;

    }

    @Override
    public void onClick(View v) {
        if (mCamera != null) {
            mCamera.autoFocus(this);
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) { }

    private Camera.PreviewCallback _previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Log.d(TAG, "onPreviewFrame Called");

            if (mDialog.isShowing()) return;

            // Read Range
            Camera.Size size = camera.getParameters().getPreviewSize();

            // Create BinaryBitmap
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    data, size.width, size.height, 0, 0, size.width, size.height, false);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Read QR Code
            Reader reader = new MultiFormatReader();
            Result result = null;
            try {
                result = reader.decode(bitmap);
                String text = result.getText();
                Embarque(text);
                Thread.sleep(200);
            //} catch (NotFoundException e) {
            //} catch (ChecksumException e) {
            //} catch (FormatException e) {
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

        }
    };

    private void Embarque(String codigo) {
        Map<String, Object> param = new HashMap<>();
        param.put("idPassagem", codigo);
        param.put("idViagem", viagem.getId());

        ParseCloud.callFunctionInBackground("Embarque", param, new FunctionCallback<ArrayList<ParseObject>>() {
            @Override
            public void done(ArrayList<ParseObject> list, ParseException e) {
                if (e == null) {
                    /*Passagem newPassagem = new Passagem();
                    newPassagem.setNomeCompleto(list.get(0).get("NomeCompleto").toString());
                    newPassagem.setPoltrona(list.get(0).get("Poltrona").toString());
                    newPassagem.setPassagemId(list.get(0).getObjectId());

                    if(viagem.getListaPassagens() == null) {
                        ArrayList<Passagem> newListaPassagens = new ArrayList<Passagem>();
                        viagem.setListaPassagens(newListaPassagens);
                    }

                    viagem.addListaPassagens(newPassagem);*/

                    String nomePassageiro = list.get(0).get("NomeCompleto").toString();

                    Activity act = getActivity();
                    if (act instanceof MainOperadorActivity) {
                        ((MainOperadorActivity) act).addListaNomePassageirosEmbarcados(nomePassageiro);
                    }

                    logEmbarqueTestCSV("");

                    mDialog.create();
                    mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                    mDialog.setMessage(nomePassageiro + " liberado para fazer o embarque!");
                    mDialog.show();
                }
                else {
                    Log.d(TAG,"Passagem Inválida!");
                    mDialog.create();
                    mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                    mDialog.setMessage("Passagem Inválida!");
                    mDialog.show();
                }
            }
        });
    }

    //Classe para logar teste de método de embarque
    public void logEmbarqueTestCSV(String error) {
        try {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");

            long now = System.currentTimeMillis();
            float intervalo = (now - auxiliarTesteEmbarqueInterval)/1000; //seconds
            auxiliarTesteEmbarqueInterval = now;
            String hora = df.format(System.currentTimeMillis());

            File root = new File(Environment.getExternalStorageDirectory(), "TesteTCC");
            // if external memory exists and folder with name Notes
            if (!root.exists()) {
                root.mkdirs(); // this will create folder.
            }

            File filepath = new File(root, "embarque_teste.csv");
            FileWriter writer = new FileWriter(filepath, true);
            writer.append(Integer.toString(auxiliarTesteEmbarqueItem));
            writer.append(", ");
            writer.append(hora);
            writer.append(", ");
            writer.append(Float.toString(intervalo));
            writer.append(", ");
            writer.append(error);
            writer.append("\r\n");
            writer.flush();
            writer.close();

            auxiliarTesteEmbarqueItem++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}