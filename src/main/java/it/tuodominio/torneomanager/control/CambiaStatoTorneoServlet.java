package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CambiaStatoTorneoServlet")
public class CambiaStatoTorneoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idTorneo = -1;
        try {
            // 1. Recupero Dati
            Utente utente = (Utente) request.getSession().getAttribute("utente");
            idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            String azione = request.getParameter("azione"); // "chiudi" o "apri"

            // Convertiamo l'azione in boolean (Logica di presentazione)
            boolean nuovoStato = (azione != null && azione.equals("chiudi"));

            // 2. Istanzio il Proxy
            TorneoService service = new TorneoServiceProxy(utente);

            // 3. Eseguo l'operazione (protetta)
            service.cambiaStato(idTorneo, nuovoStato);

            // 4. Successo
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo);

        } catch (SecurityException e) {
            // Accesso Negato
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            // Errore generico
            e.printStackTrace();
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
        }
    }
}