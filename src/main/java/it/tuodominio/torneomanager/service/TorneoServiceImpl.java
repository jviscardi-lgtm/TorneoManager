package it.tuodominio.torneomanager.service;

import it.tuodominio.torneomanager.model.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TorneoServiceImpl implements TorneoService {

    @Override
    public void creaTorneo(Torneo t) throws Exception {
        TorneoDAO dao = new TorneoDAO();
        dao.doSave(t);
    }

    @Override
    public void generaCalendario(int idTorneo) throws Exception {

        SquadraDAO sDao = new SquadraDAO();
        List<Squadra> squadre = sDao.doRetrieveByTorneo(idTorneo);


        if (squadre.size() < 2) {
            // Nel Service lanciamo un'eccezione, non facciamo redirect!
            throw new Exception("SQUADRE_INSUFFICIENTI");
        }


        if (squadre.size() % 2 != 0) {
            squadre.add(null); // null rappresenta il "Riposo"
        }

        int numSquadre = squadre.size();
        int giornate = numSquadre - 1;
        int partitePerGiornata = numSquadre / 2;

        Calendar calendarioData = Calendar.getInstance();
        calendarioData.add(Calendar.DAY_OF_MONTH, 1); // Inizia domani

        PartitaDAO pDao = new PartitaDAO();
        List<Squadra> girone = new ArrayList<>(squadre);


        for (int i = 0; i < giornate; i++) {
            if(i > 0) calendarioData.add(Calendar.DAY_OF_MONTH, 7);

            for (int j = 0; j < partitePerGiornata; j++) {
                int squadraCasaIdx = j;
                int squadraTrasfertaIdx = numSquadre - 1 - j;

                Squadra casa = girone.get(squadraCasaIdx);
                Squadra trasferta = girone.get(squadraTrasfertaIdx);

                if (casa != null && trasferta != null) {
                    Partita p = new Partita();
                    p.setIdTorneo(idTorneo);
                    p.setIdSquadraCasa(casa.getIdSquadra());
                    p.setIdSquadraOspite(trasferta.getIdSquadra());
                    p.setLuogo("Campo Centrale");
                    p.setDataOra(new Timestamp(calendarioData.getTimeInMillis()));
                    p.setGiocata(false);

                    pDao.doSave(p);
                }
            }
            Squadra ultima = girone.remove(girone.size() - 1);
            girone.add(1, ultima);
        }

        System.out.println("LOG: Calendario generato con successo per il torneo " + idTorneo);
    }
    public void cambiaStato(int idTorneo, boolean nuovoStato) throws Exception {
        TorneoDAO dao = new TorneoDAO();

        dao.doUpdateStato(idTorneo, nuovoStato);

        System.out.println("LOG: Stato torneo " + idTorneo + " aggiornato a: " + (nuovoStato ? "CHIUSO" : "APERTO"));
    }
    @Override
    public void modificaTorneo(Torneo t) throws Exception {
        TorneoDAO dao = new TorneoDAO();
        dao.doUpdate(t);
        System.out.println("LOG: Torneo " + t.getIdTorneo() + " modificato con successo.");
    }
    @Override
    public void assegnaArbitro(int idPartita, int idArbitro) throws Exception {
        PartitaDAO pDao = new PartitaDAO();
        pDao.doAssegnaArbitro(idPartita, idArbitro);
        System.out.println("LOG: Arbitro " + idArbitro + " assegnato alla partita " + idPartita);
    }

    @Override
    public void inserisciRisultato(int idPartita, int golCasa, int golOspite) throws Exception {
        PartitaDAO pDao = new PartitaDAO();
        pDao.doUpdateRisultato(idPartita, golCasa, golOspite);
        System.out.println("LOG: Risultato aggiornato per partita " + idPartita);
    }

    @Override
    public void iscriviSquadra(int idTorneo, int idSquadra) throws Exception {
        TorneoDAO tDao = new TorneoDAO();
        if(tDao.isIscritto(idTorneo, idSquadra)) {
            throw new Exception("SQUADRA_GIA_ISCRITTA");
        }
        tDao.doIscriviSquadra(idTorneo, idSquadra);
        System.out.println("LOG: Squadra " + idSquadra + " iscritta al torneo " + idTorneo);
    }
}