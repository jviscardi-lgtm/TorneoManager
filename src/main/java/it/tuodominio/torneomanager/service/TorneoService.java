package it.tuodominio.torneomanager.service;

import it.tuodominio.torneomanager.model.Torneo;

public interface TorneoService {

    void creaTorneo(Torneo t) throws Exception;
    void generaCalendario(int idTorneo) throws Exception;
    void cambiaStato(int idTorneo, boolean nuovoStato) throws Exception;
    void modificaTorneo(Torneo t) throws Exception;

    void assegnaArbitro(int idPartita, int idArbitro) throws Exception;


    void inserisciRisultato(int idPartita, int golCasa, int golOspite) throws Exception;


    void iscriviSquadra(int idTorneo, int idSquadra) throws Exception;
}