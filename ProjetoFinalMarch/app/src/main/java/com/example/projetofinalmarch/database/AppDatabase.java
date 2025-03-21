package com.example.projetofinalmarch.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.projetofinalmarch.models.Usuario;

//Define a classe AppDatabase para armazenar o banco de dados.
@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDAO usuarioDAO();
}
