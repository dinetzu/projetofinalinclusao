package com.example.projetofinalapril.database;

import static com.example.projetofinalapril.database.UsuarioBancoMigrations.MIGRATION_1_2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projetofinalapril.models.Usuario;

//Define a classe AppDatabase para armazenar o banco de dados.
@Database(entities = {Usuario.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    // Variável estática para armazenar a única instância do banco de dados do usuario
    private static volatile AppDatabase instance;

    //Metodo abstrato para a interface usuario dao

    public abstract UsuarioDAO usuarioDAO();

    //Metodo para ter apenas uma instancia do bd, sem gerar conflitos e resets

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) { // Bloqueio para evitar que muitas threads criem várias instâncias.
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "usuarios_comuns")
                                    .addMigrations(MIGRATION_1_2)
                                    .build();
                }
            }
        }
        //Retorna a instancia se for chamada em outra parte do projeto
        return instance;
    }
}