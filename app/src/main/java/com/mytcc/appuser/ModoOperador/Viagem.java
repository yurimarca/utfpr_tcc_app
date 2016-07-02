package com.mytcc.appuser.ModoOperador;

import com.mytcc.appuser.ModoPassageiro.Passageiro;
import com.mytcc.appuser.ModoPassageiro.Passagem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Viagem {

    private String Origem, Destino;
    private int VagasOcupadas;
    private Date Partida;
    private ArrayList<Passagem> listaPassagens;
    private String id;

    public Viagem() {}

    public Viagem(String _id) {
        this.id = _id;
    }

    public String getOrigem() {
        return Origem;
    }

    public void setOrigem(String origem) {
        Origem = origem;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public int getVagasOcupadas() {
        return VagasOcupadas;
    }

    public void setVagasOcupadas(int vagasOcupadas) {
        VagasOcupadas = vagasOcupadas;
    }

    public Date getPartida() {
        return Partida;
    }

    public String getPartidaString(DateFormat f) {
        return f.format(this.Partida);
    }

    public void setPartida(Date partida) {
        Partida = partida;
    }

    public void setPartidaString(String partida) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date _partida;
        try {
            _partida = df.parse(partida);
            this.Partida = _partida;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Passagem> getListaPassagens() {
        return listaPassagens;
    }

    public void setListaPassagens(ArrayList<Passagem> listaPassagens) {
        this.listaPassagens = listaPassagens;
    }

    public void addListaPassagens(Passagem newPassagem) {
        this.listaPassagens.add(newPassagem);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
