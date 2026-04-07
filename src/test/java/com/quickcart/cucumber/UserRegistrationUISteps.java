package com.quickcart.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

public class UserRegistrationUISteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before("@ui")
    public void setUpBrowser() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @After("@ui")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user opens the QuickCart registration page")
    public void openRegistrationPage() {
        String filePath = "file:///C:/Codebase/quickcart/src/quickcart-frontend/register.html";
        driver.get(filePath);
    }

    @When("the user types the name {string}, email {string}, and password {string}")
    public void fillOutForm(String name, String email, String password) throws InterruptedException{
        driver.findElement(By.id("name")).sendKeys(name);
        Thread.sleep(1000);
        driver.findElement(By.id("email")).sendKeys(email);
        Thread.sleep(1000);
        driver.findElement(By.id("password")).sendKeys(password);
        Thread.sleep(1000);
    }

    @When("the user clicks the Create Account button")
    public void clickSubmit() throws InterruptedException {
        driver.findElement(By.id("registerButton")).click();
        Thread.sleep(3000);
    }

    @Then("a success message saying {string} should appear on the screen")
    public void verifySuccessMessage(String expectedMessage) {
        WebElement messageDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        wait.until(ExpectedConditions.textToBePresentInElement(messageDiv, expectedMessage));
        assertEquals(expectedMessage, messageDiv.getText());
    }
}
