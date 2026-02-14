package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.TorneoDAO;
import it.tuodominio.torneomanager.model.Utente;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EliminaTorneoServlet")
public class EliminaTorneoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Controllo Sicurezza: Solo Organizzatore
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if(utente == null || !utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Recupero ID Torneo
        String idParam = request.getParameter("id");
        if(idParam != null && !idParam.isEmpty()) {
            int idTorneo = Integer.parseInt(idParam);

            // 3. Cancellazione dal DB
            TorneoDAO dao = new TorneoDAO();
            dao.doDelete(idTorneo);
        }

        // 4. Torna alla home
        response.sendRedirect("home.jsp");
    }
}