package com.example.projetofinalapril.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Criação da entidade para gerar tabela no Room
@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public long codigoUsuario;
    //Escolhemos long porque podem haver vários registros.

    @ColumnInfo(name = "nome") @NonNull
    public String nome;

    @ColumnInfo(name = "email") @NonNull
    public String email;
    @ColumnInfo(name = "senha") @NonNull
    public String senha;

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        setSenha(senha);
    }

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

    // Método para gerar hashing de senha utilizando o algoritmo SHA-256
    private String gerarHashSenha(String senha) {
        try {
            // Tenta criar o hashing
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Mostra caso não consiga gerar o hashing da senha
            throw new RuntimeException("Erro ao gerar hash da senha!", e);
        }
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
            this.senha = gerarHashSenha(senha); // Senha válida
        } else {//Se a senha for inválida, aparece mensagem de erro
            throw new IllegalArgumentException("Senha inválida!");
        }
    }



}