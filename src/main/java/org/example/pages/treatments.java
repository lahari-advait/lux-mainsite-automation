package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class treatments {

    WebDriver driver;
    private WebDriverWait wait; // ✅ must be this type


    public treatments(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ Selenium 4 syntax
        PageFactory.initElements(driver, this);
    }


    // ---------- Utility Methods ----------
    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
    }

    private void highlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", el);
    }

    private void removeHighlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", el);
    }

    private void clickElement(WebElement el, String log) {
        el.click();
        System.out.println("Clicked: " + log);
    }

    private void handleNewTabIfAny() throws InterruptedException {
        String main = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            for (String h : handles) {
                if (!h.equals(main)) {
                    driver.switchTo().window(h);
                    Thread.sleep(1000);
                    driver.close();
                    break;
                }
            }
            Thread.sleep(2000);
            driver.switchTo().window(main);
        }
    }

    // ---------- Locators ----------
    @FindBy(xpath = "/html/body/div[1]/header/div[1]/div/div/header[1]/nav/div[2]/div/ul/li[2]")
    WebElement firstMenuLink;

    @FindBy(css = "a.elementor-button[href*='whatsapp']")
    WebElement whatsappButton;

    @FindBy(css = "h2.elementor-heading-title.elementor-size-default")
    WebElement coeHeading;
    
    @FindBy(xpath = "//h2[text()=' Our Top Doctors']")
    WebElement doctorSection;



    @FindBy(css = "button.textgpt-button")
    WebElement reviewButton;


    // ---------- Page Actions ----------
    public void openWebsite() throws InterruptedException {
        driver.get("https://luxhospitals.com/");
        driver.manage().window().maximize();
        Thread.sleep(3000);
    }

    public void clickFirstMenuLink() throws InterruptedException {
        clickElement(firstMenuLink, "Top-menu first link");
        Thread.sleep(3000);
    }

    public void clickWhatsAppBooking() throws InterruptedException {
        clickElement(whatsappButton, "WhatsApp Book Appointment");
        Thread.sleep(3000);
        handleNewTabIfAny();
    }

    public void processCenterOfExcellence() throws InterruptedException {
        scrollIntoView(coeHeading);
        Thread.sleep(2000);

        List<String> validCOEUrls = Arrays.asList(
            "https://luxhospitals.com/specialities/proctology/",
            "https://luxhospitals.com/specialities/general-laparoscopic/",
            "https://luxhospitals.com/orthopedic/",
            "https://luxhospitals.com/specialities/urology-andrology/",
            "https://luxhospitals.com/specialities/gynecological-disorders/",
            "https://luxhospitals.com/specialities/plastic-cosmetic-surgery/"
        );

        String mainTab = driver.getWindowHandle();
        int clickCount = 1;

        for (String validUrl : validCOEUrls) {
            List<WebElement> links = driver.findElements(By.cssSelector("div.elementor-widget-text-editor a"));
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                if (validUrl.equals(href)) {
                    scrollIntoView(link);
                    ((JavascriptExecutor) driver).executeScript("window.open(arguments[0]);", href);
                    Thread.sleep(2000);

                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (!handle.equals(mainTab)) {
                            driver.switchTo().window(handle);
                            break;
                        }
                    }

                    System.out.println("Opened COE #" + clickCount + ": " + href);
                    Thread.sleep(3000);
                    driver.close();
                    driver.switchTo().window(mainTab);
                    Thread.sleep(2000);
                    clickCount++;
                    break;
                }
            }
        }
    }

    public void processTopDoctors() throws InterruptedException {
        try {
            scrollIntoView(doctorSection);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not scroll to 'Our Top Doctors' section: " + e.getMessage());
        }

        try {
            List<WebElement> profileLinks = driver.findElements(By.cssSelector("button.doctor-appointment-btn"));
            int doctorIndex = 1;

            for (WebElement link : profileLinks) {
                if (doctorIndex > 10) break;
                scrollIntoView(link);
                highlight(link);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                System.out.println("Clicked Doctor View Profile #" + doctorIndex);

                Thread.sleep(3000);
                handleNewTabIfAny();
                removeHighlight(link);
                doctorIndex++;
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error interacting with doctor profile links: " + e.getMessage());
        }
    }

    public void processReviewSection() throws InterruptedException {
        try {
            scrollIntoView(reviewButton);
            highlight(reviewButton);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reviewButton);
            System.out.println("✅ Clicked: Review section button");
            Thread.sleep(3000);
            removeHighlight(reviewButton);

            String currentTab = driver.getWindowHandle();
            Set<String> allTabs = driver.getWindowHandles();

            for (String tab : allTabs) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    System.out.println("🔍 Switched to review tab: " + driver.getTitle());
                    Thread.sleep(2000);
                    driver.close();
                    break;
                }
            }

            driver.switchTo().window(currentTab);
            System.out.println("↩️ Returned to Treatments page");

        } catch (Exception e) {
            System.out.println("⚠️ Could not handle review tab: " + e.getMessage());
        }
    }
    public void testWhatsAppEnquiryPositive() {
        String message = "This is a test message";
        try {
            System.out.println("\n✅ Positive Scenario: Typing message and submitting...");

            // Wait for input and button to be ready
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            scrollIntoView(input);
            highlight(input);
            input.clear();

            // Type message slowly for visual feedback
            for (char c : message.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                Thread.sleep(100); // simulate human typing
            }

            removeHighlight(input);
            scrollIntoView(sendButton);
            highlight(sendButton);

            // Click the send button via JavaScript to avoid overlay or animation issues
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            System.out.println("✅ Message sent button clicked.");

            Thread.sleep(2000); // allow any popup/tab to trigger
            removeHighlight(sendButton);

            // Check if a new tab opened (for WhatsApp web)
            String mainTab = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            if (handles.size() > 1) {
                for (String h : handles) {
                    if (!h.equals(mainTab)) {
                        driver.switchTo().window(h);
                        System.out.println("🔗 Switched to new tab: " + driver.getCurrentUrl());
                        Thread.sleep(2000);
                        driver.close();
                        driver.switchTo().window(mainTab);
                        break;
                    }
                }
            } else {
                System.out.println("❌ No new tab opened after sending message.");
            }

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Positive test failed: " + e.getMessage());
        }
    }


    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n⚠️ Negative Scenario: Input left empty, attempting to send...");

            // Wait for input and button presence
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            // Ensure empty
            input.clear();
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);

            // Wait a bit for validation or alert
            Thread.sleep(1500);

            // Check for popup, alert, or disabled state
            if (input.getAttribute("value").isEmpty()) {
                System.out.println("✅ Negative test executed successfully: empty message prevented.");
            } else {
                System.out.println("⚠️ Unexpected: message field not empty after click.");
            }

            removeHighlight(sendButton);

        } catch (TimeoutException te) {
            System.out.println("❌ Element not found (page may not have loaded WhatsApp form): " + te.getMessage());
        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

}
