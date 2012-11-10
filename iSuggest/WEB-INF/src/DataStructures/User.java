package DataStructures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.DatabaseUtils;
import Utils.TextUtils;
public class User {
	
	private String userId = null;
	private String emailAddr = null;
	private String firstName = null;
	private String lastName = null;
	private String password = null;
	private String userType = null;
	
	private List errorMessage = new ArrayList();
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	DatabaseUtils db = new DatabaseUtils();
	
	public User() {}
	
	public void insert() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		boolean exists = false;
		
		//Error checking for if the user has left blank values in the registration form
		if (this.emailAddr == null) {
			this.errorMessage.add("E-mail address cannot be left blank.");
		}
		if (this.password == null) {
			this.errorMessage.add("Password cannot be left blank.");
		}
		
		if (emailAddr != null && password != null) {
			//Check if the user id already exists for the entered user
			ps = con.prepareStatement(
					"SELECT user_id" +
					" FROM user" +
					" WHERE email_addr=?");
			ps.setString(1, this.emailAddr);
			rs = ps.executeQuery();
			if (rs.next()) {
				exists = true;
				this.errorMessage.add("That e-mail address already has an account registered to it.");
			}
			rs.close();
			ps.close();
			
			if (!exists) {
				int userId = 0;
				ps = con.prepareStatement(
						"SELECT MAX(user_id) AS user_id" +
						" FROM user" +
						" WHERE 1=1");
				rs = ps.executeQuery();
				if (rs.next()) {
					userId = rs.getInt("user_id") + 1;
				}
				rs.close();
				ps.close();
				
				ps = con.prepareStatement(
						"INSERT INTO user (user_id, email_addr, first_name, last_name, password, user_type)" +
						" VALUES (?, ?, ?, ?, ?, ?)");
				ps.setInt(1, userId);
				ps.setString(2, this.emailAddr);
				ps.setString(3, this.firstName);
				ps.setString(4, this.lastName);			
				ps.setString(5, this.password);
				ps.setString(6, this.userType);
				ps.executeUpdate();
				ps.close();
			}
		}
		if (con != null) {
			con.close();
		}
	}

	public void query() throws SQLException {
		con = db.connectToDatabase();
		if (this.emailAddr == null) {
			errorMessage.add("Please enter valid email address.");		
		}		
		if (this.password == null) {
			errorMessage.add("Please enter valid password.");
		}
		boolean exists = false;		
		ps = con.prepareStatement(
				"SELECT user_id, email_addr, first_name, last_name, password, user_type" +
				" FROM user" +
				" WHERE email_addr=? AND password=?");
		ps.setString(1, this.emailAddr);
		ps.setString(2, this.password);
		rs = ps.executeQuery();
		if (rs.next()) {
			exists = true;
			this.userId = rs.getString("userId");
			this.emailAddr = rs.getString("emailAddr");
			this.firstName = rs.getString("firstName");
			this.lastName = rs.getString("lastName");
			this.password = rs.getString("password");
			this.userType = rs.getString("userType");
		}
		rs.close();
		ps.close();			
		if (con != null) {
			con.close();
		}
		if (emailAddr != null && password != null && !exists) {
			errorMessage.add("Email address or password isn't correct.  Please re-enter.");
		}	
	}

	public String getUserId() {
		return userId;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserType() {
		return userType;
	}

	public List getErrorMessage() {
		return errorMessage;
	}

	public void setUserId(String userId) {
		this.userId = TextUtils.zeroToNull(userId);
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = TextUtils.zeroToNull(emailAddr);
	}

	public void setFirstName(String firstName) {
		this.firstName = TextUtils.zeroToNull(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName = TextUtils.zeroToNull(lastName);
	}

	public void setPassword(String password) {
		this.password = TextUtils.zeroToNull(password);
	}

	public void setUserType(String userType) {
		this.userType = TextUtils.zeroToNull(userType);
	}
}



