package com.mytcc.appuser.ModoPassageiro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.widgets.Dialog;
import com.mytcc.appuser.Activities.LoginActivity;
import com.mytcc.appuser.ModoPassageiro.Fragments.ChooseTicketFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.MainFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.MyAccountFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.MyTicketsFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.NewTicketFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.SettingsFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.TicketConfirmationFragment;
import com.mytcc.appuser.ModoPassageiro.Fragments.TicketFragment;
import com.mytcc.appuser.MyApplication;
import com.mytcc.appuser.ModoPassageiro.NavigationBar.NavigationDrawerCallbacks;
import com.mytcc.appuser.ModoPassageiro.NavigationBar.NavigationDrawerFragment;
import com.mytcc.appuser.R;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MainPassageioActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    public static final String TAG = "MainPassageioActivity" ;

    //Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private MyApplication myApp;

    private ArrayList<ParseObject> newTicketsFound;
    private ParseObject newTicket;
    private Passagem newTicketPassagem;
    private Set<Integer> listPoltronas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainPassageioActivity.onCreate()");

        myApp = (MyApplication)getApplication();

        setContentView(R.layout.activity_mainpassageiro);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(myApp.getMyUser().getNome(), myApp.getMyUser().getEmail() /*, BitmapFactory.decodeResource(getResources(), R.drawable.avatar)*/);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: //menu_home//todo
                switchFragments(MainFragment.TAG);
                break;
            case 1: //menu_myaccount//todo
                switchFragments(MyAccountFragment.TAG);
                break;
            case 2: //menu_myticket//todo
                switchFragments(MyTicketsFragment.TAG);
                break;
            case 3: //menu_newticket //todo
                switchFragments(NewTicketFragment.TAG);
                break;
            case 4: //menu_settings //todo
                switchFragments(SettingsFragment.TAG);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

                    Intent i = new Intent(MainPassageioActivity.this, LoginActivity.class);
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
                case MainFragment.TAG:
                    fragment = new MainFragment();
                    break;
                case MyAccountFragment.TAG:
                    fragment = new MyAccountFragment();
                    break;
                case MyTicketsFragment.TAG:
                    fragment = new MyTicketsFragment();
                    break;
                case NewTicketFragment.TAG:
                    fragment = new NewTicketFragment();
                    break;
                case SettingsFragment.TAG:
                    fragment = new SettingsFragment();
                    break;
                case ChooseTicketFragment.TAG:
                    fragment = new ChooseTicketFragment();
                    break;
                case TicketConfirmationFragment.TAG:
                    fragment = new TicketConfirmationFragment();
                    break;
                case TicketFragment.TAG:
                    fragment = new TicketFragment();
                    break;
            }
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    public ArrayList<ParseObject> getNewTicketsFound() {
        ArrayList<ParseObject> ret = newTicketsFound;
        return ret;
    }

    public void pushNewTicketsFound(ArrayList<ParseObject> newTicketsFound) {
        this.newTicketsFound = newTicketsFound;
    }

    public void clearNewTicketsFound() {
        this.newTicketsFound.clear();
    }

    public ParseObject getNewTicket() {
        return newTicket;
    }

    public void setNewTicket(ParseObject newTicket) {
        this.newTicket = newTicket;
    }

    public Passagem getNewTicketPassagem() {
        return newTicketPassagem;
    }

    public void setNewTicketViagem(Passagem newTicketPassagem) {
        this.newTicketPassagem = newTicketPassagem;
    }

    public void setListPoltronas(ArrayList <String> poltronas) {
        listPoltronas = new HashSet<Integer>();

        for(int i = 0; i < poltronas.size(); i++){
            listPoltronas.add(Integer.valueOf(poltronas.get(i)));
        }
    }

    public Set<Integer> getListPoltronas() {
        return this.listPoltronas;
    }
}