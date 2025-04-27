package com.example.projetofinalapril;

import static android.provider.LiveFolders.INTENT;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalapril.database.AppSession;
import com.example.projetofinalapril.databinding.ActivityLoginUsuarioBinding;

public class LoginUsuarioActivity extends AppCompatActivity {

    private ActivityLoginUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla o layout com View Binding
        binding = ActivityLoginUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Define o comportamento do botão de login
        binding.btnLogar.setOnClickListener(view -> {
            String email = binding.editEmail.getText().toString();
            String senha = binding.editSenha.getText().toString();

            // Verifica credenciais fixas para exemplo
            if (email.equals("diana@gmail.com") && senha.equals("euAm0Diego!")) {
                ((AppSession) getApplication()).login(); // Salva estado da sessão
                startActivity(new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }

            //SetOnClickListener para ir à tela de listagem.
            binding.btnVoltarInicial.setOnClickListener(v -> {
                //Intenção criada para ir à tela desejada.
                Intent INTENT = new Intent(LoginUsuarioActivity.this, TelaInicialActivity.class);

                //Handler para criar um delay
                Handler HANDLER = new Handler(Looper.getMainLooper());
                long DELAY = 100;

                HANDLER.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(INTENT);
                        finish();
                    }
                }, DELAY);
            });

        });
    }
}
