import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.PartitaDAO;

class PartitaDAOTest {

    // TC_U_04.1: Aggiornamento Risultato Valido
    @Test
    void testAggiornamentoRisultato() {
        PartitaDAO dao = new PartitaDAO();

      
        int idPartitaReale = 5;
        int golCasa = 2;
        int golOspite = 1;

        // Eseguiamo l'update
        assertDoesNotThrow(() -> dao.doUpdateRisultato(idPartitaReale, golCasa, golOspite));
        Partita pAggiornata = dao.doRetrieveById(idPartitaReale);
        assertNotNull(pAggiornata, "La partita deve esistere nel DB");
        assertEquals(2, pAggiornata.getGolCasa(), "I gol in casa devono essere aggiornati a 2");
        assertEquals(1, pAggiornata.getGolOspite(), "I gol in trasferta devono essere aggiornati a 1");
        assertTrue(pAggiornata.isGiocata(), "Lo stato della partita deve essere 'giocata=true'");
    
    }

    // TC_U_04.2: ID Partita Inesistente
    @Test
    void testAggiornamentoPartitaInesistente() {
        PartitaDAO dao = new PartitaDAO();

        int idPartitaFinta = 999; // Un ID che sicuramente non c'è

        // Il metodo non deve esplodere, semplicemente non aggiornerà nulla
        assertDoesNotThrow(() -> dao.doUpdateRisultato(idPartitaFinta, 1, 1));
    }
}
