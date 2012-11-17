package DataStructures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.DatabaseUtils;
import Utils.TextUtils;

public class Post {
	private String postId = null;
	private String userId = null;
	private String title = null;
	private String category = null;
	private String description = null;
	private String thumbsUp = null;
	private String thumbsDown = null;
	
	private List errorMessage = new ArrayList();
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	DatabaseUtils db = new DatabaseUtils();
	
	public Post() {}
	
	public void postSuggestion() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		if (title != null && category != null && description != null && description.length() <= 255) {
			System.out.println("1");
			ps = con.prepareStatement(
					"SELECT MAX(post_id) AS post_id" +
					" FROM pending_posts" +
					" WHERE 1=1");
			rs = ps.executeQuery();
			if (rs.next()) {
				this.postId = Integer.toString(rs.getInt("post_id") + 1);
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO pending_posts (post_id, user_id, title, category, description)" +
					" VALUES(?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(this.postId));
			ps.setString(2, this.userId);
			ps.setString(3, this.title);
			ps.setString(4, this.category);
			ps.setString(5, this.description);
			ps.executeUpdate();
			ps.close();
		}
		else {
			if (title == null) {
				this.errorMessage.add("Please enter a title for your suggestion.");
			}
			if (category == null) {
				this.errorMessage.add("Please choose a category for your suggestion.");
			}
			if (description == null) {
				this.errorMessage.add("Please enter a description for your suggestion.");
			}
			if (description.length() > 255) {
				System.out.println("3");
				this.errorMessage.add("Your description is too long. Max length is 255 characters. Your length: " + description.length() + " characters.");
			}
		}
		if (con != null) {
			con.close();
		}
	}
	
	public void approvePost() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		
		String postId = null;
		String userId = null;
		String title = null;
		String category = null;
		String description = null;
		
		if (this.postId != null) {
			ps = con.prepareStatement(
					"SELECT post_id, user_id, title, category, description" +
					" FROM pending_posts" +
					" WHERE post_id=?");
			ps.setString(1, this.postId);
			rs = ps.executeQuery();
			if (rs.next()) {
				postId = rs.getString("post_id");
				userId = rs.getString("user_id");
				title = rs.getString("title");
				category = rs.getString("category");
				description = rs.getString("description");
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO active_posts (post_id, user_id, title, category, description, thumbs_up, thumbs_down)" +
					" VALUES(?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(postId));
			ps.setString(2, userId);
			ps.setString(3, title);
			ps.setString(4, category);
			ps.setString(5, description);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.executeUpdate();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO archived_posts (post_id, user_id, title, category, description, thumbs_up, thumbs_down)" +
					" VALUES(?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(postId));
			ps.setString(2, userId);
			ps.setString(3, title);
			ps.setString(4, category);
			ps.setString(5, description);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.executeUpdate();
			ps.close();
			
			ps = con.prepareStatement(
					"DELETE FROM pending_posts" +
					" WHERE post_id=?");
			ps.setInt(1, Integer.parseInt(postId));
			ps.executeUpdate();
			ps.close();
		}
		else {
			this.errorMessage.add("Invalid Post-ID.");
		}
		if (con != null) {
			con.close();
		}
	}
	
	public List getErrorMessage() {
		return errorMessage;
	}
	public String getPostId() {
		return postId;
	}
	public String getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
	public String getThumbsUp() {
		return thumbsUp;
	}
	public String getThumbsDown() {
		return thumbsDown;
	}
	public void setPostId(String postId) {
		this.postId = TextUtils.zeroToNull(postId);
	}
	public void setUserId(String userId) {
		this.userId = TextUtils.zeroToNull(userId);
	}
	public void setTitle(String title) {
		this.title = TextUtils.zeroToNull(title);
	}
	public void setCategory(String category) {
		this.category = TextUtils.zeroToNull(category);
	}
	public void setDescription(String description) {
		this.description = TextUtils.zeroToNull(description);
	}
	public void setThumbsUp(String thumbsUp) {
		this.thumbsUp = TextUtils.zeroToNull(thumbsUp);
	}
	public void setThumbsDown(String thumbsDown) {
		this.thumbsDown = TextUtils.zeroToNull(thumbsDown);
	}
}