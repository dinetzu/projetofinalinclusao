package com.example.projetofinalapril.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Questao {
    @PrimaryKey(autoGenerate = true)
    private long codigoQuestao;
    private String texto;
    private String textoResposta;
    private String nivelDificuldade;


    //construtor
    public Questao(String texto, String textoResposta, String nivelDificuldade) {
        this.texto = texto;
        this.textoResposta = textoResposta;
        this.nivelDificuldade = nivelDificuldade;
    }

    public long getCodigoQuestao() {
        return codigoQuestao;
    }

    public void setCodigoQuestao(long codigoQuestao) {
        this.codigoQuestao = codigoQuestao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }

    public String getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(String nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }
}