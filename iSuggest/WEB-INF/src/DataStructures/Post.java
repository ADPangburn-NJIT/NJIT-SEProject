package DataStructures;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	private String userType = null;
	private String title = null;
	private String category = null;
	private String description = null;
	private String thumbsUp = null;
	private String thumbsDown = null;
	private String date = null;
	
	private User user = new User();
	private List errorMessage = new ArrayList();
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	DatabaseUtils db = new DatabaseUtils();
	
	public Post() {}
	
	public void voteSuggestion(int vote) throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		int thumbsUp = 0;
		int thumbsDown = 0;
		
		//Get the vote count for this specific post
		ps = con.prepareStatement(
				"SELECT thumbs_up, thumbs_down" +
				" FROM active_posts" +
				" WHERE post_id=?");
		ps.setString(1, this.postId);
		rs = ps.executeQuery();
		if (rs.next()) {
			thumbsUp = rs.getInt("thumbs_up");
			thumbsDown = rs.getInt("thumbs_down");
		}
		rs.close();
		ps.close();
		
		if (vote == 1) {
			thumbsUp++;
		}
		else if (vote == -1) {
			thumbsDown++;
		}
		else {
			//Do nothing.
		}
		
		//Do the update
		ps = con.prepareStatement(
				"UPDATE active_posts" +
				" SET thumbs_up=?, thumbs_down=?" +
				" WHERE post_id=?");
		ps.setInt(1, thumbsUp);
		ps.setInt(2, thumbsDown);
		ps.setString(3, postId);
		ps.executeUpdate();
		ps.close();
		
		//Make a note of the user voting
		ps = con.prepareStatement(
				"INSERT INTO voted_suggestion (post_id, user_id, vote)" +
				" VALUES (?, ?, ?)");
		ps.setString(1, this.postId);
		ps.setString(2, this.user.getUserId());
		ps.setInt(3, vote);
		ps.executeUpdate();
		ps.close();
	}
	public ArrayList getPendingPosts() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		ArrayList pendingPosts = new ArrayList();
		//Get the relevant post data.
		ps = con.prepareStatement(
				"SELECT post_id, user_id, title, description" +
				" FROM pending_posts" +
				" WHERE 1=1;");
		rs = ps.executeQuery();
		while (rs.next()) {
			Post post = new Post();
			post.setPostId(rs.getString("post_id"));
			post.setUserId(rs.getString("user_id"));
			post.setTitle(rs.getString("title"));
			post.setDescription(rs.getString("description"));
			pendingPosts.add(post);
		}
		rs.close();
		ps.close();

		/*Now that we have the relevant post data, we need to loop through it to get the user data that corresponds with the
		 * user_id grabbed from `active_posts` and match it with the one in `user`
		 */
		for (int i = 0; i < pendingPosts.size(); i++) {
			User user = ((Post)pendingPosts.get(i)).getUser();
			ps = con.prepareStatement(
					"SELECT user_id, first_name, last_name, email_addr, user_type" +
					" FROM user" +
					" WHERE user_id=?");
			ps.setString(1, ((Post)pendingPosts.get(i)).getUserId());
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getString("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmailAddr(rs.getString("email_addr"));
				user.setUserType(rs.getString("user_type"));
			}
			rs.close();
			ps.close();
		}

		return pendingPosts;
	}
	
	public ArrayList getActiveSuggestions(int page, String category, String group, String popularity, String search) throws SQLException, UnsupportedEncodingException {
		//Database connection stuff
		con = db.connectToDatabase();
		int startPost = (page * 6) - 5; //6 posts per page
		int endPost = page * 6; //6 Posts per page
		int postCount = 1;
		ArrayList activePosts = new ArrayList();
		
		//Get the relevant post data.
		String query = "SELECT post_id, user_id, title, thumbs_up, thumbs_down" +
				" FROM active_posts" +
				" WHERE 1=1";
		if (category != null && !"null".equals(category) && !"All".equals(category)) {
			query += " AND category='" + category + "'";
		}
		if (search != null && !"null".equals(search)) {
			query += " AND title LIKE " + "'%" + URLEncoder.encode(search, "UTF-8") + "%'";
		}
		if (group != null && !"null".equals(group) && !"All".equals(group)) {
			query += " AND user_type='" + group + "'"; 
		}
		if (popularity != null && !"null".equals(popularity) && !"All".equals(popularity)) {
			if ("BestSuggestion".equals(popularity)) {
				query += " ORDER BY thumbs_up DESC"; 
			}
			else if ("WorstSuggestion".equals(popularity)) {
				query += " ORDER BY thumbs_down DESC"; 
			}
			else if ("MostCommented".equals(popularity)) {
				query += " ORDER BY comments DESC"; 
			}
		}
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		while (rs.next()) {
			if (postCount >= startPost && postCount <= endPost) {
				Post post = new Post();
				post.setPostId(rs.getString("post_id"));
				post.setUserId(rs.getString("user_id"));
				post.setTitle(rs.getString("title"));
				post.setThumbsUp(rs.getString("thumbs_up"));
				post.setThumbsDown(rs.getString("thumbs_down"));
				activePosts.add(post);
			}
			postCount++;
		}
		rs.close();
		ps.close();

		/*Now that we have the relevant post data, we need to loop through it to get the user data that corresponds with the
		 * user_id grabbed from `active_posts` and match it with the one in `user`
		 */
		for (int i = 0; i < activePosts.size(); i++) {
			User user = ((Post)activePosts.get(i)).getUser();
			ps = con.prepareStatement(
					"SELECT user_id, first_name, last_name, user_type" +
					" FROM user" +
					" WHERE user_id=?");
			ps.setString(1, ((Post)activePosts.get(i)).getUserId());
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getString("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserType(rs.getString("user_type"));
			}
			rs.close();
			ps.close();
			
			//Get the string role of the user
			ps = con.prepareStatement(
					"SELECT role" +
					" FROM user_roles" +
					" WHERE role_id=?");
			ps.setString(1, user.getUserType());
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setRole(rs.getString("role"));
			}
			rs.close();
			ps.close();
		}

		return activePosts;
	}
	
	public void postSuggestion() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		if (title != null && category != null && description != null && description.length() <= 255) {
			if (TextUtils.bannedWords(title) == false && TextUtils.bannedWords(description) == false) {
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
						"INSERT INTO pending_posts (post_id, user_id, title, category, description, user_type, date)" +
						" VALUES(?, ?, ?, ?, ?, ?, CURDATE())");
				ps.setInt(1, Integer.parseInt(this.postId));
				ps.setString(2, this.userId);
				ps.setString(3, this.title);
				ps.setString(4, this.category);
				ps.setString(5, this.description);
				ps.setString(6, this.userType);
				ps.executeUpdate();
				ps.close();
			}
			else {
				this.errorMessage.add("Please sanitize your language.");
			}
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
			if (description != null) {
				if (description.length() > 255) {
					this.errorMessage.add("Your description is too long. Max length is 255 characters. Your length: " + description.length() + " characters.");
				}
			}
		}
		if (con != null) {
			con.close();
		}
	}
	
	public void acceptPost() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		
		String postId = null;
		String activePostId = null;
		String userId = null;
		String title = null;
		String category = null;
		String description = null;
		String userType = null;
		String date = null;
		
		if (this.postId != null) {
			ps = con.prepareStatement(
					"SELECT post_id, user_id, title, category, description, user_type, date" +
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
				userType = rs.getString("user_type");
				date = rs.getString("date");
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"SELECT MAX(post_id) AS active_post_id" +
					" FROM active_posts" +
					" WHERE 1=1");
			rs = ps.executeQuery();
			if (rs.next()) {
				activePostId = Integer.toString(rs.getInt("active_post_id") + 1);
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO active_posts (post_id, user_id, title, category, description, thumbs_up, thumbs_down, user_type, comments)" +
					" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(activePostId));
			ps.setString(2, userId);
			ps.setString(3, title);
			ps.setString(4, category);
			ps.setString(5, description);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setString(8, userType);
			ps.setInt(9, 0);
			ps.executeUpdate();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO archived_posts (post_id, user_id, title, category, description, thumbs_up, thumbs_down, date)" +
					" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(activePostId));
			ps.setString(2, userId);
			ps.setString(3, title);
			ps.setString(4, category);
			ps.setString(5, description);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setString(8, date);
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
	
	public void rejectPost() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		
		String postId = null;
		String archivedPostId = null;
		String userId = null;
		String title = null;
		String category = null;
		String description = null;
		String date = null;
		
		if (this.postId != null) {
			ps = con.prepareStatement(
					"SELECT post_id, user_id, title, category, description, date" +
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
				date = rs.getString("date");
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"SELECT MAX(post_id) AS archived_post_id" +
					" FROM archived_posts_rejected" +
					" WHERE 1=1");
			rs = ps.executeQuery();
			if (rs.next()) {
				archivedPostId = Integer.toString(rs.getInt("archived_post_id") + 1);
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO archived_posts_rejected (post_id, user_id, title, category, description, thumbs_up, thumbs_down, date)" +
					" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(archivedPostId));
			ps.setString(2, userId);
			ps.setString(3, title);
			ps.setString(4, category);
			ps.setString(5, description);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setString(8, date);
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
	public String getUserType() {
		return userType;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = TextUtils.zeroToNull(date);
	}

	public void setUserType(String userType) {
		this.userType = TextUtils.zeroToNull(userType);
	}

	public void setThumbsDown(String thumbsDown) {
		this.thumbsDown = TextUtils.zeroToNull(thumbsDown);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}