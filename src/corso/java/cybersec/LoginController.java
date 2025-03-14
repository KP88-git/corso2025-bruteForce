package corso.java.cybersec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {

	private static final String dburl = "jdbc:mysql://localhost:3306/database";
	private static final String dbusername = "kamil";
	private static final String dbpassword = "password";

    public boolean login(String username, String password) {
        // Connessione al database
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dburl, dbusername, dbpassword);

            // Query SQL costruita in modo insicuro
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            
            stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Login avvenuto con successo
                return true;
            } else {
                // Credenziali errate
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Chiusura risorse
            closeResources(conn, stmt, rs);
        }
    }

	private void closeResources(Connection conn, Statement pstmt, ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
