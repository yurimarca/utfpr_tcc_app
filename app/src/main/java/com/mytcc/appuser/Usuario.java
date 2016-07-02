package com.mytcc.appuser;

public class Usuario {

    public static final String TAG = "Usuario" ;

    public static final String UsuarioNomeKey = "UsuarioNome" ;
    public static final String UsuarioSobrenomeKey = "UsuarioSobrenome" ;
    public static final String UsuarioEmailKey = "UsuarioEmail" ;
    public static final String UsuarioDataKey = "UsuarioData" ;
    public static final String UsuarioPhoneKey = "UsuarioPhone" ;
    public static final String UsuarioCPFKey = "UsuarioCPF" ;

    protected String nome, sobrenome, email, data_nascimento, phone, cpf;

    public Usuario() {}

    public Usuario(String newnome, String newsobrenome, String newemail, String newdata_nascimento,
            String newphone,String newcpf)
    {
        this.nome = newnome;
        this.sobrenome = newsobrenome;
        this.email = newemail;
        this.data_nascimento = newdata_nascimento;
        this.phone = newphone;
        this.cpf = newcpf;
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
