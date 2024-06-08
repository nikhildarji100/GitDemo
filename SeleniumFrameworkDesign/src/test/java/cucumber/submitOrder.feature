@tag
Feature: Purchase the Order from Ecommerce Website.
  I want to use this template for my feature file

  Background:
  Given I landed on Ecommerce Page
  
  @Regression
  Scenario Outline: Positive Test of submitting a order.
    Given Logged in with username <username> and password <password>
    When I add product <productName> from cart
    And Checkout <productName> and submit the order 
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation Page

    Examples: 
      | username                  | password           | productName     |
      | nikhil2001@gmail.com			|  Nikhil@123        |	ZARA COAT 3|
      