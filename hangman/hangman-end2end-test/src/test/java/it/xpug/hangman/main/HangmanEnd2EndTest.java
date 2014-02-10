package it.xpug.hangman.main;


import it.xpug.generic.web.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

public class HangmanEnd2EndTest {

	private static final int APPLICATION_PORT = 8123;
	protected static ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet());
	protected static WebDriver driver = new FirefoxDriver();

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(APPLICATION_PORT, "../hangman-server/src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	@AfterClass
	public static void closeDriver() throws Exception {
		driver.close();
	}
}
