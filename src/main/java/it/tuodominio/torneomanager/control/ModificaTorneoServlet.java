package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Torneo;
import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.service.TorneoService;
import it.tuodominio.torneomanager.service.TorneoServiceProxy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/ModificaTorneoServlet")
public class ModificaTorneoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idTorneo = -1;

        try {
            // 1. Recupero Utente
            Utente utente = (Utente) request.getSession().getAttribute("utente");

            // 2. Recupero Parametri
            idTorneo = Integer.parseInt(request.getParameter("idTorneo"));
            String nome = request.getParameter("nome");
            String luogo = request.getParameter("luogo");
            String descrizione = request.getParameter("descrizione");
            Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
            Date dataFine = Date.valueOf(request.getParameter("dataFine"));

            // 3. Creazione Bean
            Torneo t = new Torneo();
            t.setIdTorneo(idTorneo); // Importante: settare l'ID per sapere quale modificare
            t.setNome(nome);
            t.setLuogo(luogo);
            t.setDescrizione(descrizione);
            t.setDataInizio(dataInizio);
            t.setDataFine(dataFine);

            // Nota: Manteniamo lo stato "chiuso" o "aperto" invariato, o lo recuperiamo se servisse.
            // Il DAO doUpdate solitamente aggiorna solo i campi passati.

            // 4. Esecuzione tramite PROXY
            TorneoService service = new TorneoServiceProxy(utente);
            service.modificaTorneo(t);

            // 5. Successo
            response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo);

        } catch (SecurityException e) {
            // Accesso Negato
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=unauthorized");

        } catch (Exception e) {
            // Errore generico (es. Date sbagliate)
            e.printStackTrace();
            if(idTorneo != -1) {
                response.sendRedirect("dettaglioTorneo.jsp?id=" + idTorneo + "&error=generic");
            } else {
                response.sendRedirect("home.jsp?error=generic");
            }
        }
    }
}