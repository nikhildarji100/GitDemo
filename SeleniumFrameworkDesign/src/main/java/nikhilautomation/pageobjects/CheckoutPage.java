package nikhilautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nikhilautomation.AbstractCompenents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{
	WebDriver driver ;
	public By selectCountry_By = By.cssSelector("input[placeholder='Select Country']");
	By autosuggestionResults_By = By.xpath("//section[contains(@class, 'ta-results')]");
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver ;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "input[placeholder='Select Country']")
	private WebElement selectCountryField ;
	
	@FindBy(xpath = "(//button[contains(@class, 'ta-item')])[2]")
	private WebElement autosuggestionOption ;
	
	@FindBy(css = ".action__submit")
	private WebElement placeOrderButton ;
	
	public void selectCountry(String country) {
		waitForElementToAppear(selectCountry_By);
		selectCountryField.sendKeys(country);
		waitForElementToAppear(autosuggestionResults_By);
		autosuggestionOption.click();
	}
	
	public ConfirmationPage submitOrder() {
		placeOrderButton.click();
		return new ConfirmationPage(driver);	
		}
	
	
	
	

}
