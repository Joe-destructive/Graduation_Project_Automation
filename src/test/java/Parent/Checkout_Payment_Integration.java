package Parent;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class Checkout_Payment_Integration {

    WebDriver d;

    // ================= BEFORE =================
    // Runs before every test: open a new browser (NOT logged in, each test logs in when it needs to)
    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.manage().window().maximize();
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);
    }

    // ================= TESTS =================

    // IT1 - Verify complete Bank Transfer checkout flow with valid account credentials
    @Test(priority = 1)
    public void BankTransferCompleteFlow() throws InterruptedException {

        Login();
        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Bank Transfer");
        Thread.sleep(2000);

        d.findElement(By.id("bank_name")).sendKeys("QNB");
        Thread.sleep(1000);
        d.findElement(By.id("account_name")).sendKeys("Kamal");
        Thread.sleep(1000);
        d.findElement(By.id("account_number")).sendKeys("1234567890");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Success message was not displayed after Bank Transfer with valid data");

        // NOTE: the sheet also expects order status 'Pending Bank Verification',
        // there is no place for it in the checkout page so it is not checked here
    }

    // IT2 - Verify Buy Now Pay Later (BNPL) installment plan selection & validation
    @Test(priority = 2)
    public void BuyNowPayLaterInstallments() throws InterruptedException {

        Login();
        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Buy Now Pay Later");
        Thread.sleep(2000);

        // leave the installments dropdown without choosing -> required error
        d.findElement(By.id("monthly_installments")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        String error = d.findElement(By.xpath("//select[@id='monthly_installments']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();
        Assert.assertTrue(error.contains("Please select the number of monthly installments"),
                "Required message was not displayed. Actual message: " + error);
        Assert.assertFalse(d.findElement(By.cssSelector("[data-test='finish']")).isEnabled(),
                "Check payment button must be disabled before choosing installments");

        // choose 3 monthly installments -> payment succeeds
        d.findElement(By.id("monthly_installments")).sendKeys("3 Monthly Installments");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Success message was not displayed after choosing 3 monthly installments");
    }

    // IT3 - Verify mandatory Street & City field validations in Checkout Address step  (expected FAIL -> bug)
    @Test(priority = 3)
    public void MandatoryStreetAndCity() throws InterruptedException {

        Login();
        AddRegularProduct();

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-1']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-2']")).click();
        Thread.sleep(3000);

        d.findElement(By.id("country")).sendKeys("Egypt");
        d.findElement(By.id("postal_code")).sendKeys("11511");
        d.findElement(By.id("house_number")).sendKeys("42");
        Thread.sleep(3000);          // the site fills street, city and state automatically

        // 1. leave Street empty
        d.findElement(By.id("street")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("street")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("street")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        boolean streetMessage = d.findElements(By.xpath("//input[@id='street']/following-sibling::div")).size() > 0;
        Assert.assertFalse(d.findElement(By.cssSelector("[data-test='proceed-3']")).isEnabled(),
                "Proceed button must be disabled when Street is empty");

        // 2. valid Street and City must be accepted
        d.findElement(By.id("street")).sendKeys("Tahrir Street");
        Thread.sleep(1000);
        d.findElement(By.id("city")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("city")).sendKeys("Cairo");
        d.findElement(By.id("city")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        Assert.assertFalse(d.findElement(By.id("city")).getAttribute("class").contains("ng-invalid"),
                "A valid City value was not accepted");

        // 3. leave City empty
        d.findElement(By.id("city")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("city")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("city")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        boolean cityMessage = d.findElements(By.xpath("//input[@id='city']/following-sibling::div")).size() > 0;
        Assert.assertFalse(d.findElement(By.cssSelector("[data-test='proceed-3']")).isEnabled(),
                "Proceed button must be disabled when City is empty");

        Assert.assertTrue(streetMessage && cityMessage,
                "Expected 'Street is required' and 'City is required' messages. Actual: Street message displayed = "
                        + streetMessage + " | City message displayed = " + cityMessage);
    }

    // IT4 - Verify Authentication integration during Checkout (wrong credentials / blank fields / valid login)
    @Test(priority = 4)
    public void AuthenticationDuringCheckout() throws InterruptedException {

        // guest user (NOT logged in)
        AddRegularProduct();

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-1']")).click();
        Thread.sleep(3000);

        // 1. wrong credentials
        // (an email that is not registered, so the real account does not get locked)
        d.findElement(By.id("email")).sendKeys("nouser@practicesoftwaretesting.com");
        Thread.sleep(1000);
        d.findElement(By.id("password")).sendKeys("WrongPass123");
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(2000);

        String loginError = d.findElement(By.cssSelector("[data-test='login-error']")).getText();
        Assert.assertTrue(loginError.contains("Invalid email or password"),
                "Wrong credentials message was not displayed. Actual: " + loginError);

        // 2. blank fields
        d.findElement(By.id("email")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("email")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("email")).sendKeys(Keys.TAB);
        d.findElement(By.id("password")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id("password")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id("password")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        String emailError = d.findElement(By.xpath("//input[@id='email']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();
        String passError = d.findElement(By.xpath("//input[@id='password']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();
        Assert.assertEquals(emailError, "Email is required", "Wrong email required message");
        Assert.assertEquals(passError, "Password is required", "Wrong password required message");

        // 3. valid login -> cart is kept and user can continue the checkout
        d.findElement(By.id("email")).sendKeys("customer1@practicesoftwaretesting.com");
        Thread.sleep(1000);
        d.findElement(By.id("password")).sendKeys("AbCdFgHi01@$");
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(3000);

        Assert.assertEquals(d.findElements(By.xpath("//*[contains(text(),'you are already logged in')]")).size(), 1,
                "'You are already logged in' message was not displayed after a valid login");
        Assert.assertEquals(d.findElement(By.cssSelector("[data-test='cart-quantity']")).getText(), "1",
                "Cart item was not kept after login");

        d.findElement(By.cssSelector("[data-test='proceed-2']")).click();
        Thread.sleep(3000);
        Assert.assertEquals(d.findElements(By.id("postal_code")).size(), 1,
                "User was not moved to the Billing Address step after login");
    }

    // IT5 - Verify Cart Quantity modifications (zero / valid quantity) and impact on Checkout
    @Test(priority = 5)
    public void CartQuantityModifications() throws InterruptedException {

        Login();
        AddRegularProduct();

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);

        // 1. zero quantity -> the value goes back to 1
        SetQuantity("0");
        String qty = d.findElement(By.cssSelector("[data-test='product-quantity']")).getAttribute("value");
        Assert.assertEquals(qty, "1", "Quantity 0 must not be accepted (should go back to 1)");

        // 2. valid quantity -> price multiplied
        SetQuantity("3");
        double unitPrice = Money(d.findElement(By.cssSelector("[data-test='product-price']")).getText());
        double linePrice = Money(d.findElement(By.cssSelector("[data-test='line-price']")).getText());
        double total = Money(d.findElement(By.cssSelector("[data-test='cart-total']")).getText());

        Assert.assertEquals(linePrice, unitPrice * 3, 0.02, "Line price is not (unit price x 3)");
        Assert.assertEquals(total, linePrice, 0.02, "Cart total is not equal to the line price");

        // 3. continue the checkout and pay
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Cash on Delivery");
        Thread.sleep(2000);
        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Payment failed after changing the quantity");
    }

    // ================= Reusable steps =================

    // Login with the test account
    public void Login() throws InterruptedException {
        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(3000);
        d.findElement(By.id("email")).sendKeys("customer1@practicesoftwaretesting.com");
        Thread.sleep(1000);
        d.findElement(By.id("password")).sendKeys("AbCdFgHi01@$");
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(3000);
    }

    // Add "Combination Pliers" to the cart
    public void AddRegularProduct() throws InterruptedException {
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(3000);
        d.findElement(By.xpath("//img[@alt='Combination Pliers']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(3000);
    }

    // Cart -> Sign in step -> Billing address -> Payment step (user must be logged in)
    public void GoToPayment() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-1']")).click();
        Thread.sleep(3000);
        d.findElement(By.cssSelector("[data-test='proceed-2']")).click();
        Thread.sleep(3000);

        d.findElement(By.id("country")).sendKeys("Egypt");
        d.findElement(By.id("postal_code")).sendKeys("11511");
        d.findElement(By.id("house_number")).sendKeys("42");
        Thread.sleep(3000);          // the site fills street, city and state automatically

        // delete the auto-filled values and type ours
        ReplaceText("street", "Tahrir Street");
        ReplaceText("city", "Cairo");
        ReplaceText("state", "Cairo");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='proceed-3']")).click();
        Thread.sleep(3000);
    }

    // Delete the old value of a field and type a new one
    public void ReplaceText(String id, String text) {
        d.findElement(By.id(id)).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.id(id)).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.id(id)).sendKeys(text);
    }

    // Change the quantity in the cart page
    public void SetQuantity(String value) throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='product-quantity']")).sendKeys(Keys.CONTROL + "a");
        d.findElement(By.cssSelector("[data-test='product-quantity']")).sendKeys(Keys.BACK_SPACE);
        d.findElement(By.cssSelector("[data-test='product-quantity']")).sendKeys(value);
        d.findElement(By.cssSelector("[data-test='product-quantity']")).sendKeys(Keys.TAB);
        Thread.sleep(3000);
    }

    // "$14.15"  ->  14.15
    public double Money(String text) {
        return Double.parseDouble(text.replace("$", "").replace("-", "").replace(",", "").trim());
    }

    // ================= AFTER =================
    // Runs after every test: close the browser
    @AfterMethod
    public void End() {
        d.quit();
    }

    // assert.assertTrue (Condition is true ?, message);
    // assert.assertFalse (Condition is false ?, message);
    // assert.assertEquals (ExpectedResult, equals ActualResult ?, message);
    // assert.assertNotEquals (ExpectedResult, Not equals ActualResult ?, message);

    // TODO: Bug report + Summary report

}
