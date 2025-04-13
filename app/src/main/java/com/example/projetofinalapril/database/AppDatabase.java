package com.example.projetofinalmarch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projetofinalmarch.models.Usuario;

//Define a classe AppDatabase para armazenar o banco de dados.
@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // Variável estática para armazenar a única instância do banco de dados do usuario
    private static volatile AppDatabase INSTANCE;

    /*
    Metodo abstrato para a interface usuario dao
     */
    public abstract UsuarioDAO usuarioDAO();

    /*
    Metodo para ter apenas uma instancia do bd, sem gerar conflitos e resets
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) { // Bloqueio para garantir thread safety
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "usuarios_comuns")
                            .build();
                }
            }
        }
        //Retorna a instancia se for chamada em outra parte do projeto
        return INSTANCE;
    }
}
