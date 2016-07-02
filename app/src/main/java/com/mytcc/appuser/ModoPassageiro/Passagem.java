package com.mytcc.appuser.ModoPassageiro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Passagem {

    public static final String PassagemOrigemKey = "PassagemOrigem" ;
    public static final String PassagemDestinoKey = "PassagemDestino" ;
    public static final String PassagemPartidaKey = "PassagemPartida" ;
    public static final String PassagemChegadaKey = "PassagemChegada" ;
    public static final String PassagemClasseKey = "PassagemClasse" ;
    public static final String PassagemPrecoKey = "PassagemPreco" ;
    public static final String PassagemPoltronaKey = "PassagemPoltrona" ;

    public static final String ViagemIdKey = "ViagemId" ;
    public static final String PassagemIdKey = "PassagemId" ;

    private String Origem, Destino, Classe, Preco, Poltrona;
    private Date Partida, PrevisaoChegada;
    private String PassagemId, ViagemId;
    private String NomeCompleto;

    public Passagem() {}

    public Passagem(String origem, String destino, Date partida,  Date chegada,
                    String classe, String preco,   String poltrona, String viagemId,
                    String passagemId) {
        this.Origem = origem;
        this.Destino = destino;
        this.Partida = partida;
        this.PrevisaoChegada = chegada;
        this.Classe = classe;
        this.Preco = preco;
        this.Poltrona = poltrona;
        this.ViagemId = viagemId;
        this.PassagemId = passagemId;
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

    public String getClasse() {
        return Classe;
    }

    public void setClasse(String classe) {
        Classe = classe;
    }

    public String getPreco() {
        return Preco;
    }

    public void setPreco(String preco) {
        Preco = preco;
    }

    public String getPoltrona() {
        return Poltrona;
    }

    public void setPoltrona(String poltrona) {
        Poltrona = poltrona;
    }

    public String getPassagemId() {
        return PassagemId;
    }

    public void setPassagemId(String passagemId) {
        PassagemId = passagemId;
    }

    public String getViagemId() {
        return ViagemId;
    }

    public void setViagemId(String viagemId) {
        ViagemId = viagemId;
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

    public void setPartidaString(String partidaString) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date partidaDate;
        try {
            partidaDate = df.parse(partidaString);
            this.Partida = partidaDate;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }



    public Date getPrevisaoChegada() {
        return PrevisaoChegada;
    }

    public String getPrevisaoChegadaString(DateFormat f) {
        return f.format(this.PrevisaoChegada);
    }

    public void setPrevisaoChegada(Date previsaoChegada) {
        PrevisaoChegada = previsaoChegada;
    }

    public void setPrevisaoChegadaString(String chegadaString) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date chegadaDate;
        try {
            chegadaDate = df.parse(chegadaString);
            this.Partida = chegadaDate;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        NomeCompleto = nomeCompleto;
    }
}
