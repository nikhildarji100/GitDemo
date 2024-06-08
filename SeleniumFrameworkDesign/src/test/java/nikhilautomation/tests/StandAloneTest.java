package nikhilautomation.tests;

import java.util.List;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;




public class StandAloneTest {
	public WebDriver driver;
	@Test
	public void submitOrder() throws IOException, InterruptedException{
		String requiredProduct  = "ZARA COAT 3";
		String country = "India";
		String expectedOrderConfirmMessage = "Thankyou for the order.";
		String expectedMessage_AddedProduct = "Product Added To Cart";
		
		//WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		driver.findElement(By.id("userEmail")).sendKeys("nikhil2001@gmail.com");
		 driver.findElement(By.id("userPassword")).sendKeys("Nikhil@123");
		 driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".mb-3"))));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("toast-container"))));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement desiredProduct = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals(requiredProduct)).findFirst().orElse(null);
		desiredProduct.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("toast-container"))));
		Assert.assertTrue(driver.findElement(By.id("toast-container")).isDisplayed(), "Success message is not displayed.");
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), expectedMessage_AddedProduct, "Required message is not displayed.");
		driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='cartSection']//h3"))));
		List<WebElement> cartProducts = driver.findElements(By.xpath("//*[@class='cartSection']//h3"));
		Boolean isProductPresent = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(requiredProduct));
		Assert.assertTrue(isProductPresent, "Selected product is not added in the cart.");
//	65	Clicking on Checkout button.
		driver.findElement(By.cssSelector(".totalRow button")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Select Country']"))));
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys(country);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[contains(@class, 'ta-results')]")));
		driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("input[placeholder='Select Country']")).getAttribute("value").trim(), country, "Selected country is not displayed");
//		Clicking on Place Order button.
		driver.findElement(By.cssSelector(".action__submit")).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".hero-primary")).getText().equalsIgnoreCase(expectedOrderConfirmMessage), "Required order confirm message is not displayed.");
		driver.quit();
		System.out.println("The tests are passed.");
	}

}
