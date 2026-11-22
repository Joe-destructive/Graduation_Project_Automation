package Parent;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class Base {

    WebDriver d = new ChromeDriver();
    @BeforeMethod
    public void Preconditions() {
        d.navigate().to("https://www.saucedemo.com/");
    }


    @Test
    public void HappyScenarioTest() throws InterruptedException {

        WebElement User = d.findElement(By.id("user-name"));
        WebElement Pass = d.findElement(By.id("password"));
        WebElement BTN = d.findElement(By.id("login-button"));

        User.sendKeys("standard_user");
        Thread.sleep(2000);

        Pass.sendKeys("secret_sauce");
        Thread.sleep(2000);

        BTN.click();

        Assert.assertEquals(d.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "User was not redirected to the inventory page");

    }
    @Test
    public void Invalid_1() throws InterruptedException {


        WebElement User = d.findElement(By.id("user-name"));
        WebElement Pass = d.findElement(By.id("password"));
        WebElement BTN = d.findElement(By.id("login-button"));

        User.sendKeys("YYYYYYYYYYYYY");
        Thread.sleep(2000);

        Pass.sendKeys("RRRRRRRRRRRRRRRRRRR");
        Thread.sleep(2000);

        BTN.click();

        Assert.assertEquals(d.getCurrentUrl(),
                "https://www.saucedemo.com/inventory.html",
                "User was not redirected to the inventory page");
    }

    @AfterTest
    public void End(){

        d.close();
    }

    // assert.assertTrue (Condition is true ?, message);
    // assert.assertFalse (Condition is false ?, message);
    // assert.assertEquals (ExpectedResult, equals ActualResult ?, message);
    // assert.assertNotEquals (ExpectedResult, Not equals ActualResult ?, message);


}
