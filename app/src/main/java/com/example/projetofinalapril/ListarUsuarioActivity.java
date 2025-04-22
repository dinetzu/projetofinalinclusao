package com.example.projetofinalapril;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projetofinalapril.database.AppDatabase;
import com.example.projetofinalapril.database.UsuarioDAO;
import com.example.projetofinalapril.databinding.ActivityListarUsuarioBinding;
import com.example.projetofinalapril.models.Usuario;
import com.example.projetofinalapril.ui.UsuarioAdapter;

import java.util.List;


// não conseguimos passar os dados do BD para cá, apesar de estar funcional no CadastroActivity
public class ListarUsuarioActivity extends AppCompatActivity {

    private ActivityListarUsuarioBinding binding;
    private UsuarioAdapter itemAdapter;
    private List<Usuario> listaUsuario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o View Binding
        binding = ActivityListarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurando RecyclerView
        binding.recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));


        binding.btnmostrarListagem.setOnClickListener(v-> {
            new Thread(new Runnable() {

                public void run(){
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                    //Cria uma variavel para acessar o usuario DAO
                    UsuarioDAO usuarioDao = db.usuarioDAO();
                    List<Usuario> listaUsuario = usuarioDao.listarUsuarios();

                        runOnUiThread(() -> {
                            //verifica se há usuários no BD
                            if (listaUsuario == null || listaUsuario.isEmpty()) {
                                Toast.makeText(ListarUsuarioActivity.this, "Sem usuários", Toast.LENGTH_SHORT).show();
                            } else {
                                itemAdapter = new UsuarioAdapter(listaUsuario);
                                binding.recyclerViewUsuarios.setAdapter(itemAdapter);
                            }
                        });

                }
            });
        });



    }
}