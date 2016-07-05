package com.mytcc.appuser.ModoPassageiro;

import android.util.Log;

import com.parse.ParseUser;

import java.util.ArrayList;

public class Passageiro {

    public static final String TAG = "Passageiro" ;

    public static final String PassageiroNomeKey = "PassageiroNome" ;
    public static final String PassageiroSobrenomeKey = "PassageiroSobrenome" ;
    public static final String PassageiroEmailKey = "PassageiroEmail" ;
    public static final String PassageiroDataKey = "PassageiroData" ;
    public static final String PassageiroPhoneKey = "PassageiroPhone" ;
    public static final String PassageiroCPFKey = "PassageiroCPF" ;

    public static final String PassageiroNTicketsKey = "PassageiroNTickets" ;
    public static final String PassageiroTicketKey = "PassageiTicket" ;

    private int nTickets;

    private String nome, sobrenome, email, data_nascimento, phone, cpf;

    private ArrayList<Passagem> tickets;

    /*public Passageiro(String nome, String sobrenome, String poltrona, boolean embarque) {
        this.poltrona = poltrona;
        this.embarque = embarque;
    }*/

    public Passageiro() {}

    public Passageiro(String newnome,
                      String newsobrenome,
                      String newemail,
                      String newdata_nascimento,
                      String newphone,
                      String newcpf)
    {
        this.nome = newnome;
        this.sobrenome = newsobrenome;
        this.email = newemail;
        this.data_nascimento = newdata_nascimento;
        this.phone = newphone;
        this.cpf = newcpf;
    }

    public Passageiro(ParseUser newUser)
    {
        this.nome = newUser.getString("Name");
        this.sobrenome = newUser.getString("Surname");
        this.email = newUser.getUsername();
        this.data_nascimento = newUser.getString("Birthday");
        this.phone = newUser.getString("Phone");
        this.cpf = newUser.getString("CPF");
    }

    public void addTicket(Passagem newTicket)
    {
        if(tickets == null) {
            tickets = new ArrayList<Passagem>();
            tickets.add(newTicket);
        }
        else {
            tickets.add(newTicket);
        }
    }

    public void clearTickets() {
        if(tickets != null) {
            try {
                tickets.clear();
            }
            catch (Exception e) {
                Log.d(TAG,e.getMessage().toString());
            }
        }
    }

    public int getnTickets() {
        return nTickets;
    }

    public void setnTickets(int nTickets) {
        this.nTickets = nTickets;
    }

    public ArrayList<Passagem> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Passagem> tickets) {
        this.tickets = tickets;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
