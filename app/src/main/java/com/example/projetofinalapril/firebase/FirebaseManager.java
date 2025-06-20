package com.example.projetofinalapril.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.projetofinalapril.models.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {
    private static final String TAG = "FirebaseManager";
    private static final String COLLECTION_USUARIOS = "usuarios";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static FirebaseManager instance;

    private FirebaseManager() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public void initializeFirebase(Context context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }
    }

    // Interface para callbacks
    public interface OnCompleteListener<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }

    // Cadastrar usuário com Authentication
    public void cadastrarUsuario(String nome, String email, String senha, OnCompleteListener<String> listener) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Salvar dados adicionais no Firestore
                            salvarDadosUsuario(user.getUid(), nome, email, listener);
                        }
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }

    // Salvar dados do usuário no Firestore
    private void salvarDadosUsuario(String uid, String nome, String email, OnCompleteListener<String> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("uid", uid);
        userData.put("nome", nome);
        userData.put("email", email);
        userData.put("timestamp", System.currentTimeMillis());

        db.collection(COLLECTION_USUARIOS)
                .document(uid)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Usuário salvo no Firestore");
                    listener.onSuccess("Usuário cadastrado com sucesso!");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Erro ao salvar usuário no Firestore", e);
                    listener.onFailure(e);
                });
    }

    // Login de usuário
    public void loginUsuario(String email, String senha, OnCompleteListener<FirebaseUser> listener) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        listener.onSuccess(user);
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }

    // Logout
    public void logout() {
        mAuth.signOut();
    }

    // Verificar se usuário está logado
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    // Obter usuário atual
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Listar todos os usuários (apenas para administradores)
    public void listarUsuarios(OnCompleteListener<List<Usuario>> listener) {
        db.collection(COLLECTION_USUARIOS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Usuario> usuarios = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                String nome = document.getString("nome");
                                String email = document.getString("email");
                                String uid = document.getString("uid");

                                if (nome != null && email != null) {
                                    // Criar usuário sem senha para listagem
                                    Usuario usuario = new Usuario(nome, email, "********");
                                    usuario.setCodigoUsuario(uid.hashCode()); // Usar hash do UID como código
                                    usuarios.add(usuario);
                                }
                            } catch (Exception e) {
                                Log.w(TAG, "Erro ao processar documento: " + document.getId(), e);
                            }
                        }
                        listener.onSuccess(usuarios);
                    } else {
                        listener.onFailure(task.getException());
                    }
                });
    }

    // Obter dados do usuário atual
    public void obterDadosUsuarioAtual(OnCompleteListener<Map<String, Object>> listener) {
        FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null) {
            db.collection(COLLECTION_USUARIOS)
                    .document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            listener.onSuccess(document.getData());
                        } else {
                            listener.onFailure(new Exception("Dados do usuário não encontrados"));
                        }
                    })
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("Usuário não está logado"));
        }
    }

    // Atualizar dados do usuário
    public void atualizarUsuario(String nome, OnCompleteListener<String> listener) {
        FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("nome", nome);
            updates.put("lastUpdate", System.currentTimeMillis());

            db.collection(COLLECTION_USUARIOS)
                    .document(currentUser.getUid())
                    .update(updates)
                    .addOnSuccessListener(aVoid -> listener.onSuccess("Usuário atualizado com sucesso!"))
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("Usuário não está logado"));
        }
    }

    // Deletar conta do usuário
    public void deletarContaUsuario(OnCompleteListener<String> listener) {
        FirebaseUser currentUser = getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            // Primeiro deletar do Firestore
            db.collection(COLLECTION_USUARIOS)
                    .document(uid)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Depois deletar da Authentication
                        currentUser.delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        listener.onSuccess("Conta deletada com sucesso!");
                                    } else {
                                        listener.onFailure(task.getException());
                                    }
                                });
                    })
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("Usuário não está logado"));
        }
    }
}