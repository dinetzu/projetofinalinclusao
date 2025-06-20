package com.example.projetofinalapril;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalapril.database.AppSession;
import com.example.projetofinalapril.databinding.ActivityTelaInicialBinding;
import com.example.projetofinalapril.firebase.FirebaseManager;

public class TelaInicialActivity extends AppCompatActivity {

    private ActivityTelaInicialBinding binding;
    private AppSession appSession;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa componentes
        appSession = (AppSession) getApplication();
        firebaseManager = appSession.getFirebaseManager();

        // Atualiza a interface baseado no estado de login
        atualizarInterface();

        binding.btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(TelaInicialActivity.this, CadastrarUsuarioActivity.class);

            Handler handler = new Handler(Looper.getMainLooper());
            long delay = 100;

            handler.postDelayed(() -> {
                startActivity(intent);
                finish();
            }, delay);
        });

        // SetOnClickListener para ir à tela de listagem
        binding.btnListagem.setOnClickListener(v -> {
            // Verifica se o usuário está logado antes de permitir acesso
            if (appSession.isLoggedIn()) {
                Intent intent = new Intent(TelaInicialActivity.this, ListarUsuarioActivity.class);

                Handler handler = new Handler(Looper.getMainLooper());
                long delay = 100;

                handler.postDelayed(() -> {
                    startActivity(intent);
                    finish();
                }, delay);
            } else {
                Toast.makeText(this, "Você precisa estar logado para ver a lista de usuários",
                        Toast.LENGTH_LONG).show();
            }
        });

        // SetOnClickListener para ir à tela de login
        binding.btnLogin.setOnClickListener(v -> {
            if (appSession.isLoggedIn()) {
                // Se já está logado, oferece opção de logout
                mostrarDialogoLogout();
            } else {
                // Se não está logado, vai para tela de login
                Intent intent = new Intent(TelaInicialActivity.this, LoginUsuarioActivity.class);

                Handler handler = new Handler(Looper.getMainLooper());
                long delay = 100;

                handler.postDelayed(() -> {
                    startActivity(intent);
                    finish();
                }, delay);
            }
        });

        // Fecha o programa
        binding.btnSair.setOnClickListener(v -> {
            // Se estiver logado, faz logout antes de sair
            if (appSession.isLoggedIn()) {
                appSession.logout();
            }
            finish();
        });
    }

    private void atualizarInterface() {
        if (appSession.isLoggedIn()) {
            // Usuário logado
            String email = appSession.getCurrentUserEmail();
            binding.btnLogin.setText("Logout (" + (email != null ? email : "Usuário") + ")");

            Toast.makeText(this, "Bem-vindo de volta!", Toast.LENGTH_SHORT).show();
        } else {
            // Usuário não logado
            binding.btnLogin.setText("Login");
        }
    }

    private void mostrarDialogoLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Deseja fazer logout?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    // Faz logout
                    appSession.logout();

                    Toast.makeText(TelaInicialActivity.this, "Logout realizado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    // Atualiza a interface
                    atualizarInterface();
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza a interface sempre que a activity volta ao foco
        atualizarInterface();
    }
}