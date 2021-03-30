package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseUrl;
	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

//	@Test
//	public void getLoginPage() {
//		driver.get("http://localhost:" + this.port + "/login");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}

	private void login(String username, String password){
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}
	private void signup (String firstName, String lastName, String username, String password) {
		driver.get(baseUrl + "/signup");
		SignupPage signUpPage = new SignupPage(driver);
		signUpPage.signup(firstName, lastName, username, password);
	}
	@Test
	@Order(1)
	public void testUnauthorizedAccessRestrictions(){
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testSignupLogin() throws InterruptedException {
		this.signup("Stephanie", "Hallak", "stephtest", "password");
		Thread.sleep(3000);
		this.login("stephtest", "password");
		Thread.sleep(3000);
		HomePage homePage = new HomePage(driver);
		Thread.sleep(5000);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();
		Thread.sleep(3000);
		Assertions.assertNotEquals("Home", driver.getTitle());

	}

	@Test
	@Order(3)
	public void testCreateNote() throws InterruptedException {
		this.signup("Stephanie", "Hallak", "stephtest1", "password");
		Thread.sleep(3000);
		this.login("stephtest1", "password");
		Thread.sleep(3000);
		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.createNote("test title", "test description", driver);
		Thread.sleep(3000);
		driver.get(baseUrl + "/home");
		homePage.noteNavigation.click();
		Thread.sleep(3000);
		Assertions.assertTrue(homePage.getNoteContent().contains("test title"));
	}

	@Test
	@Order(4)
	public void testEditNote() throws InterruptedException {
//		this.signup("Stephanie", "Hallak", "stephtest", "password");
//		Thread.sleep(3000);
		this.login("stephtest1", "password");
		Thread.sleep(3000);
		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.editNote("update title", "update description", driver);
		driver.get(baseUrl + "/home");
		homePage.noteNavigation.click();
		Thread.sleep(3000);
		Assertions.assertTrue(homePage.getNoteContent().contains("update title"));
	}

	@Test
	@Order(5)
	public void testDeleteANote() throws InterruptedException {
//		this.signup("Stephanie", "Hallak", "stephtest1", "password");
//		Thread.sleep(3000);
		this.login("stephtest1", "password");
		Thread.sleep(3000);
		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.deleteNote(driver);
		driver.get(baseUrl + "/home");
		homePage.noteNavigation.click();
		Thread.sleep(3000);
		Assertions.assertTrue(homePage.getNoteContent().isBlank());
	}

	@Test
	@Order(6)
	public void testCreatesACredential() throws InterruptedException {
//		this.signup("Stephanie", "Hallak", "stephtest1", "password");
//		Thread.sleep(3000);
		this.login("stephtest1", "password");
		Thread.sleep(3000);

		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createCredential("url test","steph","1234",driver);
		driver.get(baseUrl + "/home");

		Assertions.assertFalse(homePage.getCredentialPassword().contains("1234"));
		Assertions.assertTrue(homePage.getCredentialUrl().contains("url test"));
	}

	@Test
	@Order(7)
	public void testEditACredential() throws InterruptedException {
		this.login("stephtest1", "password");
		Thread.sleep(3000);

		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);


		var decryptedPassword = homePage.editCredential("url test edit","stephedit" ,"abcd", driver);
		driver.get(baseUrl + "/home");


		Assertions.assertTrue(homePage.getCredentialUrl().contains("url test edit"));
		Assertions.assertTrue(decryptedPassword.equals("1234"));
	}

	@Test
	@Order(8)
	public void testDeleteCredential() throws InterruptedException {
		this.login("stephtest1", "password");
		Thread.sleep(3000);

		driver.get(baseUrl + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.deleteCredential(driver);
		driver.get(baseUrl + "/home");

		System.out.println(homePage.getCredentialUrl());
		homePage.credentialNavigation.click();
		Thread.sleep(3000);

		Assertions.assertFalse(homePage.getCredentialUrl().contains("url test edit"));
	}
}
