package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShoppingCartsTests {

    WebDriver d;

    @BeforeMethod
    public void pretest() throws InterruptedException {
        d = new ChromeDriver();
        d.manage().window().maximize();
        d.get("https://practicesoftwaretesting.com/");
        Thread.sleep(1000);
    }

    @Test
    public void testUpdateSecondProductQuantity() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Claw Hammer");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.get("https://practicesoftwaretesting.com/");
        Thread.sleep(1000);

        d.findElement(By.id("search-query")).sendKeys("Bolt Cutters");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(1000);

        WebElement quantity = d.findElements(By.className("quantity")).get(1);

        quantity.clear();
        quantity.sendKeys("4");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        String actualQuantity = d.findElements(By.className("quantity"))
                .get(1).getAttribute("value");

        Assert.assertEquals(actualQuantity, "4",
                "Second product quantity was not updated");
    }

    @Test
    public void testNewQuantityStaysAfterRefresh() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(1000);

        WebElement quantity = d.findElement(By.className("quantity"));

        quantity.clear();
        quantity.sendKeys("3");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        d.navigate().refresh();
        Thread.sleep(1000);

        String actualQuantity = d.findElement(By.className("quantity"))
                .getAttribute("value");

        Assert.assertEquals(actualQuantity, "3",
                "Quantity was not saved after refresh");
    }

    @Test
    public void testQuantityZeroIsRejected() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(1000);

        WebElement quantity = d.findElement(By.className("quantity"));

        quantity.clear();
        quantity.sendKeys("0");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        String actualQuantity = d.findElement(By.className("quantity"))
                .getAttribute("value");

        Assert.assertNotEquals(actualQuantity, "0",
                "System accepted zero quantity");
    }

    @Test
    public void testNegativeQuantityIsRejected() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(1000);

        WebElement quantity = d.findElement(By.className("quantity"));

        quantity.clear();
        quantity.sendKeys("-1");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        String actualQuantity = d.findElement(By.className("quantity"))
                .getAttribute("value");

        Assert.assertNotEquals(actualQuantity, "-1",
                "System accepted negative quantity");
    }

    @Test
    public void testValidQuantityWorksAfterInvalidQuantity() throws InterruptedException {

        d.findElement(By.id("search-query")).sendKeys("Combination Pliers");
        d.findElement(By.cssSelector("[data-test='search-submit']")).click();
        Thread.sleep(1000);

        d.findElement(By.className("card-title")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(1000);

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(1000);

        WebElement quantity = d.findElement(By.className("quantity"));

        quantity.clear();
        quantity.sendKeys("0");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        quantity = d.findElement(By.className("quantity"));

        quantity.clear();
        quantity.sendKeys("2");
        quantity.sendKeys(Keys.TAB);

        Thread.sleep(1000);

        String actualQuantity = d.findElement(By.className("quantity"))
                .getAttribute("value");

        Assert.assertEquals(actualQuantity, "2",
                "System did not accept valid quantity after invalid input");
    }

    @AfterMethod
    public void End() {
        d.quit();
    }
}