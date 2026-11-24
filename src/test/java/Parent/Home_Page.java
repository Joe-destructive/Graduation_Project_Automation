package Parent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Home_Page {
    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void precondition(){
        driver.navigate().to("https://practicesoftwaretesting.com/");
    }

    @Test
    public void valid_search() throws InterruptedException {
        Thread.sleep(3000);
        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("Hammer");
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
    }

    @Test
    public void invalid_search() throws InterruptedException {
        Thread.sleep(3000);
        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("mmm");
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
    }

    @Test
    public void valid_Product() throws InterruptedException {
        Thread.sleep(3000);
        WebElement pliers = driver.findElement(By.xpath("//label[normalize-space(.)='Pliers']/input"));
        pliers.click();
    }
    @Test
    public void Quantity() throws InterruptedException {
        Thread.sleep(3000);
        WebElement product = driver.findElement(By.xpath("//img[@alt='Slip Joint Pliers']"));
        product.click();
        Thread.sleep(3000);
        WebElement minus = driver.findElement(By.cssSelector("svg[data-icon='minus']"));
        minus.click();
}   @Test
    public void matching_price_of_product() throws InterruptedException {
        Thread.sleep(3000);
        String priceBefore = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']/ancestor::a//*[@data-test='product-price']")).getText();
        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(3000);
        String priceAfter = driver.findElement(By.xpath("//span[@data-test='unit-price']/..")).getText();
        Assert.assertEquals(priceAfter, priceBefore);
    }
    @AfterClass
    public void close(){
        driver.close();
    }

}
