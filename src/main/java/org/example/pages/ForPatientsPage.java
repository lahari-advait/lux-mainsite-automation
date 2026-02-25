package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForPatientsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public ForPatientsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    /* ========= LOCATORS (SCOPED) ========= */

    // Hover/click target for "For Patients"
    private final By forPatientsHoverTarget =
            By.xpath("//span[normalize-space()='For Patients']/ancestor::li[contains(@class,'dropdown__item')][1]" +
                    "//*[contains(@class,'nav__link') or self::a or self::button][1]");

    // Dropdown menu inside For Patients
    private final By forPatientsDropdown =
            By.xpath("//span[normalize-space()='For Patients']/ancestor::li[contains(@class,'dropdown__item')][1]" +
                    "//ul[contains(@class,'dropdown__menu')]");

    // Stable item to confirm dropdown opened
    private final By insuranceLink =
            By.xpath("//span[normalize-space()='For Patients']/ancestor::li[contains(@class,'dropdown__item')][1]" +
                    "//ul[contains(@class,'dropdown__menu')]//a[normalize-space()='Insurance']");

    /* ========= CORE ACTIONS ========= */

    public void openForPatientsDropdown() {
        WebElement hoverTarget = wait.until(ExpectedConditions.visibilityOfElementLocated(forPatientsHoverTarget));

        // Try hover first
        try {
            actions.moveToElement(hoverTarget).perform();
            wait.until(ExpectedConditions.visibilityOfElementLocated(insuranceLink));
        } catch (TimeoutException e) {
            // Fallback to click (touch/mobile behavior)
            safeClick(hoverTarget);
            wait.until(ExpectedConditions.visibilityOfElementLocated(insuranceLink));
        }
    }

    public boolean isDropdownVisible() {
        openForPatientsDropdown();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(forPatientsDropdown)).isDisplayed();
    }

    /** Generic click for any dropdown item by visible label */
    public void clickForPatientsItem(String label) {
        openForPatientsDropdown();

        By item = By.xpath("//span[normalize-space()='For Patients']/ancestor::li[contains(@class,'dropdown__item')][1]" +
                "//ul[contains(@class,'dropdown__menu')]//a[normalize-space()='" + label + "']");

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(item));
        safeClick(el);
    }

    /* ========= Typed navigation helpers ========= */

    public InsurancePage openInsurancePage() {
        clickForPatientsItem("Insurance");
        return new InsurancePage(driver);
    }

    public BlogsPage openBlogsPage() {
        clickForPatientsItem("Blogs");
        return new BlogsPage(driver);
    }

    public SecondOpinionPage openSecondOpinionPage() {
        clickForPatientsItem("Second Opinion");
        return new SecondOpinionPage(driver);
    }

    /* ========= Safe click ========= */

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}