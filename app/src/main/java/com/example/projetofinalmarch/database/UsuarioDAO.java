package com.example.projetofinalmarch.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projetofinalmarch.models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDAO {

    //criação da interface de persistência DAO

    //seção para a listagem de todos os registros.
    @Query("SELECT * FROM usuario")
    List<Usuario> listarUsuarios();

    //seção para a inserção de dados no db.
    @Insert
    void inserirInfo(Usuario... usuarios);

    //seção para a remoção de dados no db.
    @Delete
    void deletarInfo(Usuario usuario);
}