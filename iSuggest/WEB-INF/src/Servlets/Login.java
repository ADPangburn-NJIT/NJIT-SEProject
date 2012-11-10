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

import DataStructures.User;

public class Login extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		List generalMessage = new ArrayList();
		User user = new User();
		try {
			user.setEmailAddr(request.getParameter("emailAddr"));
			user.setPassword(request.getParameter("password"));
			user.query();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (user.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", user.getErrorMessage());
		}
		else {
			session.setAttribute("user", user);
			generalMessage.add("Welcome to iSuggest: " + user.getEmailAddr());
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}