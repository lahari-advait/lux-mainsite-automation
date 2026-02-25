package org.example.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class advisoryTeamPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public advisoryTeamPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Advisory Team page load validation
     * Uses URL + document readiness (CMS-safe)
     */
    public boolean isPageLoaded() {
        try {
            // Wait for full page load
            wait.until(d ->
                ((JavascriptExecutor) d)
                    .executeScript("return document.readyState")
                    .equals("complete")
            );

            return driver.getCurrentUrl().contains("/advisory-team");

        } catch (Exception e) {
            System.out.println("❌ Advisory Team page validation failed: " + e.getMessage());
            return false;
        }
    }

    public void goBackToHome() {
        driver.navigate().back();
    }
}
