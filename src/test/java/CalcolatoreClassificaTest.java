import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.CalcolatoreClassifica;

class CalcolatoreClassificaTest {

    // Qui traduciamo la riga della tabella TCS: TC_U_01.1
    @Test
    void testCalcoloTorneoInesistente() {
        // 1. Input (Dalla tabella TCS)
        int idTorneo = -1;

        // 2. Esecuzione
      
        var risultato = CalcolatoreClassifica.calcola(idTorneo);

        // 3. Oracolo (Dalla tabella TCS)
        assertNotNull(risultato, "La lista non dovrebbe essere null");
        assertTrue(risultato.isEmpty(), "La lista dovrebbe essere vuota per ID negativo");

    }
}
