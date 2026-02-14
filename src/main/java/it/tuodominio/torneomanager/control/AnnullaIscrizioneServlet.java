package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Squadra;
import it.tuodominio.torneomanager.model.SquadraDAO;
import it.tuodominio.torneomanager.model.TorneoDAO;
import it.tuodominio.torneomanager.model.Utente;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AnnullaIscrizioneServlet")
public class AnnullaIscrizioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Controllo Sicurezza: Solo Presidente
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if(utente == null || !utente.getTipo().equalsIgnoreCase("PRESIDENTE")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idTorneoParam = request.getParameter("idTorneo");
        if(idTorneoParam != null && !idTorneoParam.isEmpty()) {
            int idTorneo = Integer.parseInt(idTorneoParam);

            // 2. Recupero la squadra del presidente loggato
            SquadraDAO sDao = new SquadraDAO();
            Squadra miaSquadra = sDao.doRetrieveByPresidente(utente.getIdUtente());

            if(miaSquadra != null) {
                // 3. Cancello l'iscrizione
                TorneoDAO tDao = new TorneoDAO();
                tDao.doAnnullaIscrizione(idTorneo, miaSquadra.getIdSquadra());
            }
        }

        response.sendRedirect("home.jsp");
    }
}