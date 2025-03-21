package com.example.projetofinalmarch.models;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Questao {
    @PrimaryKey(autoGenerate = true)
    private long ID;
    @ColumnInfo(name = "MATERIA")
    public String MATERIA;

    public Questao(String MATERIA) {
        this.MATERIA = MATERIA;
    }

    public long getID() {
        return ID;
    }

    public void setId(long ID) {
        ID = ID;
    }

    public String getMATERIA() {
        return MATERIA;
    }

    public void setMATERIA(String MATERIA) {
        this.MATERIA = MATERIA;
    }
}

