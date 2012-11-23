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

import Utils.TextUtils;

import DataStructures.Comments;
import DataStructures.User;

public class LeaveComment extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("postDetails.jsp?postId=" + request.getParameter("postId"));
		List generalMessage = new ArrayList();
		User user = new User();
		Comments comment = new Comments();
		try {
			user = (User)session.getAttribute("user");
			comment.setUserId(user.getUserId());
			comment.setPostId(request.getParameter("postId"));
			System.out.println(request.getParameter("postId"));
			System.out.println(request.getParameter("userId"));
			System.out.println(request.getParameter("commentText"));
			comment.setCommentText(request.getParameter("commentText"));
			comment.postComment();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (comment.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", comment.getErrorMessage());
		}
		else {
			generalMessage.add("Comment has been posted successfully.");
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}