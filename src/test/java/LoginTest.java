import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    private final static String PAGE_URL = "http://localhost/litecart/public_html/admin/";
    private final static String LOGIN = "admin";
    private final static String PASSWORD = "admin";
    static WebDriver driver;

    @BeforeClass
    public static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void login() {
        driver.get(PAGE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("[name=\"username\"]")).sendKeys(LOGIN);
        driver.findElement(By.cssSelector("[name=\"password\"]")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("[name=\"login\"]")).click();
    }

    @AfterClass
    public static void quit() {
        driver.quit();
    }
}
