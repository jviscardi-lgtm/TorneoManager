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

            Utente utente = (Utente) request.getSession().getAttribute("utente");
            idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            String azione = request.getParameter("azione"); // "chiudi" o "apri"


            boolean nuovoStato = (azione != null && azione.equals("chiudi"));


            TorneoService service = new TorneoServiceProxy(utente);


            service.cambiaStato(idTorneo, nuovoStato);


            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo);

        } catch (SecurityException e) {

            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
        }
    }
}