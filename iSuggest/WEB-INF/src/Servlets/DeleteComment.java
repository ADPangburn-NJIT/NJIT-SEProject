package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataStructures.Comments;

public class DeleteComment extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("postDetails.jsp?postId=" + request.getParameter("postId"));
		List generalMessage = new ArrayList();
		Comments comment = new Comments();
		try {
			comment.setPostId(request.getParameter("postId"));
			comment.setCommentId(request.getParameter("commentId"));
			comment.deleteComment();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (comment.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", comment.getErrorMessage());
		}
		else {
			generalMessage.add("Comment has been deleted.");
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}