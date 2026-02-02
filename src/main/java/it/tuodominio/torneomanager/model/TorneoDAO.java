package it.tuodominio.torneomanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAO {


    public synchronized List<Torneo> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Torneo> lista = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM torneo";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Torneo t = new Torneo();
                t.setIdTorneo(rs.getInt("id_torneo"));
                t.setNome(rs.getString("nome"));
                t.setDataInizio(rs.getDate("data_inizio"));
                t.setDataFine(rs.getDate("data_fine"));
                t.setDescrizione(rs.getString("descrizione"));
                t.setLuogo(rs.getString("luogo"));
                t.setIdOrganizzatore(rs.getInt("id_organizzatore"));
                lista.add(t);
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
        return lista;
    }


    public synchronized void doSave(Torneo t) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO torneo (nome, data_inizio, data_fine, descrizione, luogo, id_organizzatore) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, t.getNome());
            ps.setDate(2, t.getDataInizio());
            ps.setDate(3, t.getDataFine());
            ps.setString(4, t.getDescrizione());
            ps.setString(5, t.getLuogo());
            ps.setInt(6, t.getIdOrganizzatore());

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
    public synchronized void doIscriviSquadra(int idTorneo, int idSquadra) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO iscrizione (id_torneo, id_squadra) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);
            ps.setInt(2, idSquadra);
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

    public synchronized boolean isIscritto(int idTorneo, int idSquadra) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean iscritto = false;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM iscrizione WHERE id_torneo = ? AND id_squadra = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);
            ps.setInt(2, idSquadra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                iscritto = true;
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
        return iscritto;
    }
    public synchronized void doUpdateStato(int idTorneo, boolean chiuso) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE torneo SET chiuso = ? WHERE id_torneo = ?";
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, chiuso);
            ps.setInt(2, idTorneo);
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

    public synchronized void doUpdate(Torneo t) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE torneo SET nome = ?, luogo = ?, descrizione = ?, data_inizio = ?, data_fine = ? WHERE id_torneo = ?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, t.getNome());
            ps.setString(2, t.getLuogo());
            ps.setString(3, t.getDescrizione());
            ps.setDate(4, t.getDataInizio());
            ps.setDate(5, t.getDataFine());
            ps.setInt(6, t.getIdTorneo());

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

    public synchronized Torneo doRetrieveByKey(int idTorneo) {
        Connection conn = null;
        PreparedStatement ps = null;
        Torneo t = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM torneo WHERE id_torneo = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                t = new Torneo();
                t.setIdTorneo(rs.getInt("id_torneo"));
                t.setNome(rs.getString("nome"));
                t.setLuogo(rs.getString("luogo"));
                t.setDescrizione(rs.getString("descrizione"));
                t.setDataInizio(rs.getDate("data_inizio"));
                t.setDataFine(rs.getDate("data_fine"));
                t.setChiuso(rs.getBoolean("chiuso"));
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
        return t;
    }
}