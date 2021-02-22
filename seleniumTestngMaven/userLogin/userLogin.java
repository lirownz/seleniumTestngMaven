package userLogin;

import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class userLogin {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		// asking for number of time to run and open the form
		@SuppressWarnings("resource")
		Scanner myObj = new Scanner(System.in);
		System.out.println("***** Please enter the number of round for the text to run: ");
		int round = myObj.nextInt();
		System.out.println("the Test will run: " + round + " times in a row");
		Thread.sleep(1000);


		//writing Chrome logs
		//System.setProperty("webdriver.chrome.driver", "D:\\selenum-java\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//opening the Campaign form with java script executor with FOR loop
		for (int i = 1; i <= round; i++) {

			// getting the site URL
			driver.navigate().to("https://paispersonal.ewavetest.co.il/Pages/SignIn.aspx");

			//entering email to get the OTP
			driver.findElement(By.id("cEmail")).sendKeys("orit.adin@pais.co.il");
			Thread.sleep(1000);

			//clicking CTA enter
			driver.findElement(By.id("bEnter")).click();
			Thread.sleep(1000);

			//clicking CTA sending the email to get the OTP
			driver.findElement(By.id("bSend")).click();
			Thread.sleep(1000);

			//clicking CTA sending the email to get the OTP
			driver.findElement(By.id("cImut")).sendKeys("111111");
			Thread.sleep(1000);

			//clicking CTA enter the site
			driver.findElement(By.id("bLogin")).click();
			Thread.sleep(3000);

			//getting the username text to compare
			WebElement textPresentOnUI = driver.findElement(By.xpath("//*[@id=\"divLogged\"]/div/span[1]/a[2]/strong"));
			String siteUserName = textPresentOnUI.getText();
			System.out.println("Presented text:  >>  " + siteUserName);
			Thread.sleep(1000);

			//*** text wanted to be compare to
			String expectedText = "×�×•×¨×™×ª ×¢×“×™×Ÿ";
			System.out.println("expecting text:  >>  " + expectedText);
			Thread.sleep(1000);

			//***** Using Collector and Locale  for non English Text encode (change text to English encode)
			Collator collator = Collator.getInstance(Locale.ENGLISH);
			collator.setStrength(Collator.PRIMARY);

			//***** Comparing between the two Strings
			if (collator.compare(siteUserName, expectedText) == 0) {
				System.out.println("##  PASS - Text is Equal  ##");
			} else {
				System.out.println("##  FAIL - Text is NOT Equal  ##");
			}
			Thread.sleep(1000);
			
			// CTO log out
			driver.findElement(By.xpath("//*[@id=\"divLogged\"]/div/a[3]")).click();
			Thread.sleep(1000);

			System.out.println("##  ending test - number: "+i);
			driver.navigate().refresh();
			Thread.sleep(1000);


		}

		//***** closing browser *****
		driver.close();

	}

}
