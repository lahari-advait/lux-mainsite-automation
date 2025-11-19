package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class accreditationsPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(normalize-space(),'Accreditations')]")
    WebElement pageHeading;

    @FindBy(id = "messageInput")
    WebElement whatsappInput;

    @FindBy(css = "form.luxgpt-input-row button[type='submit']")
    WebElement sendButton;

    public accreditationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            return pageHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Positive Scenario
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            wait.until(ExpectedConditions.visibilityOf(whatsappInput));
            wait.until(ExpectedConditions.elementToBeClickable(sendButton));

            scrollIntoView(whatsappInput);
            highlight(whatsappInput);
            whatsappInput.clear();

            try {
                whatsappInput.sendKeys(message);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));",
                        whatsappInput, message);
            }

            removeHighlight(whatsappInput);
            scrollIntoView(sendButton);
            highlight(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            Thread.sleep(3000);

            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);
            if (!newTabs.isEmpty()) {
                String newTab = newTabs.iterator().next();
                driver.switchTo().window(newTab);
                System.out.println("🔗 New WhatsApp tab opened: " + driver.getCurrentUrl());
                driver.close();
                driver.switchTo().window(oldTabs.iterator().next());
                System.out.println("✅ Closed WhatsApp tab and returned to Accreditations page.");
            } else {
                System.out.println("⚠️ No new tab detected — might open in same window.");
            }

            removeHighlight(sendButton);
            System.out.println("✅ WhatsApp Positive Scenario completed successfully.");

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Positive test failed: " + e.getMessage());
        }
    }

    // ⚠️ Negative Scenario
    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n⚠️ Negative Scenario: Attempting to send empty message...");
            wait.until(ExpectedConditions.visibilityOf(whatsappInput));
            wait.until(ExpectedConditions.visibilityOf(sendButton));

            whatsappInput.clear();
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            Thread.sleep(1500);

            removeHighlight(sendButton);
            System.out.println("✅ WhatsApp Negative test executed successfully.");
        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

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
