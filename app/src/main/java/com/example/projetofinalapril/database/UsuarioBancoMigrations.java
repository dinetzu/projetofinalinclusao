package com.example.projetofinalapril.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//aqui ficarão as migrações necessárias para o banco de dados dos usuários
public class UsuarioBancoMigrations {
    /*  Migração da versão 1 para a versão 2:
    Adiciona not null às colunas de nome, senha e e-mail
    para não cadastrarem campos vazios.*/
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE usuarios ADD COLUMN email TEXT NOT NULL DEFAULT ''");
            database.execSQL("ALTER TABLE usuarios ADD COLUMN nome TEXT NOT NULL DEFAULT ''");
            database.execSQL("ALTER TABLE usuarios ADD COLUMN senha TEXT NOT NULL DEFAULT ''");
        }
    };
}
