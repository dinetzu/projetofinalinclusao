package com.example.projetofinalapril.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Mantendo anotações do Room para compatibilidade, mas agora usando Firebase como principal
@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public long codigoUsuario;

    @ColumnInfo(name = "nome") @NonNull
    public String nome;

    @ColumnInfo(name = "email") @NonNull
    public String email;

    @ColumnInfo(name = "senha") @NonNull
    public String senha;

    // Firebase UID (usado quando vem do Firebase)
    private String firebaseUid;

    // Timestamp de criação
    private long timestamp;

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

    // Construtor principal
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        setSenha(senha);
        this.timestamp = System.currentTimeMillis();
    }

    // Construtor para dados vindos do Firebase (sem validação de senha)
    public Usuario(String nome, String email, String senha, String firebaseUid) {
        this.nome = nome;
        this.email = email;
        this.senha = senha; // Já processada pelo Firebase
        this.firebaseUid = firebaseUid;
        this.timestamp = System.currentTimeMillis();
    }

    // Construtor vazio necessário para Firebase
    public Usuario() {
        // Construtor vazio necessário para Firebase Firestore
    }

    // Permite a impressão formatada dos dados usuário
    @Override
    public String toString() {
        return "Usuario{" +
                "Codigo=" + codigoUsuario +
                ", Nome='" + nome + '\'' +
                ", Email='" + email + '\'' +
                ", FirebaseUID='" + firebaseUid + '\'' +
                ", Timestamp=" + timestamp +
                '}';
    }

    // Método para gerar hashing de senha utilizando o algoritmo SHA-256
    private String gerarHashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha!", e);
        }
    }

    // Método para validar formato de email
    public static boolean isValidEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para validar senha
    public static boolean isValidPassword(String senha) {
        return senha != null && senha.matches(PASSWORD_REGEX);
    }

    // Método para obter mensagem de erro da validação de senha
    public static String getPasswordValidationMessage() {
        return "A senha deve conter:\n" +
                "• Pelo menos 8 caracteres\n" +
                "• Pelo menos 1 letra minúscula\n" +
                "• Pelo menos 1 letra maiúscula\n" +
                "• Pelo menos 1 número";
    }

    // Getters e Setters
    public long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        // Verifica os dados da senha apenas se não for vinda do Firebase
        if (firebaseUid == null && senha != null) {
            if (senha.matches(PASSWORD_REGEX)) {
                this.senha = gerarHashSenha(senha); // Senha válida, gera hash
            } else {
                throw new IllegalArgumentException("Senha inválida! " + getPasswordValidationMessage());
            }
        } else {
            // Se é do Firebase ou nula, aceita como está
            this.senha = senha;
        }
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Método para verificar se o usuário foi criado através do Firebase
    public boolean isFirebaseUser() {
        return firebaseUid != null && !firebaseUid.isEmpty();
    }

    // Método para converter para formato de mapa (útil para Firebase)
    public java.util.Map<String, Object> toMap() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("nome", nome);
        map.put("email", email);
        map.put("timestamp", timestamp);
        if (firebaseUid != null) {
            map.put("uid", firebaseUid);
        }
        return map;
    }
}