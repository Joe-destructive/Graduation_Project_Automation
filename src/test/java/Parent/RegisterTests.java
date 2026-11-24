package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterTests {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/auth/register");
        Thread.sleep(7000);
    }

    // Register_TCs.xlsx / Component testing row 3
    @Test(priority = 1)
    public void FirstNameRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='first-name-error']")).getText(),
                "First name is required", "Expected field validation was not displayed");
    }

    // Register_TCs.xlsx / Component testing row 4. First name must not be only spaces.
    @Test(priority = 2)
    public void FirstNameOnlySpacesRejected() throws InterruptedException {
        d.findElement(By.id("first_name")).sendKeys("        ");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertTrue(d.findElement(By.tagName("body")).getText().contains("First name is required"),
                "First name accepts only spaces without a validation error");
    }

    // Register_TCs.xlsx / Component testing row 7
    @Test(priority = 3)
    public void DateOfBirthRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        String error = d.findElement(By.cssSelector("[data-test='dob-error']")).getText();
        Assert.assertTrue(error.contains("Date of Birth is required"), "Required field message is missing");
    }

    // Register_TCs.xlsx / Component testing row 24
    @Test(priority = 4)
    public void CountryRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='country-error']")).getText(),
                "Country is required", "Expected field validation was not displayed");
    }

    // Register_TCs.xlsx / Component testing row 4. First name must not be only spaces.4
    @Test(priority = 5)
    public void EmailRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
    }

    // Register_TCs.xlsx / Component testing row 45
    @Test(priority = 6)
    public void PasswordRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        String error = d.findElement(By.cssSelector("[data-test='password-error']")).getText();
        Assert.assertTrue(error.contains("Password is required"), "Required field message is missing");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
