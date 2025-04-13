package com.example.projetofinalapril.ui;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalapril.databinding.UsuarioLayoutBinding;
import com.example.projetofinalapril.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioViewHolder> {

    private List<Usuario> usuarios;

    public UsuarioAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios != null ? usuarios : new ArrayList<>();// Evita null
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UsuarioLayoutBinding binding = UsuarioLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UsuarioViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        Log.d("UsuarioAdapter", "Usuário " + position + ": " + usuario.getNome() + " | " + usuario.getEmail());
        holder.binding.textoNome.setText(usuario.getNome());
        holder.binding.textoEmail.setText(usuario.getEmail());
    }

    public int getItemCount() {
        return usuarios.size();
    }
}