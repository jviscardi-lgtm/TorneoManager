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

@WebServlet("/InserisciRisultatoServlet")
public class InserisciRisultatoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Utente utente = (Utente) request.getSession().getAttribute("utente");


            int idPartita = Integer.parseInt(request.getParameter("idPartita"));
            int golCasa = Integer.parseInt(request.getParameter("golCasa"));
            int golOspite = Integer.parseInt(request.getParameter("golOspite"));

            TorneoService service = new TorneoServiceProxy(utente);


            service.inserisciRisultato(idPartita, golCasa, golOspite);


            response.sendRedirect("arbitro.jsp?status=ok");

        } catch (SecurityException e) {
            .printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("arbitro.jsp?status=error");
        }
    }
}