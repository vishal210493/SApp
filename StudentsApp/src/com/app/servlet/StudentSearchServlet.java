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

public class StudentSearchServlet extends HttpServlet {

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
			dispatcher=req.getRequestDispatcher("view/StudentSearch.html");
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
	{
		out.print("<center><h2>Invalid Session</h2></center>");
		dispatcher=req.getRequestDispatcher("view/Login.html");
		dispatcher.include(req, resp);
	}
	else{
		String reg=req.getParameter("regno");
		String fName=req.getParameter("name");
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//Header
		dispatcher=req.getRequestDispatcher("view/Header.html");
		dispatcher.include(req, resp);
		try {
			//1.Load the Driver
			Class.forName("com.mysql.jdbc.Driver");
			//2.Get the Connection via Driver
			String dbUrl="jdbc:mysql://localhost:3306/becm67?user=root&password=root";
			con=DriverManager.getConnection(dbUrl);
			//3.Issue Sql Query
			//Condition 1st
			if(fName==null)
			{String query = "Select * from student_info si, guardian_info gi,Student_otherinfo soi where "
					+ "si.reg_no=gi.reg_no and si.reg_no=soi.reg_no and si.reg_no=?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,Integer.parseInt(reg));
			System.out.println(query);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				int regno=rs.getInt("Reg_no");
				String fname = rs.getString("First_Name");
				String mName = rs.getString("Middle_Name");
				String lName = rs.getString("Last_Name");
				String gfName = rs.getString("GFirst_Name");
				String gmName = rs.getString("GMiddle_Name");
				String glName = rs.getString("GLast_Name");
				String admin = rs.getString("Admin");
				out.print("<center><table border=\"1px\">" + "<tr>" + "<th>Registration Number</th>" + "<th>First Name</th>"
						+ "<th>Middle Name</th>" + "<th>Last Name</th>" + "<th>Guardian First Name</th>"
						+ "<th>Guardian Middle Name</th>" + "<th>Guardian Last Name</th>" + "<th>Admin</th>"
						+ "</tr>" + "<tr>" + "<th>" + regno + "</th>" + "<th>" + fname + "</th>" + "<th>" + mName
						+ "</th>" + "<th>" + lName + "</th>" + "<th>" + gfName + "</th>" + "<th>" + gmName + "</th>"
						+ "<th>" + glName + "</th>" + "<th>" + admin + "</th>" + "</tr></table></center>");
			
			}
			else
			{
				out.print("<center><h2>No Result Found</h2></center>");
			}
			}
			else
			{
				String query = "Select * from student_info si, guardian_info gi,Student_otherinfo soi where "
						+ "si.reg_no=gi.reg_no and si.reg_no=soi.reg_no and si.First_Name like ?";
				pstmt=con.prepareStatement(query);
				pstmt.setString(1,"%"+fName+"%");	
				System.out.println(query);
				rs=pstmt.executeQuery();
			
			
			if(rs.next())
			{	int regno=rs.getInt("Reg_no");
				String fname = rs.getString("First_Name");
				String mName = rs.getString("Middle_Name");
				String lName = rs.getString("Last_Name");
				String gfName = rs.getString("GFirst_Name");
				String gmName = rs.getString("GMiddle_Name");
				String glName = rs.getString("GLast_Name");
				String admin = rs.getString("Admin");
				out.print("<center><table border=\"1px\">" + "<tr>" + "<th>Registration Number</th>" + "<th>First Name</th>"
						+ "<th>Middle Name</th>" + "<th>Last Name</th>" + "<th>Guardian First Name</th>"
						+ "<th>Guardian Middle Name</th>" + "<th>Guardian Last Name</th>" + "<th>Admin</th>"
						+ "</tr>" + "<tr>" + "<th>" + regno + "</th>" + "<th>" + fname + "</th>" + "<th>" + mName
						+ "</th>" + "<th>" + lName + "</th>" + "<th>" + gfName + "</th>" + "<th>" + gmName + "</th>"
						+ "<th>" + glName + "</th>" + "<th>" + admin + "</th>" + "</tr>");
				while(rs.next())
				{
					 regno=rs.getInt("Reg_no");
					 fname = rs.getString("First_Name");
					 mName = rs.getString("Middle_Name");
					 lName = rs.getString("Last_Name");
					 gfName = rs.getString("GFirst_Name");
					 gmName = rs.getString("GMiddle_Name");
					 glName = rs.getString("GLast_Name");
					 admin = rs.getString("Admin");
					out.print("<tr>" + "<th>" + regno + "</th>" + "<th>" + fname + "</th>" + "<th>" + mName
							+ "</th>" + "<th>" + lName + "</th>" + "<th>" + gfName + "</th>" + "<th>" + gmName + "</th>"
							+ "<th>" + glName + "</th>" + "<th>" + admin + "</th>" + "</tr>");
				}
				
				out.print("</table></center>");
			}
			else
			{
				out.print("<center><h2>No Result Found</h2></center>");
			}
			
			}//Footer
			out.print("<center><h2>Search Again</h2></center>");
			dispatcher=req.getRequestDispatcher("view/StudentSearch.html");
			dispatcher.include(req, resp);
			dispatcher=req.getRequestDispatcher("view/Footer.html");
			dispatcher.include(req, resp);
			
			} catch (ClassNotFoundException e)//Due To Driver
		{
			e.printStackTrace();
		} catch (SQLException e) //Due To Connection
		{
			e.printStackTrace();//Error Response
		}
		finally
		{
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
			} 
			catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	}
}
