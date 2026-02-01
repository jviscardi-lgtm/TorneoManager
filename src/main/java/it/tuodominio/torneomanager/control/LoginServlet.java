package it.tuodominio.torneomanager.control; // Cambia col tuo package

import it.tuodominio.torneomanager.model.Utente;
import it.tuodominio.torneomanager.model.UtenteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO dao = new UtenteDAO();
        Utente utente = dao.doRetrieveByEmailPassword(email, password);

        if (utente != null) {
            // LOGIN SUCCESSO
            HttpSession session = request.getSession();
            session.setAttribute("utente", utente); // Salviamo l'utente in sessione

            // Reindirizziamo alla Home (che creeremo dopo)
            response.sendRedirect("home.jsp");
        } else {
            // LOGIN FALLITO
            request.setAttribute("errore", "Email o Password errati!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}