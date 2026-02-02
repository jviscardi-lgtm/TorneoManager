package it.tuodominio.torneomanager.model;

import java.io.Serializable;

public class Squadra implements Serializable {
    private int idSquadra;
    private String nomeSquadra;
    private String logoPath;
    private int idPresidente;

    public Squadra() {}


    public int getIdSquadra() { return idSquadra; }
    public void setIdSquadra(int idSquadra) { this.idSquadra = idSquadra; }

    public String getNomeSquadra() { return nomeSquadra; }
    public void setNomeSquadra(String nomeSquadra) { this.nomeSquadra = nomeSquadra; }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }

    public int getIdPresidente() { return idPresidente; }
    public void setIdPresidente(int idPresidente) { this.idPresidente = idPresidente; }
}