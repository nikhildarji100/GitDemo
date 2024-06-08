
@tag
Feature: Error Validation Test
  Need to validate the error when user tries to login wit incorrect credentials.

  
  @ErrorValidation
  Scenario Outline: 
    Given I landed on Ecommerce Page
    When Logged in with username <username> and password <password>
    Then "Incorrect email or password." message should be displayed
    Examples: 
     | username                  | password             | 
     | nikhil2001@gmail.com		   |  Nikhil@13           |	
