package it.tuodominio.torneomanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SquadraDAO {


    public synchronized void doSave(Squadra s) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO squadra (nome_squadra, logo_path, id_presidente) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, s.getNomeSquadra());
            ps.setString(2, s.getLogoPath());
            ps.setInt(3, s.getIdPresidente());

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


    public synchronized List<Squadra> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Squadra> lista = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM squadra";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Squadra s = new Squadra();
                s.setIdSquadra(rs.getInt("id_squadra"));
                s.setNomeSquadra(rs.getString("nome_squadra"));
                s.setLogoPath(rs.getString("logo_path"));
                s.setIdPresidente(rs.getInt("id_presidente"));
                lista.add(s);
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


    public synchronized Squadra doRetrieveByPresidente(int idPresidente) {
        Connection conn = null;
        PreparedStatement ps = null;
        Squadra s = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM squadra WHERE id_presidente = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idPresidente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Squadra();
                s.setIdSquadra(rs.getInt("id_squadra"));
                s.setNomeSquadra(rs.getString("nome_squadra"));
                s.setLogoPath(rs.getString("logo_path"));
                s.setIdPresidente(rs.getInt("id_presidente"));
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
        return s;
    }

    public synchronized List<Squadra> doRetrieveByTorneo(int idTorneo) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Squadra> iscritte = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();

            String sql = "SELECT s.* FROM squadra s " +
                    "JOIN iscrizione i ON s.id_squadra = i.id_squadra " +
                    "WHERE i.id_torneo = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idTorneo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Squadra s = new Squadra();
                s.setIdSquadra(rs.getInt("id_squadra"));
                s.setNomeSquadra(rs.getString("nome_squadra"));
                s.setLogoPath(rs.getString("logo_path"));
                s.setIdPresidente(rs.getInt("id_presidente"));
                iscritte.add(s);
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
        return iscritte;
    }
}