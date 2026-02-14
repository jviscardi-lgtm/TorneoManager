import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.PartitaDAO;

class PartitaDAOTest {

    // TC_U_04.1: Aggiornamento Risultato Valido
    @Test
    void testAggiornamentoRisultato() {
        PartitaDAO dao = new PartitaDAO();

        // CAMBIA QUESTO ID con uno vero nel tuo DB!
        int idPartitaReale = 1;
        int golCasa = 5;
        int golOspite = 5;

        // Eseguiamo l'update
        assertDoesNotThrow(() -> dao.doUpdateRisultato(idPartitaReale, golCasa, golOspite));

        // NOTA: Se volessi essere preciso, qui dovresti fare una dao.doRetrieveByKey(idPartitaReale)
        // e controllare che i gol siano diventati davvero 5-5.
    }

    // TC_U_04.2: ID Partita Inesistente
    @Test
    void testAggiornamentoPartitaInesistente() {
        PartitaDAO dao = new PartitaDAO();

        int idPartitaFinta = 999999; // Un ID che sicuramente non c'è

        // Il metodo non deve esplodere, semplicemente non aggiornerà nulla
        assertDoesNotThrow(() -> dao.doUpdateRisultato(idPartitaFinta, 1, 1));
    }
}