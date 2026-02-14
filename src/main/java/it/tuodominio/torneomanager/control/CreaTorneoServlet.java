package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Torneo;
import it.tuodominio.torneomanager.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/CreaTorneoServlet")
public class CreaTorneoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Recupero Utente dalla sessione (può essere null)
            Utente utente = (Utente) request.getSession().getAttribute("utente");

            // 2. Recupero Parametri dal form
            String nome = request.getParameter("nome");
            String luogo = request.getParameter("luogo");
            String descrizione = request.getParameter("descrizione");
            Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
            Date dataFine = Date.valueOf(request.getParameter("dataFine"));

            // 3. Creazione del Bean
            Torneo t = new Torneo();
            t.setNome(nome);
            t.setLuogo(luogo);
            t.setDescrizione(descrizione);
            t.setDataInizio(dataInizio);
            t.setDataFine(dataFine);
            t.setChiuso(false);

            // ---> ECCO LA RIGA CHE MANCAVA! <---
            // Dobbiamo dire al database a quale utente (Organizzatore) appartiene questo torneo!
            if (utente != null) {
                t.setIdOrganizzatore(utente.getIdUtente());
            }

            // 4. Implementazione Proxy
            it.tuodominio.torneomanager.service.TorneoService service =
                    new it.tuodominio.torneomanager.service.TorneoServiceProxy(utente);

            // Eseguiamo l'azione protetta
            service.creaTorneo(t);

            // 5. Successo
            response.sendRedirect("home.jsp");

        } catch (SecurityException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            e.printStackTrace();
            // Se fallisce, rimandiamo alla pagina di creazione
            response.sendRedirect("creaTorneo.jsp?error=generic");
        }
    }
}