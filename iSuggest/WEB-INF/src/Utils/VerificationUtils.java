package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificationUtils {
	public VerificationUtils() {}
	
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