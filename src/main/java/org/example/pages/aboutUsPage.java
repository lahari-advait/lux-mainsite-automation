package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class aboutUsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // Hover target
    @FindBy(xpath = "//span[normalize-space()='About Us']")
    private WebElement aboutUsMenu;

    public aboutUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // ---------------- Hover Logic ----------------

    /**
     * Hover on About Us menu (UI validation only)
     */
    public void hoverOnAboutUs() {
        actions.moveToElement(aboutUsMenu).perform();
    }

    // ---------------- Navigation Engine ----------------

    private void navigateTo(String url) {
        driver.get(url);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        wait.until(d ->
            ((JavascriptExecutor) d)
                .executeScript("return document.readyState")
                .equals("complete")
        );
    }

    // ---------------- About Us Pages ----------------

    public void openVisionAndMission() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/vision-and-mission/");
    }

    public void openCoreValues() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/core-values/");
    }

    public void openInfrastructure() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/infrastructure/");
    }

    public void openAccreditations() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/accreditations/");
    }

    public void openAdvisoryTeam() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/advisory-team/");
    }

    public void openSuccessStories() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/success-stories/");
    }

    public void openCareers() {
        hoverOnAboutUs();
        navigateTo("https://luxhospitals.com/careers/");
    }
}
