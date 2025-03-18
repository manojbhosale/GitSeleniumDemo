package organization.division;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class ScreenerActions {
	
	public static void main(String[] args) throws InterruptedException {
		//define company name
		String companyName = "Laurus Labs";
		
		//companies array
		
		List<String> companiesList = new ArrayList<String>();
		companiesList.add("Laurus Labs");
		companiesList.add("Vimta Labs");
		companiesList.add("Neuland Labs");
		
		//setup chrome driver
		WebDriverManager.chromedriver().setup();
		
		//Create driver
		WebDriver driver = new ChromeDriver();
		
		// add implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		//maximize window
		driver.manage().window().maximize();
		
		//get site
		driver.get("https://www.screener.in/");
		
		//click login link
		driver.findElement(By.cssSelector("[href*='/login/?']")).click();
		
		//Enter UN/PWD and login
		driver.findElement(By.id("id_username")).sendKeys("manoj.bhosale31@gmail.com");
		driver.findElement(By.id("id_password")).sendKeys("Manoj!@#007");
		driver.findElement(By.cssSelector("[type*='submit']")).click();
		

		//search company
		WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@placeholder='Search for a company'])[2]")));
		
		for(String company : companiesList) {
			findPERatio(driver, company);
		}
		
		driver.quit();
		
		
	}
	
	public static void findPERatio(WebDriver driver, String companyName) throws InterruptedException {
		
				
				
				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(By.xpath("(//input[@placeholder='Search for a company'])[2]")))
				.click()
				.sendKeys(companyName)
				.build()
				.perform();
				
				Thread.sleep(2000);
				
				/*
				 * Actions action1 = new Actions(driver);
				 * action1.moveToElement(driver.findElement(By.
				 * xpath("(//input[@placeholder='Search for a company'])[2]"))) .click()
				 * .sendKeys(Keys.RETURN) .build() .perform();
				 */
//				action.sendKeys(driver.findElement(By.xpath("(//input[@placeholder='Search for a company'])[2]")), "laurus labs");
//				driver.findElement(By.xpath("(//input[@placeholder='Search for a company'])[2]")).sendKeys("laurus labs");
				WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-content.visible li:nth-child(1)")));
//				WebElement ele = driver.findElement(By.cssSelector(".dropdown-content.visible li"));
				
				driver.findElement(By.cssSelector(".dropdown-content.visible li:nth-child(1)")).click();
				////ul[@class='dropdown-content visible']
//				driver.quit();
				String peRatio = driver.findElement(By.xpath("//span[contains(text(),'Stock P/E')]//following-sibling::span//child::span")).getText();
				System.out.println("P/E ratio of "+companyName+" is "+peRatio);
				
		
		
	}

}
