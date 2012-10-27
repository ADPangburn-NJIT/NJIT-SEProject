package DataStructures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utils.DatabaseUtils;

public class User {
	private String userId = null;
	private String emailAddr = null;
	private String firstName = null;
	private String lastName = null;
	private String passWd = null;
	private String userType = null;
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	DatabaseUtils db = new DatabaseUtils();
	
	public User() {}
	
	public boolean insert() throws SQLException {
		con = db.connectToDatabase();
		boolean exists = false;
		
		ps = con.prepareStatement(
				"SELECT user_id" +
				" FROM user" +
				" WHERE user_id=?");
		ps.setString(1, this.userId);
		rs = ps.executeQuery();
		if (rs.next()) {
			exists = true;
		}
		rs.close();
		ps.close();
		
		if (!exists) {
			ps = con.prepareStatement(
					"INSERT INTO user (user_id, email_addr, first_name, last_name, password, user_type)" +
					" VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, this.userId);
			ps.setString(2, this.emailAddr);
			ps.setString(3, this.firstName);
			ps.setString(4, this.lastName);			
			ps.setString(5, this.passWd);
			ps.setString(6, this.userType);
			ps.executeUpdate();
			ps.close();
			
			if (con != null) {
				con.close();
			}
			return true;
		}
		else {
			return false;
		}
	}

	public void query() throws SQLException {
		con = db.connectToDatabase();
		ps = con.prepareStatement(
				"SELECT user_id, email_addr, first_name, last_name, password, user_type" +
				" FROM user" +
				" WHERE user_id=?" +
				" ORDER BY CAST(user_id AS SIGNED) ASC");
		ps.setString(1, this.userId);
		rs = ps.executeQuery();
		if (rs.next()) {
			this.userId = rs.getString("userId");
			this.emailAddr = rs.getString("emailAddr");
			this.firstName = rs.getString("firstName");
			this.lastName = rs.getString("lastName");
			this.emailAddr = rs.getString("passWd");
			this.emailAddr = rs.getString("userType");
			
			
			
		}
		rs.close();
		ps.close();
		
		if (con != null) {
			con.close();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassWd() {
		return passWd;
	}

	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public DatabaseUtils getDb() {
		return db;
	}

	public void setDb(DatabaseUtils db) {
		this.db = db;
	}
}



