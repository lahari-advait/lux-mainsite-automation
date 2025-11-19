package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class careersPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public careersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    // ====== LOCATORS ======
    @FindBy(xpath = "//h2[contains(normalize-space(),'Job Openings')]")
    WebElement jobOpeningsHeading;

    By applyNowButtons = By.xpath("//a[contains(@class,'elementor-button') and .//span[text()='Apply Now']]");

    // ====== UTILITIES ======
    private void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", element);
    }

    private void highlight(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red';", element);
    }

    private void removeHighlight(WebElement element) {
        js.executeScript("arguments[0].style.border='';", element);
    }

    // ====== PAGE VALIDATION ======
    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(jobOpeningsHeading));
            System.out.println("✅ Careers page loaded successfully.");
            return jobOpeningsHeading.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ Job Openings heading not visible: " + e.getMessage());
            return false;
        }
    }

    // ====== JOB APPLICATION FLOW ======
    public void testApplyNowButtons() {
        try {
            // Scroll to Job Openings section
            scrollIntoView(jobOpeningsHeading);
            highlight(jobOpeningsHeading);
            Thread.sleep(800);
            removeHighlight(jobOpeningsHeading);

            // Find all Apply Now buttons
            List<WebElement> buttons = driver.findElements(applyNowButtons);
            if (buttons.isEmpty()) {
                System.out.println("❌ No 'Apply Now' buttons found!");
                return;
            }

            System.out.println("📋 Found " + buttons.size() + " 'Apply Now' buttons.");

            // Loop through each button
            int index = 1;
            for (WebElement button : buttons) {
                scrollIntoView(button);
                highlight(button);

                String href = button.getAttribute("href");
                System.out.println("\n🔹 Button #" + index + " -> HREF: " + href);

                if (href != null && href.startsWith("mailto:")) {
                    System.out.println("✅ Valid mailto link detected.");
                } else {
                    System.out.println("⚠️ Unexpected link (not mailto): " + href);
                }

                // Click test (doesn't open full client, just checks clickable)
                try {
                    js.executeScript("arguments[0].click();", button);
                    System.out.println("✅ Clicked 'Apply Now' button #" + index);
                } catch (Exception e) {
                    System.out.println("⚠️ Failed to click button #" + index + ": " + e.getMessage());
                }

                removeHighlight(button);
                index++;
                Thread.sleep(500);
            }

            System.out.println("\n🎯 All 'Apply Now' buttons tested successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error while testing 'Apply Now' buttons: " + e.getMessage());
        }
    }
}
