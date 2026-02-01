package it.tuodominio.torneomanager.control;

import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.model.UtenteDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recuperiamo i parametri dal form (anche se il form non esiste ancora, sappiamo i nomi)
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telefono = request.getParameter("telefono");
        String tipo = request.getParameter("tipo"); // Sarà un menu a tendina nel mockup

        // 2. Creiamo il Bean
        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setTelefono(telefono);
        nuovoUtente.setTipo(tipo); // Deve essere "PRESIDENTE" o "ARBITRO"

        // 3. Salviamo nel DB
        UtenteDAO dao = new UtenteDAO();
        // Qui potresti aggiungere un controllo: check se l'email esiste già
        dao.doSave(nuovoUtente);

        // 4. Reindirizzamento
        // Una volta registrato, lo mandiamo al login con un messaggio di successo
        request.setAttribute("messaggio", "Registrazione completata! Ora puoi accedere.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp"); // La registrazione si fa solo via POST
    }
}