package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataStructures.Category;
import DataStructures.Role;

public class VerificationUtils {
	public VerificationUtils() {}
	
	public static ArrayList getCategories() throws SQLException {
		ArrayList categories = new ArrayList();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ps = con.prepareStatement(
				"SELECT category_id, category" +
				" FROM categories" +
				" WHERE 1=1");
		rs = ps.executeQuery();
		while (rs.next()) {
			Category category = new Category();
			category.setCategoryId(rs.getString("category_id"));
			category.setCategory(rs.getString("category"));
			categories.add(category);
		}
		rs.close();
		ps.close();
		
		return categories;
	}
	
	public static ArrayList getRoles() throws SQLException {
		ArrayList roles = new ArrayList();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ps = con.prepareStatement(
				"SELECT role_id, role" +
				" FROM user_roles" +
				" WHERE role_id < '50'");
		rs = ps.executeQuery();
		while (rs.next()) {
			Role role = new Role();
			role.setRoleId(rs.getString("role_id"));
			role.setRole(rs.getString("role"));
			roles.add(role);
		}
		rs.close();
		ps.close();
		
		return roles;
	}
	
	public static boolean canVoteSuggestion(String postId, String userId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		boolean canVote = true;
		ps = con.prepareStatement(
				"SELECT post_id, user_id" +
				" FROM voted_suggestion" +
				" WHERE post_id=? AND user_id=?");
		ps.setString(1, postId);
		ps.setString(2, userId);
		rs = ps.executeQuery();
		if (rs.next()) {
			canVote = false;
		}
		rs.close();
		ps.close();
		
		if (con != null) {
			con.close();
		}
		return canVote;
	}
}