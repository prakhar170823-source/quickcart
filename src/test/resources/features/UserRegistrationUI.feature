@ui
Feature: User Registration Frontend UI
  As a new customer
  I want to register an account using the website
  So that I can see a success message on my screen

  Scenario: Successfully register a user via the Web UI
    Given the user opens the QuickCart registration page
    When the user types the name "Test", email "test@gmail.com", and password "Secure123!"
    And the user clicks the Create Account button
    Then a success message saying "Registration Successful!" should appear on the screen