package it.tuodominio.torneomanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartitaDAO {


    public synchronized void doSave(Partita p) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();

            String sql = "INSERT INTO partita (data_ora, luogo, id_torneo, id_squadra_casa, id_squadra_ospite) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, p.getDataOra());
            ps.setString(2, p.getLuogo());
            ps.setInt(3, p.getIdTorneo());
            ps.setInt(4, p.getIdSquadraCasa());
            ps.setInt(5, p.getIdSquadraOspite());

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

    public synchronized List<Partita> doRetrieveByTorneo(int idTorneo) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> calendario = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM partita WHERE id_torneo = ? ORDER BY data_ora ASC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Partita p = new Partita();
                p.setIdPartita(rs.getInt("id_partita"));
                p.setDataOra(rs.getTimestamp("data_ora"));
                p.setLuogo(rs.getString("luogo"));
                p.setIdTorneo(rs.getInt("id_torneo"));
                p.setIdSquadraCasa(rs.getInt("id_squadra_casa"));
                p.setIdSquadraOspite(rs.getInt("id_squadra_ospite"));
                p.setIdArbitro(rs.getInt("id_arbitro"));
                p.setGolCasa(rs.getInt("gol_casa"));
                p.setGolOspite(rs.getInt("gol_ospite"));
                p.setGiocata(rs.getBoolean("giocata"));
                calendario.add(p);
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
        return calendario;
    }

    public synchronized void doUpdateRisultato(int idPartita, int golCasa, int golOspite) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE partita SET gol_casa = ?, gol_ospite = ?, giocata = 1 WHERE id_partita = ?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, golCasa);
            ps.setInt(2, golOspite);
            ps.setInt(3, idPartita);

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
    public synchronized void doAssegnaArbitro(int idPartita, int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE partita SET id_arbitro = ? WHERE id_partita = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
            ps.setInt(2, idPartita);
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
}