package com.example.projetofinalmarch;

import com.example.projetofinalmarch.database.AppDatabase;
import com.example.projetofinalmarch.database.UsuarioDAO;
import com.example.projetofinalmarch.models.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.projetofinalmarch.databinding.ActivityCadastroBinding;

import java.util.List;


public class CadastroActivity extends AppCompatActivity {
    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o View Binding
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Define o clique do botão usando View Binding
        binding.btnCadastrar.setOnClickListener(v -> {

            try {
                //worker thread criada para não sobrecarregar a ui na consulta/criação do banco de dados
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //criação do usuário e seus valores no BD
                        Usuario USUARIO = new Usuario();
                        USUARIO.NOME = binding.editNOME.getText().toString().trim();
                        USUARIO.EMAIL = binding.editEMAIL.getText().toString().trim();
                        USUARIO.SENHA = binding.editSENHA.getText().toString().trim();



                        //instanciando o BD
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios_comuns").build();
                        UsuarioDAO dao_usuario = db.usuarioDAO();
                        dao_usuario.inserirInfo(USUARIO);

                        //prova de que o BD está funcional
                        List<Usuario> usuarios = dao_usuario.listarUsuarios();
                        for (Usuario usuario : usuarios) {
                            System.out.println("Usuário: " + usuario.NOME);
                        }


                        //thread para atualizar a ui (principal)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                                builder.setMessage("Salvo com sucesso!");
                                builder.create().show();
                            }

                        });

                    }
                }).start();

            }

            catch (Exception ex){
                Toast.makeText(this, "Cadastro não realizado", Toast.LENGTH_SHORT).show();
            }


        });

        binding.btnVoltarInicial.setOnClickListener(v ->{
            //intent para voltar
            Intent INTENT = new Intent(CadastroActivity.this, TelaInicialActivity.class);
            startActivity(INTENT);
            finishAffinity();
        });

        /*
        Depois

        binding.btnVoltarInicial.setOnClickListener(v -> {
            //intent para voltar
            //intent para iniciar a atividade de listagem
            Intent INTENT = new Intent(CadastroActivity.this, TelaInicialActivity.class);


            //Handler para criar um delay
            Handler HANDLER = new Handler(Looper.getMainLooper());
            long DELAY = 2000;

            HANDLER.postDelayed(new Runnable(){
                public void run(){
                    startActivity(INTENT);
                    finish();
                }
            }, DELAY);
        });

         */



    }
}

