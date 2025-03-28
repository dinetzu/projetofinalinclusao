package com.example.projetofinalmarch.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projetofinalmarch.models.Questao;


import java.util.List;

public interface QuestaoDAO {
    //seção para a listagem de todos os registros.
    @Query("SELECT * FROM questao")
    List<Questao> listarTodosQ();

    //seção para a inserção de dados no db.
    @Insert
    void inserirInfoQ(Questao... questoes);

    //seção para a remoção de dados no db.
    @Delete
    void deletarInfoQ(Questao questao);
}
