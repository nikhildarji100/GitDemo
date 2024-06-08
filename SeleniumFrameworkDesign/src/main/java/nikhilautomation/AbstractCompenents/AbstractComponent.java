package nikhilautomation.AbstractCompenents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import nikhilautomation.pageobjects.CartPage;
import nikhilautomation.pageobjects.OrdersPage;

public class AbstractComponent {
	WebDriver driver ;
	public By messageBy = By.id("toast-container");
	public By loading = By.cssSelector(".ng-animating");
	
	
	@FindBy(css = "button[routerlink='/dashboard/cart']")
	WebElement cartButton;
	
	@FindBy(css = "button[routerlink = '/dashboard/myorders']")
	WebElement ordersButton ;
	public AbstractComponent(WebDriver driver) {
		this.driver = driver ;
	}

	public void waitForElementToAppear(By findBy) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebelementToAppear(WebElement element) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToDisappear(By findBy) {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(findBy)));
	}
	
	public CartPage goToCartPage() {
		cartButton.click();
		return new CartPage(driver);
	}
	
	public OrdersPage goToOrdersPage() {
		ordersButton.click();
		return new OrdersPage(driver);
	}
	
	
	
}
