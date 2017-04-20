package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession(false);
		
		RequestDispatcher dispatcher=null;
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		if(session==null)
		{	out.print("<center><h2>Invalid Session</h2></center>");
			dispatcher=req.getRequestDispatcher("view/Login.html");
			dispatcher.include(req, resp);
		}
		else{
			dispatcher=req.getRequestDispatcher("view/Header.html");
			dispatcher.include(req, resp);
			dispatcher=req.getRequestDispatcher("view/ChangePassword.html");
			dispatcher.include(req, resp);
			dispatcher=req.getRequestDispatcher("view/Footer.html");
			dispatcher.include(req, resp);
		}
}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
HttpSession session=req.getSession(false);
		
		RequestDispatcher dispatcher=null;
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		if(session==null)
		{	out.print("<center><h2>Invalid Session</h2></center>");
			dispatcher=req.getRequestDispatcher("view/Login.html");
			dispatcher.include(req, resp);
		}
		else
		{
			String oldpassword=req.getParameter("curntpassword");
			String newpassword=req.getParameter("newpassword");
			int regno=(int) session.getAttribute("user");
			System.out.println(regno);
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			dispatcher=req.getRequestDispatcher("view/Header.html");
			dispatcher.include(req, resp);
			
			
			try {
				// 1.Get the Instance Of Driver Class
				Class.forName("com.mysql.jdbc.Driver");
				// 2.Get The Connection Via Driver
				String dbUrl = "jdbc:mysql://localhost:3306/becm67?user=root&password=root";
				con = DriverManager.getConnection(dbUrl);
				// 3.Issue SQL Query
				String query = "update student_otherinfo set password=? where reg_no=? and password=?";
				pstmt=con.prepareStatement(query);
				pstmt.setString(1, newpassword);
				pstmt.setInt(2, regno);
				pstmt.setString(3, oldpassword);
				int status=pstmt.executeUpdate();
				if(status==1)
				{
					out.print("<center><h2>Password Updated Succesfully</h2>"
							+ "<h2>New Password : "+newpassword+"</center>");
				}
				else{
					out.print("<center><h2>InCorrect Password</h2>"
							+ "<h2>Please Try Again...</h2></center>");
					dispatcher=req.getRequestDispatcher("view/ChangePassword.html");
					dispatcher.include(req, resp);
				}
				dispatcher=req.getRequestDispatcher("view/Footer.html");
				dispatcher.include(req, resp);
			

			} catch (Exception e) {
				e.printStackTrace();
				out.print("<center><h2>Error Occured........</h2>" + "<h2>Please try after Some Time</h2></center>");
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}




		}
	}
}
