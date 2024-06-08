package nikhilautomation.tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import nikhilautomation.TestComponents.BaseTests;
import nikhilautomation.TestComponents.Retry;
import nikhilautomation.pageobjects.CartPage;
import nikhilautomation.pageobjects.CheckoutPage;
import nikhilautomation.pageobjects.ConfirmationPage;
import nikhilautomation.pageobjects.ProductCatalogue;



public class ErrorValidation extends BaseTests{
	@Test
	public void Login_Error_Validation() throws IOException, InterruptedException{
//		checking
		String expectedErrorMessage = "Incorrect email or password.";
		landingPage.loginApplication("nikhil2101@gmail.com", "Nikhil@123");
		Assert.assertEquals(landingPage.getErrorMessage(), expectedErrorMessage, "Required error message is not displayed.");
	}
	
	@Test(retryAnalyzer = Retry.class)
	public void Product_Error_Validation() throws IOException, InterruptedException{
		String requiredProduct  = "ZARA COAT 33";
		String country = "India";
		String expectedOrderConfirmMessage = "Thankyou for the order.";
		String expectedMessage_AddedProduct = "Product Added To Cart";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("nikhil2001@gmail.com", "Nikhil@123");
		productCatalogue.addProductToCart(requiredProduct);
		Assert.assertTrue(driver.findElement(By.id("toast-container")).isDisplayed(), "Success message is not displayed.");
		Assert.assertEquals(driver.findElement(By.id("toast-container")).getText(), expectedMessage_AddedProduct, "Required message is not displayed.");
		
		CartPage cartPage = productCatalogue.goToCartPage();
		productCatalogue.waitForElementToAppear(By.xpath("//*[@class='cartSection']//h3"));
		
		Boolean isProductPresent = cartPage.verifyIsProductDisplayed(requiredProduct) ;
		Assert.assertFalse(isProductPresent, "Selected product is not added in the cart.");
	
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(country);
		
		Assert.assertEquals(driver.findElement(checkoutPage.selectCountry_By).getAttribute("value").trim(), country, "Selected country is not displayed");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		Assert.assertTrue(confirmationPage.getOrderConfirmationMessage().equalsIgnoreCase(expectedOrderConfirmMessage), "Required order confirm message is not displayed.");
		
	}

}
