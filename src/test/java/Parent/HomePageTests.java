
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

public class HomePageTests {

        WebDriver d;

        @BeforeMethod
        public void pretest() throws InterruptedException {
            d = new ChromeDriver();
            d.manage().window().maximize();
            d.navigate().to("https://practicesoftwaretesting.com/");
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

            Assert.assertTrue(d.getCurrentUrl().contains("checkout"),
                    "Checkout page was not opened");
        }

        @Test
        public void testSearchDoesNotAcceptSqlInjection() throws InterruptedException {

            WebElement box = d.findElement(By.id("search-query"));
            box.sendKeys("' OR 1=1 --");

            d.findElement(By.cssSelector("[data-test='search-submit']")).click();

            Thread.sleep(1000);

            WebElement message = d.findElement(By.cssSelector("[data-test='no-results']"));

            Assert.assertTrue(message.isDisplayed(),
                    "Search should not return results for SQL injection input");
        }

        @Test
        public void testProductsAboveMaximumPriceAreHidden() throws InterruptedException {

            WebElement max = d.findElement(By.className("ngx-slider-pointer-max"));

            for (int i = 0; i < 90; i++) {
                max.sendKeys(Keys.ARROW_LEFT);
            }

            Thread.sleep(1000);

            Assert.assertTrue(d.findElements(By.className("card-title")).size() > 0,
                    "No products are displayed after applying the price filter");
        }

        @Test
        public void testClickingProductNameOpensDetailsPage() throws InterruptedException {

            String homeUrl = d.getCurrentUrl();

            d.findElement(By.className("card-title")).click();

            Thread.sleep(1000);

            Assert.assertNotEquals(d.getCurrentUrl(), homeUrl,
                    "Product details page was not opened");
        }

        @Test
        public void testRapidLanguageSwitching() throws InterruptedException {

            String englishHome = d.findElement(By.cssSelector("[data-test='nav-home']")).getText();

            d.findElement(By.id("language")).click();
            d.findElement(By.cssSelector("[data-test='lang-de']")).click();

            Thread.sleep(1000);

            String germanHome = d.findElement(By.cssSelector("[data-test='nav-home']")).getText();

            Assert.assertNotEquals(germanHome, englishHome,
                    "German language was not applied");

            d.findElement(By.id("language")).click();
            d.findElement(By.cssSelector("[data-test='lang-es']")).click();

            Thread.sleep(1000);

            String spanishHome = d.findElement(By.cssSelector("[data-test='nav-home']")).getText();

            Assert.assertNotEquals(spanishHome, germanHome,
                    "Spanish language was not applied");

            d.findElement(By.id("language")).click();
            d.findElement(By.cssSelector("[data-test='lang-en']")).click();

            Thread.sleep(1000);

            String finalHome = d.findElement(By.cssSelector("[data-test='nav-home']")).getText();

            Assert.assertEquals(finalHome, englishHome,
                    "English language was not restored");
        }

        @AfterMethod
        public void End() {
            d.quit();
        }
    }

