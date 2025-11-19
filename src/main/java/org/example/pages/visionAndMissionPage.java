package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class visionAndMissionPage {

    WebDriver driver;
    WebDriverWait wait;

    public visionAndMissionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== ELEMENTS =====
    @FindBy(xpath = "//a[contains(@href,'wa.me') and contains(@class,'elementor-button')]")
    WebElement bookAppointmentDesktop;

    @FindBy(xpath = "//a[contains(@href,'tel:') and contains(@class,'elementor-button')]")
    WebElement bookAppointmentMobile;

    @FindBy(xpath = "//h1[contains(normalize-space(),'About Us')]")
    WebElement aboutUsHeading;

    // WhatsApp enquiry elements
    @FindBy(id = "messageInput")
    WebElement whatsappInput;

    @FindBy(css = "form.luxgpt-input-row button[type='submit']")
    WebElement sendButton;

    // ===== UTILITIES =====
    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void highlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
    }

    private void removeHighlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='';", element);
    }

    // ===== PAGE VALIDATION =====
    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(aboutUsHeading));
            return aboutUsHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== BOOK APPOINTMENT FLOW =====
    public boolean clickBookAppointment() {
        String parentWindow = driver.getWindowHandle();
        Set<String> beforeClick = driver.getWindowHandles();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            WebElement target = null;
            if (bookAppointmentDesktop.isDisplayed()) {
                target = bookAppointmentDesktop;
            } else if (bookAppointmentMobile.isDisplayed()) {
                target = bookAppointmentMobile;
            }

            if (target == null) throw new NoSuchElementException("No visible 'Book Appointment' button found!");

            scrollIntoView(target);
            js.executeScript("arguments[0].click();", target);

            // Wait for new tab
            wait.until(driver -> driver.getWindowHandles().size() > beforeClick.size());

            Set<String> afterClick = driver.getWindowHandles();
            afterClick.removeAll(beforeClick);

            if (!afterClick.isEmpty()) {
                String newTab = afterClick.iterator().next();
                driver.switchTo().window(newTab);
                System.out.println("✅ Switched to new WhatsApp tab: " + driver.getCurrentUrl());
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to click 'Book Appointment': " + e.getMessage());
        }
        return false;
    }

    public void closeNewTabAndReturn() {
        String originalWindow = driver.getWindowHandles().iterator().next();
        try {
            driver.close();
            driver.switchTo().window(originalWindow);
            wait.until(ExpectedConditions.visibilityOf(aboutUsHeading));
            System.out.println("✅ Closed WhatsApp tab and returned to Vision & Mission page.");
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to close WhatsApp tab or switch back: " + e.getMessage());
        }
    }

    // ===== WHATSAPP ENQUIRY POSITIVE =====
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            wait.until(ExpectedConditions.visibilityOf(whatsappInput));
            wait.until(ExpectedConditions.elementToBeClickable(sendButton));

            whatsappInput.clear();

            for (char c : message.toCharArray()) {
                whatsappInput.sendKeys(String.valueOf(c));
                Thread.sleep(150); // simulate typing
            }

            System.out.println("✅ Positive Scenario: Message entered visibly.");
            scrollIntoView(sendButton);
            highlight(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            sendButton.click();
            Thread.sleep(2000);
            removeHighlight(sendButton);

            // Verify new tab
            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);
            if (!newTabs.isEmpty()) {
                String newTab = newTabs.iterator().next();
                driver.switchTo().window(newTab);
                System.out.println("🔗 Positive Scenario: New Tab URL - " + driver.getCurrentUrl());
                Thread.sleep(2000);
                driver.close();
                driver.switchTo().window(oldTabs.iterator().next());
            } else {
                System.out.println("❌ Positive Scenario failed: No tab opened after sending message.");
            }

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Positive test failed: " + e.getMessage());
        }
    }

    // ===== WHATSAPP ENQUIRY NEGATIVE =====
    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n⚠️ Negative Scenario: Input is empty. Attempting to send...");

            wait.until(ExpectedConditions.visibilityOf(whatsappInput));
            wait.until(ExpectedConditions.visibilityOf(sendButton));

            whatsappInput.clear();
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            Thread.sleep(1500);

            System.out.println("✅ WhatsApp Negative test executed (popup or warning should appear).");
            removeHighlight(sendButton);

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }
}
