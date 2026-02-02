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

            idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            Utente utente = (Utente) request.getSession().getAttribute("utente");


            TorneoService service = new TorneoServiceProxy(utente);


            service.generaCalendario(idTorneo);


            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&msg=calendar_ok");

        } catch (SecurityException e) {

            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {

            e.printStackTrace();
            if (e.getMessage().equals("SQUADRE_INSUFFICIENTI")) {
                response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=few_teams");
            } else {
                response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
            }
        }
    }
}