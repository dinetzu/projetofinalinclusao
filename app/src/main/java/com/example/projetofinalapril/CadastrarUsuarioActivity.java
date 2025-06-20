package com.example.projetofinalapril;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalapril.databinding.ActivityCadastrarUsuarioBinding;
import com.example.projetofinalapril.firebase.FirebaseManager;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    private ActivityCadastrarUsuarioBinding binding;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o View Binding
        binding = ActivityCadastrarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa Firebase
        firebaseManager = FirebaseManager.getInstance();
        firebaseManager.initializeFirebase(this);

        // Define o clique do botão usando View Binding
        binding.btnCadastrar.setOnClickListener(v -> {
            cadastrarUsuario();
        });

        binding.btnVoltarInicial.setOnClickListener(v -> {
            // Intent para voltar
            Intent intent = new Intent(CadastrarUsuarioActivity.this, TelaInicialActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }

    private void cadastrarUsuario() {
        String nome = binding.editNome.getText().toString().trim();
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        // Validações básicas
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Não aceitamos campos vazios!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.length() < 8) {
            Toast.makeText(this, "Senha deve ter no mínimo 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação de regex para senha forte
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (!senha.matches(passwordRegex)) {
            Toast.makeText(this, "Senha deve conter ao menos: 1 letra minúscula, 1 maiúscula e 1 número",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Desabilita o botão para evitar cliques múltiplos
        binding.btnCadastrar.setEnabled(false);

        // Mostra loading
        Toast.makeText(this, "Cadastrando usuário...", Toast.LENGTH_SHORT).show();

        // Cadastra no Firebase
        firebaseManager.cadastrarUsuario(nome, email, senha, new FirebaseManager.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                runOnUiThread(() -> {
                    binding.btnCadastrar.setEnabled(true);
                    new AlertDialog.Builder(CadastrarUsuarioActivity.this)
                            .setTitle("Sucesso!")
                            .setMessage(result)
                            .setPositiveButton("OK", (dialog, which) -> {
                                // Limpa os campos
                                binding.editNome.setText("");
                                binding.editEmail.setText("");
                                binding.editSenha.setText("");

                                // Volta para tela inicial
                                Intent intent = new Intent(CadastrarUsuarioActivity.this, TelaInicialActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .show();
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    binding.btnCadastrar.setEnabled(true);
                    String errorMessage = "Erro ao cadastrar: ";

                    if (e.getMessage() != null) {
                        if (e.getMessage().contains("email address is already in use")) {
                            errorMessage += "Este email já está em uso!";
                        } else if (e.getMessage().contains("email address is badly formatted")) {
                            errorMessage += "Formato de email inválido!";
                        } else if (e.getMessage().contains("network error")) {
                            errorMessage += "Erro de conexão. Verifique sua internet.";
                        } else {
                            errorMessage += e.getMessage();
                        }
                    } else {
                        errorMessage += "Erro desconhecido";
                    }

                    Toast.makeText(CadastrarUsuarioActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}