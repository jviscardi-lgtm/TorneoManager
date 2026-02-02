package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GiocatoreServlet")
public class GiocatoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String ruolo = request.getParameter("ruolo");
        int numero = Integer.parseInt(request.getParameter("numero"));
        int idSquadra = Integer.parseInt(request.getParameter("idSquadra"));


        Giocatore g = new Giocatore();
        g.setNome(nome);
        g.setCognome(cognome);
        g.setRuolo(ruolo);
        g.setNumeroMaglia(numero);
        g.setIdSquadra(idSquadra);


        GiocatoreDAO dao = new GiocatoreDAO();
        dao.doSave(g);


        response.sendRedirect("squadra.jsp");
    }
}