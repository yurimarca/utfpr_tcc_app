package com.mytcc.appuser.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mytcc.appuser.ConnectionDetector;
import com.mytcc.appuser.ModoOperador.MainOperadorActivity;
import com.mytcc.appuser.ModoPassageiro.MainPassageioActivity;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.ModoPassageiro.Passageiro;
import com.mytcc.appuser.R;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialog;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

    public static final String TAG = "LoginActivity" ;

    private MyApplication myApp;

    ConnectionDetector connectionDetector;

    private EditText etUser, etPass;
    private ButtonRectangle bAcessar;
    private ButtonFlat bCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "LoginActivity.onCreate()");

        myApp = (MyApplication)getApplication();

        /*Intent i = new Intent(LoginActivity.this, CodeReaderActivity.class);
        startActivity(i);
        finish();*/

        if(myApp.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainPassageioActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            connectionDetector = new ConnectionDetector(getApplicationContext());

            etUser = (EditText) findViewById(R.id.etEmail);
            etPass = (EditText) findViewById(R.id.etPass);
            bAcessar = (ButtonRectangle) findViewById(R.id.btnAcessar);
            bCadastrar = (ButtonFlat) findViewById(R.id.btnCadastrar);

            //todo: Fazer verificação do login corretamente
            bAcessar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Validate the log in data
                    boolean validationError = false;
                    StringBuilder validationErrorMessage =
                            new StringBuilder(getResources().getString(R.string.login_error_intro));
                    if (isEmpty(etUser)) {
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.login_error_blank_username));
                    }
                    if (isEmpty(etPass)) {
                        if (validationError) {
                            validationErrorMessage.append(getResources().getString(R.string.login_error_join));
                        }
                        validationError = true;
                        validationErrorMessage.append(getResources().getString(R.string.login_error_blank_password));
                    }
                    validationErrorMessage.append(getResources().getString(R.string.login_error_end));

                    // If there is a validation error, display the error
                    if (validationError) {
                        Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                                .show();
                    }
                    else {
                        //Check if there is internet connection
                        if(connectionDetector.isConnectingToInternet()) {
                            // Set up a progress dialog
                            final ProgressDialog dlg = new ProgressDialog(v.getContext(), "Carregando...");
                            dlg.show();
                            // Call the Parse login method
                            ParseUser.logInInBackground(etUser.getText().toString(), etPass.getText()
                                    .toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    //Log.d(TAG, "User: " + user.getUsername() + ", id: " + user.getObjectId() + ", Name: " + user.getString("Name"));
                                    dlg.dismiss();
                                    if (e != null) {
                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        if(user.getBoolean("Passenger")) {
                                            Passageiro newUser = new Passageiro(user);

                                            myApp.setMyUser(newUser);
                                            myApp.setLoggedIn(true);
                                            myApp.listaPassagensDoPassageiro();

                                            Intent i = new Intent(LoginActivity.this, MainPassageioActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        else {
                                            Intent i = new Intent(LoginActivity.this, MainOperadorActivity.class);
                                            i.putExtra(MainOperadorActivity.NomeOperadorKey, user.get("Surname").toString());
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                        //No internet Connection
                        else {
                            Toast.makeText(LoginActivity.this, "Você tem que estar conectado a internet!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            bCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
