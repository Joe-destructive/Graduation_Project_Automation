package Parent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Customer_area {
    WebDriver driver = new ChromeDriver();
    @BeforeMethod
    public void precondition() throws InterruptedException {
        driver.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(3000);
        WebElement Email = driver.findElement(By.id("email"));
        Email.sendKeys("customer@practicesoftwaretesting.com");
        WebElement Pass = driver.findElement(By.id("password"));
        Pass.sendKeys("welcome01");
        Thread.sleep(3000);
        WebElement BTN = driver.findElement(By.cssSelector("[data-test='login-submit']"));
        BTN.click();
        Thread.sleep(3000);
    }
    @Test
    public void Open_profile() throws InterruptedException {
        Thread.sleep(3000);
        WebElement profile = driver.findElement(By.cssSelector("[data-test='nav-profile']"));
        profile.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("/profile"));
    }
    @Test
    public void invalid_current_password() throws InterruptedException {
        Thread.sleep(3000);
        WebElement profile = driver.findElement(By.cssSelector("[data-test='nav-profile']"));
        profile.click();
        Thread.sleep(3000);
        WebElement current_password = driver.findElement(By.cssSelector("[data-test='current-password']"));
        current_password.sendKeys("welcome");
        Thread.sleep(3000);
        WebElement new_password = driver.findElement(By.id("new-password"));
        new_password.sendKeys("welcom10@");
        Thread.sleep(2000);
        WebElement confirm = driver.findElement(By.id("new-password-confirm"));
        confirm.sendKeys("welcom10@");
        Thread.sleep(2000);
        WebElement change_password = driver.findElement(By.cssSelector("[data-test='change-password-submit']"));
        change_password.click();
    }
    @Test(priority = 3)
    public void favourite() throws InterruptedException {
        driver.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);
        WebElement pliers = driver.findElement(By.xpath("//h5[normalize-space(.)='Pliers']"));
        pliers.click();
        Thread.sleep(3000);
        WebElement Add_To_Favourite = driver.findElement(By.id("btn-add-to-favorites"));
        Add_To_Favourite.click();
        Thread.sleep(3000);
        WebElement menu = driver.findElement(By.id("menu"));
        menu.click();
        Thread.sleep(2000);
        WebElement My_Favourite = driver.findElement(By.cssSelector("[data-test='nav-my-favorites']"));
        My_Favourite.click();
        Thread.sleep(3000);

    }
    @Test(priority = 4)
    public void delete_product_from_myfavourite() throws InterruptedException {
        Thread.sleep(3000);
        WebElement menu = driver.findElement(By.id("menu"));
        menu.click();
        Thread.sleep(2000);
        WebElement My_Favourite = driver.findElement(By.cssSelector("[data-test='nav-my-favorites']"));
        My_Favourite.click();
        Thread.sleep(2000);
        WebElement Delete_Button = driver.findElement(By.cssSelector("[data-test='delete']"));
        Delete_Button.click();
    }
    @Test(priority = 5)
    public void logout() throws InterruptedException {
        driver.navigate().to("https://practicesoftwaretesting.com/account");
        Thread.sleep(3000);
        WebElement menu = driver.findElement(By.id("menu"));
        menu.click();
        Thread.sleep(2000);

        WebElement signOut = driver.findElement(By.cssSelector("[data-test='nav-sign-out']"));
        signOut.click();
        Thread.sleep(3000);


    }
    @AfterClass
    public void close(){
        driver.close();
    }



}