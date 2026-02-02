package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Torneo;
import it.tuodominio.torneomanager.model.TorneoDAO;
import it.tuodominio.torneomanager.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/CreaTorneoServlet")
public class CreaTorneoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Utente utente = (Utente) request.getSession().getAttribute("utente");


            String nome = request.getParameter("nome");
            String luogo = request.getParameter("luogo");
            String descrizione = request.getParameter("descrizione");

            Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
            Date dataFine = Date.valueOf(request.getParameter("dataFine"));


            Torneo t = new Torneo();
            t.setNome(nome);
            t.setLuogo(luogo);
            t.setDescrizione(descrizione);
            t.setDataInizio(dataInizio);
            t.setDataFine(dataFine);
            t.setChiuso(false);


            it.tuodominio.torneomanager.service.TorneoService service =
                    new it.tuodominio.torneomanager.service.TorneoServiceProxy(utente);


            service.creaTorneo(t);


            response.sendRedirect("home.jsp");

        } catch (SecurityException e) {

            e.printStackTrace();

            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("creaTorneo.jsp?error=generic");
        }
    }
}