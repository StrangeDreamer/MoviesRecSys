package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCtr extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password=request.getParameter("password");
		//password="12345";
		//int user= Integer.parseInt(username);
		//boolean validate=validateLogin(user,password);
		request.setAttribute("username", username);
		//request.getRequestDispatcher("home.jsp").forward(request, response);
		if(password.equals("12345")){
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		else{
			request.getRequestDispatcher("failed.jsp").forward(request, response);
		}
	
	}
	

}
