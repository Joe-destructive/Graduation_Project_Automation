package Parent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Home_CustomerArea_Integration {
    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void precondition() throws InterruptedException {
        driver.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(2000);
        WebElement Email = driver.findElement(By.id("email"));
        Email.sendKeys("customer@practicesoftwaretesting.com");
        WebElement Pass = driver.findElement(By.id("password"));
        Pass.sendKeys("welcome01");
        Thread.sleep(2000);
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='login-submit']"));
        BTN.click();
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }
    @Test
    public void login_home() throws InterruptedException {
        Thread.sleep(1000);
        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        Thread.sleep(2000);
    }
    @Test
    public void login_home_invalid_search() throws InterruptedException {
        Thread.sleep(1000);
        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("mmm");
        Thread.sleep(2000);
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
    }
    @Test
    public void login_home_valid_search_AddFavourite_delete() throws InterruptedException {
        Thread.sleep(1000);
        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("pliers");
        Thread.sleep(3000);
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
        Thread.sleep(3000);
        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(2000);
        WebElement Add_To_Favourite = driver.findElement(By.id("btn-add-to-favorites"));
        Add_To_Favourite.click();
        Thread.sleep(2000);
        WebElement menu = driver.findElement(By.id("menu"));
        menu.click();
        Thread.sleep(2000);
        WebElement My_Favourite = driver.findElement(By.cssSelector("[data-test='nav-my-favorites']"));
        My_Favourite.click();
        Thread.sleep(3000);
        WebElement Delete_Button = driver.findElement(By.cssSelector("[data-test='delete']"));
        Delete_Button.click();
    }
    @AfterClass
    public void close(){
        driver.close();
    }
//M


}
