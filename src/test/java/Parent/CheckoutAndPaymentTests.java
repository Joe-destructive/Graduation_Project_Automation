package Parent;



import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class CheckoutAndPaymentTests {

    WebDriver d;

    // ================= BEFORE =================
    // Runs before every test: open a new browser, login
    @BeforeMethod
    public void Preconditions() throws InterruptedException {
        d = new ChromeDriver();
        d.manage().window().maximize();
        d.navigate().to("https://practicesoftwaretesting.com/auth/login");
        Thread.sleep(5000);          // wait for the login page to load

        d.findElement(By.id("email")).sendKeys("Y@gmail.com");
        Thread.sleep(1000);
        d.findElement(By.id("password")).sendKeys("Youssef@123456789123456789");
        Thread.sleep(1000);
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='login-submit']")).click();
        Thread.sleep(5000);
    }

    // ================= TESTS =================

    // TC57 - Verify pay with all valid credit card data
    @Test(priority = 1)
    public void PayWithValidCreditCard() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Credit Card");
        Thread.sleep(2000);

        d.findElement(By.id("credit_card_number")).sendKeys("1111-1111-1111-1111");
        Thread.sleep(1000);
        d.findElement(By.id("expiration_date")).sendKeys("08/2028");
        Thread.sleep(1000);
        d.findElement(By.id("cvv")).sendKeys("123");
        Thread.sleep(1000);
        d.findElement(By.id("card_holder_name")).sendKeys("islam ismail");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Success message was not displayed after paying with credit card");
    }

    // TC61 - Verify pay with valid gift card
    @Test(priority = 2)
    public void PayWithValidGiftCard() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Gift Card");
        Thread.sleep(2000);

        d.findElement(By.id("gift_card_number")).sendKeys("1234456783654272");
        Thread.sleep(1000);
        d.findElement(By.id("validation_code")).sendKeys("1234");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Success message was not displayed after paying with gift card");
    }

    // TC63 - Verify success message after payment (Cash on Delivery)
    @Test(priority = 3)
    public void SuccessMessageCashOnDelivery() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Cash on Delivery");
        Thread.sleep(2000);

        d.findElement(By.cssSelector("[data-test='finish']")).click();
        Thread.sleep(2000);

        String msg = d.findElement(By.cssSelector("[data-test='payment-success-message']")).getText();
        Assert.assertEquals(msg, "Payment was successful",
                "Success message was not displayed after Cash on Delivery");
    }

    // TC33 - Verify 15% discount when buying Regular + Rental products together
    @Test(priority = 4)
    public void DiscountRegularPlusRental() throws InterruptedException {

        AddRegularProduct();
        AddRentalProduct();

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(10000);

        // example: "$148.51"  and  "- $22.28"  ->  148.51  and  22.28
        double subtotal = Double.parseDouble(d.findElement(By.cssSelector("[data-test='cart-subtotal']"))
                .getText().replace("$", "").replace("-", "").trim());
        double discount = Double.parseDouble(d.findElement(By.cssSelector("[data-test='cart-discount']"))
                .getText().replace("$", "").replace("-", "").trim());
        double total = Double.parseDouble(d.findElement(By.cssSelector("[data-test='cart-total']"))
                .getText().replace("$", "").replace("-", "").trim());

        // 15% of the subtotal (0.02 = allowed rounding difference)
        Assert.assertEquals(discount, subtotal * 0.15, 0.02,
                "Discount is not 15% of the subtotal");
        Assert.assertEquals(total, subtotal - discount, 0.02,
                "Total is not equal to subtotal - discount");
    }

    // TC36 - Verify discount disappears when removing rental product
    @Test(priority = 5)
    public void DiscountDisappearsAfterRemovingRental() throws InterruptedException {

        AddRegularProduct();
        AddRentalProduct();

        d.findElement(By.cssSelector("[data-test='nav-cart']")).click();
        Thread.sleep(10000);

        // discount is displayed before removing
        Assert.assertEquals(d.findElements(By.cssSelector("[data-test='cart-discount']")).size(), 1,
                "Discount was not displayed for Regular + Rental products");

        // click the red X in the Excavator row
        d.findElement(By.xpath("//tr[.//span[contains(text(),'Excavator')]]//a[contains(@class,'btn-danger')]")).click();
        Thread.sleep(10000);

        // discount row must disappear
        Assert.assertEquals(d.findElements(By.cssSelector("[data-test='cart-discount']")).size(), 0,
                "Discount is still displayed after removing the rental product");
    }

    // TC28 - Verify leave Credit Card Number field empty   (expected FAIL -> bug)
    @Test(priority = 6)
    public void CreditCardNumberEmpty() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Credit Card");
        Thread.sleep(2000);

        // leave card number empty
        d.findElement(By.id("credit_card_number")).click();
        d.findElement(By.id("credit_card_number")).sendKeys(Keys.TAB);
        Thread.sleep(1000);

        d.findElement(By.id("expiration_date")).sendKeys("08/2028");
        Thread.sleep(1000);
        d.findElement(By.id("cvv")).sendKeys("123");
        Thread.sleep(1000);
        d.findElement(By.id("card_holder_name")).sendKeys("islam ismail");
        Thread.sleep(2000);

        // the validation message that appears under the card number field
        String error = d.findElement(By.xpath("//input[@id='credit_card_number']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();

        Assert.assertTrue(error.toLowerCase().contains("required"),
                "Expected 'required' message but the actual message is: " + error);
    }

    // TC43 - Verify enter expired credit card date
    @Test(priority = 7)
    public void ExpiredCardDate() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Credit Card");
        Thread.sleep(2000);

        d.findElement(By.id("credit_card_number")).sendKeys("1111-1111-1111-1111");
        Thread.sleep(1000);
        d.findElement(By.id("expiration_date")).sendKeys("01/2020");
        d.findElement(By.id("expiration_date")).sendKeys(Keys.TAB);
        Thread.sleep(1000);
        d.findElement(By.id("cvv")).sendKeys("123");
        Thread.sleep(1000);
        d.findElement(By.id("card_holder_name")).sendKeys("islam ismail");
        Thread.sleep(2000);

        String error = d.findElement(By.xpath("//input[@id='expiration_date']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();

        Assert.assertTrue(error.contains("must be in the future"),
                "Expired date message was not displayed. Actual message: " + error);
    }

    // TC60 - Verify leave all bank fields empty   (expected FAIL -> bug)
    @Test(priority = 8)
    public void BankTransferAllFieldsEmpty() throws InterruptedException {

        AddRegularProduct();
        GoToPayment();

        d.findElement(By.id("payment-method")).sendKeys("Bank Transfer");
        Thread.sleep(2000);

        // click inside each field and leave it empty
        d.findElement(By.id("bank_name")).click();
        d.findElement(By.id("bank_name")).sendKeys(Keys.TAB);
        d.findElement(By.id("account_name")).click();
        d.findElement(By.id("account_name")).sendKeys(Keys.TAB);
        d.findElement(By.id("account_number")).click();
        d.findElement(By.id("account_number")).sendKeys(Keys.TAB);
        Thread.sleep(2000);

        String bankError = d.findElement(By.xpath("//input[@id='bank_name']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();
        String nameError = d.findElement(By.xpath("//input[@id='account_name']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();
        String numberError = d.findElement(By.xpath("//input[@id='account_number']"
                + "/following::div[contains(@class,'ng-star-inserted')][normalize-space(text())][1]")).getText();

        Assert.assertTrue(bankError.contains("required")
                        && nameError.contains("required")
                        && numberError.contains("required"),
                "Expected 'required' message at all bank fields. Actual: Bank Name = [" + bankError
                        + "] | Account Name = [" + nameError
                        + "] | Account Number = [" + numberError + "]");
    }

    // ================= Reusable steps =================

    // Add "Combination Pliers" (Regular product) to the cart
    public void AddRegularProduct() throws InterruptedException {
        d.navigate().to("https://practicesoftwaretesting.com/");
        Thread.sleep(5000);
        d.findElement(By.xpath("//img[@alt='Combination Pliers']")).click();
        Thread.sleep(5000);
        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(5000);
    }

    // Add "Excavator" (Rental product) to the cart
    public void AddRentalProduct() throws InterruptedException {
        d.findElement(By.cssSelector("[data-test='nav-categories']")).click();
        Thread.sleep(1000);
        d.findElement(By.cssSelector("[data-test='nav-rentals']")).click();
        Thread.sleep(10000);
        d.findElement(By.xpath("//img[@alt='Excavator']")).click();
        Thread.sleep(10000);
        d.findElement(By.cssSelector("[data-test='add-to-cart']")).click();
        Thread.sleep(10000);
    }

    // Cart -> Sign in step -> Billing address -> Payment step
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

    // ================= AFTER =================
    // Runs after every test: close the browser
    @AfterMethod
    public void End() {
        d.quit();
    }



}
