package com.example.projetofinalapril;

import com.example.projetofinalapril.database.AppDatabase;
import com.example.projetofinalapril.database.UsuarioDAO;
import com.example.projetofinalapril.models.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.projetofinalapril.databinding.ActivityCadastrarUsuarioBinding;

import java.util.List;


public class CadastrarUsuarioActivity extends AppCompatActivity {
    private ActivityCadastrarUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o View Binding
        binding = ActivityCadastrarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Isto define o clique do botão usando View Binding
        binding.btnCadastrar.setOnClickListener(v -> {

            try {
                //"Worker thread" criada para não sobrecarregar a ui na consulta/criação do banco de dados
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Criação do usuário e seus valores no BD
                        Usuario usuario = new Usuario();
                        usuario.nome = binding.editNome.getText().toString().trim();
                        usuario.email = binding.editEmail.getText().toString().trim();
                        usuario.senha = binding.editSenha.getText().toString().trim();



                        //Instanciando o BD
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios_comuns").build();
                        UsuarioDAO dao_usuario = db.usuarioDAO();
                        dao_usuario.inserirInfo(usuario);

                        //Prova de que o BD está funcional
                        List<Usuario> usuarios = dao_usuario.listarUsuarios();


                        //Thread para atualizar a ui (principal)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarUsuarioActivity.this);
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
            //Intent para voltar
            Intent intent = new Intent(CadastrarUsuarioActivity.this, TelaInicialActivity.class);
            startActivity(intent);
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