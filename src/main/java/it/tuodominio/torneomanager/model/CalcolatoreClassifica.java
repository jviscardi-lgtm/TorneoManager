package it.tuodominio.torneomanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcolatoreClassifica {

    public static List<RigaClassifica> calcola(int idTorneo) {
        // 1. Mappa per collegare ID_SQUADRA -> RIGA_CLASSIFICA
        Map<Integer, RigaClassifica> mappa = new HashMap<>();

        // 2. Recupero Squadre
        SquadraDAO sDao = new SquadraDAO();
        List<Squadra> iscritte = sDao.doRetrieveByTorneo(idTorneo);

        // --- FIX SICUREZZA 1: Se il DAO torna null, evitiamo il crash ---
        if (iscritte == null) {
            return new ArrayList<>(); // Torniamo subito lista vuota
        }

        for (Squadra s : iscritte) {
            mappa.put(s.getIdSquadra(), new RigaClassifica(s.getIdSquadra(), s.getNomeSquadra()));
        }

        // 3. Recupero Partite
        PartitaDAO pDao = new PartitaDAO();
        List<Partita> partite = pDao.doRetrieveByTorneo(idTorneo);

        // --- FIX SICUREZZA 2: Se il DAO torna null, trattiamolo come lista vuota ---
        if (partite == null) {
            partite = new ArrayList<>();
        }

        for (Partita p : partite) {
            // Contiamo solo le partite FINITE
            if (p.isGiocata()) {
                RigaClassifica casa = mappa.get(p.getIdSquadraCasa());
                RigaClassifica ospite = mappa.get(p.getIdSquadraOspite());

                // Se per qualche motivo una squadra non è nella mappa (bug rari), saltiamo
                if (casa == null || ospite == null) continue;

                if (p.getGolCasa() > p.getGolOspite()) {
                    casa.aggiungiVittoria();
                    ospite.aggiungiSconfitta();
                } else if (p.getGolCasa() < p.getGolOspite()) {
                    ospite.aggiungiVittoria();
                    casa.aggiungiSconfitta();
                } else {
                    casa.aggiungiPareggio();
                    ospite.aggiungiPareggio();
                }
            }
        }

        // 4. Convertiamo la mappa in lista e ordiniamo
        List<RigaClassifica> classifica = new ArrayList<>(mappa.values());
        Collections.sort(classifica);

        return classifica;
    }
}