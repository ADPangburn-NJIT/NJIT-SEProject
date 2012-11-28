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

public class CreateSuggestion extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		List generalMessage = new ArrayList();
		Post post = new Post();
		User user = new User();
		try {
			user = (User)session.getAttribute("user");
			post.setUserId(user.getUserId());
			post.setUserType(user.getUserType());
			post.setTitle(request.getParameter("title"));
			post.setCategory(request.getParameter("category"));
			post.setDescription(request.getParameter("description"));
			post.postSuggestion();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (post.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", post.getErrorMessage());
		}
		else {
			generalMessage.add("Suggestion created successfully. It will not appear until it has been approved by an administrator.");
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}