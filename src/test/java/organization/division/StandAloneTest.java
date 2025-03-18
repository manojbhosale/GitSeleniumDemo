package organization.division;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		// hit the URL
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		
		// get username field
		driver.findElement(By.id("userEmail")).sendKeys("manoj.bhosale31@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test!@#007");
		driver.findElement(By.id("login")).click();

		//wait till products are loaded		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		// get product list
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		// Check title of elements
		String productName = "ADIDAS ORIGINAL";
		WebElement product = products.stream()
				.filter(e -> e.findElement(By.tagName("b")).getText().equalsIgnoreCase(productName)).findFirst()
				.orElse(null);
		System.out.println(product.findElement(By.tagName("b")).getText());

		//click add to cart
		//product.findElement(By.xpath("//button[2]")).click();
		product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		//check loading get invisible and check if the toast container is displayed
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		
		//click on cart button
		//driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='cart']")));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//find the elements on cart page
		List<WebElement> cartElements = driver.findElements(By.cssSelector(".cart .cartSection h3"));
		//List<WebElement> cartElements = driver.findElements(By.xpath("//*[@class=\"cartSection\"]/h3"));
		
		//check of the ADIDAS is part of cart
		Boolean bool = cartElements.stream().anyMatch(e -> e.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(bool);
		
		//click on on checkout
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//find country dropdown
		driver.findElement(By.cssSelector("[placeholder*='Select Country']")).sendKeys("INDIA");
		
		//Using action
		Actions a = new Actions(driver);
		//a.sendKeys(driver.findElement(By.cssSelector("[placeholder*='Select Country']")),"india").build().perform();
		
		//get dropdown elements
		List<WebElement> countries = driver.findElements(By.cssSelector(".ta-results .ta-item"));
		//XPATH: (//button[contains(@class,'ta-item')])[2]
		WebElement countryEle =	 countries.stream().filter(e -> e.findElement(By.cssSelector(".ng-star-inserted")).getText().equalsIgnoreCase("INDIA")).findFirst().orElse(null);
		countryEle.click();
		
		//Click place order
		driver.findElement(By.cssSelector(".actions .action__submit")).click();
		
		//Check if order placed successfully
		Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Thankyou for the order."));
		
		
		
	}

}
