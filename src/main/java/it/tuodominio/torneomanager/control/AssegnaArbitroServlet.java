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

@WebServlet("/AssegnaArbitroServlet")
public class AssegnaArbitroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTorneo = request.getParameter("idTorneo");

        try {

            Utente utente = (Utente) request.getSession().getAttribute("utente");
            int idPartita = Integer.parseInt(request.getParameter("idPartita"));
            int idArbitro = Integer.parseInt(request.getParameter("idArbitro"));


            TorneoService service = new TorneoServiceProxy(utente);


            service.assegnaArbitro(idPartita, idArbitro);


            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&msg=referee_ok");

        } catch (SecurityException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
        }
    }
}