import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class LoginRegisterServletFunctionalTest {
	// declare Selenium WebDriver
	private WebDriver webDriver;
	String domain = "http://localhost:8090/FinanceTrackerWebsite";
	
	String emailLog= "FTestLog@gmail.com";
	String passwordLog= "password";

	String emailReg = "FTest@gmail.com";
	String passwordReg = "password";
	String name = "f";
	String surname = "test";
	String income = "1000";

	@Test
	public void RegisterUser() {

		webDriver.navigate().to(domain + "/login.jsp");
		webDriver.findElement(By.name("toRegister")).click();

		webDriver.findElement(By.name("email")).sendKeys(emailReg);
		webDriver.findElement(By.name("password")).sendKeys(passwordReg);
		webDriver.findElement(By.name("name")).sendKeys(name);
		webDriver.findElement(By.name("surname")).sendKeys(surname);
		webDriver.findElement(By.name("income")).sendKeys(income);
		
		webDriver.findElement(By.name("register")).click();
		
		webDriver.switchTo().alert().accept();

		Assert.assertEquals(webDriver.getTitle(), "Login");

	}

	@Test
	public void LoginUser() {

		webDriver.navigate().to(domain + "/login.jsp");
		webDriver.findElement(By.name("email")).sendKeys(emailLog);
		webDriver.findElement(By.name("password")).sendKeys(passwordLog);
		

		webDriver.findElement(By.name("login")).click();

		Assert.assertEquals(webDriver.getTitle(), "Finance");

	}

	@Test

	public void Logout() {

		webDriver.navigate().to(domain + "/ReturnFinanceServlet/dashboard");
		Assert.assertEquals(webDriver.getTitle(), "Finance");
		webDriver.findElement(By.name("profile")).click();

		webDriver.findElement(By.name("logout")).click();

		Assert.assertEquals(webDriver.getTitle(), "Login");

	}

	@BeforeTest
	public void beforeTest() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker", "root","password");

	
		PreparedStatement createLogin = con.prepareStatement("insert into USER values(?,?,?)");
		PreparedStatement deleteRegister = con.prepareStatement("delete from user where email = ?;");

		createLogin.setString(1, null);
		createLogin.setString(2, emailLog);
		createLogin.setString(3, passwordLog);
		deleteRegister.setString(1, emailReg);

		// Step 6: perform the query on the database using the prepared statement
		createLogin.executeUpdate();
		deleteRegister.executeUpdate();
		// Setting system properties of ChromeDriver
		// to amend directory path base on your local file path
		String chromeDriverDir = "C:\\Program Files\\Google\\Chrome\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", chromeDriverDir);

		// initialize FirefoxDriver at the start of test
		webDriver = new ChromeDriver();
	}

	@AfterTest
	public void afterTest() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/financetracker", "root","password");
		PreparedStatement deleteBoth = con.prepareStatement("delete from user where email = ? or email = ?;");

		deleteBoth.setString(1, emailLog);
		deleteBoth.setString(2, emailReg);
		
		// Step 6: perform the query on the database using the prepared statement
		deleteBoth.executeUpdate();
		webDriver.quit();

		// Quit the ChromeDriver and close all associated window at the end of test
	}

}
