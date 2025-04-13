package com.example.projetofinalapril.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//Criação da entidade para gerar tabela no Room
@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public long codigoUsuario;
    //Escolhemos long porque podem haver vários registros.

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "senha")
    public String senha;

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    // Permite a impressão formatada dos dados usuário durante a main
    @Override
    public String toString() {
        return "Usuario{" +
                "Codigo=" + codigoUsuario +
                ", Nome='" + nome + '\'' +
                ", Email='" + email + '\'' +
                ", Senha='" + senha + '\'' +
                '}';
    }


    public long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        // Verifica os dados da senha
        if (senha.matches(PASSWORD_REGEX)) {
            this.senha = senha; // Senha válida
        } else {//Se a senha for inválida, aparece mensagem de erro
            throw new IllegalArgumentException("Senha inválida!");
        }
    }



}