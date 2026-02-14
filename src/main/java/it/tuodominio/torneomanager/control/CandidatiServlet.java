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

@WebServlet("/CandidatiServlet")
public class CandidatiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        String idPartitaStr = request.getParameter("idPartita");

        if(utente != null && idPartitaStr != null) {
            try {
                TorneoService service = new TorneoServiceProxy(utente);
                service.candidatiPerPartita(Integer.parseInt(idPartitaStr), utente.getIdUtente());
            } catch(Exception e) { e.printStackTrace(); }
        }
        response.sendRedirect("arbitro.jsp");
    }
}