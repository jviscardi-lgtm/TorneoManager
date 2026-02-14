package it.tuodominio.torneomanager.model;

// Implementiamo Comparable per poter ordinare la lista automaticamente
public class RigaClassifica implements Comparable<RigaClassifica> {
    private String nomeSquadra;
    private int idSquadra;
    private int punti;
    private int giocate;
    private int vinte;
    private int pareggiate;
    private int perse;

    public RigaClassifica(int idSquadra, String nomeSquadra) {
        this.idSquadra = idSquadra;
        this.nomeSquadra = nomeSquadra;
        this.punti = 0;
        this.giocate = 0;
        this.vinte = 0;
        this.pareggiate = 0;
        this.perse = 0;
    }

    // Metodi per aggiornare i punteggi
    public void aggiungiVittoria() {
        this.punti += 3;
        this.giocate++;
        this.vinte++;
    }

    public void aggiungiPareggio() {
        this.punti += 1;
        this.giocate++;
        this.pareggiate++;
    }

    public void aggiungiSconfitta() {
        this.giocate++;
        this.perse++;
    }

    // Logica di ordinamento: chi ha più punti sta sopra
    @Override
    public int compareTo(RigaClassifica o) {
        return o.punti - this.punti; // Ordine decrescente
    }

    // Getters
    public String getNomeSquadra() { return nomeSquadra; }
    public int getPunti() { return punti; }
    public int getGiocate() { return giocate; }
    public int getVinte() { return vinte; }
    public int getPareggiate() { return pareggiate; }
    public int getPerse() { return perse; }
}