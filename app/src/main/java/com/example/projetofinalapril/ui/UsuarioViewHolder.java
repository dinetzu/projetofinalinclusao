package com.example.projetofinalmarch.ui;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinalmarch.databinding.UsuarioLayoutBinding;
import com.example.projetofinalmarch.models.Usuario;

public class UsuarioViewHolder extends RecyclerView.ViewHolder {

    public UsuarioLayoutBinding binding;

    public UsuarioViewHolder(@NonNull UsuarioLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    //Atualiza componentes da interface com os dados do usuario
    public void bind(Usuario usuario) {
        binding.textoNOME.setText(usuario.NOME);
        binding.textoEMAIL.setText(usuario.EMAIL);
    }
}

