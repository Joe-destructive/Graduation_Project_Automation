package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(7000);
    }

    // Login_TCs.xlsx / Email row 3
    @Test
    public void EmailRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
    }

    // Login_TCs.xlsx / Password row 2
    @Test
    public void PasswordRequired() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='password-error']")).getText(),
                "Password is required", "Expected field validation was not displayed");
    }

    // Login_TCs.xlsx / Email row 4
    @Test
    public void EmailMissingAt() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("youssef.ramy.example.com");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email format is invalid", "Expected field validation was not displayed");
    }

    // Login_TCs.xlsx / Email row 5
    @Test
    public void EmailMissingLocalPart() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("@example.com");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email format is invalid", "Expected field validation was not displayed");
    }

    // Login_TCs.xlsx / Email row 6
    @Test
    public void EmailMissingDomain() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("youssef@");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email format is invalid", "Expected field validation was not displayed");
    }

    // Login_TCs.xlsx / Email row 7. Known bug #3: missing format validation.
    @Test
    public void EmailMissingDomainExtension() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("youssef@gmail");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        String page = d.findElement(By.tagName("body")).getText();
        Assert.assertTrue(page.contains("Email format is invalid"),
                "Email without a domain extension must show a field format error");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
