import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.CalcolatoreClassifica;

class CalcolatoreClassificaTest {

    
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
        @Test
    void testCalcoloTorneoSenzaPartite() {
        // Copre la combinazione: A1 (ID Valido) + B1 (Nessuna partita giocata)

        // Sostituisci '99' con l'ID di un torneo reale nel tuo DB che non ha partite (o creane uno al volo dal sito)
        int idTorneo = 99; 

        var risultato = CalcolatoreClassifica.calcola(idTorneo);

        assertNotNull(risultato, "La lista non dovrebbe essere null");
        // Sappiamo che se non ci sono partite, il calcolatore restituisce una lista vuota
        assertTrue(risultato.isEmpty(), "La classifica dovrebbe essere vuota se non ci sono partite giocate");
    }

    @Test
    void testCalcoloTorneoConPartite() {
        int idTorneo = 1; 

        var risultato = CalcolatoreClassifica.calcola(idTorneo);

        assertNotNull(risultato, "La lista non dovrebbe essere null");
        // Se ci sono partite, la classifica DEVE avere degli elementi
        assertFalse(risultato.isEmpty(), "La classifica NON dovrebbe essere vuota se ci sono partite giocate");

        // Verifica extra: la prima squadra in classifica non può avere punti negativi
        assertTrue(risultato.get(0).getPunti() >= 0, "I punti della prima squadra dovrebbero essere >= 0");
    }
}
