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

            //"Worker thread" criada para não sobrecarregar a ui na consulta/criação do banco de dados
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Thread para a validação dos dados
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (binding.editNome.getText().toString().isEmpty() ||
                                    binding.editEmail.getText().toString().isEmpty() ||
                                    binding.editSenha.getText().toString().isEmpty()) {
                                Toast.makeText(CadastrarUsuarioActivity.this, "Não aceitamos campos vazios!",
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (binding.editSenha.getText().toString().length() < 8) {
                                Toast.makeText(CadastrarUsuarioActivity.this, "Senha deve ter no mínimo 8 caracteres",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    // Thread para a criação do Usuário
                    new Thread(() -> {
                        try {
                            // Criando objeto usuário
                            Usuario usuario = new Usuario(
                                    binding.editNome.getText().toString().trim(),
                                    binding.editEmail.getText().toString().trim(),
                                    binding.editSenha.getText().toString().trim()
                            );

                            // Chamando a instância do banco AppDatabase
                            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                            UsuarioDAO dao_usuario = db.usuarioDAO();

                            // Inserindo usuário
                            dao_usuario.inserirInfo(usuario);

                            // Mostra que deu certo
                            runOnUiThread(() -> new AlertDialog.Builder(CadastrarUsuarioActivity.this)
                                    .setMessage("Salvo com sucesso!")
                                    .show());

                        } catch (IllegalArgumentException e) {
                            // Mostra o erro da senha que não obedece aos padrões do REGEX definido na classe usuário
                            runOnUiThread(() -> Toast.makeText(CadastrarUsuarioActivity.this,
                                    "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } catch (Exception e) {
                            // Mostra erros relacionados ao salvamento de dados no BD do Room
                            runOnUiThread(() -> Toast.makeText(CadastrarUsuarioActivity.this,
                                    "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    }).start();
                }
            }).start();




        });

        binding.btnVoltarInicial.setOnClickListener(v ->{
            //Intent para voltar
            Intent intent = new Intent(CadastrarUsuarioActivity.this, TelaInicialActivity.class);
            startActivity(intent);
            finishAffinity();
        });



    }
}