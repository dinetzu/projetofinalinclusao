package com.example.projetofinalmarch;

import com.example.projetofinalmarch.database.AppDatabase;
import com.example.projetofinalmarch.database.UsuarioDAO;
import com.example.projetofinalmarch.models.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.projetofinalmarch.databinding.ActivityCadastroBinding;


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

            String NOME_DIGITADO = binding.editNOME.getText().toString().trim();
            String EMAIL_DIGITADO = binding.editEMAIL.getText().toString().trim();
            String SENHA_DIGITADA = binding.editSENHA.getText().toString().trim();

            Usuario USUARIO1 = new Usuario(NOME_DIGITADO, EMAIL_DIGITADO, SENHA_DIGITADA);


            try {
                //worker thread criada para não sobrecarregar a ui na consulta/criação do banco de dados
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios_comuns").build();
                        UsuarioDAO dao_usuario = db.usuarioDAO();



                        //thread para atualizar a ui
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

            }catch (Exception ex){
                Toast.makeText(this, "Cadastro não realizado", Toast.LENGTH_SHORT).show();
            }

            Intent INTENT1 = new Intent(CadastroActivity.this, ListagemTesteActivity.class);
            INTENT1.putExtra("NOME", NOME_DIGITADO);
            INTENT1.putExtra("EMAIL", EMAIL_DIGITADO); //E-mail adicionado via putExtra
            INTENT1.putExtra("SENHA", SENHA_DIGITADA); //E-mail adicionado via putExtra
            startActivity(INTENT1);
            finish();
        });

    }
}