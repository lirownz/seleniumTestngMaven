package seleniumTestngMaven;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginUserTesting {

	//defualt driver
	WebDriver driver;
	//getting the time staring test.
	String timeStamp = new SimpleDateFormat("HH.mm.ss--dd.MM.yyyy").format(new java.util.Date());

	@BeforeClass
	public void setUpTest() {
		System.out.println("test started at: "+ timeStamp);
		//setting Chrome driver from Maven  
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void lockedOutUserLoginTest(){
		//entering username and pass
		driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");

		//pressing Login CTA
		driver.findElement(By.id("login-button")).click();

		//checking displayed error message 
		boolean errMessage = driver.findElement(By.cssSelector("#login_button_container > div > form > h3")).isDisplayed();
		String errMessageText = driver.findElement(By.cssSelector("#login_button_container > div > form > h3")).getText();		
		String expectedMessage = "Epic sadface: Sorry, this user has been locked out.";	
		Assert.assertEquals(errMessageText,expectedMessage);
		
		System.out.println("Locked Out User has NOT been logged in!");
	}

	@Test
	public void performanceGlitchUserLoginTest(){

		//entering username and pass
		driver.findElement(By.id("user-name")).clear();
		driver.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("secret_sauce");

		//pressing Login CTA
		driver.findElement(By.id("login-button")).click();
		
		System.out.println("Performance Glitch User has been logged in!");
		
		driver.findElement(By.cssSelector("#menu_button_container > div > div:nth-child(3) > div > button")).click();
		driver.findElement(By.id("logout_sidebar_link")).click();
	}

	@Test
	public void standardUserLoginTest(){

		//entering username and pass
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");


		//pressing Login CTA
		driver.findElement(By.id("login-button")).click();
		
		System.out.println("Standard  User has been logged in!");
	}


	@AfterClass
	public void finishTest() {
		driver.close();
		driver.quit();
		System.out.println("Test Completed");
	}
}
