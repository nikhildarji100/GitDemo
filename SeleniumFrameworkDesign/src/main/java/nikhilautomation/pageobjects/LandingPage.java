package nikhilautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nikhilautomation.AbstractCompenents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	WebDriver driver ;
	public LandingPage(WebDriver driver ) {
		super(driver);
		this.driver = driver ;
		PageFactory.initElements(driver, this);
	}
	
//	WebElement userEmail = driver.findElement(By.id("userEmail"));
//	driver.findElement(By.id("userPassword"))
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(id="toast-container")
	WebElement message;
	
	@FindBy(id = "toast-container")
	WebElement error ;
	
	public ProductCatalogue loginApplication(String email, String password){
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		return new ProductCatalogue(driver);
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage () throws InterruptedException {
		waitForWebelementToAppear(error);
		Thread.sleep(1000);
		return error.getText();
	}
	
}
