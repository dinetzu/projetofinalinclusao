package com.example.projetofinalmarch.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
    //Criação da entidade para gerar tabela no Room
    @Entity
    public class Usuario {
        @PrimaryKey(autoGenerate = true)
        public long CODIGO;
        //Escolhemos long porque podem haver vários registros.

        @ColumnInfo(name = "NOME")
        public String NOME;

        @ColumnInfo(name = "EMAIL")
        public String EMAIL;
        @ColumnInfo(name = "Senha")
        public String SENHA;

        private static final String PASSWORD_REGEX =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

        // Permite a impressão formatada dos dados usuário durante a main
        @Override
        public String toString() {
            return "Usuario{" +
                    "CODIGO=" + CODIGO +
                    ", NOME='" + NOME + '\'' +
                    ", EMAIL='" + EMAIL + '\'' +
                    ", SENHA='" + SENHA + '\'' +
                    '}';
        }


        public long getCODIGO() {
            return CODIGO;
        }

        public void setCODIGO(long CODIGO) {
            this.CODIGO = CODIGO;
        }

        public String getNOME() {
            return NOME;
        }

        public void setNOME(String NOME) {
            this.NOME = NOME;
        }

        public String getEMAIL() {
            return EMAIL;
        }

        public void setEMAIL(String EMAIL) {
            this.EMAIL = EMAIL;
        }

        public String getSENHA() {
            return SENHA;
        }

        public void setSENHA(String SENHA) {
            // Verifica os dados da senha
            if (SENHA.matches(PASSWORD_REGEX)) {
                this.SENHA = SENHA; // Senha válida
            } else {//Se a senha for inválida, aparece mensagem de erro
                throw new IllegalArgumentException("Senha inválida!");
            }
        }



    }
