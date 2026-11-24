package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginIntegrationTests {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(7000);
    }

    // Login_TCs.xlsx / Integration row 2. Public demo account.
    @Test(priority = 1)
    public void SuccessfulLogin() throws InterruptedException {
        // Public demo fixture documented by the site owner:
        // https://github.com/testsmith-io/practice-software-testing/
        WebElement Email = d.findElement(By.id("email"));
        WebElement Pass = d.findElement(By.id("password"));
        Email.sendKeys("customer@practicesoftwaretesting.com");
        Pass.sendKeys("welcome01");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(5000);
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/account",
                "The documented demo account could not log in");
    }

    // Login_TCs.xlsx / Integration row 7
    @Test(priority = 2)
    public void WrongPasswordRejected() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("inbox17mba9@mail.instinct.com");
        d.findElement(By.id("password")).sendKeys("deliberately-wrong-input");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(3000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='login-error']")).getText(),
                "Invalid email or password", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    // Login_TCs.xlsx / Integration row 3
    @Test(priority = 3)
    public void BothFieldsEmpty() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='password-error']")).getText(),
                "Password is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    // Login_TCs.xlsx / Integration row 4
    @Test(priority = 4)
    public void EmptyEmailWithPassword() throws InterruptedException {
        d.findElement(By.id("password")).sendKeys("negative-test-input");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='email-error']")).getText(),
                "Email is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    // Login_TCs.xlsx / Integration row 5
    @Test(priority = 5)
    public void EmailWithEmptyPassword() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("inbox17mba9@mail.instinct.com");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='password-error']")).getText(),
                "Password is required", "Expected field validation was not displayed");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    // Login_TCs.xlsx / Email row 7 plus Integration invalid-email/password flow. Bug #3.
    @Test(priority = 6)
    public void MissingDomainExtensionWithPassword() throws InterruptedException {
        d.findElement(By.id("email")).sendKeys("youssef@gmail");
        d.findElement(By.id("password")).sendKeys("negative-test-input");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(1000);
        String page = d.findElement(By.tagName("body")).getText();
        Assert.assertTrue(page.contains("Email format is invalid"),
                "Email without a domain extension must show a field format error");
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/auth/login");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
