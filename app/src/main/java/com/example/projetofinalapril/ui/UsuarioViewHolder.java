package com.example.projetofinalapril.ui;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalapril.databinding.UsuarioLayoutBinding;
import com.example.projetofinalapril.models.Usuario;

public class UsuarioViewHolder extends RecyclerView.ViewHolder {

    public UsuarioLayoutBinding binding;

    public UsuarioViewHolder(@NonNull UsuarioLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}