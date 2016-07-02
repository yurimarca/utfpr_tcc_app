package com.mytcc.appuser.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialog;

import com.mytcc.appuser.ConnectionDetector;
import com.mytcc.appuser.ModoPassageiro.MainActivity;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.ModoPassageiro.Passageiro;
import com.mytcc.appuser.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CadastroActivity extends Activity {

    public static final String TAG = "CadastroActivity" ;

    private MyApplication myApp;

    ConnectionDetector connectionDetector;

    private EditText etName, etSurname, etEmail, etSenha, etSenha2, etData, etFone, etCPF;
    private ButtonRectangle bCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        myApp = (MyApplication)getApplication();

        connectionDetector = new ConnectionDetector(getApplicationContext());

        etName = (EditText)findViewById(R.id.etName);
        etSurname = (EditText)findViewById(R.id.etSurname);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etSenha = (EditText)findViewById(R.id.etPass);
        etSenha2 = (EditText)findViewById(R.id.etPass2);
        etFone = (EditText)findViewById(R.id.etTelefone);
        etData = (EditText)findViewById(R.id.etDataNascimento);
        etCPF = (EditText)findViewById(R.id.etCPF);

        bCadastro = (ButtonRectangle)findViewById(R.id.btnCadastro);
        bCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForms()) {
                    //Check if there is internet connection
                    if(connectionDetector.isConnectingToInternet()) {
                        final ProgressDialog dialog = new ProgressDialog(v.getContext(), "Carregando...");
                        dialog.show();

                        ParseUser user = new ParseUser();
                        user.setUsername(etEmail.getText().toString());
                        user.setPassword(etSenha.getText().toString());
                        user.setEmail(etEmail.getText().toString());

                        user.put("Name", etName.getText().toString());
                        user.put("Surname", etSurname.getText().toString());
                        user.put("Birthday", etData.getText().toString());
                        user.put("Phone", etFone.getText().toString());
                        user.put("CPF", etCPF.getText().toString());
                        user.put("Passenger", true);

                        final Passageiro newUser = new Passageiro(user);

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Toast.makeText(CadastroActivity.this, "Deu bosta!", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();

                                    myApp.setMyUser(newUser);
                                    myApp.setLoggedIn(true);

                                    Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                    //No internet Connection
                    else {
                        Toast.makeText(CadastroActivity.this, "Você tem que estar conectado a internet!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateForms() {
        String msg_error = "Erro ";
        boolean validate = true;
        if(isEmpty(etName)) {
            validate = false;
            msg_error = msg_error + "1";
        }
        if(isEmpty(etSurname)) {
            validate = false;
            msg_error = msg_error + "2";
        }
        if(isEmpty(etEmail)) {
            validate = false;
            msg_error = msg_error + "3";
        }
        if(isEmpty(etSenha)) {
            validate = false;
            msg_error = msg_error + "4";
        }
        if(!isMatching(etSenha, etSenha2)) {
            validate = false;
            msg_error = msg_error + "5";
        }
        if(isEmpty(etFone)) {
            validate = false;
            msg_error = msg_error + "6";
        }
        if(isEmpty(etData)) {
            validate = false;
            msg_error = msg_error + "7";
        }
        if(isEmpty(etCPF)) {
            validate = false;
            msg_error = msg_error + "8";
        }

        if(!validate)
            return false;
        else
            return true;
    }
}
