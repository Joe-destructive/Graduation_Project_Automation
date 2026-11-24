package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage_Integrated {

    WebDriver d;

    @BeforeMethod
    public void pretest() throws InterruptedException {
        d = new ChromeDriver();
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(1000);
    }

    @Test
    public void testSignInFromHomePage() throws InterruptedException {

        d.findElement(By.cssSelector("[data-test='nav-sign-in']")).click();

        Thread.sleep(1000);
    }
    @Test
    public void testAddProductFromSearchToCart() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();

        Thread.sleep(1000);

        d.findElement(By.xpath("//*[normalize-space()='Combination Pliers']")).click();

        Thread.sleep(1000);

        d.findElement(By.id("btn-add-to-cart")).click();

        Thread.sleep(1000);

        d.navigate().to("https://practicesoftwaretesting.com/checkout");

        Thread.sleep(1000);
    }

    @AfterMethod
    public void End() {
        d.quit();
    }
}