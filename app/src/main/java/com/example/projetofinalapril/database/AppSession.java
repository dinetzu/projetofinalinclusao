package com.example.projetofinalapril.database;

import android.app.Application;
import com.example.projetofinalapril.firebase.FirebaseManager;

// Classe global que mantém o estado da sessão de login
public class AppSession extends Application {
    private boolean isLoggedIn = false;
    private FirebaseManager firebaseManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializa o Firebase quando a aplicação inicia
        firebaseManager = FirebaseManager.getInstance();
        firebaseManager.initializeFirebase(this);

        // Verifica se o usuário já está logado no Firebase
        if (firebaseManager.isUserLoggedIn()) {
            isLoggedIn = true;
        }
    }

    public boolean isLoggedIn() {
        // Sempre verifica o estado atual do Firebase
        if (firebaseManager != null && firebaseManager.isUserLoggedIn()) {
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
        return isLoggedIn;
    }

    public void login() {
        isLoggedIn = true;
    }

    public void logout() {
        isLoggedIn = false;
        // Também faz logout no Firebase
        if (firebaseManager != null) {
            firebaseManager.logout();
        }
    }

    // Método para obter o FirebaseManager
    public FirebaseManager getFirebaseManager() {
        return firebaseManager;
    }

    // Método para obter informações do usuário atual
    public String getCurrentUserEmail() {
        if (firebaseManager != null && firebaseManager.getCurrentUser() != null) {
            return firebaseManager.getCurrentUser().getEmail();
        }
        return null;
    }

    public String getCurrentUserId() {
        if (firebaseManager != null && firebaseManager.getCurrentUser() != null) {
            return firebaseManager.getCurrentUser().getUid();
        }
        return null;
    }
}