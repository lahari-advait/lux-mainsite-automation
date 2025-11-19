package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class advisoryTeamPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(normalize-space(),'Advisory Team')]")
    WebElement pageHeading;

    public advisoryTeamPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            return pageHeading.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ Advisory Team page heading not visible: " + e.getMessage());
            return false;
        }
    }

    public void goBackToHome() {
        try {
            driver.navigate().back();
            System.out.println("✅ Returned to previous page after Advisory Team verification.");
        } catch (Exception e) {
            System.out.println("⚠️ Could not navigate back properly: " + e.getMessage());
        }
    }
}
