package Parent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Home_CustomerArea_End_To_End {
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
    public void search_open_addFavourite_check_delete() throws InterruptedException {

        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        Thread.sleep(2000);
        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("pliers");
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
        Thread.sleep(3000);

        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(3000);
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
        Assert.assertTrue(Delete_Button.isDisplayed());

        Delete_Button.click();
        Thread.sleep(3000);

        WebElement menu2 = driver.findElement(By.id("menu"));
        menu2.click();
        Thread.sleep(2000);
        WebElement signOut = driver.findElement(By.cssSelector("[data-test='nav-sign-out']"));
        signOut.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"));
    }

    @Test
    public void invalidSearch_validSearch_quantity_profile_invalidPassword() throws InterruptedException {
        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        Thread.sleep(2000);

        WebElement search = driver.findElement(By.id("search-query"));
        search.sendKeys("mmm");
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
        Thread.sleep(3000);

        search = driver.findElement(By.id("search-query"));
        search.clear();
        search.sendKeys("pliers");

        BTN = driver.findElement(By.cssSelector("[data-test='search-submit']"));
        BTN.click();
        Thread.sleep(3000);

        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(3000);

        WebElement plus = driver.findElement(By.cssSelector("svg[data-icon='plus']"));
        plus.click();
        Thread.sleep(2000);

        WebElement quantity = driver.findElement(By.cssSelector("[data-test='quantity']"));
        Assert.assertEquals(quantity.getAttribute("value"), "2");
        driver.navigate().to("https://practicesoftwaretesting.com/account");
        Thread.sleep(3000);
        WebElement profile = driver.findElement(By.cssSelector("[data-test='nav-profile']"));
        profile.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/profile"));

        WebElement current_password = driver.findElement(By.cssSelector("[data-test='current-password']"));
        current_password.sendKeys("welcome");
        WebElement new_password = driver.findElement(By.id("new-password"));
        new_password.sendKeys("welcom10@");
        WebElement confirm = driver.findElement(By.id("new-password-confirm"));
        confirm.sendKeys("welcom10@");
        Thread.sleep(2000);
        WebElement change_password = driver.findElement(By.cssSelector("[data-test='change-password-submit']"));
        change_password.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/profile"));
    }
    @Test
    public void filterPliers_addFavourite_delete_signOut() throws InterruptedException {
        WebElement home = driver.findElement(By.cssSelector("[data-test='nav-home']"));
        home.click();
        Thread.sleep(3000);

        WebElement filter = driver.findElement(By.xpath("//label[normalize-space(.)='Pliers']/input"));
        filter.click();
        Thread.sleep(3000);

        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(3000);

        WebElement Add_To_Favourite = driver.findElement(By.id("btn-add-to-favorites"));
        Add_To_Favourite.click();
        Thread.sleep(2000);

        WebElement menu = driver.findElement(By.id("menu"));
        menu.click();
        Thread.sleep(2000);
        WebElement My_Favourite = driver.findElement(By.cssSelector("[data-test='nav-my-favorites']"));
        My_Favourite.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/favorites"));

        WebElement Delete_Button = driver.findElement(By.cssSelector("[data-test='delete']"));
        Assert.assertTrue(Delete_Button.isDisplayed());
        Delete_Button.click();
        Thread.sleep(3000);

        WebElement menu2 = driver.findElement(By.id("menu"));
        menu2.click();
        Thread.sleep(2000);

        WebElement signOut = driver.findElement(By.cssSelector("[data-test='nav-sign-out']"));
        signOut.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/login"));
    }
    @AfterClass
    public void close(){
        driver.close();
    }

}



