package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataStructures.Category;
import DataStructures.Post;
import DataStructures.Role;

public class VerificationUtils {
	public VerificationUtils() {}
	
	public static ArrayList getMyAcceptedSuggestions(String userId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ArrayList myPosts = new ArrayList();
		
		ps = con.prepareStatement(
				"SELECT post_id, title, description, date" +
				" FROM archived_posts" +
				" WHERE user_id=?" +
				" ORDER BY post_id DESC");
		ps.setString(1, userId);
		rs = ps.executeQuery();
		while (rs.next()) {
			Post post = new Post();
			post.setPostId(rs.getString("post_id"));
			post.setTitle(rs.getString("title"));
			post.setDescription(rs.getString("description"));
			post.setDate(rs.getString("date"));
			myPosts.add(post);
		}
		rs.close();
		ps.close();
		
		if (con != null) {
			con.close();
		}
		
		return myPosts;
	}
	
	public static ArrayList getMyRejectedSuggestions(String userId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ArrayList myPosts = new ArrayList();
		
		ps = con.prepareStatement(
				"SELECT post_id, title, description, date" +
				" FROM archived_posts_rejected" +
				" WHERE user_id=?" + 
				" ORDER BY post_id DESC");
		ps.setString(1, userId);
		rs = ps.executeQuery();
		while (rs.next()) {
			Post post = new Post();
			post.setPostId(rs.getString("post_id"));
			post.setTitle(rs.getString("title"));
			post.setDescription(rs.getString("description"));
			post.setDate(rs.getString("date"));
			myPosts.add(post);
		}
		rs.close();
		ps.close();
		
		if (con != null) {
			con.close();
		}
		
		return myPosts;
	}
	
	public static ArrayList getMyPendingSuggestions(String userId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ArrayList myPosts = new ArrayList();
		
		ps = con.prepareStatement(
				"SELECT post_id, title, description, date" +
				" FROM pending_posts" +
				" WHERE user_id=?");
		ps.setString(1, userId);
		rs = ps.executeQuery();
		while (rs.next()) {
			Post post = new Post();
			post.setPostId(rs.getString("post_id"));
			post.setTitle(rs.getString("title"));
			post.setDescription(rs.getString("description"));
			post.setDate(rs.getString("date"));
			myPosts.add(post);
		}
		rs.close();
		ps.close();
		
		if (con != null) {
			con.close();
		}
		
		return myPosts;
	}
	
	public static ArrayList getCategories() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();
		
		ArrayList categories = new ArrayList();
		
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
		
		if (con != null) {
			con.close();
		}
		
		return categories;
	}
	
	public static ArrayList getRoles() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		DatabaseUtils db = new DatabaseUtils();
		//Database connection stuff
		con = db.connectToDatabase();

		ArrayList roles = new ArrayList();
		
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
		
		if (con != null) {
			con.close();
		}
		
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