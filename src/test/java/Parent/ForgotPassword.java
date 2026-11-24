package Parent;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForgotPassword {
    WebDriver driver = new ChromeDriver();

    @BeforeMethod
    public void precondition() throws InterruptedException {
        driver.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);
    }

    // ================= Component Tests (5) =================

    @Test
    public void valid_email() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys("user@gmail.com");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        int errors = driver.findElements(By.cssSelector("[data-test='email-error']")).size();
        Assert.assertEquals(errors, 0);
    }

    @Test
    public void empty_email() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        WebElement error = driver.findElement(By.cssSelector("[data-test='email-error']"));
        Assert.assertTrue(error.isDisplayed());
    }

    @Test
    public void email_without_at() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys("marcgmail.com");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        WebElement error = driver.findElement(By.cssSelector("[data-test='email-error']"));
        Assert.assertTrue(error.isDisplayed());
    }

    @Test
    public void email_with_space() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys("marc @gmail.com");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        WebElement error = driver.findElement(By.cssSelector("[data-test='email-error']"));
        Assert.assertTrue(error.isDisplayed());
    }

    @Test
    public void uppercase_email() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys("USER@gmail.com");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        int errors = driver.findElements(By.cssSelector("[data-test='email-error']")).size();
        Assert.assertEquals(errors, 0);
    }

    // ================= Integration Tests (5) =================

    @Test
    public void forgot_page_opens_from_login() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("forgot-password"));
    }

    @Test
    public void back_to_login_from_forgot_page() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement signin2 = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin2.click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    public void email_field_empty_after_login_page() throws InterruptedException {

        WebElement signin = driver.findElement(By.cssSelector("[data-test='nav-sign-in']"));
        signin.click();
        Thread.sleep(2000);
        WebElement loginEmail = driver.findElement(By.cssSelector("[data-test='email']"));
        loginEmail.sendKeys("user@gmail.com");
        WebElement forgot = driver.findElement(By.cssSelector("[data-test='forgot-password-link']"));
        forgot.click();
        Thread.sleep(2000);
        WebElement forgotEmail = driver.findElement(By.cssSelector("[data-test='email']"));
        Assert.assertEquals(forgotEmail.getAttribute("value"), "");
    }

    // Known bug #1 - expected to FAIL on the with-bugs site
    @Test
    public void confirmation_message_after_submit() throws InterruptedException {

        driver.navigate().to("https://with-bugs.practicesoftwaretesting.com/#/auth/forgot-password");
        Thread.sleep(3000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys("testuser63@gmail.com");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        WebElement message = driver.findElement(By.cssSelector(".alert-success"));
        Assert.assertTrue(message.isDisplayed());
    }

    // Known bug #6 - expected to FAIL on the with-bugs site
    @Test
    public void spaces_at_beginning_and_end_of_email() throws InterruptedException {

        driver.navigate().to("https://with-bugs.practicesoftwaretesting.com/#/auth/forgot-password");
        Thread.sleep(3000);
        WebElement email = driver.findElement(By.cssSelector("[data-test='email']"));
        email.sendKeys(" testuser63@gmail.com ");
        WebElement submit = driver.findElement(By.cssSelector("[data-test='forgot-password-submit']"));
        submit.click();
        Thread.sleep(2000);
        int errors = driver.findElements(By.cssSelector("[data-test='email-error']")).size();
        int success = driver.findElements(By.cssSelector(".alert-success")).size();
        Assert.assertTrue(errors > 0 || success > 0);
    }

    @AfterTest
    public void close() {
        driver.close();
    }
}