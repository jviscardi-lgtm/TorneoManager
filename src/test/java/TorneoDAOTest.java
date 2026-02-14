package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.Torneo;
import it.tuodominio.torneomanager.model.TorneoDAO;
import it.tuodominio.torneomanager.model.Utente;     // <--- Aggiunto
import it.tuodominio.torneomanager.model.UtenteDAO;  // <--- Aggiunto
import java.sql.Date;

class TorneoDAOTest {

    // TC_U_03.1: Salvataggio corretto
    @Test
    void testSalvataggioTorneo() {
        // 1. Recuperiamo l'ID dell'organizzatore ESISTENTE
        UtenteDAO utenteDao = new UtenteDAO();
        Utente admin = utenteDao.doRetrieveByEmailPassword("admin@torneomanager.it", "admin");

        // Verifica di sicurezza: se admin è null, il test fallisce subito con un messaggio chiaro
        assertNotNull(admin, "Attenzione: L'utente admin@torneomanager.it non è stato trovato nel DB!");

        TorneoDAO dao = new TorneoDAO();
        Torneo t = new Torneo();

        t.setNome("JUNIT_TEST_TORNEO");
        t.setLuogo("Stadio Virtuale");
        t.setDescrizione("Torneo di prova automatico");
        t.setDataInizio(Date.valueOf("2025-01-01"));
        t.setDataFine(Date.valueOf("2025-02-01"));
        t.setChiuso(false);

        // 2. Impostiamo l'ID vero recuperato sopra
        t.setIdOrganizzatore(admin.getIdUtente());

        // Proviamo a salvare
        assertDoesNotThrow(() -> dao.doSave(t), "Il salvataggio non dovrebbe lanciare eccezioni");
    }

    // TC_U_03.2: Gestione oggetto nullo
    @Test
    void testSalvataggioNull() {
        TorneoDAO dao = new TorneoDAO();

        assertThrows(Exception.class, () -> {
            dao.doSave(null);
        });
    }
}