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

import DataStructures.Post;
import DataStructures.User;

public class RejectPost extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminIndex.jsp");
		List generalMessage = new ArrayList();
		Post post = new Post();
		try {
			post.setPostId(request.getParameter("rejectPostId"));
			post.rejectPost();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (post.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", post.getErrorMessage());
		}
		else {
			generalMessage.add("Suggestion has been rejected.");
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}