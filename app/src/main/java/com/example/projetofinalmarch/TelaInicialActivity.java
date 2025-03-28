package com.example.projetofinalmarch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetofinalmarch.databinding.ActivityCadastroBinding;
import com.example.projetofinalmarch.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {

    private ActivityTelaInicialBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCadastro.setOnClickListener(v ->{
            Intent INTENT = new Intent(TelaInicialActivity.this, CadastroActivity.class);


            //Handler para criar um delay
            Handler HANDLER = new Handler(Looper.getMainLooper());
            long DELAY = 100;

            HANDLER.postDelayed(new Runnable(){
                public void run(){
                    startActivity(INTENT);
                    finish();
                }
            }, DELAY);
        });

        binding.btnListagem.setOnClickListener(v ->{
            Intent INTENT = new Intent(TelaInicialActivity.this, ListagemActivity.class);


            //Handler para criar um delay
            Handler HANDLER = new Handler(Looper.getMainLooper());
            long DELAY = 100;

            HANDLER.postDelayed(new Runnable(){
                public void run(){
                    startActivity(INTENT);
                    finish();
                }
            }, DELAY);
        });

        binding.btnSair.setOnClickListener(v ->{

            finish();
        });
    }
}
