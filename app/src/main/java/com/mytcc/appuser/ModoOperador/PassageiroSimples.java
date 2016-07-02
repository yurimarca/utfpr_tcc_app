package com.mytcc.appuser.ModoOperador;

public class PassageiroSimples {

    private String NomeCompleto, poltrona;
    private boolean embarque;

    PassageiroSimples(){

    }

    PassageiroSimples(String nome, String poltrona, boolean embarque) {
        this.NomeCompleto = nome;
        this.poltrona = poltrona;
        this.embarque = embarque;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        NomeCompleto = nomeCompleto;
    }

    public String getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(String poltrona) {
        this.poltrona = poltrona;
    }

    public boolean isEmbarque() {
        return embarque;
    }

    public void setEmbarque(boolean embarque) {
        this.embarque = embarque;
    }
}
