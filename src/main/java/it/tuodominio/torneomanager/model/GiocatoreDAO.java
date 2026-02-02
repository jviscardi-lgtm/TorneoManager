package it.tuodominio.torneomanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiocatoreDAO {


    public synchronized void doSave(Giocatore g) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO giocatore (nome, cognome, ruolo, numero_maglia, id_squadra) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, g.getNome());
            ps.setString(2, g.getCognome());
            ps.setString(3, g.getRuolo());
            ps.setInt(4, g.getNumeroMaglia());
            ps.setInt(5, g.getIdSquadra());

            ps.executeUpdate();
            conn.commit();

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


    public synchronized void doDelete(int idGiocatore) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "DELETE FROM giocatore WHERE id_giocatore = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idGiocatore);

            ps.executeUpdate();
            conn.commit();

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


    public synchronized List<Giocatore> doRetrieveBySquadra(int idSquadra) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Giocatore> rosa = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM giocatore WHERE id_squadra = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSquadra);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Giocatore g = new Giocatore();
                g.setIdGiocatore(rs.getInt("id_giocatore"));
                g.setNome(rs.getString("nome"));
                g.setCognome(rs.getString("cognome"));
                g.setRuolo(rs.getString("ruolo"));
                g.setNumeroMaglia(rs.getInt("numero_maglia"));
                g.setIdSquadra(rs.getInt("id_squadra"));
                rosa.add(g);
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
        return rosa;
    }
}