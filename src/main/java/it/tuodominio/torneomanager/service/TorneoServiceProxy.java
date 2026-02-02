package it.tuodominio.torneomanager.service;

import it.tuodominio.torneomanager.model.Torneo;
import it.tuodominio.torneomanager.model.Utente;

public class TorneoServiceProxy implements TorneoService {

    private TorneoServiceImpl realService;
    private Utente utente;

    public TorneoServiceProxy(Utente utente) {
        this.utente = utente;
        this.realService = new TorneoServiceImpl();
    }

    @Override
    public void creaTorneo(Torneo t) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            realService.creaTorneo(t);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Non sei organizzatore.");
        }
    }

    @Override
    public void generaCalendario(int idTorneo) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            realService.generaCalendario(idTorneo);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo l'organizzatore può generare il calendario.");
        }
    }
    @Override
    public void cambiaStato(int idTorneo, boolean nuovoStato) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            realService.cambiaStato(idTorneo, nuovoStato);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo l'organizzatore può chiudere o aprire i tornei.");
        }
    }
    @Override
    public void modificaTorneo(Torneo t) throws Exception {

        if (utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            realService.modificaTorneo(t);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo l'organizzatore può modificare i dettagli del torneo.");
        }
    }
    @Override
    public void assegnaArbitro(int idPartita, int idArbitro) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            realService.assegnaArbitro(idPartita, idArbitro);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo l'organizzatore può assegnare arbitri.");
        }
    }


    @Override
    public void inserisciRisultato(int idPartita, int golCasa, int golOspite) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("ARBITRO")) {

            realService.inserisciRisultato(idPartita, golCasa, golOspite);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo gli arbitri possono inserire i risultati.");
        }
    }



    @Override
    public void iscriviSquadra(int idTorneo, int idSquadra) throws Exception {
        if (utente != null && utente.getTipo().equalsIgnoreCase("PRESIDENTE")) {
            realService.iscriviSquadra(idTorneo, idSquadra);
        } else {
            throw new SecurityException("ACCESSO NEGATO: Solo i presidenti possono iscrivere squadre.");
        }
    }
}