import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class Tests extends TestBase{
    private final static String PAGE_URL = "http://localhost/litecart/public_html/admin/";
    private final static String LOGIN = "admin";
    private final static String PASSWORD = "admin";


    @Test
    public void login() {
        driver.get(PAGE_URL);
        driver.findElement(By.cssSelector("[name=\"username\"]")).sendKeys(LOGIN);
        driver.findElement(By.cssSelector("[name=\"password\"]")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("[name=\"login\"]")).click();
    }

    @Test
    public void sectionTest() {
        login();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-apps-menu li:last-child")));
        String menuItemLocator = "#box-apps-menu";
        String menuItemsLocator = String.format("%s > li", menuItemLocator);
        List<WebElement> menuItems = driver.findElements(By.cssSelector(menuItemsLocator));

        for (int i = 1; i <= menuItems.size(); i++) {
            String itemLocator = String.format("%s > li:nth-child(%s)", menuItemLocator, i);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.format("#box-apps-menu > li:nth-child(%s)", menuItems.size()))));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content")));
            wait.until(wd -> {
                wd.findElement(By.cssSelector(itemLocator)).click();
                return wd.findElement(By.cssSelector(itemLocator)).getAttribute("class").contains("selected");
            });
            String subMenuLocator = " .docs";
            String subMenuItemsLocator = String.format("%s > li", subMenuLocator);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.format("#box-apps-menu li:nth-child(%s)", menuItems.size()))));
            List<WebElement> subMenuItems = wait.ignoring(StaleElementReferenceException.class).until(wd -> driver.findElement(By.cssSelector(itemLocator)).findElements(By.cssSelector(subMenuItemsLocator)));

            if (subMenuItems.size() > 0) {

                for (int subMenuIndex = 1; subMenuIndex <= subMenuItems.size(); subMenuIndex++) {
                    String subMenuItem = String.format("%s > li:nth-child(%s)", subMenuLocator, subMenuIndex);
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(String.format("%s > li:nth-child(%s)", subMenuLocator, subMenuItems.size()))));
                    wait.ignoring(StaleElementReferenceException.class).until(wd -> {
                        driver.findElement(By.cssSelector(itemLocator))
                                .findElement(By.cssSelector(subMenuItem)).click();
                        return wd.findElement(By.cssSelector(itemLocator)).findElement(By.cssSelector(subMenuItem)).getAttribute("class").contains("selected");
                    });

                }
                Assert.assertTrue("The page should contains 'h' tag", areElementsPresent(driver, By.cssSelector("#content h1")));

            }
        }
    }

    @AfterClass
    public static void quit() {
        driver.quit();
    }
}
