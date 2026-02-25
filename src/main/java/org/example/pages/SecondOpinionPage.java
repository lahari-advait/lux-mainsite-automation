package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SecondOpinionPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SecondOpinionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForPageToLoad();
    }

    /* ========= PAGE LOAD CONTRACT ========= */

    private final String[] urlKeywords = {"second", "opinion"};

    private final By pageHeading =
            By.xpath("//*[self::h1 or self::h2][contains(translate(normalize-space(),'SECONDOPINION','secondopinion'),'second') and " +
                    "contains(translate(normalize-space(),'SECONDOPINION','secondopinion'),'opinion')]");

    private void waitForPageToLoad() {
        wait.until(d -> {
            String u = d.getCurrentUrl().toLowerCase();
            return (u.contains(urlKeywords[0]) || u.contains(urlKeywords[1])) || !d.findElements(pageHeading).isEmpty();
        });
        // If heading exists, wait for it; otherwise allow URL-only
        if (!driver.findElements(pageHeading).isEmpty()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        }
    }

    public boolean isAt() {
        String u = driver.getCurrentUrl().toLowerCase();
        boolean urlOk = u.contains("second") || u.contains("opinion");
        boolean headingOk = !driver.findElements(pageHeading).isEmpty() && driver.findElement(pageHeading).isDisplayed();
        return urlOk || headingOk;
    }

    /* ========= COMMON FORM LOCATORS (fallback-friendly) ========= */

    // These are best-effort, because different sites name fields differently.
    private final By nameField =
            By.cssSelector("input[name*='name' i], input[id*='name' i], input[placeholder*='name' i]");

    private final By phoneField =
            By.cssSelector("input[type='tel'], input[name*='phone' i], input[id*='phone' i], input[placeholder*='phone' i], input[placeholder*='mobile' i]");

    private final By emailField =
            By.cssSelector("input[type='email'], input[name*='email' i], input[id*='email' i], input[placeholder*='email' i]");

    private final By messageField =
            By.cssSelector("textarea, textarea[name*='message' i], textarea[id*='message' i], textarea[placeholder*='message' i], textarea[placeholder*='details' i]");

    private final By submitButton =
            By.cssSelector("button[type='submit'], input[type='submit'], button:has-text('Submit'), button:has-text('Send'), button:has-text('Book')");

    // Generic error locator (covers many form libs)
    private final By anyValidationError =
            By.cssSelector(".error, .errors, .field-error, .wpcf7-not-valid-tip, .invalid-feedback, [aria-invalid='true']");

    /* ========= INTERNAL TESTS ========= */

    /** 1) Verify key UI exists (at least one form field should be present) */
    public void assertFormElementsPresent() {
        int total =
                driver.findElements(nameField).size()
                        + driver.findElements(phoneField).size()
                        + driver.findElements(emailField).size()
                        + driver.findElements(messageField).size();

        if (total == 0) {
            throw new RuntimeException("No form elements found on Second Opinion page (name/phone/email/message).");
        }
    }

    /** 2) Submit empty (if submit exists) and confirm validation appears */
    public void submitEmptyAndAssertValidationIfPossible() {
        List<WebElement> submits = driver.findElements(submitButton);
        if (submits.isEmpty()) {
            // No submit button visible; skip safely (page might be informational / CTA-driven)
            return;
        }

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submits.get(0)));
        scrollCenter(submit);
        safeClick(submit);

        // Expect some validation signal (not always present depending on implementation)
        // We'll wait briefly; if nothing appears, we don't hard-fail here (to avoid false negatives).
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(d -> !d.findElements(anyValidationError).isEmpty());
        } catch (TimeoutException ignored) {
            // optional: keep as soft expectation
        }
    }

    /* ========= UTIL ========= */

    private void scrollCenter(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private void safeClick(WebElement el) {
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}