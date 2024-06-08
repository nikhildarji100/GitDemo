package nikhilautomation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import nikhilautomation.AbstractCompenents.AbstractComponent;

public class CartPage extends AbstractComponent{
	WebDriver driver ;
	By cartList = By.xpath("//*[@class='cartSection']//h3");

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver ;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@class='cartSection']//h3")
	List<WebElement> cartProductTitles ;
	
	@FindBy(css = ".totalRow button")
	WebElement checkoutButton ;
	
	public Boolean verifyIsProductDisplayed(String requiredProduct) {
		waitForElementToAppear(cartList);
		Boolean match = cartProductTitles.stream().anyMatch(product -> product.getText().equalsIgnoreCase(requiredProduct));
		return match ;
	}
	
	public CheckoutPage goToCheckout() {
		checkoutButton.click();
		return new CheckoutPage(driver);
	}
	

}
