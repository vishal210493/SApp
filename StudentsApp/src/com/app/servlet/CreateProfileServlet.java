package com.app.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class CreateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		RequestDispatcher dispatcher = null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		if (session == null) {
			out.print("<center><h2>Invalid Session</h2></center>");
			dispatcher = req.getRequestDispatcher("view/Login.html");
			dispatcher.include(req, resp);
		} else {
			dispatcher = req.getRequestDispatcher("view/Header.html");
			dispatcher.include(req, resp);
			dispatcher = req.getRequestDispatcher("view/CreateProfile.html");
			dispatcher.include(req, resp);
			dispatcher = req.getRequestDispatcher("view/Footer.html");
			dispatcher.include(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		RequestDispatcher dispatcher = null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		Connection con=null;
		Statement stmt=null;
		PreparedStatement pstmt1=null,pstmt2=null,pstmt3=null;
		ResultSet rs=null;
		if (session == null) {
			out.print("<center><h2>Invalid Session</h2></center>");
			dispatcher = req.getRequestDispatcher("view/Login.html");
			dispatcher.include(req, resp);
		} else {
			String fName = req.getParameter("fname");
			String mName = req.getParameter("mname");
			String lName = req.getParameter("lname");
			String gfName = req.getParameter("gfname");
			String gmName = req.getParameter("gmname");
			String glName = req.getParameter("glname");
			String password = req.getParameter("password");
			String admin = req.getParameter("admin");
			
			
			try {
				//1.Load the Driver
				Class.forName("com.mysql.jdbc.Driver");
				//2.Get the Connection via Driver
				String dbUrl="jdbc:mysql://localhost:3306/becm67?user=root&password=root";
				con=DriverManager.getConnection(dbUrl);
				con.setAutoCommit(false);
				//3.Issue SQL Query
				//Condition 1st
				String query = "Select max(reg_no) from student_info";
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				int maxregno=0;
				if(rs.next())
				{
				maxregno=rs.getInt(1);	
				}
				//Insert Into Students Info
				String query1="Insert into student_info values(?,?,?,?)";
				pstmt1=con.prepareStatement(query1);
				pstmt1.setInt(1, maxregno+1);
				pstmt1.setString(2, fName);
				pstmt1.setString(3, mName);
				pstmt1.setString(4, lName);
				
				int status1=pstmt1.executeUpdate();
				//Insert Into Guardian_info
				String query2="Insert into guardian_info values(?,?,?,?)";
				pstmt2=con.prepareStatement(query2);
				pstmt2.setInt(1, maxregno+1);
				pstmt2.setString(2, gfName);
				pstmt2.setString(3, gmName);
				pstmt2.setString(4, glName);
				int status2=pstmt2.executeUpdate();
				//Insert into Student other Info
				String query3="Insert into student_otherinfo values(?,?,?)";
				pstmt3=con.prepareStatement(query3);
				pstmt3.setInt(1, maxregno+1);
				pstmt3.setString(2, password);
				pstmt3.setString(3, admin);
				int status3=pstmt3.executeUpdate();
				System.out.println(maxregno);
				if((status1==status2)&&(status2==status3)&&(status1==1))
				{
					con.commit();
					dispatcher = req.getRequestDispatcher("view/Header.html");
					dispatcher.include(req, resp);
					out.print("<center><h2>Kindly Note Down Your Registration Number and Password</h2>"
							+ "<h2>Registration Number : "+(maxregno+1)+"</h2>"
									+ "<h2>Password : "+password+"</h2></center>");
					dispatcher = req.getRequestDispatcher("view/Footer.html");
					dispatcher.include(req, resp);
				}
			
			} 
			catch (Exception e) {
				dispatcher = req.getRequestDispatcher("view/Header.html");
				dispatcher.include(req, resp);
				out.print("<center><h2>Some Error Occured...</h2>"
						+ "<h2>Please Try Again After Some Time...</h2>"
						+ "<h3>or</h3>"
						+ "<h2>Contact Admin if Problem Occurs Continuously...</h2></center>");
				dispatcher = req.getRequestDispatcher("view/Footer.html");
				dispatcher.include(req, resp);
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
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
					if (pstmt1 != null) {
						pstmt1.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (pstmt2 != null) {
						pstmt2.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}try {
					if (pstmt3 != null) {
						pstmt3.close();
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
				try {
					if(stmt!=null)
					{
						stmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
