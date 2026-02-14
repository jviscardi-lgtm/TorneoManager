package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy; // Importante

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GeneraCalendarioServlet")
public class GeneraCalendarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idTorneo = -1;
        try {
            // 1. Recupero Dati
            idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            Utente utente = (Utente) request.getSession().getAttribute("utente");

            // 2. Istanzio il Proxy
            TorneoService service = new TorneoServiceProxy(utente);

            // 3. Eseguo l'operazione (protetta)
            service.generaCalendario(idTorneo);

            // 4. Successo
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&msg=calendar_ok");

        } catch (SecurityException e) {
            // Caso: Utente non autorizzato (Proxy ha bloccato)
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            // Caso: Squadre insufficienti o Errore DB
            e.printStackTrace();
            if (e.getMessage().equals("SQUADRE_INSUFFICIENTI")) {
                response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=few_teams");
            } else {
                response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
            }
        }
    }
}