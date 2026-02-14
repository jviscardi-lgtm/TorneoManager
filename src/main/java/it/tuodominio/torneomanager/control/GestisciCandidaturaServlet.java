package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy;
import it.tuodominio.torneomanager.model.Utente;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GestisciCandidaturaServlet")
public class GestisciCandidaturaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        String idPartitaStr = request.getParameter("idPartita");
        String idArbitroStr = request.getParameter("idArbitro");
        String azione = request.getParameter("azione"); // "accetta" o "rifiuta"
        String idTorneoStr = request.getParameter("idTorneo");

        if(utente != null && idPartitaStr != null && idArbitroStr != null && azione != null) {
            try {
                TorneoService service = new TorneoServiceProxy(utente);
                boolean accetta = azione.equals("accetta");
                service.gestisciCandidatura(Integer.parseInt(idPartitaStr), accetta, Integer.parseInt(idArbitroStr));
            } catch(Exception e) { e.printStackTrace(); }
        }
        response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneoStr);
    }
}