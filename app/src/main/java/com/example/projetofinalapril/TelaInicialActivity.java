package com.example.projetofinalapril;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinalapril.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {

    private ActivityTelaInicialBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCadastro.setOnClickListener(v ->{
            Intent INTENT = new Intent(TelaInicialActivity.this, CadastrarUsuarioActivity.class);


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
        //SetOnClickListener para ir à tela de listagem.
        binding.btnListagem.setOnClickListener(v ->{
            //Intenção criada para ir à tela desejada.
            Intent INTENT = new Intent(TelaInicialActivity.this, ListarUsuarioActivity.class);


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
        //Fecha o programa.
        binding.btnSair.setOnClickListener(v ->{

            finish();
        });
    }
}
