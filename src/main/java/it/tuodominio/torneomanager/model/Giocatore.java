package it.tuodominio.torneomanager.model;

import java.io.Serializable;

public class Giocatore implements Serializable {
    private int idGiocatore;
    private String nome;
    private String cognome;
    private String ruolo;
    private int numeroMaglia;
    private int idSquadra;

    public Giocatore() {}


    public int getIdGiocatore() { return idGiocatore; }
    public void setIdGiocatore(int idGiocatore) { this.idGiocatore = idGiocatore; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public int getNumeroMaglia() { return numeroMaglia; }
    public void setNumeroMaglia(int numeroMaglia) { this.numeroMaglia = numeroMaglia; }

    public int getIdSquadra() { return idSquadra; }
    public void setIdSquadra(int idSquadra) { this.idSquadra = idSquadra; }
}