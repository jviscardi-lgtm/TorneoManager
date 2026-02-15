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
