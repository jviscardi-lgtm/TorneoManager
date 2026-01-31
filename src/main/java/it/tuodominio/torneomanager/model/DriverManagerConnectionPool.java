package it.tuodominio.torneomanager.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnectionPool {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/torneomanager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";    private static String user = "root";
    private static String password = "admin";

    // Caricamento del Driver al primo avvio
    static {
        try {
            Class.forName(driver);
            System.out.println("Driver MySQL caricato correttamente");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Errore caricamento Driver MySQL");
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false); // Importante per gestire le transazioni manualmente
        return conn;
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                // Riabilitiamo l'autocommit prima di chiudere (buona norma)
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}