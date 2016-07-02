package com.mytcc.appuser.ModoOperador;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoOperador.Fragments.EmbarqueFragment;
import com.mytcc.appuser.ModoOperador.Fragments.FinalizaEmbarqueFragment;
import com.mytcc.appuser.ModoOperador.Fragments.ListaPassageirosEmbarcadosFragment;
import com.mytcc.appuser.ModoOperador.Fragments.ListaPassageirosFragment;
import com.mytcc.appuser.ModoOperador.Fragments.ListaViagensFragment;
import com.mytcc.appuser.ModoOperador.Fragments.MainOperadorFragment;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainOperadorActivity extends ActionBarActivity {

    public static final String TAG = "MainOperadorActivity" ;

    public static final String NomeOperadorKey = "NomeOperador" ;

    public static final int MODO_ENVIAR_NOTIFICATION = 1;
    public static final int MODO_REALIZAR_EMBARQUE = 2;
    public static final int MODO_LISTAR_PASSAGEIROS = 3;

    private Toolbar mToolbar;

    private MyApplication myApp;

    private ArrayList<Viagem> viagensOperador, viagensOperadorNovas;
    private Viagem viagemOperador;

    private ArrayList<PassageiroSimples> listaPassageiros;

    private ArrayList<String> listaNomePassageirosEmbarcados;

    private int modoFuncionamento;

    private String NomeOperador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainOperadorActivity.onCreate()");

        retornaViagemMotorista();

        myApp = (MyApplication)getApplication();

        NomeOperador = getIntent().getExtras().getString(NomeOperadorKey);

        setContentView(R.layout.activity_mainoperador);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        switchFragments(MainOperadorFragment.TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout) {
            Dialog d = new Dialog(this, getString(R.string.main_logout_title), getString(R.string.main_logout_text));
            d.addCancelButton(getString(R.string.dialog_no));
            d.setOnAcceptButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myApp.setLoggedIn(false);
                    myApp.deleteMyUser();

                    Intent i = new Intent(MainOperadorActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            d.create();
            d.getButtonAccept().setText(getString(R.string.dialog_yes));
            d.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragments(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case MainOperadorFragment.TAG:
                    fragment = new MainOperadorFragment();
                    break;
                case ListaViagensFragment.TAG:
                    fragment = new ListaViagensFragment();
                    break;
                case EmbarqueFragment.TAG:
                    fragment = new EmbarqueFragment();
                    break;
                case FinalizaEmbarqueFragment.TAG:
                    fragment = new FinalizaEmbarqueFragment();
                    break;
                case ListaPassageirosFragment.TAG:
                    fragment = new ListaPassageirosFragment();
                    break;
                case ListaPassageirosEmbarcadosFragment.TAG:
                    fragment = new ListaPassageirosEmbarcadosFragment();
                    break;
            }
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    @Override
    public void onBackPressed() {
        switchFragments(MainOperadorFragment.TAG);
    }

    public void retornaViagemMotorista() {
        Map<String, Object> param = new HashMap<>();
        ParseCloud.callFunctionInBackground("retornaViagemMotorista", param, new FunctionCallback<ArrayList<ParseObject>>() {
            @Override
            public void done(ArrayList<ParseObject> arrayViagens, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < arrayViagens.size(); i++) {
                        Viagem v = new Viagem(arrayViagens.get(i).getObjectId());
                        v.setOrigem(arrayViagens.get(i).get("Origem").toString());
                        v.setDestino(arrayViagens.get(i).get("Destino").toString());
                        //v.setVagasOcupadas(Integer.getInteger(arrayViagens.get(i).get("Vagas").toString()) - 44);
                        v.setPartida((Date) arrayViagens.get(i).get("Partida"));
                        v.setId(arrayViagens.get(i).getObjectId());

                        addViagensOperador(v);
                    }
                } else {
                    setViagensOperador(null);
                }
            }
        });
    }

    public void listaPassageirosDaViagem() {
        listaPassageiros = new ArrayList<PassageiroSimples>();

        Map<String, Object> param = new HashMap<>();
        param.put("idViagem", viagemOperador.getId());
        ParseCloud.callFunctionInBackground("listaPassageirosDaViagem", param, new FunctionCallback<ArrayList<ParseObject>>() {
            @Override
            public void done(ArrayList<ParseObject> listaP, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < listaP.size(); i++) {
                        PassageiroSimples newp = new PassageiroSimples();
                        newp.setNomeCompleto(listaP.get(i).getString("NomeCompleto"));
                        newp.setPoltrona(listaP.get(i).getString("Poltrona"));
                        newp.setEmbarque(listaP.get(i).getBoolean("Embarque"));
                        addListaPassageiros(newp);
                    }
                } else {
                    setViagensOperador(null);
                }
            }
        });
    }

    public void listaPassageirosDaViagemCall() {
        listaPassageirosDaViagemAsync listaPassageiros = new listaPassageirosDaViagemAsync();
        listaPassageiros.execute();
    }

    private class listaPassageirosDaViagemAsync extends AsyncTask<String, String, String> {

        ProgressDialog dlg;

        public listaPassageirosDaViagemAsync() {
            dlg = new ProgressDialog(getApplicationContext(), "Carregando...");
        }

        @Override
        protected String doInBackground(String... params) {
            listaPassageirosDaViagem();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            dlg.dismiss();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... values) {

        }
    }

    public ArrayList<Viagem> getViagensOperador() {
        return viagensOperador;
    }

    public ArrayList<Viagem> getViagensNovasOperador() {
        return viagensOperadorNovas;
    }

    public void setViagensOperador(ArrayList<Viagem> viagensOperador) {
        this.viagensOperador = viagensOperador;
    }

    public void addViagensOperador(Viagem newViagem) {
        if(viagensOperador == null)
            viagensOperador = new ArrayList<Viagem>();

        this.viagensOperador.add(newViagem);
    }

    public String getNomeOperador() {
        return NomeOperador;
    }

    public Viagem getViagemOperador() {
        return viagemOperador;
    }

    public void setViagemOperador(Viagem viagemOperador) {
        this.viagemOperador = viagemOperador;
    }

    public boolean checkViagens() {
        if(getViagensNovasOperador() == null) {
            if (getViagensOperador() != null) {
                viagensOperadorNovas = new ArrayList<Viagem>();
                boolean aux_empty = false;

                for (int i = 0; i < viagensOperador.size(); i++) {
                    if (viagensOperador.get(i).getPartida().after(Calendar.getInstance().getTime())) {
                        viagensOperadorNovas.add(viagensOperador.get(i));
                        aux_empty = true;
                    }
                }

                if (aux_empty)
                    return true;
            }
            return false;
        }
        else {
            return true;
        }
    }

    public int getModoFuncionamento() {
        return modoFuncionamento;
    }

    public void setModoFuncionamento(int modoFuncionamento) {
        this.modoFuncionamento = modoFuncionamento;
    }

    public ArrayList<PassageiroSimples> getListaPassageiros() {
        return listaPassageiros;
    }

    public void setListaPassageiros(ArrayList<PassageiroSimples> listaPassageiros) {
        this.listaPassageiros = listaPassageiros;
    }

    public void addListaPassageiros(PassageiroSimples newPassageiro) {
        if(listaPassageiros == null)
            listaPassageiros = new ArrayList<PassageiroSimples>();

        this.listaPassageiros.add(newPassageiro);
    }

    public ArrayList<String> getListaNomePassageirosEmbarcados() {
        return listaNomePassageirosEmbarcados;
    }

    public void setListaNomePassageirosEmbarcados(ArrayList<String> listaNomePassageirosEmbarcados) {
        this.listaNomePassageirosEmbarcados = listaNomePassageirosEmbarcados;
    }

    public void addListaNomePassageirosEmbarcados(String nomePassageiro) {
        boolean repetido = false;

        if(listaNomePassageirosEmbarcados == null)
            listaNomePassageirosEmbarcados = new ArrayList<String>();

        for(int i=0; i<listaNomePassageirosEmbarcados.size(); i++) {
            if(listaNomePassageirosEmbarcados.get(i).matches(nomePassageiro)) {
                repetido = true;
            }
        }
        if(!repetido)
            this.listaNomePassageirosEmbarcados.add(nomePassageiro);
    }
}