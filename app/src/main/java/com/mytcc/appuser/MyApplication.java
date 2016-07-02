package com.mytcc.appuser;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mytcc.appuser.ModoPassageiro.Passageiro;
import com.mytcc.appuser.ModoPassageiro.Passagem;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    public static final String TAG = "MyApplication" ;

    public static final String PARSE_APPLICATION_ID = "ZzAwnpWUEH9VsAU44OtJtsMSg0TT9N3fxwJg40fK";
    public static final String PARSE_CLIENT_KEY= "4RgnuplFQ9VJwFbigQoXvsv3n0DTxjuPd6GKrLHU";

    public static final String MySharedPreferences = "TCCAppPreferences" ;
    public static final String LoggedInKey = "LoggedIn" ;

    private SharedPreferences appPreferences;

    private boolean loggedIn;
    private Passageiro myUser;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "MyApplication.onCreate()");

        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        appPreferences = getSharedPreferences(MySharedPreferences, Context.MODE_PRIVATE);

        boolean loggedInSaved = appPreferences.getBoolean(LoggedInKey, false);
        if(loggedInSaved) {
            Log.d(TAG, "MyApplication.loggedInSaved == true");
            loggedIn = true;
            myUser = getMyUserSaved();
        }
        else {
            Log.d(TAG, "MyApplication.loggedInSaved == false");
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putBoolean(LoggedInKey, loggedIn);
        editor.commit();
    }

    public Passageiro getMyUser() {
        return myUser;
    }

    public Passageiro getMyUserSaved() {
        Passageiro newUser = new Passageiro();

        appPreferences = getSharedPreferences(MySharedPreferences, Context.MODE_PRIVATE);

        newUser.setNome(appPreferences.getString(Passageiro.PassageiroNomeKey, "NULL"));
        newUser.setSobrenome(appPreferences.getString(Passageiro.PassageiroSobrenomeKey, "NULL"));
        newUser.setEmail(appPreferences.getString(Passageiro.PassageiroEmailKey, "NULL"));
        newUser.setData_nascimento(appPreferences.getString(Passageiro.PassageiroDataKey, "NULL"));
        newUser.setPhone(appPreferences.getString(Passageiro.PassageiroPhoneKey, "NULL"));
        newUser.setCpf(appPreferences.getString(Passageiro.PassageiroCPFKey, "NULL"));

        ArrayList<Passagem> newUserTickets = getMyUserTicketSaved(newUser);
        if(newUserTickets != null) {
            newUser.setTickets(newUserTickets);
        }
        else {
            newUser.setnTickets(0);
        }

        return newUser;
    }

    public ArrayList<Passagem> getMyUserTicketSaved(Passageiro user) {
        appPreferences = getSharedPreferences(MySharedPreferences, Context.MODE_PRIVATE);

        user.setnTickets(appPreferences.getInt(Passageiro.PassageiroNTicketsKey, 0));
        if(user.getnTickets() > 0) {
            ArrayList<Passagem> newList = new ArrayList<Passagem>();

            for(int i = 1; i<=user.getnTickets(); i++) {
                String key = Passageiro.PassageiroTicketKey + Integer.toString(i);

                String OrigemKey = key + Passagem.PassagemOrigemKey;
                String DestinoKey = key + Passagem.PassagemDestinoKey;
                String PartidaKey = key + Passagem.PassagemPartidaKey;
                String ChegadaKey = key + Passagem.PassagemChegadaKey;
                String ClasseKey = key + Passagem.PassagemClasseKey;
                String PrecoKey = key + Passagem.PassagemPrecoKey;
                String PoltronaKey = key + Passagem.PassagemPoltronaKey;
                String ViagemIdKey = key + Passagem.ViagemIdKey;
                String PassagemIdKey = key + Passagem.PassagemIdKey;

                Passagem t = new Passagem();

                String origem = appPreferences.getString(OrigemKey, "NULL");
                String destino = appPreferences.getString(DestinoKey, "NULL");
                String partidaString = appPreferences.getString(PartidaKey, "NULL");
                String chegadaString = appPreferences.getString(ChegadaKey, "NULL");
                String classe = appPreferences.getString(ClasseKey, "NULL");
                String preco = appPreferences.getString(PrecoKey, "NULL");
                String poltrona = appPreferences.getString(PoltronaKey, "NULL");
                String viagemId = appPreferences.getString(ViagemIdKey, "NULL");
                String passagemId = appPreferences.getString(PassagemIdKey, "NULL");

                t.setOrigem(origem);
                t.setDestino(destino);
                t.setPartidaString(partidaString);
                t.setPrevisaoChegadaString(chegadaString);
                t.setClasse(classe);
                t.setPreco(preco);
                t.setPoltrona(poltrona);
                t.setViagemId(viagemId);
                t.setPassagemId(passagemId);

                newList.add(t);
            }

            return newList;
        }
        else {
            return null;
        }
    }

    public void addTicketToUser(Passagem t) {
        int nTickets = myUser.getnTickets();

        myUser.setnTickets(nTickets + 1);

        myUser.addTicket(t);

        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putInt(Passageiro.PassageiroNTicketsKey, myUser.getnTickets());

        String key = Passageiro.PassageiroTicketKey + Integer.toString(myUser.getnTickets());

        String OrigemKey = key + Passagem.PassagemOrigemKey;
        String DestinoKey = key + Passagem.PassagemDestinoKey;
        String PartidaKey = key + Passagem.PassagemPartidaKey;
        String ChegadaKey = key + Passagem.PassagemChegadaKey;
        String ClasseKey = key + Passagem.PassagemClasseKey;
        String PrecoKey = key + Passagem.PassagemPrecoKey;
        String PoltronaKey = key + Passagem.PassagemPoltronaKey;
        String ViagemIdKey = key + Passagem.ViagemIdKey;
        String PassagemIdKey = key + Passagem.PassagemIdKey;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        editor.putString(OrigemKey, t.getOrigem());
        editor.putString(DestinoKey, t.getDestino());
        editor.putString(PartidaKey, t.getPartidaString(df));
        editor.putString(ChegadaKey, t.getPrevisaoChegadaString(df));
        editor.putString(ClasseKey, t.getPreco());
        editor.putString(PrecoKey, t.getPreco());
        editor.putString(PoltronaKey, t.getPoltrona());
        editor.putString(ViagemIdKey, t.getViagemId());
        editor.putString(PassagemIdKey, t.getPassagemId());

        editor.commit();
    }

    public void removeTicketFromUser(Passagem t) {
        myUser.clearTickets();

        listaPassagensDoPassageiro();
    }

    public void setMyUser(Passageiro myUser) {
        this.myUser = myUser;

        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putString(Passageiro.PassageiroNomeKey, myUser.getNome());
        editor.putString(Passageiro.PassageiroSobrenomeKey, myUser.getSobrenome());
        editor.putString(Passageiro.PassageiroEmailKey, myUser.getEmail());
        editor.putString(Passageiro.PassageiroDataKey, myUser.getData_nascimento());
        editor.putString(Passageiro.PassageiroPhoneKey, myUser.getPhone());
        editor.putString(Passageiro.PassageiroCPFKey, myUser.getCpf());
        editor.commit();
    }

    public void deleteMyUser() {
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void listaPassagensDoPassageiro() {
        Map<String, Object> param = new HashMap<>();
        ParseCloud.callFunctionInBackground("listaPassagensDoPassageiro", param, new FunctionCallback<ArrayList<ParseObject>>() {
            @Override
            public void done(ArrayList<ParseObject> arrayTickets, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < arrayTickets.size(); i++) {
                        ParseObject t = arrayTickets.get(i).getParseObject("Viagem");

                        Passagem ticket = new Passagem(t.get("Origem").toString(),
                                t.get("Destino").toString(),
                                (Date) t.get("Partida"),
                                (Date) t.get("PrevisaoChegada"),
                                t.get("Classe").toString(),
                                t.get("Preco").toString(),
                                arrayTickets.get(i).get("Poltrona").toString(),
                                t.getObjectId(),
                                arrayTickets.get(i).getObjectId());

                        addTicketToUser(ticket);
                    }
                }
            }
        });
    }
}