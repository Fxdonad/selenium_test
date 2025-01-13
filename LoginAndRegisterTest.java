import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginAndRegisterTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Cấu hình WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://practice.automationtesting.in/");
    }

    // Tạo phương thức chờ cho phần tử
    private WebElement waitForElement(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Phương thức đăng nhập
    private void login(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }

    // Phương thức đăng ký
    private void register(String email, String password) {
        driver.findElement(By.id("reg_email")).sendKeys(email);
        driver.findElement(By.id("reg_password")).sendKeys(password);
        driver.findElement(By.name("register")).click();
    }

    @Test
    public void testValidLogin() {
        try {
            System.out.println("Running Test: Valid Login");
            driver.findElement(By.linkText("My Account")).click();
            login("toquangduc2004@gmail.com", "toquangduc2004@");

            WebElement logoutLink = waitForElement(By.linkText("Logout"), 10);
            Assert.assertTrue(logoutLink.isDisplayed(), "Logout link is not displayed.");
            System.out.println("Test Passed: Valid Login");
        } catch (Exception e) {
            System.err.println("Test Failed: Valid Login - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testInvalidLogin() {
        try {
            System.out.println("Running Test: Invalid Login");
            driver.findElement(By.linkText("My Account")).click();
            login("invaliduser@gmail.com", "InvalidPass");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("ERROR"), "Error message not displayed.");
            System.out.println("Test Passed: Invalid Login");
        } catch (Exception e) {
            System.err.println("Test Failed: Invalid Login - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        try {
            System.out.println("Running Test: Registration With Existing Email");
            driver.findElement(By.linkText("My Account")).click();
            register("toquangduc2004@gmail.com", "toquangduc2004Duc@");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("An account is already registered with your email address."),
                    "Error message for existing email is not displayed.");
            System.out.println("Test Passed: Registration With Existing Email");
        } catch (Exception e) {
            System.err.println("Test Failed: Registration With Existing Email - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testRegistrationWithWeakPassword() {
        try {
            System.out.println("Running Test: Registration With Weak Password");
            driver.findElement(By.linkText("My Account")).click();
            register("newuser@gmail.com", "123");

            WebElement errorMessage = waitForElement(By.cssSelector(".woocommerce-error li"), 10);
            Assert.assertTrue(errorMessage.getText().contains("Your password is too weak"),
                    "Error message for weak password is not displayed.");
            System.out.println("Test Passed: Registration With Weak Password");
        } catch (Exception e) {
            System.err.println("Test Failed: Registration With Weak Password - " + e.getClass().getSimpleName() + " - " + e.getMessage());
            Assert.fail();
        }
    }

    @AfterMethod
    public void teardown() {
        // Đóng trình duyệt sau mỗi bài kiểm thử
        if (driver != null) {
            driver.quit();
        }
    }
}
