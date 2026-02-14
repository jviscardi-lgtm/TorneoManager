package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Squadra;
import it.tuodominio.torneomanager.model.SquadraDAO;
import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/IscrizioneServlet")
public class IscrizioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Controllo Login (recupero utente per il Proxy)
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Nota: Il controllo approfondito sul ruolo lo farà il Proxy,
        // qui controlliamo solo se è loggato per evitare NullPointer.
        if(utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Recupero Parametri
            String idTorneoStr = request.getParameter("idTorneo");

            if(idTorneoStr != null) {
                int idTorneo = Integer.parseInt(idTorneoStr);

                // 3. Recupero la Squadra (Prerequisito per chiamare il Service)
                // Questo passaggio rimane qui perché serve a tradurre "Utente" in "Squadra"
                SquadraDAO squadraDAO = new SquadraDAO();
                Squadra miaSquadra = squadraDAO.doRetrieveByPresidente(utente.getIdUtente());

                if(miaSquadra != null) {

                    // --- PATTERN PROXY ---
                    TorneoService service = new TorneoServiceProxy(utente);

                    // Proviamo a iscrivere la squadra.
                    // Il Proxy controllerà se siamo PRESIDENTE.
                    // Il ServiceImpl controllerà se siamo già iscritti (e lancerà eccezione se sì).
                    service.iscriviSquadra(idTorneo, miaSquadra.getIdSquadra());

                    request.setAttribute("messaggio", "Iscrizione avvenuta con successo!");

                } else {
                    request.setAttribute("errore", "Devi prima creare una squadra per iscriverti!");
                }
            }

        } catch (SecurityException e) {
            // Caso: Non è un Presidente
            e.printStackTrace();
            request.setAttribute("errore", "Accesso Negato: Non hai i permessi per iscrivere squadre.");

        } catch (Exception e) {
            // Caso: Già iscritto o Errore DB
            e.printStackTrace();
            // Se l'eccezione arriva dal ServiceImpl perché è già iscritta:
            if(e.getMessage().equals("SQUADRA_GIA_ISCRITTA")) {
                request.setAttribute("errore", "La tua squadra è già iscritta a questo torneo.");
            } else {
                request.setAttribute("errore", "Errore durante l'iscrizione.");
            }
        }

        // 5. Torno alla Home mantenendo i messaggi
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}