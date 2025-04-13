package com.example.projetofinalapril.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Questao {
    @PrimaryKey(autoGenerate = true)
    private long codigoQuestao;
    @ColumnInfo(name = "disciplina")
    public String disciplina;

    @ColumnInfo(name="conteudo")
    public String conteudo;

    @ColumnInfo(name="serie")
    public String serie;

    @ColumnInfo(name="tipoAdaptacao")
    public String tipoAdaptacao;

    @ColumnInfo(name="data")
    public String data;


    //construtor
    public Questao(String disciplina, String conteudo, String serie,
                   String tipoAdaptacao, String data) {
        this.disciplina = disciplina;
        this.conteudo = conteudo;
        this.serie = serie;
        this.tipoAdaptacao = tipoAdaptacao;
        this.data = data;
    }

    public long getCodigoQuestao() {
        return codigoQuestao;
    }

    public void setCodigoQuestao(long codigoQuestao) {
        this.codigoQuestao = codigoQuestao;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disiciplina) {
        this.disciplina = disiciplina;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipoAdaptacao() {
        return tipoAdaptacao;
    }

    public void setTipoAdaptacao(String tipoAdaptacao) {
        this.tipoAdaptacao = tipoAdaptacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}