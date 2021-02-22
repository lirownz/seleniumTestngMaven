package seleniumTestngMaven;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class standardUserPurchase {

	//defualt driver
	WebDriver driver;
	//getting the time staring test
	String timeStamp = new SimpleDateFormat("HH.mm.ss--dd.MM.yyyy").format(new java.util.Date());

	@BeforeClass
	public void setUpTest() {
		System.out.println("test started at: "+ timeStamp);
		//setting Chrome driver from Maven  
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void standardUserPurchase(){
		//entering username and pass
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");

		//pressing Login CTA
		driver.findElement(By.id("login-button")).click();

		
		System.out.println("Standard  User has been logged in!");
	}

	@Test
	public void addingItemsToCart(){
		//adding first item to cart
		driver.findElement(By.cssSelector("#inventory_container > div > div:nth-child(1) > div.pricebar > button")).click();
		System.out.println("first item has been added");
		//adding second item to cart
		driver.findElement(By.cssSelector("#inventory_container > div > div:nth-child(2) > div.pricebar > button")).click();
		System.out.println("second item has been added");

		//checking that the right amount of items added to the cart
		String numberOfItem = driver.findElement(By.cssSelector("#shopping_cart_container > a > span")).getText();
		System.out.println("Presented text:  >>  " + numberOfItem);
		String expectedText = "2";
		System.out.println("expecting text:  >>  " + expectedText);
		Assert.assertEquals(numberOfItem, expectedText);
	}

	@Test
	public void enteringCartAndCheckout() {
		//entering the cart
		driver.findElement(By.cssSelector("#shopping_cart_container > a > svg")).click();

		//pressing checkout
		driver.findElement(By.cssSelector("#cart_contents_container > div > div.cart_footer > a.btn_action.checkout_button")).click();
	}

	@Test
	public void efillingDetailsInCheckout() {
		//filling user info
		driver.findElement(By.id("first-name")).sendKeys("test");
		driver.findElement(By.id("last-name")).sendKeys("test");
		driver.findElement(By.id("postal-code")).sendKeys("123456");


		//clicking continue CTA
		driver.findElement(By.cssSelector("#checkout_info_container > div > form > div.checkout_buttons > input")).click();
	}

	@Test
	public void comparingPrices() {
		//checking total price
		String totalPrice = driver.findElement(By.cssSelector("#checkout_summary_container > div > div.summary_info > div.summary_subtotal_label")).getText();
		System.out.println("Presented text:  >>  " + totalPrice);
		String expectedPrice = "Item total: $319.98";
		System.out.println("expecting text:  >>  " + expectedPrice);
		Assert.assertEquals(totalPrice, expectedPrice);


		//checking total price with tax
		String totalPriceTax = driver.findElement(By.cssSelector("#checkout_summary_container > div > div.summary_info > div.summary_total_label")).getText();
		System.out.println("Presented text:  >>  " + totalPriceTax);
		String expectedPriceTax = "Total: $43.18";
		System.out.println("expecting text:  >>  " + expectedPriceTax);
		Assert.assertEquals(totalPriceTax, expectedPriceTax);


		//clicking finish CTA
		driver.findElement(By.cssSelector("#checkout_summary_container > div > div.summary_info > div.cart_footer > a.btn_action.cart_button")).click();

	}

	@Test
	public void verifyThankYouPage() throws IOException {
		//verify - thank you text in last page
		boolean thankYouMessage = driver.findElement(By.cssSelector("#checkout_complete_container > h2")).isDisplayed();		
		if(thankYouMessage == true) {
			System.out.println("PASS Thank You Message displyed to user");
		}
		else {
			System.out.println("No Thank You  Message found!");
		}

		//taking "Thank You" page screen shot
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"));
	}


	@AfterClass
	public void finishTest() {
		driver.close();
		driver.quit();
		System.out.println("Test Completed");
	}

}