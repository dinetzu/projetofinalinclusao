package com.example.projetofinalmarch;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetofinalmarch.database.AppDatabase;
import com.example.projetofinalmarch.database.UsuarioDAO;
import com.example.projetofinalmarch.databinding.ActivityListagemTesteBinding;
import com.example.projetofinalmarch.models.Usuario;

import androidx.room.Room;

import java.util.List;

public class ListagemTesteActivity extends AppCompatActivity {

    /*
    Esta atividade ainda está em DESENVOLVIMENTO, ou seja, INCOMPLETA e não está COMPLETAMENTE FUNCIONAL.
    Sugestões para a continuação do código são bem-vindas.
     */



    private ActivityListagemTesteBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o View Binding
        binding = ActivityListagemTesteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String NOME1 = getIntent().getStringExtra("NOME");
        String EMAIL1 = getIntent().getStringExtra("EMAIL");
        String SENHA1 = getIntent().getStringExtra("SENHA");
    }

    public void listagemUsuarios() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "usuarios_comuns").build();
                UsuarioDAO dao_usuario = db.usuarioDAO();

                List<Usuario> usuario = dao_usuario.listarTodos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(usuario);
                    }

                });
            }
        }).start();


    }
    public void voltarAoInicio(){
        finish();
    }
}