package it.tuodominio.torneomanager.model;

import java.io.Serializable;

public class Utente implements Serializable {
    private int idUtente;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String tipo; // "ORGANIZZATORE", "PRESIDENTE", "ARBITRO"
    private String telefono;

    public Utente() {}

    // Getters e Setters
    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}