package DataStructures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.DatabaseUtils;

public class Comments {
	private String commentId = null;
	private String postId = null;
	private String userId = null;
	private String commentText = null;
	private String thumbsUp = null;
	private String thumbsDown = null;
	private Post post = new Post();
	private User user = new User();
	
	private List errorMessage = new ArrayList();
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	DatabaseUtils db = new DatabaseUtils();
	
	public void postComment() throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		
		if (commentText != null && commentText.length() <= 150) {
			ps = con.prepareStatement(
					"SELECT MAX(comment_id) AS comment_id" +
					" FROM comments" +
					" WHERE 1=1;");
			rs = ps.executeQuery();
			if (rs.next()) {
				this.commentId = Integer.toString(rs.getInt("comment_id") + 1);
			}
			rs.close();
			ps.close();
			
			ps = con.prepareStatement(
					"INSERT INTO comments (comment_id, post_id, user_id, comment_text, thumbs_up, thumbs_down)" +
					" VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.parseInt(this.commentId));
			ps.setInt(2, Integer.parseInt(this.postId));
			ps.setInt(3, Integer.parseInt(this.userId));
			ps.setString(4, this.commentText);
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.executeUpdate();
			ps.close();
		}
		else {
			if (this.commentText == null) {
				this.errorMessage.add("Please enter text into your comment.");
			}
			if (this.commentText.length() > 150) {
				this.errorMessage.add("Comment length is too long, max length is 150. Yours: " + commentText.length());
			}
		}
		if (con != null) {
			con.close();
		}
	}
	
	public ArrayList getActiveComments(int postId) throws SQLException {
		//Database connection stuff
		con = db.connectToDatabase();
		ArrayList activeComments = new ArrayList();
		//Get the relevant post data.
		ps = con.prepareStatement(
				"SELECT comment_text, user_id, thumbs_up, thumbs_down" +
				" FROM comments" +
				" WHERE post_id=?");
		ps.setInt(1, postId);
		rs = ps.executeQuery();
		while (rs.next()) {
			Comments comment = new Comments();
			comment.setCommentText(rs.getString("comment_text"));
			comment.setUserId(rs.getString("user_id"));
			comment.setThumbsUp(rs.getString("thumbs_up"));
			comment.setThumbsDown(rs.getString("thumbs_down"));
			activeComments.add(comment);
		}
		rs.close();
		ps.close();

		ps = con.prepareStatement(
				"SELECT user_id, title, description" +
				" FROM active_posts" +
				" WHERE post_id=?");
		ps.setInt(1, postId);
		rs = ps.executeQuery();
		if (rs.next()) {
			post.setUserId(rs.getString("user_id"));
			post.setTitle(rs.getString("title"));
			post.setDescription(rs.getString("description"));
		}
		rs.close();
		ps.close();
		
		/*Now that we have the relevant post data, we need to loop through it to get the user data that corresponds with the
		 * user_id grabbed from `active_posts` and match it with the one in `user`
		 */
		for (int i = 0; i < activeComments.size(); i++) {
			User user = ((Comments)activeComments.get(i)).getUser();
			ps = con.prepareStatement(
					"SELECT user_id, first_name, last_name, user_type" +
					" FROM user" +
					" WHERE user_id=?");
			ps.setString(1, ((Comments)activeComments.get(i)).getUserId());
			rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getString("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserType(rs.getString("user_type"));
			}
			rs.close();
			ps.close();
		}
		
		return activeComments;
	}

	public String getCommentId() {
		return commentId;
	}

	public String getPostId() {
		return postId;
	}

	public String getUserId() {
		return userId;
	}

	public String getCommentText() {
		return commentText;
	}

	public String getThumbsUp() {
		return thumbsUp;
	}

	public String getThumbsDown() {
		return thumbsDown;
	}

	public List getErrorMessage() {
		return errorMessage;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public void setThumbsUp(String thumbsUp) {
		this.thumbsUp = thumbsUp;
	}

	public void setThumbsDown(String thumbsDown) {
		this.thumbsDown = thumbsDown;
	}

	public void setErrorMessage(List errorMessage) {
		this.errorMessage = errorMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}