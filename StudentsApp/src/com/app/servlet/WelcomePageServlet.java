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

public class WelcomePageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doPost(req, resp);
}	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

HttpSession session=req.getSession(false);
RequestDispatcher dispatcher=null;
if(session==null)
{
dispatcher=req.getRequestDispatcher("view/Login.html");
dispatcher.include(req, resp);
}
else
{
	int regno=(int) session.getAttribute("user");
	System.out.println(regno);

	resp.setContentType("text/html");
	PrintWriter out = resp.getWriter();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	

	try {
		System.out.println("Welcome Page Servlet");
		// 1.Get the Instance Of Driver Class
		Class.forName("com.mysql.jdbc.Driver");
		// 2.Get The Connection Via Driver
		String dbUrl = "jdbc:mysql://localhost:3306/becm67?user=root&password=root";
		con = DriverManager.getConnection(dbUrl);
		// 3.Issue SQL Query
		String query = "Select * from student_info si, guardian_info gi,Student_otherinfo soi where "
				+ "si.reg_no=gi.reg_no and si.reg_no=soi.reg_no and soi.reg_no=?";
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, regno);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			
			dispatcher = req.getRequestDispatcher("view/Header.html");
			dispatcher.include(req, resp);
			if (rs.getString("Admin").equals("Y")) {
				out.print("<center><h3><a href=\"./view\">Click Here</a>&nbsp&nbspTo View All Students Info</h3></center>");
			}
			String fName = rs.getString("First_Name");
			String mName = rs.getString("Middle_Name");
			String lName = rs.getString("Last_Name");
			String gfName = rs.getString("GFirst_Name");
			String gmName = rs.getString("GMiddle_Name");
			String glName = rs.getString("GLast_Name");
			String admin = rs.getString("Admin");
			out.print("<center><table border=\"1px\">" + "<tr>" + "<th>Registration Number</th>" + "<th>First Name</th>"
					+ "<th>Middle Name</th>" + "<th>Last Name</th>" + "<th>Guardian First Name</th>"
					+ "<th>Guardian Middle Name</th>" + "<th>Guardian Last Name</th>" + "<th>Admin</th>"
					+ "</tr>" + "<tr>" + "<th>" + regno + "</th>" + "<th>" + fName + "</th>" + "<th>" + mName
					+ "</th>" + "<th>" + lName + "</th>" + "<th>" + gfName + "</th>" + "<th>" + gmName + "</th>"
					+ "<th>" + glName + "</th>" + "<th>" + admin + "</th>" + "</tr>" + "</table>");
			dispatcher = req.getRequestDispatcher("view/Footer.html");
			dispatcher.include(req, resp);
		} else {
			out.print("<Center><h2>InValid Registration Number Or Password</h2></center>");
			dispatcher = req.getRequestDispatcher("view/Login.html");
			dispatcher.include(req, resp);
		}

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
