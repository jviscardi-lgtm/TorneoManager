package it.tuodominio.torneomanager.model; // Cambia col tuo package

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public synchronized Utente doRetrieveByEmailPassword(String email, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        Utente utente = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            // Query SQL diretta sulla tabella
            String sql = "SELECT * FROM utente WHERE email = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                utente = new Utente();
                utente.setIdUtente(rs.getInt("id_utente"));
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setTipo(rs.getString("tipo"));
                utente.setTelefono(rs.getString("telefono"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return utente; // Se ritorna null, il login è fallito
    }
    //registrare un nuovo utente
    public synchronized void doSave(Utente utente) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO utente (email, password, nome, cognome, tipo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getPassword()); // Ricorda: in produzione le password vanno criptate!
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setString(5, utente.getTipo()); // "PRESIDENTE" o "ARBITRO"
            ps.setString(6, utente.getTelefono());

            ps.executeUpdate();
            conn.commit(); // Salviamo le modifiche

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Recupera tutti gli utenti che sono ARBITRI
    public synchronized List<Utente> doRetrieveAllArbitri() {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Utente> arbitri = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM utente WHERE tipo = 'ARBITRO'";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Utente u = new Utente();
                u.setIdUtente(rs.getInt("id_utente"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setEmail(rs.getString("email"));
                u.setTipo(rs.getString("tipo"));
                arbitri.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arbitri;
    }
}