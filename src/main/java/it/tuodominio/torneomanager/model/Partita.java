package it.tuodominio.torneomanager.model;

import java.io.Serializable;
import java.sql.Timestamp; // Importante per Data + Ora

public class Partita implements Serializable {
    private int idPartita;
    private Timestamp dataOra;
    private String luogo;
    private int idTorneo;
    private int idSquadraCasa;
    private int idSquadraOspite;
    private int idArbitro;
    private int golCasa;
    private int golOspite;
    private boolean giocata;

    public Partita() {}


    public int getIdPartita() { return idPartita; }
    public void setIdPartita(int idPartita) { this.idPartita = idPartita; }

    public Timestamp getDataOra() { return dataOra; }
    public void setDataOra(Timestamp dataOra) { this.dataOra = dataOra; }

    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }

    public int getIdTorneo() { return idTorneo; }
    public void setIdTorneo(int idTorneo) { this.idTorneo = idTorneo; }

    public int getIdSquadraCasa() { return idSquadraCasa; }
    public void setIdSquadraCasa(int idSquadraCasa) { this.idSquadraCasa = idSquadraCasa; }

    public int getIdSquadraOspite() { return idSquadraOspite; }
    public void setIdSquadraOspite(int idSquadraOspite) { this.idSquadraOspite = idSquadraOspite; }

    public int getIdArbitro() { return idArbitro; }
    public void setIdArbitro(int idArbitro) { this.idArbitro = idArbitro; }

    public int getGolCasa() { return golCasa; }
    public void setGolCasa(int golCasa) { this.golCasa = golCasa; }

    public int getGolOspite() { return golOspite; }
    public void setGolOspite(int golOspite) { this.golOspite = golOspite; }

    public boolean isGiocata() { return giocata; }
    public void setGiocata(boolean giocata) { this.giocata = giocata; }
}