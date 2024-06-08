package nikhilautomation.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import nikhilautomation.TestComponents.BaseTests;
import nikhilautomation.TestComponents.Retry;
import nikhilautomation.pageobjects.CartPage;
import nikhilautomation.pageobjects.CheckoutPage;
import nikhilautomation.pageobjects.ConfirmationPage;
import nikhilautomation.pageobjects.OrdersPage;
import nikhilautomation.pageobjects.ProductCatalogue;



public class SubmitOrderTest extends BaseTests{
//	String requiredProduct  = "ZARA COAT 3";
	@Test(dataProvider = "getData", retryAnalyzer = Retry.class)
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException{
		String country = "India";
		String expectedOrderConfirmMessage = "Thankyou for the order.";
		String expectedMessage_AddedProduct = "Product Added To Cart";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		landingPage.waitForElementToDisappear(landingPage.messageBy);
		productCatalogue.addProductToCart(input.get("requiredProduct"));
		Assert.assertTrue(driver.findElement(By.id("toast-container")).isDisplayed(), "Success message is not displayed.");
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), expectedMessage_AddedProduct, "Required message is not displayed.");
		
		CartPage cartPage = productCatalogue.goToCartPage();
		productCatalogue.waitForElementToAppear(By.xpath("//*[@class='cartSection']//h3"));
		
		Boolean isProductPresent = cartPage.verifyIsProductDisplayed(input.get("requiredProduct")) ;
		Assert.assertTrue(isProductPresent, "Selected product is not added in the cart.");
	
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(country);
		
		Assert.assertEquals(driver.findElement(checkoutPage.selectCountry_By).getAttribute("value").trim(), country, "Selected country is not displayed");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		Assert.assertTrue(confirmationPage.getOrderConfirmationMessage().equalsIgnoreCase(expectedOrderConfirmMessage), "Required order confirm message is not displayed.");
	}
	
//	To verify ZARA COAT 3 is displayed in orders page 
	@Test(dependsOnMethods = {"submitOrder"}, dataProvider = "getData")
	public void verifyOrderHistory(HashMap<String, String> input) {
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.verifyIsProductDisplayed(input.get("requiredProduct")), "Ordered product is not displayed in the Orders Page.");
	}
	
	@DataProvider
	public Object[][] getData() throws IOException{
//		return new Object [][] {{"nikhil2001@gmail.com", "Nikhil@123", "ZARA COAT 3"}, {"happy22@gmail.com", "Nikhil@1234", "ADIDAS ORIGINAL"}};
		List<HashMap<String, String>> data = getJSonDataToMap(System.getProperty("user.dir")+"//src//test//java//nikhilautomation//data//PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}

	
}
