package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SystemE2ETests {

    WebDriver d;

    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.manage().window().maximize();
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(5000);
    }

    @Test
    public void SearchAddToCartAndPayCashOnDelivery() throws InterruptedException {
        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(3000);
        d.findElement(By.className("card-title")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-1']")).click();
        Thread.sleep(3000);
        d.findElement(By.id("email")).sendKeys("customer2@practicesoftwaretesting.com");
        d.findElement(By.id("password")).sendKeys("welcome01");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(4000);
        d.findElement(By.cssSelector("[data-test='proceed-2']")).click();
        Thread.sleep(3000);
        d.findElement(By.id("country")).sendKeys("Egypt");
        d.findElement(By.id("postal_code")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("postal_code")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("postal_code")).sendKeys("11511");
        d.findElement(By.id("house_number")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("house_number")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("house_number")).sendKeys("42");
        Thread.sleep(3000);
        d.findElement(By.id("street")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("street")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("street")).sendKeys("Tahrir Street");
        d.findElement(By.id("city")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("city")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("city")).sendKeys("Cairo");
        d.findElement(By.id("state")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("state")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("state")).sendKeys("Cairo");
        Thread.sleep(2000);
        d.findElement(By.cssSelector("[data-test='proceed-3']")).click();
        Thread.sleep(3000);
        d.findElement(By.id("payment-method")).sendKeys("Cash on Delivery");
        Thread.sleep(2000);
        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(3000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText(),
                "Payment was successful", "The order was not completed after paying with Cash on Delivery");
    }

    @Test
    public void CartKeptAfterLoginDuringCheckout() throws InterruptedException {
        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(3000);
        d.findElement(By.className("card-title")).click();
        Thread.sleep(3000);
        String name = d.findElement(By.cssSelector("[data-test='product-name']")).getText();
        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-1']")).click();
        Thread.sleep(3000);
        d.findElement(By.id("email")).sendKeys("customer2@practicesoftwaretesting.com");
        d.findElement(By.id("password")).sendKeys("welcome01");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(4000);
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='cart-quantity']")).getText(), "1",
                "The cart icon did not show the product added before login");
        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        Assert.assertTrue(d.findElement(By.cssSelector("[data-test='product-title']")).getText().contains(name),
                "The product added before login was not in the cart after login");
    }

    @Test
    public void LoginOpenProfileAndLogout() throws InterruptedException {
        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(3000);
        d.findElement(By.id("email")).sendKeys("customer2@practicesoftwaretesting.com");
        d.findElement(By.id("password")).sendKeys("welcome01");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(4000);
        Assert.assertEquals(d.getCurrentUrl(), "https://practicesoftwaretesting.com/account",
                "Login did not open the account page");
        d.findElement(By.cssSelector("[data-test='nav-menu']")).click();
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='nav-sign-out']")).click();
        Thread.sleep(3000);
        d.navigate().to("https://practicesoftwaretesting.com/account");
        Thread.sleep(3000);
        Assert.assertTrue(d.getCurrentUrl().contains("/auth/login"),
                "The account page was still open after sign out");
    }

    @AfterMethod(alwaysRun = true)
    public void End() {
        d.quit();
    }
}
