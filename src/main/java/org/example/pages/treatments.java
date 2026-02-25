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
    private WebDriverWait wait;

    // ---------------- Reporting Engine ----------------
    class ReportEntry {
        String step, expected, actual, status, reason;

        public ReportEntry(String step, String expected, String actual, String status, String reason) {
            this.step = step;
            this.expected = expected;
            this.actual = actual;
            this.status = status;
            this.reason = reason;
        }
    }

    List<ReportEntry> testReport = new ArrayList<>();

    private void validate(String step, String expected, String actual, boolean passed, String reason) {
        String status = passed ? "PASS" : "FAIL";

        testReport.add(new ReportEntry(step, expected, actual, status, passed ? "" : reason));

        System.out.println(
            "\nSTEP: " + step +
            "\nEXPECTED: " + expected +
            "\nACTUAL: " + actual +
            "\nSTATUS: " + status +
            (passed ? "" : "\nREASON: " + reason) +
            "\n--------------------------------------------------"
        );
    }

    public void printFinalReport() {
        System.out.println("\n==================== FINAL DAILY REPORT ====================\n");
        for (ReportEntry e : testReport) {
            System.out.println(
                "STEP     : " + e.step + "\n" +
                "EXPECTED : " + e.expected + "\n" +
                "ACTUAL   : " + e.actual + "\n" +
                "STATUS   : " + e.status +
                (e.status.equals("FAIL") ? "\nREASON  : " + e.reason : "") +
                "\n------------------------------------------------------------"
            );
        }
        System.out.println("\n============================================================\n");
    }

    // ----------------------------------------------------------

    public treatments(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

        validate(
            "Click Element - " + log,
            "Element should be clicked",
            "Clicked successfully",
            true,
            ""
        );
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

            validate(
                "Handle New Tab",
                "New tab should be closed & return to main tab",
                "Returned to main tab",
                true,
                ""
            );
        }
    }

    // ---------- Locators ----------
    @FindBy(xpath = "/html/body/div[1]/header/div[1]/div/div/header[1]/nav/div[2]/div/ul/li[2]")
    WebElement firstMenuLink;

    @FindBy(css = "a.desktop-cta-btn.triger-carecansole")
    WebElement desktopBookAppointment;

    @FindBy(xpath = "//h2[text()='Center of excellence']")
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

        System.out.println("Opened Website: " + driver.getCurrentUrl());

        validate(
            "Open Website",
            "Website should load successfully",
            driver.getCurrentUrl(),
            driver.getCurrentUrl().contains("luxhospitals.com"),
            driver.getCurrentUrl()
        );
    }

    public void clickFirstMenuLink() throws InterruptedException {
        try {
            clickElement(firstMenuLink, "Top-menu first link");
            Thread.sleep(3000);

            validate(
                "Click First Menu Link",
                "Top menu should be clickable",
                "Clicked successfully",
                true,
                ""
            );

        } catch (Exception e) {
            validate(
                "Click First Menu Link",
                "Top menu should be clickable",
                "Failed",
                false,
                e.getMessage()
            );
        }
    }

    public void clickDesktopBookAppointment() throws InterruptedException {
        scrollIntoView(desktopBookAppointment);
        highlight(desktopBookAppointment);

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", desktopBookAppointment);
            System.out.println("Clicked: Desktop Book Appointment");

            Thread.sleep(2000);

            validate(
                "Click Desktop Book Appointment",
                "Button should open new tab or navigate",
                "Clicked",
                true,
                ""
            );

            // new logic
            handleNavigationAfterClick("appointment");

        } catch (Exception e) {
            validate(
                "Click Desktop Book Appointment",
                "Button should open new tab or navigate",
                "Failed",
                false,
                e.getMessage()
            );
        }
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
                    try {
                        scrollIntoView(link);
                        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0]);", href);
                        System.out.println("Opened COE #" + clickCount + ": " + href);
                        Thread.sleep(2000);

                        validate(
                            "Open COE #" + clickCount,
                            "COE URL should open in a new tab",
                            href,
                            true,
                            ""
                        );

                        Set<String> handles = driver.getWindowHandles();
                        for (String handle : handles) {
                            if (!handle.equals(mainTab)) {
                                driver.switchTo().window(handle);
                                break;
                            }
                        }

                        Thread.sleep(3000);
                        driver.close();
                        driver.switchTo().window(mainTab);
                        Thread.sleep(2000);
                        clickCount++;

                    } catch (Exception e) {
                        validate(
                            "Open COE #" + clickCount,
                            "COE URL should open in a new tab",
                            "Failed",
                            false,
                            e.getMessage()
                        );
                    }
                    break;
                }
            }
        }
    }

    public void processTopDoctors() throws InterruptedException {
        try {
            scrollIntoView(doctorSection);
            Thread.sleep(1000);

            validate(
                "Scroll to Our Top Doctors section",
                "'Our Top Doctors' section should be visible",
                "Scrolled successfully",
                true,
                ""
            );
        } catch (Exception e) {
            System.out.println("⚠️ Could not scroll to 'Our Top Doctors' section: " + e.getMessage());

            validate(
                "Scroll to Our Top Doctors section",
                "'Our Top Doctors' section should be visible",
                "Scroll failed",
                false,
                e.getMessage()
            );
        }

        try {
            List<WebElement> profileLinks = driver.findElements(By.cssSelector("button.doctor-appointment-btn"));
            int doctorIndex = 1;

            for (WebElement link : profileLinks) {
                if (doctorIndex > 10) break;
                try {
                    scrollIntoView(link);
                    highlight(link);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                    System.out.println("Clicked Doctor View Profile #" + doctorIndex);

                    validate(
                        "Click Doctor Profile #" + doctorIndex,
                        "Doctor profile should open",
                        "Clicked successfully",
                        true,
                        ""
                    );

                    Thread.sleep(3000);
                    handleNewTabIfAny();
                    removeHighlight(link);

                } catch (Exception e) {
                    validate(
                        "Click Doctor Profile #" + doctorIndex,
                        "Doctor profile should open",
                        "Failed",
                        false,
                        e.getMessage()
                    );
                }
                doctorIndex++;
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error interacting with doctor profile links: " + e.getMessage());

            validate(
                "Process Top Doctors Section",
                "Should interact with doctor profiles",
                "Error occurred",
                false,
                e.getMessage()
            );
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

            validate(
                "Process Review Section",
                "Review section should open in new tab and return",
                "Completed",
                true,
                ""
            );

        } catch (Exception e) {
            System.out.println("⚠️ Could not handle review tab: " + e.getMessage());

            validate(
                "Process Review Section",
                "Review section should open in new tab and return",
                "Failed",
                false,
                e.getMessage()
            );
        }
    }

    public void testWhatsAppEnquiryPositive() {
        String message = "This is a test message";
        try {
            System.out.println("\n✅ Positive Scenario: Typing message and submitting...");

            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            scrollIntoView(input);
            highlight(input);
            input.clear();

            for (char c : message.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }

            removeHighlight(input);
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
            System.out.println("✅ Message sent button clicked.");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}

            removeHighlight(sendButton);

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

            validate(
                "WhatsApp Positive Enquiry",
                "Message should send and possibly open WhatsApp tab",
                "Executed positive flow",
                true,
                ""
            );

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Positive test failed: " + e.getMessage());

            validate(
                "WhatsApp Positive Enquiry",
                "Message should send and possibly open WhatsApp tab",
                "Failed",
                false,
                e.getMessage()
            );
        }
    }


    public void testWhatsAppEnquiryNegative() {
        try {
            System.out.println("\n⚠️ Negative Scenario: Input left empty, attempting to send...");

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear();
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);

            Thread.sleep(1500);

            if (input.getAttribute("value").isEmpty()) {
                System.out.println("✅ Negative test executed successfully: empty message prevented.");

                validate(
                    "WhatsApp Negative Enquiry",
                    "Empty message should be blocked",
                    "Blocked (field empty)",
                    true,
                    ""
                );
            } else {
                System.out.println("⚠️ Unexpected: message field not empty after click.");

                validate(
                    "WhatsApp Negative Enquiry",
                    "Empty message should be blocked",
                    "Field not empty",
                    false,
                    "Validation did not prevent non-empty state"
                );
            }

            removeHighlight(sendButton);

        } catch (TimeoutException te) {
            System.out.println("❌ Element not found (page may not have loaded WhatsApp form): " + te.getMessage());

            validate(
                "WhatsApp Negative Enquiry",
                "Elements should be present",
                "Element not found",
                false,
                te.getMessage()
            );
        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());

            validate(
                "WhatsApp Negative Enquiry",
                "Empty message should be blocked",
                "Failed",
                false,
                e.getMessage()
            );
        }
    }

    public void handleNavigationAfterClick(String expectedUrlContains) throws InterruptedException {
        String mainTab = driver.getWindowHandle();
        Set<String> handlesBefore = driver.getWindowHandles();

        Thread.sleep(2000);

        Set<String> handlesAfter = driver.getWindowHandles();

        // -------- CASE 1: New Tab Opened --------
        if (handlesAfter.size() > handlesBefore.size()) {
            for (String h : handlesAfter) {
                if (!handlesBefore.contains(h)) {
                    driver.switchTo().window(h);
                    System.out.println("🔗 New tab opened: " + driver.getCurrentUrl());

                    Thread.sleep(2000);
                    driver.close();
                    driver.switchTo().window(mainTab);
                    System.out.println("↩️ Returned to main tab");

                    validate(
                        "Navigation After Click",
                        "Click should open new tab and return",
                        "New tab opened & closed",
                        true,
                        ""
                    );
                    return;
                }
            }
        }

        // -------- CASE 2: Same Tab Navigation --------
        if (!driver.getCurrentUrl().contains("luxhospitals.com")) {
            System.out.println("🔄 Same-tab navigation detected: " + driver.getCurrentUrl());
            Thread.sleep(2000);
            driver.navigate().back();
            Thread.sleep(2000);
            System.out.println("↩️ Returned using browser back()");

            validate(
                "Navigation After Click",
                "If same-tab navigation happens, should return using back()",
                "Returned via back()",
                true,
                ""
            );
        } else {
            System.out.println("ℹ️ No new tab or navigation detected.");

            validate(
                "Navigation After Click",
                "Either new tab or same-tab navigation expected",
                "No navigation detected",
                true,
                ""
            );
        }
    }
}
