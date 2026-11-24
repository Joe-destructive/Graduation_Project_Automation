package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class CustomerAreaTests {

    WebDriver d;

    @BeforeMethod



    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.manage().window().maximize();

        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(3000);

        d.findElement(By.id("email")).sendKeys("customer2@practicesoftwaretesting.com");
        d.findElement(By.id("password")).sendKeys("welcome01");
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(3000);

        Assert.assertTrue(d.getCurrentUrl().contains("/account"),
                "Login failed. Actual URL: " + d.getCurrentUrl());
    }

    @Test(priority = 1)
    public void AddToFavorites() throws InterruptedException {

        OpenPliersProduct();

        d.findElement(By.cssSelector("[data-test='add-to-favorites']")).click();
        Thread.sleep(2000);

        d.navigate().to("https://practicesoftwaretesting.com/account/favorites");
        Thread.sleep(3000);

        String name = d.findElement(By.cssSelector("[data-test='product-name']")).getText();
        Assert.assertEquals(name, "Pliers", "Product was not added to favorites");
    }

    @Test(priority = 2)
    public void FavoriteCardShowsDescription() throws InterruptedException {

        OpenPliersProduct();

        d.findElement(By.cssSelector("[data-test='add-to-favorites']")).click();
        Thread.sleep(2000);

        d.navigate().to("https://practicesoftwaretesting.com/account/favorites");
        Thread.sleep(3000);

        String description = d.findElement(By.cssSelector("[data-test='product-description']")).getText();
        Assert.assertFalse(description.isEmpty(), "Product description is empty in favorites");
    }

    @Test(priority = 3)
    public void FavoriteStaysAfterRefresh() throws InterruptedException {

        OpenPliersProduct();

        d.findElement(By.cssSelector("[data-test='add-to-favorites']")).click();
        Thread.sleep(2000);

        d.navigate().to("https://practicesoftwaretesting.com/account/favorites");
        Thread.sleep(3000);

        d.navigate().refresh();
        Thread.sleep(3000);

        String name = d.findElement(By.cssSelector("[data-test='product-name']")).getText();
        Assert.assertEquals(name, "Pliers", "Favorite disappeared after refresh");
    }

    @Test(priority = 4)
    public void DeleteFromFavorites() throws InterruptedException {

        OpenPliersProduct();

        d.findElement(By.cssSelector("[data-test='add-to-favorites']")).click();
        Thread.sleep(2000);

        d.navigate().to("https://practicesoftwaretesting.com/account/favorites");
        Thread.sleep(3000);

        d.findElement(By.cssSelector("[data-test='delete']")).click();
        Thread.sleep(2000);

        String page = d.findElement(By.tagName("body")).getText();
        Assert.assertTrue(page.contains("There are no favorites yet"),
                "Empty favorites message was not displayed");
    }

    @Test(priority = 5)
    public void InvoicesPageHeaders() throws InterruptedException {

        d.navigate().to("https://practicesoftwaretesting.com/account/invoices");
        Thread.sleep(3000);

        String page = d.findElement(By.tagName("body")).getText();
        Assert.assertTrue(page.contains("Invoice Number"), "Invoice Number header is missing");
        Assert.assertTrue(page.contains("Billing Address"), "Billing Address header is missing");
        Assert.assertTrue(page.contains("Invoice Date"), "Invoice Date header is missing");
        Assert.assertTrue(page.contains("Total"), "Total header is missing");
    }

    @Test(priority = 6)
    public void InvalidInvoiceNumber() throws InterruptedException {

        d.navigate().to("https://practicesoftwaretesting.com/account/invoices/INVALID");
        Thread.sleep(3000);

        String page = d.findElement(By.tagName("body")).getText().toLowerCase();
        Assert.assertTrue(page.contains("not found"),
                "No 'not found' message. Actual URL: " + d.getCurrentUrl());
    }

    @AfterMethod
    public void End() {
        d.quit();
    }

    public void OpenPliersProduct() throws InterruptedException {
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);

        d.findElement(By.xpath("//h5[@data-test='product-name'][normalize-space()='Pliers']")).click();
        Thread.sleep(3000);
    }
}
