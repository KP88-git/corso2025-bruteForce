package corso.java.cybersec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsecureBruteForceLoginController {
	private Connection conn = null;
	private int failedAttempts = 0;
	private final int maxFails = 3;
	
	//assumo che questo oggetto sia in qualche modo legato alla sessione 
	//e venga chiuso (perciò resettato) quando la sessione scade.
	//tipo 15 minuti di inattività di solito se non erro?

	public boolean login(String username, String password) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dburl = "jdbc:mysql://localhost:3306/hotel";

		if (failedAttempts < maxFails) {

			try {
				this.conn = DriverManager.getConnection(dburl, username, password);
				String query = "SELECT password FROM users WHERE username = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, username);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("password");
					if (storedPassword.equals(password)) {
						// Login avvenuto con successo
						return true;
					} 
				}

				// autenticazione fallita
				failedAttempts++;
				return false;

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeResources(conn, pstmt, rs);
			}
		} else {
			System.out.println("Max failed attempts.");
			return false;
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
