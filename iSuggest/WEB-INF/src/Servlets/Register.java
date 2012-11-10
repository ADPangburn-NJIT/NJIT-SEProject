package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataStructures.User;

public class Register extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		List generalMessage = new ArrayList();
		User user = new User();
		try {
			user.setEmailAddr(request.getParameter("emailAddr"));
			user.setPassword(request.getParameter("password"));
			user.setFirstName(request.getParameter("firstName"));
			user.setLastName(request.getParameter("lastName"));
			user.setUserType(request.getParameter("userType"));
			user.insert();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (user.getErrorMessage().size() > 0) {
			request.setAttribute("errorMessage", user.getErrorMessage());
		}
		else {
			generalMessage.add("Registration successful. You can now log in with your e-mail address and password.");
			request.setAttribute("user", user);
			request.setAttribute("generalMessage", generalMessage);
		}
		dispatcher.forward(request, response);
	}
}