package it.tuodominio.torneomanager.control;
import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceImpl; // Import corretto del tuo service
import it.tuodominio.torneomanager.model.Utente;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RispostaArbitroServlet")
public class RispostaArbitroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Controllo di Sicurezza nella Servlet
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if(utente == null || !utente.getTipo().equalsIgnoreCase("ARBITRO")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idPartitaStr = request.getParameter("idPartita");
        String azione = request.getParameter("azione"); // "accetta" o "rifiuta"

        if(idPartitaStr != null && azione != null) {
            int idPartita = Integer.parseInt(idPartitaStr);
            boolean accetta = azione.equals("accetta");

            try {
                // 2. Chiamata al Service Stateless
                TorneoService service = new TorneoServiceImpl();
                service.rispondiPropostaArbitro(idPartita, accetta, utente.getIdUtente());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 3. Torna alla dashboard dell'arbitro
        response.sendRedirect("arbitro.jsp");
    }
}