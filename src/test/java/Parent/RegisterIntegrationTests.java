package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterIntegrationTests {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/auth/register");
        Thread.sleep(7000);
    }

    // Register_TCs.xlsx / Integration testing row 5
    @Test(priority = 1)
    public void AllMandatoryErrorsTogether() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='first-name-error']")).getText(),
                "First name is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='last-name-error']")).getText(),
                "Last name is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='country-error']")).getText(),
                "Country is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='postal_code-error']")).getText(),
                "Postcode is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='house_number-error']")).getText(),
                "House number is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='street-error']")).getText(),
                "Street is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='city-error']")).getText(),
                "City is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='state-error']")).getText(),
                "State is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='phone-error']")).getText(),
                "Phone is required.", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
        Assert.assertTrue(d.findElement(By.cssSelector("[data-test='dob-error']")).getText().contains("Date of Birth is required"));
        Assert.assertTrue(d.findElement(By.cssSelector("[data-test='password-error']")).getText().contains("Password is required"));
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/register");
    }

    // Register_TCs.xlsx / Integration testing row 7
    @Test(priority = 2)
    public void InvalidEmailBlocksRegistration() throws InterruptedException {
        d.findElement(By.id("first_name")).sendKeys("Youssef");
        d.findElement(By.id("last_name")).sendKeys("Ramy");
        d.findElement(By.id("dob")).sendKeys("2000-05-18");
        d.findElement(By.id("country")).sendKeys("Egypt");
        d.findElement(By.id("postal_code")).sendKeys("11511");
        d.findElement(By.id("house_number")).sendKeys("42");
        Thread.sleep(3000);
        d.findElement(By.id("phone")).sendKeys("01200000000");
        d.findElement(By.id("email")).sendKeys("youssef@@example.com");
        // Dummy validation input only. No account is created with this password.
        d.findElement(By.id("password")).sendKeys("Youssef-Validation-Only-86!");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email format is invalid", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/register");
    }

    // Register_TCs.xlsx / Integration row 6 + Component row 4.
    // Same spaces defect as the component case, tested during whole-form error correction.
    @Test(priority = 3)
    public void SpacesMustNotClearRequiredError() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='first-name-error']")).getText(),
                "First name is required");

        d.findElement(By.id("first_name")).sendKeys("        ");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/register");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='last-name-error']")).getText(),
                "Last name is required");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required");
        Assert.assertTrue(d.findElement(By.tagName("body")).getText().contains("First name is required"),
                "Spaces must not clear the first-name error while the form is still invalid");
    }

    // Register_TCs.xlsx / Integration rows 5-7: email and password required together.
    @Test(priority = 4)
    public void MissingEmailAndPasswordTogether() throws InterruptedException {
        d.findElement(By.id("first_name")).sendKeys("Youssef");
        d.findElement(By.id("last_name")).sendKeys("Ramy");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required");
        Assert.assertTrue(d.findElement(By.cssSelector("[data-test='password-error']")).getText().contains("Password is required"));
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/register");
    }

    // Register_TCs.xlsx / Integration testing row 6, validation stage only
    @Test(priority = 5)
    public void CorrectedFieldsDoNotClearOtherErrors() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='first-name-error']")).getText(),
                "First name is required", "Expected field validation was not displayed");
        d.findElement(By.id("first_name")).sendKeys("Youssef");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertFalse(d.findElement(By.tagName("body")).getText().contains("First name is required"),
                "First-name error should clear after correction");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='last-name-error']")).getText(),
                "Last name is required", "Expected field validation was not displayed");
        d.findElement(By.id("last_name")).sendKeys("Ramy");
        d.findElement(By.cssSelector("[data-test='register-submit']")).click();
        Thread.sleep(1000);
        Assert.assertFalse(d.findElement(By.tagName("body")).getText().contains("Last name is required"));
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/register");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
