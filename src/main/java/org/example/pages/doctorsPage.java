package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class doctorsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // Doctor names list
    public static final String[] DOCTORS = {
        "Dr. Samhitha Reddy",
        "Dr. Abhishek Katha",
        "Dr. Samhitha Alukur",
        "Dr. Harshita Kakarla",
        "Dr. P Pragnia",
        "Dr. Priyank Salecha",
        "Dr. Chandana Guduru",
        "Dr. M. Ram Prabhu",
        "Dr. Madhan Mohan",
        "Dr. Sai Kishan Sirasala",
        "Dr. Bathini Hithesh",
        "Dt. Kruthi Goud"
    };

    // Locators
    private By doctorsMenu = By.cssSelector("li.has_megamenu[data-menu='doctors'] a");
    private By doctorCards = By.cssSelector("div.doctor-card");
    private By dropdown = By.cssSelector("li.has_megamenu[data-menu='doctors'] svg.arrow_icons");

    public doctorsPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("❌ WebDriver is null. Check BaseTest.setUp()");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    // ✅ Navigate to Doctors page
    public void openDoctorsPage() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(doctorsMenu));
        scrollIntoView(menu);
        actions.moveToElement(menu).perform();   // 👈 Hover before click
        actions.click(menu).perform();
        wait.until(ExpectedConditions.urlContains("/doctors"));
    }

    // ✅ Return all doctor cards
    public List<WebElement> getDoctorCards() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(doctorCards));
    }

    // ✅ Find doctor card by name
    public WebElement findDoctorCardByName(List<WebElement> cards, String doctorName) {
        for (WebElement card : cards) {
            if (card.getText().contains(doctorName)) {
                return card;
            }
        }
        return null;
    }

    // ✅ Scroll into view
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);
    }

    // ✅ Generic click handler (handles new tab or back)
    private void clickAndHandle(WebElement link, String label) {
        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(link));
        scrollIntoView(link);

        // 👇 Hover before click
        actions.moveToElement(link).perform();

        try {
            link.click();
            System.out.println("✅ Clicked: " + label);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            System.out.println("✅ JS Click fallback: " + label);
        }

        // Handle new tab
        try {
            wait.until(d -> driver.getWindowHandles().size() > 1 || !driver.getCurrentUrl().contains("/doctors"));

            Set<String> windows = driver.getWindowHandles();
            if (windows.size() > 1) {
                for (String w : windows) {
                    if (!w.equals(originalWindow)) {
                        driver.switchTo().window(w);
                        wait.until(d -> ((JavascriptExecutor) d)
                            .executeScript("return document.readyState").equals("complete"));
                        Thread.sleep(1000);
                        driver.close();
                        Thread.sleep(1000);
                        driver.switchTo().window(originalWindow);
                        System.out.println("🔙 Closed new tab after: " + label);
                        break;
                    }
                }
            } else {
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(doctorCards));
                System.out.println("⬅️ Navigated back after: " + label);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Navigation issue after: " + label + " → " + e.getMessage());
        }
    }
 // ✅ Click "View Profile" and handle new tab
    public void clickViewProfile(WebElement card, String doctorName) throws InterruptedException {
        clickDoctorButton(card, doctorName, "viewProfile");
        Thread.sleep(1000);
        
    }

    // ✅ Click "Book Appointment" and handle new tab
    public void clickBookAppointment(WebElement card, String doctorName) throws InterruptedException {
        clickDoctorButton(card, doctorName, "bookAppointment");
        Thread.sleep(1000);
    }

    // ✅ Generic method to click doctor buttons (View Profile / Book Appointment)
    private void clickDoctorButton(WebElement card, String doctorName, String buttonType) {
        WebElement link;
        if (buttonType.equalsIgnoreCase("viewProfile")) {
            link = card.findElement(By.cssSelector("div.doctor-buttons a:nth-child(1)"));
        } else if (buttonType.equalsIgnoreCase("bookAppointment")) {
            link = card.findElement(By.cssSelector("div.doctor-buttons a:nth-child(2)"));
        } else {
            throw new IllegalArgumentException("Invalid button type: " + buttonType);
        }

        scrollIntoView(card);
        actions.moveToElement(card).perform(); // hover card
        wait.until(ExpectedConditions.elementToBeClickable(link));

        String parentWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        // Click the link
        try {
            link.click();
            System.out.println("✅ Clicked " + buttonType + " for " + doctorName);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            System.out.println("✅ JS Click fallback for " + buttonType + " of " + doctorName);
        }

        // Wait for new tab
        wait.until(d -> driver.getWindowHandles().size() > oldWindows.size());

        // Switch to new tab
        Set<String> newWindows = driver.getWindowHandles();
        newWindows.removeAll(oldWindows); // get only the new tab
        if (!newWindows.isEmpty()) {
            String newTab = newWindows.iterator().next();
            driver.switchTo().window(newTab);
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            System.out.println("🔗 New Tab URL: " + driver.getCurrentUrl());
            driver.close(); // close new tab
            driver.switchTo().window(parentWindow); // go back to parent
            System.out.println("⬅️ Returned to parent window after " + buttonType);
        } else {
            System.out.println("❌ No new tab opened for " + buttonType + " of " + doctorName);
        }
    }

    // ✅ WhatsApp enquiry – Positive scenario
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear();

            // Type character by character (optional effect)
            for (char c : message.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                Thread.sleep(150); // can be replaced with explicit waits
            }
            System.out.println("✅ Positive Scenario: Message entered visibly.");

            scrollIntoView(sendButton);
            //highlight(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            sendButton.click();
            Thread.sleep(2000); // ⚠️ replace with WebDriverWait if stable selector available
            //removeHighlight(sendButton);

            // Verify new tab opened
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

    // ✅ WhatsApp enquiry – Negative scenario
    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n❓ Negative Scenario: Input is empty. Attempting to send...");

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear(); // ensure empty
            scrollIntoView(sendButton);
           // highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);

            Thread.sleep(1500); // ⚠️ ideally replace with explicit wait for popup validation

            System.out.println("✅ WhatsApp Negative test executed (popup should appear).");
            removeHighlight(sendButton);

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

    // 👉 Utility: highlight element
    private void highlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    // 👉 Utility: remove highlight
    private void removeHighlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
    }

    // ✅ Doctors dropdown arrow
    public void clickDoctorsDropdown() {
        WebElement drop = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        scrollIntoView(drop);
        actions.moveToElement(drop).perform();   // 👈 Hover
        drop.click();
        System.out.println("✅ Clicked Doctors dropdown arrow");
    }
}
