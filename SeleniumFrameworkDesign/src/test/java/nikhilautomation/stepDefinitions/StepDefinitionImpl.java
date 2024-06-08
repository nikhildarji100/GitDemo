package nikhilautomation.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nikhilautomation.TestComponents.BaseTests;
import nikhilautomation.pageobjects.CartPage;
import nikhilautomation.pageobjects.CheckoutPage;
import nikhilautomation.pageobjects.ConfirmationPage;
import nikhilautomation.pageobjects.LandingPage;
import nikhilautomation.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTests{
	public LandingPage landingPage ;
	public ProductCatalogue productCatalogue ;
	public ConfirmationPage confirmationPage ;
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}
//		since this is dynamic hence we need to use ^ and $ symbols at start and end 
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_In_Username_And_Password(String username, String password ) {
		productCatalogue = landingPage.loginApplication(username, password);
		}

	@When("^I add product (.+) from cart$")
	public void I_add_Product_To_cart(String product) {
		List<WebElement>products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(product);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
		CartPage cartPage = productCatalogue.goToCartPage();
		productCatalogue.waitForElementToAppear(By.xpath("//*[@class='cartSection']//h3"));
		
		Boolean isProductPresent = cartPage.verifyIsProductDisplayed(productName) ;
		Assert.assertTrue(isProductPresent, "Selected product is not added in the cart.");
	
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("India");
		
		Assert.assertEquals(driver.findElement(checkoutPage.selectCountry_By).getAttribute("value").trim(), "India", "Selected country is not displayed");
		confirmationPage = checkoutPage.submitOrder();
	}
// (.+) is written when data is coming from Examples section.
//	Here data is coming from the Statement hence we use{message}
	@Then("{string} message is displayed on Confirmation Page")
	public void message_displayed_confirmationPage (String string) {
		Assert.assertTrue(confirmationPage.getOrderConfirmationMessage().equalsIgnoreCase(string), "Required order confirm message is not displayed.");
		driver.close();
	}
	
	
	@Then("{string} message should be displayed")
	public void message_displayed_for_Login_With_Invalid_Credentials (String string) throws InterruptedException {
		Assert.assertEquals(landingPage.getErrorMessage(), string, "Required error message is not displayed.");
		driver.close();
	}
}
