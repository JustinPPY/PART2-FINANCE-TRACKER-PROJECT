import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */

/**
 * @author justi
 *
 */
class LoginRegisterServletTest {
	
	LoginRegisterServlet TestClass = new LoginRegisterServlet();
	StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    
	static String email = "LogUnitTest@gmail.com";
	static String password = "password";
	
	static String emailReg = "RegUnitTest@gmail.com";
	String passwordReg = "password";
	String name = "Reg";
	String surname = "Test";
	String income =  "1000";

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker", "root","password");

	
		PreparedStatement createLogin = con.prepareStatement("insert into USER values(?,?,?)");
		PreparedStatement deleteRegister = con.prepareStatement("delete from user where email = ?;");

		createLogin.setString(1, null);
		createLogin.setString(2, email);
		createLogin.setString(3, password);
		deleteRegister.setString(1, emailReg);

		// Step 6: perform the query on the database using the prepared statement
		createLogin.executeUpdate();
		deleteRegister.executeUpdate();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker", "root","password");
		PreparedStatement deleteBoth = con.prepareStatement("delete from user where email = ? or email = ?;");

		deleteBoth.setString(1, email);
		deleteBoth.setString(2, emailReg);
		
		// Step 6: perform the query on the database using the prepared statement
		deleteBoth.executeUpdate();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	     writer.flush();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void WrongURL() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);
	     
	     
	     when(request.getServletPath()).thenReturn("/WrongURL");
	     when(response.getWriter()).thenReturn(writer);
	     
	     TestClass.doGet(request, response);
	     
	     assertEquals("Wrong URL",stringWriter.toString());
	     

	}
	@Test
	void LoginWrongCredentials() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);
	     
	     when(request.getServletPath()).thenReturn("/LoginRegisterServlet");
	     
	     when(request.getParameter("login")).thenReturn("login");
	     when(request.getParameter("password")).thenReturn(password);
	     when(request.getParameter("email")).thenReturn(email);

	     //Using Mockito ArgumentCaptor for capturing argument passed into method 
	     ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
	     
	     TestClass.doGet(request, response);
	     
	     verify(request).getParameter("login");
	     verify(response).sendRedirect(captor.capture());
	     assertEquals(request.getContextPath()+"/ReturnFinanceServlet/dashboard", captor.getValue());
	}
	
	@Test
	void LoginCorrectCredentials() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);

	     when(request.getServletPath()).thenReturn("/LoginRegisterServlet");
	     
	     when(response.getWriter()).thenReturn(writer);

	     when(request.getParameter("login")).thenReturn("login");
	     when(request.getParameter("password")).thenReturn("");
	     when(request.getParameter("email")).thenReturn("");

	     
	     TestClass.doGet(request, response);
	     
	     verify(request).getParameter("login");
	     assertTrue(stringWriter.toString().contains("Wrong email or password"));

	}
	@Test
	void RegisterNewUser() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);

	     when(request.getServletPath()).thenReturn("/LoginRegisterServlet");
	     
	     when(response.getWriter()).thenReturn(writer);

	     when(request.getParameter("register")).thenReturn("register");
	     
	     when(request.getParameter("email")).thenReturn(emailReg);
	     when(request.getParameter("password")).thenReturn(passwordReg);
	     when(request.getParameter("name")).thenReturn(name);
	     when(request.getParameter("surname")).thenReturn(surname);
	     when(request.getParameter("income")).thenReturn(income);

	     when(response.getWriter()).thenReturn(writer);

	     //Using Mockito ArgumentCaptor to capture argument passed into method 
	     
	     TestClass.doGet(request, response);
	     
	     verify(request).getParameter("register");
	     assertTrue(stringWriter.toString().contains("User is now registered"));

	}
	@Test
	void RegisterUsedEmail() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);

	     when(request.getServletPath()).thenReturn("/LoginRegisterServlet");
	     
	     when(response.getWriter()).thenReturn(writer);

	     when(request.getParameter("register")).thenReturn("register");
	     
	     when(request.getParameter("email")).thenReturn(email);
	     when(request.getParameter("password")).thenReturn(passwordReg);
	     when(request.getParameter("name")).thenReturn(name);
	     when(request.getParameter("surname")).thenReturn(surname);
	     when(request.getParameter("income")).thenReturn(income);

	     when(response.getWriter()).thenReturn(writer);

	     //Using Mockito ArgumentCaptor to capture argument passed into method 
	     
	     TestClass.doGet(request, response);
	     
	     verify(request).getParameter("register");
	     assertTrue(stringWriter.toString().contains("Email already registered"));

	}
	
	
	@Test
	void Logout() throws ServletException, IOException {
		 HttpServletRequest request = mock(HttpServletRequest.class);       
	     HttpServletResponse response = mock(HttpServletResponse.class);

	     when(request.getServletPath()).thenReturn("/LoginRegisterServlet/logout");

	     
	     //Using Mockito ArgumentCaptor to capture argument passed into method 
	     ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

	     TestClass.doGet(request, response);
	     
	     verify(response).sendRedirect(captor.capture());
	     assertEquals(request.getContextPath()+"/FinanceTrackerWebsite/login.jsp", captor.getValue());
	     
	}

}
