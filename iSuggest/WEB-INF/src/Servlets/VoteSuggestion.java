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

public class VoteSuggestion extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp?page=" + request.getParameter("currentPage"));
		List generalMessage = new ArrayList();
		User user = new User();
		Post post = new Post();
		try {
			user = (User)session.getAttribute("user");
			post.setPostId(request.getParameter("voteSuggestionPostId"));
			post.setUser(user);
			post.voteSuggestion(Integer.parseInt(request.getParameter("vote")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (post.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", post.getErrorMessage());
		}
		else {
			/*generalMessage.add("Voted.");
			request.setAttribute("generalMessage", generalMessage);*/
		}
		dispatcher.forward(request, response);
	}
}