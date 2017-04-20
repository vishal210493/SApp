package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewAllStudentsInfo extends HttpServlet
{
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
			doPost(req,resp);
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
			Connection con=null;
			Statement stmt=null;
			ResultSet rs=null;
			try {
				//1.Load the Driver
				Class.forName("com.mysql.jdbc.Driver");
				//2.Get the Connection via Driver
				String dbUrl="jdbc:mysql://localhost:3306/becm67?user=root&password=root";
				con=DriverManager.getConnection(dbUrl);
				//3.Issue Sql Query
				
				String query = "Select * from student_info si, guardian_info gi,Student_otherinfo soi where "
						+ "si.reg_no=gi.reg_no and si.reg_no=soi.reg_no";
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				dispatcher=req.getRequestDispatcher("view/Header.html");
				dispatcher.include(req, resp);
				out.print("<center><table border=\"1px\">" + "<tr>" + "<th>Registration Number</th>" + "<th>First Name</th>"
						+ "<th>Middle Name</th>" + "<th>Last Name</th>" + "<th>Guardian First Name</th>"
						+ "<th>Guardian Middle Name</th>" + "<th>Guardian Last Name</th>" + "<th>Admin</th><th>Password</th>"
						+ "</tr>");
				while(rs.next())
				{
					int regno=rs.getInt("Reg_no");
					String fname = rs.getString("First_Name");
					String mName = rs.getString("Middle_Name");
					String lName = rs.getString("Last_Name");
					String gfName = rs.getString("GFirst_Name");
					String gmName = rs.getString("GMiddle_Name");
					String glName = rs.getString("GLast_Name");
					String admin = rs.getString("Admin");
					String pass = rs.getString("Password");
					out.print("<tr>" + "<th>" + regno + "</th>" + "<th>" + fname + "</th>" + "<th>" + mName
							+ "</th>" + "<th>" + lName + "</th>" + "<th>" + gfName + "</th>" + "<th>" + gmName + "</th>"
							+ "<th>" + glName + "</th>" + "<th>" + admin + "</th>" + "<th>" + pass + "</th></tr>");
				}
				out.print("</table></center>");
				dispatcher=req.getRequestDispatcher("view/Footer.html");
				dispatcher.include(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
