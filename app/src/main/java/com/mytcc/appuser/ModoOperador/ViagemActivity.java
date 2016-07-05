package com.mytcc.appuser.ModoOperador;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.EmbarqueFragment;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.FinalizaEmbarqueFragment;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.ListaPassageirosEmbarcadosFragment;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.ListaPassageirosFragment;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.ListaViagensFragment;
import com.mytcc.appuser.ModoOperador.FragmentsOperador.MainOperadorFragment;
import com.mytcc.appuser.ModoOperador.FragmentsViagem.MainViagemFragment;
import com.mytcc.appuser.ModoOperador.FragmentsViagem.RegistroPercursoFragment;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.R;

public class ViagemActivity extends ActionBarActivity {

    public static final String TAG = "ViagemActivity" ;

    private Toolbar mToolbar;

    private MyApplication myApp;

    private Viagem viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "ViagemActivity.onCreate()");

        myApp = (MyApplication)getApplication();

        viagem = myApp.getMyViagem();

        setContentView(R.layout.activity_mainoperador);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        switchFragments(MainViagemFragment.TAG);

        startService(new Intent(this, ViagemService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viagem, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void switchFragments(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case MainViagemFragment.TAG:
                    fragment = new MainViagemFragment();
                    break;
                case RegistroPercursoFragment.TAG:
                    fragment = new RegistroPercursoFragment();
                    break;
            }
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    @Override
    public void onBackPressed() {
        switchFragments(MainViagemFragment.TAG);
    }

    public void FinalizarViagem() {
        stopService(new Intent(this, ViagemService.class));

        Intent i = new Intent(ViagemActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public Viagem getViagem() {
        return viagem;
    }
}
