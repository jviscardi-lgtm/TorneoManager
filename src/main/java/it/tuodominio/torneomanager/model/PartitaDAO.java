package it.tuodominio.torneomanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartitaDAO {

    // 1. CREAZIONE PARTITA (Salva esplicitamente 'LIBERA')
    public synchronized void doSave(Partita p) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "INSERT INTO partita (data_ora, luogo, id_torneo, id_squadra_casa, id_squadra_ospite, stato_arbitro) VALUES (?, ?, ?, ?, ?, 'LIBERA')";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, p.getDataOra());
            ps.setString(2, p.getLuogo());
            ps.setInt(3, p.getIdTorneo());
            ps.setInt(4, p.getIdSquadraCasa());
            ps.setInt(5, p.getIdSquadraOspite());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) { e.printStackTrace(); }
        // Ricorda sempre il blocco finally come l'hai già scritto tu...
    }





    // 3. NUOVO doAssegnaArbitro (Gestisce Accettazione e Rifiuto)
    public synchronized void doAssegnaArbitro(int idPartita, int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            if(idArbitro == 0) {
                // Se rifiuta, la partita torna LIBERA e id_arbitro torna NULL
                String sql = "UPDATE partita SET id_arbitro = NULL, stato_arbitro = 'LIBERA' WHERE id_partita = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, idPartita);
            } else {
                // Se accetta, diventa CONFERMATO
                String sql = "UPDATE partita SET id_arbitro = ?, stato_arbitro = 'CONFERMATO' WHERE id_partita = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, idArbitro);
                ps.setInt(2, idPartita);
            }
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // FONDAMENTALE PER NON BLOCCARE IL DATABASE
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 4. L'Organizzatore Propone l'Arbitro
    public synchronized void doProponiArbitro(int idPartita, int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE partita SET id_arbitro = ?, stato_arbitro = 'PROPOSTA' WHERE id_partita = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
            ps.setInt(2, idPartita);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // FONDAMENTALE PER NON BLOCCARE IL DATABASE
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 5. L'Arbitro si Candida da solo
    public synchronized void doCandidaturaArbitro(int idPartita, int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "UPDATE partita SET id_arbitro = ?, stato_arbitro = 'CANDIDATO' WHERE id_partita = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
            ps.setInt(2, idPartita);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // FONDAMENTALE PER NON BLOCCARE IL DATABASE
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // 6. RECUPERA PARTITE LIBERE (Per far candidare l'arbitro)
    public synchronized List<Partita> doRetrievePartiteLibere() {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> libere = new ArrayList<>();
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM partita WHERE stato_arbitro = 'LIBERA' AND giocata = 0 ORDER BY data_ora ASC";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Partita p = new Partita();
                p.setIdPartita(rs.getInt("id_partita"));
                p.setDataOra(rs.getTimestamp("data_ora"));
                p.setLuogo(rs.getString("luogo"));
                p.setIdTorneo(rs.getInt("id_torneo"));
                p.setStatoArbitro(rs.getString("stato_arbitro")); // Importante
                libere.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return libere;
    }

    // 7. RECUPERA PROPOSTE PER L'ARBITRO
    public synchronized List<Partita> doRetrieveProposteArbitro(int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> proposte = new ArrayList<>();
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM partita WHERE id_arbitro = ? AND stato_arbitro = 'PROPOSTA'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Partita p = new Partita();
                p.setIdPartita(rs.getInt("id_partita"));
                p.setDataOra(rs.getTimestamp("data_ora"));
                p.setLuogo(rs.getString("luogo"));
                p.setStatoArbitro(rs.getString("stato_arbitro"));
                proposte.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return proposte;
    }
    public synchronized void doUpdateRisultato(int idPartita, int golCasa, int golOspite) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManagerConnectionPool.getConnection();
            // Aggiorniamo i gol e settiamo lo stato a 'giocata' (TRUE)
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
    // 2. RECUPERA CALENDARIO DI UN TORNEO
    public synchronized List<Partita> doRetrieveByTorneo(int idTorneo) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> calendario = new ArrayList<>();

        try {
            conn = DriverManagerConnectionPool.getConnection();
            // Ordiniamo per data, così vediamo prima le partite più vicine
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

                // Se nel DB è NULL, JDBC restituirà 0 in automatico (perfetto per noi)
                p.setIdArbitro(rs.getInt("id_arbitro"));

                // LA NUOVA COLONNA FONDAMENTALE
                p.setStatoArbitro(rs.getString("stato_arbitro"));

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
    // 8. RECUPERA LE CANDIDATURE IN ATTESA EFFETTUATE DALL'ARBITRO
    public synchronized List<Partita> doRetrieveCandidatureInAttesa(int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> inAttesa = new ArrayList<>();
        try {
            conn = DriverManagerConnectionPool.getConnection();
            String sql = "SELECT * FROM partita WHERE id_arbitro = ? AND stato_arbitro = 'CANDIDATO' ORDER BY data_ora ASC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Partita p = new Partita();
                p.setIdPartita(rs.getInt("id_partita"));
                p.setDataOra(rs.getTimestamp("data_ora"));
                p.setLuogo(rs.getString("luogo"));
                p.setIdTorneo(rs.getInt("id_torneo"));
                p.setStatoArbitro(rs.getString("stato_arbitro"));
                inAttesa.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return inAttesa;
    }
    // 9. RECUPERA SOLO LE PARTITE UFFICIALMENTE ASSEGNATE ALL'ARBITRO
    public synchronized List<Partita> doRetrievePartiteConfermate(int idArbitro) {
        Connection conn = null;
        PreparedStatement ps = null;
        List<Partita> confermate = new ArrayList<>();
        try {
            conn = DriverManagerConnectionPool.getConnection();
            // LA MAGIA È QUI: Aggiungiamo AND stato_arbitro = 'CONFERMATO'
            String sql = "SELECT * FROM partita WHERE id_arbitro = ? AND stato_arbitro = 'CONFERMATO' ORDER BY data_ora ASC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idArbitro);
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
                p.setStatoArbitro(rs.getString("stato_arbitro"));
                p.setGolCasa(rs.getInt("gol_casa"));
                p.setGolOspite(rs.getInt("gol_ospite"));
                p.setGiocata(rs.getBoolean("giocata"));
                confermate.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                DriverManagerConnectionPool.releaseConnection(conn);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return confermate;
    }
}