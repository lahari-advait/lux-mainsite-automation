package org.example.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    protected void scroll(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", element);
    }

    protected void clickJS(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}
