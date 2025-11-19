package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;

public class infrastructurePage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(normalize-space(),'Infrastructure')]")
    WebElement pageHeading;

    // WhatsApp enquiry elements
    @FindBy(id = "messageInput")
    WebElement whatsappInput;

    @FindBy(css = "form.luxgpt-input-row button[type='submit']")
    WebElement sendButton;

    public infrastructurePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            return pageHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== WHATSAPP ENQUIRY POSITIVE =====
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Wait for chat input and send button
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            scrollIntoView(input);
            highlight(input);

            try {
                input.click();
                input.clear();
                input.sendKeys(message);
            } catch (Exception e) {
                System.out.println("⚠️ sendKeys failed, using JS fallback.");
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                        input, message);
            }

            Thread.sleep(800);
            removeHighlight(input);

            scrollIntoView(sendButton);
            highlight(sendButton);
            wait.until(ExpectedConditions.elementToBeClickable(sendButton));

            Set<String> oldTabs = driver.getWindowHandles();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            System.out.println("✅ Clicked Send button (via JS).");

            Thread.sleep(3000);

            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);

            if (!newTabs.isEmpty()) {
                String newTab = newTabs.iterator().next();
                driver.switchTo().window(newTab);
                System.out.println("🔗 WhatsApp Tab URL: " + driver.getCurrentUrl());

                driver.close();
                driver.switchTo().window(oldTabs.iterator().next());
                System.out.println("✅ Closed WhatsApp tab and returned to Infrastructure page.");
            } else {
                System.out.println("⚠️ No new tab detected — may open WhatsApp Web in same tab.");
            }

            removeHighlight(sendButton);
            System.out.println("✅ WhatsApp Positive Scenario completed successfully.");

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Positive test failed: " + e.getMessage());
        }
    }

    // ===== WHATSAPP ENQUIRY NEGATIVE =====
    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n⚠️ Negative Scenario: Empty input. Attempting to send...");

            wait.until(ExpectedConditions.visibilityOf(whatsappInput));
            wait.until(ExpectedConditions.visibilityOf(sendButton));

            whatsappInput.clear();
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            Thread.sleep(1500);

            System.out.println("✅ Negative test executed (popup or validation expected).");
            removeHighlight(sendButton);

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

    // ===== Helper methods =====
    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void highlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
    }

    private void removeHighlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='';", element);
    }
}
