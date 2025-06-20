package com.example.projetofinalapril;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalapril.database.AppSession;
import com.example.projetofinalapril.databinding.ActivityLoginUsuarioBinding;
import com.example.projetofinalapril.firebase.FirebaseManager;
import com.google.firebase.auth.FirebaseUser;

public class LoginUsuarioActivity extends AppCompatActivity {

    private ActivityLoginUsuarioBinding binding;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla o layout com View Binding
        binding = ActivityLoginUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa Firebase
        firebaseManager = FirebaseManager.getInstance();
        firebaseManager.initializeFirebase(this);

        // Verifica se usuário já está logado
        if (firebaseManager.isUserLoggedIn()) {
            // Se já está logado, vai direto para tela inicial
            ((AppSession) getApplication()).login();
            startActivity(new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class));
            finish();
            return;
        }

        // Define o comportamento do botão de login
        binding.btnLogar.setOnClickListener(view -> {
            realizarLogin();
        });

        // SetOnClickListener para voltar à tela inicial
        binding.btnVoltarInicial.setOnClickListener(v -> {
            Intent intent = new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class);

            Handler handler = new Handler(Looper.getMainLooper());
            long delay = 100;

            handler.postDelayed(() -> {
                startActivity(intent);
                finish();
            }, delay);
        });
    }

    private void realizarLogin() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        // Validações básicas
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Desabilita o botão para evitar cliques múltiplos
        binding.btnLogar.setEnabled(false);

        // Mostra loading
        Toast.makeText(this, "Realizando login...", Toast.LENGTH_SHORT).show();

        // Realiza login com Firebase
        firebaseManager.loginUsuario(email, senha, new FirebaseManager.OnCompleteListener<FirebaseUser>() {
            @Override
            public void onSuccess(FirebaseUser user) {
                runOnUiThread(() -> {
                    binding.btnLogar.setEnabled(true);

                    // Salva estado da sessão
                    ((AppSession) getApplication()).login();

                    Toast.makeText(LoginUsuarioActivity.this,
                            "Login realizado com sucesso! Bem-vindo, " + user.getEmail(),
                            Toast.LENGTH_SHORT).show();

                    // Vai para tela inicial
                    startActivity(new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class));
                    finish();
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    binding.btnLogar.setEnabled(true);

                    String errorMessage = "Erro no login: ";

                    if (e.getMessage() != null) {
                        if (e.getMessage().contains("password is invalid")) {
                            errorMessage += "Senha incorreta!";
                        } else if (e.getMessage().contains("no user record")) {
                            errorMessage += "Usuário não encontrado!";
                        } else if (e.getMessage().contains("email address is badly formatted")) {
                            errorMessage += "Formato de email inválido!";
                        } else if (e.getMessage().contains("network error")) {
                            errorMessage += "Erro de conexão. Verifique sua internet.";
                        } else if (e.getMessage().contains("too many requests")) {
                            errorMessage += "Muitas tentativas. Tente novamente mais tarde.";
                        } else {
                            errorMessage += "Credenciais inválidas";
                        }
                    } else {
                        errorMessage += "Erro desconhecido";
                    }

                    Toast.makeText(LoginUsuarioActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Verifica novamente se o usuário está logado ao iniciar a activity
        if (firebaseManager.isUserLoggedIn()) {
            ((AppSession) getApplication()).login();
            startActivity(new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class));
            finish();
        }
    }
}