package com.example.projetofinalapril;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projetofinalapril.databinding.ActivityListarUsuarioBinding;
import com.example.projetofinalapril.firebase.FirebaseManager;
import com.example.projetofinalapril.models.Usuario;
import com.example.projetofinalapril.ui.UsuarioAdapter;

import java.util.List;

public class ListarUsuarioActivity extends AppCompatActivity {

    private ActivityListarUsuarioBinding binding;
    private UsuarioAdapter itemAdapter;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o View Binding
        binding = ActivityListarUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa Firebase
        firebaseManager = FirebaseManager.getInstance();
        firebaseManager.initializeFirebase(this);

        // Configurando RecyclerView
        binding.recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        binding.btnmostrarListagem.setOnClickListener(v -> {
            carregarListaUsuarios();
        });

        // Carrega automaticamente ao abrir a tela
        carregarListaUsuarios();
    }

    private void carregarListaUsuarios() {
        // Verifica se o usuário está logado
        if (!firebaseManager.isUserLoggedIn()) {
            Toast.makeText(this, "Você precisa estar logado para ver a lista de usuários",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Desabilita o botão temporariamente
        binding.btnmostrarListagem.setEnabled(false);

        // Mostra loading
        Toast.makeText(this, "Carregando usuários...", Toast.LENGTH_SHORT).show();

        // Busca usuários no Firebase
        firebaseManager.listarUsuarios(new FirebaseManager.OnCompleteListener<List<Usuario>>() {
            @Override
            public void onSuccess(List<Usuario> listaUsuarios) {
                runOnUiThread(() -> {
                    binding.btnmostrarListagem.setEnabled(true);

                    // Verifica se há usuários
                    if (listaUsuarios == null || listaUsuarios.isEmpty()) {
                        Toast.makeText(ListarUsuarioActivity.this,
                                "Nenhum usuário encontrado no sistema",
                                Toast.LENGTH_SHORT).show();

                        // Limpa o RecyclerView se não há usuários
                        if (itemAdapter != null) {
                            binding.recyclerViewUsuarios.setAdapter(null);
                        }
                    } else {
                        // Atualiza o adapter com a nova lista
                        itemAdapter = new UsuarioAdapter(listaUsuarios);
                        binding.recyclerViewUsuarios.setAdapter(itemAdapter);

                        Toast.makeText(ListarUsuarioActivity.this,
                                "Carregados " + listaUsuarios.size() + " usuários",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    binding.btnmostrarListagem.setEnabled(true);

                    String errorMessage = "Erro ao carregar usuários: ";

                    if (e.getMessage() != null) {
                        if (e.getMessage().contains("permission")) {
                            errorMessage += "Sem permissão para acessar os dados";
                        } else if (e.getMessage().contains("network")) {
                            errorMessage += "Erro de conexão. Verifique sua internet.";
                        } else {
                            errorMessage += e.getMessage();
                        }
                    } else {
                        errorMessage += "Erro desconhecido";
                    }

                    new AlertDialog.Builder(ListarUsuarioActivity.this)
                            .setTitle("Erro")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega a lista quando a activity volta ao foco
        // para garantir que os dados estão atualizados
        if (firebaseManager.isUserLoggedIn()) {
            carregarListaUsuarios();
        }
    }
}