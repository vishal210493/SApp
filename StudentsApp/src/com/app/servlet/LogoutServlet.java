package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doPost(req, resp);
}
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	PrintWriter out=resp.getWriter();
	HttpSession session=req.getSession(false);
	RequestDispatcher dispatcher=null;
	if(session!=null)
	{
		session.invalidate();
	}
	out.print("<center><h2>Thank You</h2>"
			+ "<h2>You Have Been Logged Out Successfully.</h2></center>");
		dispatcher=req.getRequestDispatcher("view/Login.html");
		dispatcher.include(req, resp);
}
}
