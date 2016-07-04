package com.mytcc.appuser.ModoOperador.FragmentsOperador;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CadastroEtiquetaFragment extends Fragment
        implements SurfaceHolder.Callback, View.OnClickListener, Camera.AutoFocusCallback {

    public static final String TAG = "CadastroEtiquetaFrag";

    private Context mContext;
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private Dialog mDialog;

    private MyApplication myApp;

    private ButtonRectangle btnCadastrar, btnRecuperar;

    private String codigo;
    private boolean codigoCapturado;

    private ArrayList<String> etiquetas;
    private boolean cadastrarEtiquetas;

    public CadastroEtiquetaFragment() {
        Log.d(TAG, "CadastroEtiquetaFragment()");

        codigoCapturado = false;
        cadastrarEtiquetas = false;

        etiquetas = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "CadastroEtiquetaFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_cadastroetiqueta, container, false);

        mContext = getActivity().getApplicationContext();
        mSurfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView);
        mSurfaceView.setOnClickListener(this);

        mDialog = new Dialog(getActivity(),
                getString(R.string.embarque_dialog_title),
                getString(R.string.embarque_dialog_message));


        btnCadastrar = (ButtonRectangle) rootView.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarEtiqueta();
            }
        });

        btnRecuperar = (ButtonRectangle) rootView.findViewById(R.id.btnRecuperar);
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperarEtiquetas();
            }
        });


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

                if(cadastrarEtiquetas) {
                    EtiquetaCapturada(text);
                }
                else {
                    codigoCapturado(text);
                }

                Thread.sleep(200);
                //} catch (NotFoundException e) {
                //} catch (ChecksumException e) {
                //} catch (FormatException e) {
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

        }
    };

    private void codigoCapturado(final String newcodigo) {
        mDialog = new Dialog(getActivity(),
                getString(R.string.cadastroetiqueta_dialog_title_codigoCapturado),
                getString(R.string.cadastroetiqueta_dialog_text_codigoCapturado));
        mDialog.addCancelButton(getString(R.string.dialog_cancel));
        mDialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoCapturado = false;
            }
        });
        mDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoCapturado = true;
                codigo = newcodigo;
            }
        });
        mDialog.create();
        mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
        mDialog.show();
    }

    private void CadastrarEtiqueta() {
        if(codigoCapturado) {
            mDialog = new Dialog(getActivity(),
                    getString(R.string.cadastroetiqueta_dialog_title_cadastrar),
                    getString(R.string.cadastroetiqueta_dialog_text_cadastrar));
            mDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cadastrarEtiquetas = true;
                }
            });
            mDialog.create();
            mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
            mDialog.show();
        }
        else {
            Toast.makeText(getActivity(),
                    "Nenhum código de passagem capturado!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void EtiquetaCapturada(final String newcodigo) {
        etiquetas.add(newcodigo);

        mDialog = new Dialog(getActivity(),
                getString(R.string.cadastroetiqueta_dialog_title_etiquetacapturada),
                getString(R.string.cadastroetiqueta_dialog_text_etiquetacapturada));
        mDialog.addCancelButton(getString(R.string.dialog_no));
        mDialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Finalizar
                cadastrarEtiquetas = false;
                codigoCapturado = false;

                Map<String, Object> param = new HashMap<>();
                param.put("idPassagem", codigo);
                param.put("Bagagens", etiquetas);

                ParseCloud.callFunctionInBackground("associaBagagem", param, new FunctionCallback<Boolean>() {
                    @Override
                    public void done(Boolean object, ParseException e) {
                        if (e == null) {
                            String result = String.valueOf(etiquetas.size()) +
                                    " etiquetas cadastradas.";
                            mDialog = new Dialog(getActivity(),
                                    getString(R.string.cadastroetiqueta_dialog_title_etiquetas),
                                    result);
                            mDialog.create();
                            mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                            mDialog.show();
                        } else {
                            Log.d(TAG, "Passagem Inválida!");
                            mDialog = new Dialog(getActivity(),
                                    getString(R.string.cadastroetiqueta_dialog_title_invalid),
                                    getString(R.string.cadastroetiqueta_dialog_text_invalid));
                            mDialog.create();
                            mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                            mDialog.show();
                        }
                    }
                });
            }
        });
        mDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cadastrarEtiquetas = true;
            }
        });
        mDialog.create();
        mDialog.getButtonAccept().setText(getString(R.string.dialog_yes));
        mDialog.show();
    }

    private void RecuperarEtiquetas() {
        if( (codigoCapturado) && (!cadastrarEtiquetas) ) {
            codigoCapturado = false;

            Map<String, Object> param = new HashMap<>();
            param.put("idPassagem", codigo);

            ParseCloud.callFunctionInBackground("retornaBagagem", param, new FunctionCallback<ArrayList<String>>() {
                @Override
                public void done(ArrayList<String> list, ParseException e) {
                    if (e == null)
                    {
                        String result = "Lista de Etiquetas:\n";
                        Iterator<String> it = list.iterator();
                        while (it.hasNext()) {
                            result += it.next() + "\n";
                        }

                        mDialog = new Dialog(getActivity(),
                                getString(R.string.cadastroetiqueta_dialog_title_etiquetas),
                                result);
                        mDialog.create();
                        mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                        mDialog.show();
                    }
                    else
                    {
                        Log.d(TAG, "Passagem Inválida!");
                        mDialog = new Dialog(getActivity(),
                                getString(R.string.cadastroetiqueta_dialog_title_invalid),
                                getString(R.string.cadastroetiqueta_dialog_text_invalid));
                        mDialog.create();
                        mDialog.getButtonAccept().setText(getString(R.string.dialog_ok));
                        mDialog.show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getActivity(),
                    "Nenhum código de passagem capturado!",
                    Toast.LENGTH_LONG).show();
        }
    }
}