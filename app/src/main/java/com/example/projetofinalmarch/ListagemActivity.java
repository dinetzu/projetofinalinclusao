package com.example.projetofinalmarch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.projetofinalmarch.database.AppDatabase;
import com.example.projetofinalmarch.database.UsuarioDAO;
import com.example.projetofinalmarch.databinding.ActivityListagemBinding;
import com.example.projetofinalmarch.models.Usuario;
import com.example.projetofinalmarch.ui.UsuarioAdapter;

import java.util.List;


// não conseguimos passar os dados do BD para cá, apesar de estar funcional no CadastroActivity
public class ListagemActivity extends AppCompatActivity {

    private ActivityListagemBinding binding;
    private UsuarioAdapter itemAdapter;
    private List<Usuario> listaUsuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o View Binding
        binding = ActivityListagemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurando RecyclerView
        binding.recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        new Thread(new Runnable() {

            public void run(){
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                //Cria uma variavel para acessar o usuario DAO
                UsuarioDAO usuarioDao = db.usuarioDAO();
                List<Usuario> listaUsuario = usuarioDao.listarUsuarios();

                runOnUiThread(() -> {
                    // Inicializando Adapter
                    itemAdapter = new UsuarioAdapter(listaUsuario);
                    binding.recyclerViewUsuarios.setAdapter(itemAdapter);
                });
            }
        });


    }
}