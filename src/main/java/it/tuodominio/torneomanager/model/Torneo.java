package it.tuodominio.torneomanager.model;

import java.io.Serializable;
import java.sql.Date; // Attenzione: usa sql.Date per le date del DB

public class Torneo implements Serializable {
    private int idTorneo;
    private String nome;
    private Date dataInizio;
    private Date dataFine;
    private String descrizione;
    private String luogo;
    private int idOrganizzatore;
    private boolean chiuso;
    public Torneo() {}


    public int getIdTorneo() { return idTorneo; }
    public void setIdTorneo(int idTorneo) { this.idTorneo = idTorneo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Date getDataInizio() { return dataInizio; }
    public void setDataInizio(Date dataInizio) { this.dataInizio = dataInizio; }

    public Date getDataFine() { return dataFine; }
    public void setDataFine(Date dataFine) { this.dataFine = dataFine; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }

    public int getIdOrganizzatore() { return idOrganizzatore; }
    public void setIdOrganizzatore(int idOrganizzatore) { this.idOrganizzatore = idOrganizzatore; }
    public boolean isChiuso() {
        return chiuso;
    }
    public void setChiuso(boolean chiuso) {
        this.chiuso = chiuso;
    }
}