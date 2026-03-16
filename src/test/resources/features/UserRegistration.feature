Feature: User Registration API
  As a new customer
  I want to register an account
  So that I can log in and buy products

  Scenario: Successfully register a new user
    Given the user provides a valid name "Rahul", email "rahul@test.com", and password "Secure123!"
    When the user sends a POST request to register
    Then the API should respond with status code 201
    And the response body should contain the email "rahul@test.com"