package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    // ⏱ Default explicit wait = 12 seconds
    private static final int DEFAULT_TIMEOUT = 12;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    // -------------------------------------------------------
    // ELEMENT WAITING UTILITIES
    // -------------------------------------------------------

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // -------------------------------------------------------
    // PAGE & JS WAITS
    // -------------------------------------------------------

    // Wait until document.readyState = complete
    public void waitForPageToLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    // Wait for jQuery AJAX (if used in website)
    public void waitForJQuery() {
        try {
            wait.until((ExpectedCondition<Boolean>) d ->
                    (Boolean) ((JavascriptExecutor) d)
                            .executeScript("return window.jQuery != null && jQuery.active === 0"));
        } catch (Exception ignored) {
        }
    }

    // Combined page + AJAX wait
    public void waitForFullLoad() {
        waitForPageToLoad();
        waitForJQuery();
    }

    // -------------------------------------------------------
    // GENERIC SAFE ACTION HELPERS
    // -------------------------------------------------------

    public void safeClick(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (StaleElementReferenceException e) {
            waitForClickable(locator).click();
        }
    }

    public void safeType(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String safeGetText(By locator) {
        return waitForVisible(locator).getText();
    }

    public void scrollIntoView(By locator) {
        WebElement element = waitForVisible(locator);
        scrollIntoView(element);
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // -------------------------------------------------------
    // WAIT FOR URL
    // -------------------------------------------------------

    public void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    public void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

}
