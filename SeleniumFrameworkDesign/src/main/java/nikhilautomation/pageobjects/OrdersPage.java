package nikhilautomation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import nikhilautomation.AbstractCompenents.AbstractComponent;

public class OrdersPage extends AbstractComponent{
	WebDriver driver ;
	By productList = By.xpath("//tr/td[2]");

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver ;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//tr/td[2]")
	List<WebElement> orderproductnames ;
	
	@FindBy(css = ".totalRow button")
	WebElement checkoutButton ;
	
	public Boolean verifyIsProductDisplayed(String requiredProduct) {
		waitForElementToAppear(productList);
		Boolean match = orderproductnames.stream().anyMatch(product -> product.getText().equalsIgnoreCase(requiredProduct));
		return match ;
	}
	
	
}
