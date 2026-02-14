package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy; // <-- Assicurati che il package sia giusto!
import it.tuodominio.torneomanager.model.Utente;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AssegnaArbitroServlet")
public class AssegnaArbitroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Attenzione: usiamo doPost perché il tuo form HTML usa method="post"
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Recupero Utente e controllo sicurezza base
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        String idTorneoStr = request.getParameter("idTorneo");

        if (utente == null || !utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Recupero parametri dal form della tendina
            String idPartitaStr = request.getParameter("idPartita");
            String idArbitroStr = request.getParameter("idArbitro");

            if (idPartitaStr != null && idArbitroStr != null) {
                int idPartita = Integer.parseInt(idPartitaStr);
                int idArbitro = Integer.parseInt(idArbitroStr);

                // 3. Chiamata tramite PROXY (Il proxy farà il controllo di sicurezza definitivo)
                TorneoService service = new TorneoServiceProxy(utente);

                // Questo chiamerà realService.assegnaArbitro, che a sua volta farà pDao.doProponiArbitro (ID negativo)
                service.assegnaArbitro(idPartita, idArbitro);

                System.out.println("DEBUG SERVLET: Assegnazione/Proposta inviata con successo per partita " + idPartita);
            }

        } catch (Exception e) {
            // Se c'è un errore, LO STAMPIAMO IN CONSOLE, così sappiamo cosa è andato storto!
            System.out.println("ERRORE IN AssegnaArbitroServlet:");
            e.printStackTrace();
        }

        // 4. Torna alla pagina dei dettagli del torneo (ricaricando la pagina)
        if (idTorneoStr != null) {
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneoStr);
        } else {
            response.sendRedirect("home.jsp");
        }
    }
}