package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class ForgotPassword {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/auth/register");
        Thread.sleep(7000);
    }

    @Test
    public void FirstNameRequired() throws InterruptedException {
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.id("first_name-error")).getText(),
                "First name is required", "Expected field validation was not displayed");
    }

    @Test
    public void FirstNameOnlySpacesRejected() throws InterruptedException {
        d.findElement(By.id("first_name")).sendKeys("        ");
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        Assert.assertTrue(d.findElement(By.tagName("body")).getText().contains("First name is required"),
                "First name accepts only spaces without a validation error");
    }

    @Test
    public void DateOfBirthRequired() throws InterruptedException {
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        String error = d.findElement(By.id("dob-error")).getText();
        Assert.assertTrue(error.contains("Date of Birth is required"), "Required field message is missing");
    }

    @Test
    public void DateOfBirthAbsurdRejected() throws InterruptedException {
        d.findElement(By.id("dob")).sendKeys("1800-01-01");
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        Assert.assertTrue(d.findElements(By.id("dob-error")).size() > 0,
                "Date of Birth accepts an absurd year without a validation error");
    }

    @Test
    public void EmailRequired() throws InterruptedException {
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        Assert.assertEquals(d.findElement(By.id("email-error")).getText(),
                "Email is required", "Expected field validation was not displayed");
    }

    @Test
    public void PasswordRequired() throws InterruptedException {
        d.findElement(By.className("btnSubmit")).click();
        Thread.sleep(1000);
        String error = d.findElement(By.id("password-error")).getText();
        Assert.assertTrue(error.contains("Password is required"), "Required field message is missing");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
