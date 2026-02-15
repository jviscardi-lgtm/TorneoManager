import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.model.UtenteDAO;
class UtenteDAOTest {
    @Test
    void testLoginCorretto() {
        UtenteDAO dao = new UtenteDAO();

        String emailEsistente = "arbitro@arbitro.it";
        String passwordCorretta = "arbitro";

        Utente u = dao.doRetrieveByEmailPassword(emailEsistente, passwordCorretta);


        assertNotNull(u, "Errore: L'utente " + emailEsistente + " non è stato trovato nel DB!");
        assertEquals(emailEsistente, u.getEmail(), "L'email restituita dovrebbe corrispondere");
    }

    @Test
    void testLoginPasswordErrata() {
        UtenteDAO dao = new UtenteDAO();


        String emailEsistente = "arbitro@fischietto.it";
        String passwordErrata = "passwordSbagliata123";

        Utente u = dao.doRetrieveByEmailPassword(emailEsistente, passwordErrata);

        assertNull(u, "Il login dovrebbe fallire (return null) con password errata");
    }

    @Test
    void testLoginEmailInesistente() {
        UtenteDAO dao = new UtenteDAO();

        Utente u = dao.doRetrieveByEmailPassword("email@inesistente.com", "admin");

        assertNull(u, "Il login dovrebbe fallire con email inesistente");
    }
}
