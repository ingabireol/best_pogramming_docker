package servlet;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Servlet implementation class MyServlet
 */
//@WebServlet("/new")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	private static final Logger logger = LogManager.getLogger(MyServlet.class);
	
    public MyServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String name = "Ingabire Olivier";
		logger.info("Entered get Method");
		String theId = request.getParameter("id");
		if(theId == null || !theId.matches("\\d+")) {
			response.sendRedirect("studentForm.html");
			logger.error("Id is invalid");
			response.getWriter().print("<h1>Id Invalid</h1>");
			return;
		}		
		Integer id = Integer.parseInt(theId);
		try {
			String db_url = "jdbc:postgresql://localhost:5432/auca";
			String username = "postgres";
			String password = "078868";	
	
			Class.forName("org.postgresql.Driver");
			logger.info("Loaded postgresql driver");
			Connection con = DriverManager.getConnection(db_url,username,password);
			
			PreparedStatement pst =con.prepareStatement("select * from students where id = ?");
			pst.setInt(1, id);
//			pst.setString(2, name);
			
//			int rowsAffected = pst.ex();
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				String lname = rs.getString("lname");
//				int id = rs.getInt(1);
				logger.info("The name is "+lname+" and Id is "+id);
				response.getWriter().print("<h1>The name is "+lname+" and Id is "+id+"</h1>");
			}
			else {
				logger.info("Id entered does not exist");
				response.getWriter().print("<h1>Id does not exist</h1>");
			}
			
		} catch (SQLException e) {
			logger.error("SQL exceptions caought : connection to db failed: "+e);
			e.printStackTrace(response.getWriter());
		} catch (ClassNotFoundException e) {
			logger.error("Some class was not found"+e);
			response.getWriter().print(e);
//			e.printStackTrace(response.getWriter());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Entered Post Method");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		if(!id.matches("\\d+")) {
			logger.warn("Entered an invalid id");
			response.getWriter().print("<h1>Id must be an integer</h1>");
			response.sendRedirect("studentForm.html");
//			response.sendRedirect(request.getContextPath());
			return;
		}
		else {
			Integer theId = Integer.parseInt(id);
			try {
				String db_url = "jdbc:postgresql://localhost:5432/auca";
				String username = "postgres";
				String password = "078868";			
				Class.forName("org.postgresql.Driver");
				Connection con = DriverManager.getConnection(db_url,username,password);
				PreparedStatement pst =con.prepareStatement("insert into students(id,lname) values(?,?)");
				pst.setInt(1, theId);
				pst.setString(2, name);
				int rowsAffected = pst.executeUpdate();
				if(rowsAffected !=0) {		
					logger.info("Inserted data in databse");
					response.getWriter().print("<h1>Inserted success</h1>");
				}
				else {
					logger.error("Insert in database falied");
					response.getWriter().print("<h1>Inserted Failed</h1>");
				}
								
			} catch (SQLException e) {
				logger.error("SQL exceptions caought : connection to db failed: "+e);
//				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.error("Some class was not found"+e);
//				e.printStackTrace();
			}
		}
		
	}

}
