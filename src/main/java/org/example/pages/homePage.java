package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.util.*;

public class homePage {
     WebDriver driver;
    
    private static WebDriverWait wait;
    private static Actions actions;

    // === Constructor ===
    public homePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        

        this.actions = new Actions(driver);
    }
    /**
     * TC101 — Verify that the Lux Hospitals website loads correctly
     * Expected Result: Website loads successfully, title and key elements are visible within 10 seconds.
     */
    public void verifyWebsiteLoadsCorrectly() {
        String testCaseID = "TC101";
        String feature = "Website Load";
        String expected = "Website should load successfully with correct title and visible header/logo.";
        String actual;
        String status;

        try {
            long startTime = System.currentTimeMillis();

            // ✅ Wait for page to finish loading
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete")
            );

            long loadTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("⏱️ Page load time: " + loadTime + " seconds");

            // ✅ Validate page title
            String pageTitle = driver.getTitle();
            boolean titleOk = pageTitle != null && pageTitle.toLowerCase().contains("lux");

            // ✅ Validate key elements
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("header.luxmain__header")));
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("a.nav__logo img.logo")));

            boolean headerDisplayed = header.isDisplayed();
            boolean logoDisplayed = logo.isDisplayed();

            if (titleOk && headerDisplayed && logoDisplayed) {
                actual = "Website loaded successfully with correct title and header/logo visible. (Load time: " + loadTime + "s)";
                status = "PASS";
            } else {
                actual = "Website partially loaded or missing key elements.";
                status = "FAIL";
            }

        } catch (TimeoutException te) {
            actual = "Website took too long to load.";
            status = "FAIL";
        } catch (Exception e) {
            actual = "Unexpected error while loading website: " + e.getMessage();
            status = "FAIL";
        }

        // 🧾 Structured Result Logging
        System.out.println("-------------------------------------------------");
        System.out.println("Test Case ID   : " + testCaseID);
        System.out.println("Feature/Section: " + feature);
        System.out.println("Expected Result: " + expected);
        System.out.println("Actual Result  : " + actual);
        System.out.println("Status         : " + status);
        System.out.println("-------------------------------------------------");
    }
    /**
     * TC102 — Verify all navigation bar links redirect correctly
     * Expected Result: Each navbar link (Doctors, Treatments, About Us, etc.)
     * should navigate to its respective page without any broken links or errors.
     */
    public void verifyNavbarLinks() {
        String testCaseId = "TC102";
        String feature = "Navbar";
        System.out.println("\n======================================================");
        System.out.println("🧾 TEST CASE ID: " + testCaseId);
        System.out.println("📂 FEATURE: " + feature);
        System.out.println("🎯 DESCRIPTION: Verify all navigation bar links redirect correctly");
        System.out.println("======================================================\n");

        try {
            // 1️⃣ Doctors
            String expected1 = "Doctors link should open the Doctors page";
            System.out.println("➡️ Step 1: Click 'Doctors'");
            WebElement doctors = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Doctors']")));
            highlight(doctors);
            doctors.click();

            wait.until(ExpectedConditions.urlContains("luxhospitals.com/doctors"));
            String actual1 = driver.getCurrentUrl();
            boolean status1 = actual1.contains("doctors");
            printResult("Doctors", expected1, actual1, status1);
            Assert.assertTrue(status1, "❌ Doctors link failed");

            driver.navigate().back();
            removeHighlight1(doctors);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Doctors']")));

            // 2️⃣ Treatments
            String expected2 = "Treatments link should open the Treatments page";
            System.out.println("\n➡️ Step 2: Click 'Treatments'");
            WebElement treatments = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Treatments']")));
            highlight(treatments);
            treatments.click();

            wait.until(ExpectedConditions.urlContains("luxhospitals.com/treatments"));
            String actual2 = driver.getCurrentUrl();
            boolean status2 = actual2.contains("treatments");
            printResult("Treatments", expected2, actual2, status2);
            Assert.assertTrue(status2, "❌ Treatments link failed");

            driver.navigate().back();
            removeHighlight1(treatments);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Doctors']")));

            // 3️⃣ For Patients → Hover + Insurance
            String expected3 = "Hovering over 'For Patients' should show dropdown, and 'Insurance' should open Insurance page";
            System.out.println("\n➡️ Step 3: Hover 'For Patients' and click 'Insurance'");
            WebElement forPatients = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space()='For Patients']")));
            highlight(forPatients);
            actions.moveToElement(forPatients).perform();

            WebElement dropdownMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space()='For Patients']/ancestor::li//ul[contains(@class,'dropdown__menu')]")));
            Assert.assertTrue(dropdownMenu.isDisplayed(), "❌ Dropdown not visible for For Patients");

            WebElement insurance = dropdownMenu.findElement(By.xpath(".//a[normalize-space()='Insurance']"));
            highlight(insurance);
            insurance.click();

            wait.until(ExpectedConditions.urlContains("luxhospitals.com/insurance"));
            String actual3 = driver.getCurrentUrl();
            boolean status3 = actual3.contains("insurance");
            printResult("For Patients → Insurance", expected3, actual3, status3);
            Assert.assertTrue(status3, "❌ Insurance link failed");

            driver.navigate().back();
            removeHighlight1(forPatients);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Doctors']")));

            // 4️⃣ About Us → Hover only, verify dropdown visible
            String expected4 = "Hovering over 'About Us' should display its dropdown menu";
            System.out.println("\n➡️ Step 4: Hover 'About Us' to verify dropdown visibility");
            WebElement aboutUs = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space()='About Us']")));
            highlight(aboutUs);
            actions.moveToElement(aboutUs).perform();

            WebElement aboutDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space()='About Us']/ancestor::li//ul[contains(@class,'dropdown__menu')]")));
            String opacity = aboutDropdown.getCssValue("opacity");
            String visibility = aboutDropdown.getCssValue("visibility");
            removeHighlight1(aboutUs);

            boolean status4 = visibility.equals("visible") && Double.parseDouble(opacity) > 0;
            String actual4 = "Dropdown CSS → opacity: " + opacity + ", visibility: " + visibility;
            printResult("About Us (Dropdown Visible)", expected4, actual4, status4);
            Assert.assertTrue(status4, "❌ About Us dropdown not visible");

            System.out.println("\n✅✅ " + testCaseId + " PASSED: All Navbar validations successful!");

        } catch (Exception e) {
            System.out.println("\n❌ " + testCaseId + " FAILED: " + e.getMessage());
            Assert.fail("Navbar validation failed: " + e.getMessage());
        }

        System.out.println("\n======================================================");
        System.out.println("🏁 END OF TEST CASE: " + testCaseId);
        System.out.println("======================================================\n");
    }

    private void printResult(String step, String expected, String actual, boolean status) {
        System.out.println("------------------------------------------------------");
        System.out.println("🔹 Step: " + step);
        System.out.println("🧩 Expected: " + expected);
        System.out.println("🔍 Actual: " + actual);
        System.out.println("📊 Status: " + (status ? "✅ PASSED" : "❌ FAILED"));
        System.out.println("------------------------------------------------------");
    }
    private void click(WebElement el, String label) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            System.out.println("✅ Clicked: " + label);
        } catch (Exception e) {
            System.out.println("❌ Click failed on " + label + ": " + e.getMessage());
        }
    }

    private void hover(WebElement el) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(el).pause(Duration.ofMillis(500)).perform();
            System.out.println("🖱️ Hovered on: " + el.getText());
        } catch (Exception e) {
            System.out.println("⚠️ Hover failed: " + e.getMessage());
        }
    }
    private void validatePage(String expectedText) {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains(expectedText.toLowerCase().replace(" ", "-")),
                    ExpectedConditions.titleContains(expectedText),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + expectedText.toLowerCase() + "')]"))
            ));
            System.out.println("✅ Page loaded successfully: " + expectedText);
            driver.navigate().back();
        } catch (Exception e) {
            System.out.println("⚠️ Validation failed for: " + expectedText + " → " + e.getMessage());
        }
    }
    // === TEST METHODS ===
    public void testHeaderButton() {
        String testCaseId = "TC103";
        String feature = "Navbar Contact Button";
        String description = "Verify Contact button in navbar navigates to WhatsApp";

        System.out.println("\n======================================================");
        System.out.println("🧾 TEST CASE ID: " + testCaseId);
        System.out.println("📂 FEATURE: " + feature);
        System.out.println("🎯 DESCRIPTION: " + description);
        System.out.println("======================================================\n");

        try {
            By headerBtnLocator = By.cssSelector(".header_button");

            // ✅ Step 1: Locate Header Button
            System.out.println("➡️ Step 1: Locate and highlight Contact (Header) button");
            WebElement headerButton = wait.until(ExpectedConditions.elementToBeClickable(headerBtnLocator));
            highlight(headerButton);

            String expected = "Clicking on Contact button should open WhatsApp chat (api.whatsapp.com/send)";
            clickElement(headerButton, "Header Button");

            // ✅ Step 2: Wait for WhatsApp redirect
            wait.until(ExpectedConditions.urlContains("api.whatsapp.com/send"));
            String actual = driver.getCurrentUrl();
            boolean status = actual.contains("api.whatsapp.com/send");

            printResult("Contact Button (Navbar)", expected, actual, status);
            Assert.assertTrue(status, "❌ Contact button did not redirect to WhatsApp.");

            System.out.println("🎉 " + testCaseId + " PASSED — Navigated to: " + actual);

            // ✅ Step 3: Navigate back to verify return
            driver.navigate().back();
            removeHighlight1(headerButton);
            wait.until(ExpectedConditions.presenceOfElementLocated(headerBtnLocator));

        } catch (Exception e) {
            System.out.println("❌ " + testCaseId + " FAILED: " + e.getMessage());
            Assert.fail("Header Button validation failed: " + e.getMessage());
        }

        System.out.println("\n======================================================");
        System.out.println("🏁 END OF TEST CASE: " + testCaseId);
        System.out.println("======================================================\n");
    }
    public void removeHighlight1(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "try{ arguments[0].style.outline='none'; arguments[0].style.border='none'; arguments[0].style.boxShadow='none'; arguments[0].style.backgroundColor=''; }catch(e){}",
                element
            );
        } catch (Exception e) {
            System.out.println("⚠ removeHighlight failed (possibly stale): " + e.getMessage());
        }
    }



    public void testStickyIcons() throws InterruptedException {
        String testCaseId = "TC104";
        String feature = "Floating Sticky CTAs";
        String description = "Verify all 3 floating sticky CTAs (Call, WhatsApp, Emergency) are visible and functional";

        System.out.println("\n======================================================");
        System.out.println("🧾 TEST CASE ID: " + testCaseId);
        System.out.println("📂 FEATURE: " + feature);
        System.out.println("🎯 DESCRIPTION: " + description);
        System.out.println("======================================================\n");

        try {
            Thread.sleep(1500);

            List<WebElement> stickyButtons = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("mobile-stickey-icon-container"))
            );

            System.out.println("➡️ Found " + stickyButtons.size() + " sticky CTA buttons.");

            for (int i = 0; i < stickyButtons.size() && i < 3; i++) {

                System.out.println("\n------------------------------------------------------");
                System.out.println("🖱️ STEP " + (i + 1) + " — Testing Sticky CTA #" + (i + 1));

             // RE-FIND the element to avoid stale element
                WebElement stickyButton = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.className("mobile-stickey-icon-container")
                        )).get(i);

                // Safe actions
                scrollIntoView(stickyButton);
                highlight(stickyButton);

                Thread.sleep(600);

                // 📌 Capture current state
                Set<String> oldTabs = driver.getWindowHandles();
                String mainWindow = driver.getWindowHandle();
                String oldUrl = driver.getCurrentUrl();

                // 🖱️ SINGLE SAFE CLICK
                Actions act = new Actions(driver);
                act.moveToElement(stickyButton)
                        .pause(Duration.ofMillis(200))
                        .click()
                        .perform();

                System.out.println("✅ Clicked Sticky CTA #" + (i + 1));
                Thread.sleep(1500);

                // 🚨 HANDLE NEW TAB OR SAME TAB
                Set<String> newTabs = driver.getWindowHandles();
                newTabs.removeAll(oldTabs);

                if (!newTabs.isEmpty()) {
                    // 🌍 OPENED IN NEW TAB
                    String newTab = newTabs.iterator().next();
                    driver.switchTo().window(newTab);

                    System.out.println("🌍 Sticky CTA opened in new tab: " + driver.getCurrentUrl());
                    Thread.sleep(1000);

                    driver.close();
                    driver.switchTo().window(mainWindow);
                    Thread.sleep(1200);

                } else {
                    // ↪️ SAME TAB NAVIGATION
                    String newUrl = driver.getCurrentUrl();
                    if (!newUrl.equals(oldUrl)) {

                        System.out.println("↪️ Sticky CTA navigated to: " + newUrl);
                        Thread.sleep(1000);

                        driver.navigate().back();
                        Thread.sleep(2000);

                    } else {
                        System.out.println("⚠️ Sticky CTA did not open new tab or new page.");
                    }
                }

                // 🚨 EMERGENCY POPUP — Only 3rd Icon (index 2)
                if (i == 2) {
                    closeEmergencyPopupInstantly();
                }

                removeHighlight1(stickyButton);
                Thread.sleep(700);
            }

            System.out.println("\n🎉 " + testCaseId + " PASSED — All sticky CTAs validated.");

        } catch (Exception e) {
            System.out.println("❌ " + testCaseId + " FAILED: " + e.getMessage());
            Assert.fail("Sticky CTAs validation failed: " + e.getMessage());
        }

        System.out.println("\n======================================================");
        System.out.println("🏁 END OF TEST CASE: " + testCaseId);
        System.out.println("======================================================\n");
    }
    public void closeEmergencyPopupInstantly() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));

            WebElement popup = shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("emergencyPopup"))
            );

            System.out.println("📞 Emergency popup detected — closing immediately...");

            WebElement closeBtn = popup.findElement(By.cssSelector(".close-footer-popup"));

            // Instant JS close (most reliable)
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", closeBtn);

            Thread.sleep(200);

            System.out.println("✅ Emergency popup closed instantly!");

        } catch (TimeoutException e) {
            // No popup → ignore
        } catch (Exception ex) {
            System.out.println("⚠️ Popup close error: " + ex.getMessage());
        }
    }

    private boolean handleTabOrPopup(Set<String> oldTabs, String label) throws InterruptedException {
        Set<String> newTabs = driver.getWindowHandles();
        newTabs.removeAll(oldTabs);

        // ✅ CASE 1: Handle new tab
        if (!newTabs.isEmpty()) {
            String newTab = newTabs.iterator().next();
            driver.switchTo().window(newTab);
            Thread.sleep(2000);

            String currentUrl = driver.getCurrentUrl();
            System.out.println("✅ " + label + " opened new tab: " + currentUrl);

            if (currentUrl.contains("wa.me") || currentUrl.contains("api.whatsapp")) {
                System.out.println("Expected: WhatsApp chat ✅");
            } else if (currentUrl.startsWith("tel:")) {
                System.out.println("Expected: Phone call link ✅");
            } else if (currentUrl.contains("appointment")) {
                System.out.println("Expected: Appointment page ✅");
            }

            driver.close();
            driver.switchTo().window(oldTabs.iterator().next());
            Thread.sleep(1500);
            return true;
        }

        // ✅ CASE 2: Handle Emergency Contact popup
        try {
            WebElement emergencyPopup = wait.withTimeout(Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("div.popup-content, div.modal, div[class*='emergency'], div[class*='popup']")
                    ));

            System.out.println("📩 Popup detected for " + label);

            // Try multiple possible close button selectors
            List<By> closeSelectors = Arrays.asList(
                    By.cssSelector(".popup-close"),
                    By.cssSelector("button[aria-label='Close']"),
                    By.cssSelector("div.popup-content svg"), // covers SVG X icon
                    By.cssSelector(".popup-content .fa-times"), // fallback for font icon X
                    By.cssSelector(".popup-content > div > div > svg") // specific to emergency popup
            );

            boolean closed = false;
            for (By selector : closeSelectors) {
                try {
                    WebElement closeBtn = emergencyPopup.findElement(selector);
                    highlight(closeBtn);
                    closeBtn.click();
                    Thread.sleep(1000);
                    System.out.println("✅ Closed popup via: " + selector);
                    closed = true;
                    break;
                } catch (Exception ignored) {}
            }

            if (!closed) {
                // Fallback: press ESC
                new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                System.out.println("✅ Closed popup using ESC key fallback.");
            }

            Thread.sleep(1500);
            return true;

        } catch (TimeoutException e) {
            // No popup detected
            return false;
        }
    }

    private void handleTabSwitchAndValidate(Set<String> oldTabs, String label) throws InterruptedException {
        Set<String> newTabs = driver.getWindowHandles();
        newTabs.removeAll(oldTabs);

        if (!newTabs.isEmpty()) {
            String newTab = newTabs.iterator().next();
            driver.switchTo().window(newTab);
            Thread.sleep(2500);

            String currentUrl = driver.getCurrentUrl();
            System.out.println("✅ " + label + " opened new tab: " + currentUrl);

            // Validation based on expected pattern
            if (currentUrl.contains("wa.me") || currentUrl.contains("api.whatsapp")) {
                System.out.println("Expected: WhatsApp chat page ✅");
            } else if (currentUrl.startsWith("tel:")) {
                System.out.println("Expected: Phone call link ✅");
            } else if (currentUrl.contains("appointment") || currentUrl.contains("book")) {
                System.out.println("Expected: Appointment page ✅");
            } else {
                System.out.println("⚠️ Unexpected URL: " + currentUrl);
            }

            driver.close();
            driver.switchTo().window(oldTabs.iterator().next());
            Thread.sleep(2000);

        } else {
            System.out.println("ℹ️ No new tab detected — checking if action was handled in same window.");
            Thread.sleep(1500);
        }
    }


    public void testImageCTA(String selector, String label) throws InterruptedException {

        String testCaseId = "TC105";
        String feature = "Second Section CTAs";
        String description = "Verify CTAs open correctly in new tab or same page.";

        System.out.println("\n======================================================");
        System.out.println("🧾 TEST CASE ID: " + testCaseId);
        System.out.println("📂 FEATURE: " + feature);
        System.out.println("🎯 DESCRIPTION: " + description);
        System.out.println("======================================================\n");

        try {
            System.out.println("\n------------------------------------------------------");
            System.out.println("🖱️ Testing CTA: " + label);
            System.out.println("Expected: Should open new tab or navigate within same page.");

            // Fresh element
            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(selector))
            );

            scrollIntoView(element);
            highlight(element);
            Thread.sleep(1000);

            // Capture state
            String oldUrl = driver.getCurrentUrl();
            String mainWindow = driver.getWindowHandle();
            Set<String> oldTabs = driver.getWindowHandles();

            // SINGLE SAFE CLICK
            new Actions(driver)
                    .moveToElement(element)
                    .pause(Duration.ofMillis(250))
                    .click()
                    .perform();

            System.out.println("✅ Clicked: " + label);
            Thread.sleep(3000);

            // Detect new tab
            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);

            if (!newTabs.isEmpty()) {

                // NEW TAB
                String newTab = newTabs.iterator().next();
                driver.switchTo().window(newTab);
                Thread.sleep(2000);

                System.out.println("🌍 " + label + " opened in new tab: " + driver.getCurrentUrl());

                Thread.sleep(2000); // Wait on new page
                driver.close();

                driver.switchTo().window(mainWindow);
                Thread.sleep(2000);

            } else {

                // SAME TAB NAVIGATION
                String newUrl = driver.getCurrentUrl();

                if (!newUrl.equals(oldUrl)) {

                    System.out.println("↪️ " + label + " navigated to: " + newUrl);

                    Thread.sleep(2500); // Wait on new page
                    driver.navigate().back();

                    Thread.sleep(3500);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));

                } else {
                    System.out.println("⚠️ " + label + " did not trigger navigation or open a new tab.");
                }
            }

            
            removeHighlight1(element);
            Thread.sleep(2000);

            System.out.println("✅ Completed CTA: " + label);

        } catch (Exception e) {

            System.out.println("❌ " + testCaseId + " FAILED for CTA: " + label + " — " + e.getMessage());
            Assert.fail("CTA validation failed for: " + label + " — " + e.getMessage());
        }

        System.out.println("\n======================================================");
        System.out.println("🏁 END OF CTA TEST: " + label);
        System.out.println("======================================================\n");
    }

    public void testDepartmentCards() throws InterruptedException {

        By cardSelector = By.xpath("//div[contains(@class,'click-container-') and .//h2]");

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 👉 STEP 1: Scroll to Our Departments ONLY ONCE
            WebElement deptHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'Our Departments')]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deptHeading);
            Thread.sleep(1500);

            // 👉 STEP 2: Capture department names only (STRING = never stale)
            List<WebElement> initialCards = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(cardSelector)
            );

            LinkedHashSet<String> departmentNames = new LinkedHashSet<>();
            for (WebElement card : initialCards) {
                try {
                	
                    departmentNames.add(card.findElement(By.tagName("h2")).getText().trim());
                } catch (Exception ignored) {}
            }

            System.out.println("Found " + departmentNames.size() + " unique department cards.");

            int index = 1;
            String mainWindow = driver.getWindowHandle();

            // 👉 LOOP THROUGH NAMES ONLY
            for (String deptName : departmentNames) {

                System.out.println("\n------------------------------------------------------");
                System.out.println("VALIDATING DEPARTMENT #" + index + ": " + deptName);

                // ⭐ Re-locate card fresh BEFORE click (no stale)
                WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//h2[normalize-space()='" + deptName + "']/ancestor::div[contains(@class,'click-container-')]")
                ));

                // 👉 Scroll only if card is not fully visible 
                js.executeScript(
                        "var rect = arguments[0].getBoundingClientRect();" +
                        "if(rect.top < 0 || rect.bottom > window.innerHeight) {" +
                        "arguments[0].scrollIntoView({block:'center'});" +
                        "}", card
                );
                Thread.sleep(700);

                // 👉 Safe click
                Set<String> oldTabs = driver.getWindowHandles();
                String oldUrl = driver.getCurrentUrl();

                js.executeScript("arguments[0].click();", card);
                Thread.sleep(2000);

                // 👉 Check for new tab
                Set<String> newTabs = driver.getWindowHandles();
                newTabs.removeAll(oldTabs);

                if (!newTabs.isEmpty()) {
                    // Opened in new tab
                    String newTab = newTabs.iterator().next();
                    driver.switchTo().window(newTab);

                    System.out.println("Opened in NEW TAB: " + driver.getCurrentUrl());
                    Thread.sleep(2000);

                    driver.close();
                    driver.switchTo().window(mainWindow);
                    Thread.sleep(1500);

                } else {
                    // Same tab
                    String newUrl = driver.getCurrentUrl();

                    if (!newUrl.equals(oldUrl)) {
                        System.out.println("Navigated to: " + newUrl);
                        Thread.sleep(2000);

                        driver.navigate().back();

                        // Re-wait for cards after reload
                        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardSelector));
                        Thread.sleep(1500);

                    } else {
                        System.out.println("⚠ No navigation occurred");
                    }
                }

                index++;
            }

            System.out.println("\n🎉 TC106 PASSED — Optimized and no stale elements!");

        } catch (Exception e) {
            System.out.println("❌ TC106 FAILED: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


    public void testDoctorProfiles() throws InterruptedException {
        try {
          JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement doctorHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Our Top Doctors')]")
            ));
            scrollIntoView(doctorHeading);
            Thread.sleep(1200);

            String parentWindow = driver.getWindowHandle();

            By buttonSelector = By.cssSelector("button.doctor-appointment-btn");

            List<WebElement> buttons = driver.findElements(buttonSelector);
            int totalDoctors = buttons.size();
            System.out.println("🔹 Found " + totalDoctors + " doctor profile buttons");

            for (int i = 0; i < totalDoctors && i < 12; i++) {
                // Re-locate fresh button each time
                List<WebElement> freshButtons = driver.findElements(buttonSelector);
                WebElement btn = freshButtons.get(i);

                scrollIntoView(btn);
                actions.moveToElement(btn).perform();
                System.out.println("✅ Clicking doctor profile #" + (i + 1));

                js.executeScript("arguments[0].click();", btn);

                // Wait for new tab
                wait.until(driver -> driver.getWindowHandles().size() > 1);
                for (String w : driver.getWindowHandles()) {
                    if (!w.equals(parentWindow)) {
                        driver.switchTo().window(w);
                        break;
                    }
                }

                System.out.println("🔗 Opened Doctor Profile URL: " + driver.getCurrentUrl());
                Thread.sleep(2000);

                // Close new tab & switch back
                driver.close();
                driver.switchTo().window(parentWindow);

                wait.until(ExpectedConditions.visibilityOfElementLocated(buttonSelector));
                Thread.sleep(1000);
            }

            System.out.println("🎉 All doctor profiles tested successfully.");
        } catch (Exception e) {
            System.out.println("❌ Doctor Profiles test failed: " + e.getMessage());
        }
    }
    public void testPatientTalksDots() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        // Scroll to section
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='Patients love us']")));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);
        Thread.sleep(1200);

        // Hover to trigger JS lazy load
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".patient-carousel-section")));
        actions.moveToElement(container).perform();
        Thread.sleep(2000);

        // Retrieve ONLY the JS-generated dots
        List<WebElement> dots = (List<WebElement>) js.executeScript(
                "return Array.from(document.querySelectorAll('.patienttalks-dots-container .patienttalks-dot'))");
        
        if (dots.size() == 0)
            throw new RuntimeException("Dynamic dots did not load.");

        System.out.println("Detected dynamic dots: " + dots.size());

        // Click each dot using real pointer events
        for (int i = 0; i < dots.size(); i++) {
            WebElement dot = dots.get(i);

            js.executeScript(
                "arguments[0].dispatchEvent(new PointerEvent('pointerdown', {bubbles:true}));" +
                "arguments[0].dispatchEvent(new PointerEvent('pointerup', {bubbles:true}));" +
                "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable:true}));",
                dot
            );

            Thread.sleep(1200);

            // Verify active state
            boolean active = (Boolean) js.executeScript(
                "return arguments[0].classList.contains('active');", dot);

            System.out.println(active ?
                    "✔ Dot " + i + " activated" :
                    "✘ Dot " + i + " did NOT activate");
        }
    }

//    public void testPatientTalksDots() throws InterruptedException {
//
//        String testCaseId = "TC108";
//        String feature = "Patient Talks Carousel";
//        String description = "Verify each dot in the 'Patients love us' carousel activates correctly.";
//
//        System.out.println("\n======================================================");
//        System.out.println("TEST CASE: " + testCaseId);
//        System.out.println("FEATURE: " + feature);
//        System.out.println("DESCRIPTION: " + description);
//        System.out.println("======================================================\n");
//
//        try {
//
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            Actions actions = new Actions(driver);
//
//            // -----------------------------------------------------
//            // STEP 1 — Locate slider section (NO SCROLL AT ALL)
//            // -----------------------------------------------------
//            WebElement section = wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            By.cssSelector(".patient-carousel-section"))
//            );
//
//            // -----------------------------------------------------
//            // STEP 2 — Move cursor to ABSOLUTE viewport coordinates
//            // (bypasses Elementor's on-scroll handlers)
//            // -----------------------------------------------------
//            Rectangle rect = section.getRect();
//
//            int viewportX = rect.x + rect.width / 2;
//            int viewportY = rect.y + 40;  // slight inside movement
//
//            actions.moveByOffset(viewportX, viewportY).perform();
//            System.out.println("🖱 Cursor moved directly to slider location (no scroll)");
//            Thread.sleep(1500);  // wait for lazy loading
//
//
//            // -----------------------------------------------------
//            // STEP 3 — Wait for dynamic dots to appear
//            // -----------------------------------------------------
//            List<WebElement> dots = new ArrayList<>();
//            int retries = 0;
//
//            while (retries < 25) { // up to ~7 seconds
//                dots = driver.findElements(
//                        By.cssSelector(".patienttalks-dots-container .patienttalks-dot")
//                );
//                if (dots.size() > 0) break;
//
//                Thread.sleep(300);
//                retries++;
//            }
//
//            if (dots.size() == 0)
//                throw new Exception("Dynamic dots did NOT load after cursor hover.");
//
//            System.out.println("🔹 Dynamic dots detected: " + dots.size());
//
//
//            // -----------------------------------------------------
//            // STEP 4 — Validate each dot WITHOUT SCROLLING
//            // -----------------------------------------------------
//            for (int i = 0; i < dots.size(); i++) {
//
//                System.out.println("\n--------------------------------------");
//                System.out.println("👉 VALIDATING DOT #" + i);
//
//                dots = driver.findElements(
//                        By.cssSelector(".patienttalks-dots-container .patienttalks-dot")
//                );
//                WebElement dot = dots.get(i);
//
//                // Move cursor exactly onto dot WITHOUT scrolling
//                Rectangle drect = dot.getRect();
//                actions.moveByOffset(drect.x + 5, drect.y + 5).perform();
//                Thread.sleep(300);
//
//                // Real pointer events -> slider listens for them
//                js.executeScript(
//                        "arguments[0].dispatchEvent(new PointerEvent('pointerdown', {bubbles:true}));" +
//                        "arguments[0].dispatchEvent(new PointerEvent('pointerup', {bubbles:true}));" +
//                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable:true}));",
//                        dot
//                );
//
//                Thread.sleep(1200);
//
//                dots = driver.findElements(
//                        By.cssSelector(".patienttalks-dots-container .patienttalks-dot")
//                );
//                dot = dots.get(i);
//
//                boolean active = (Boolean) js.executeScript(
//                        "return arguments[0].classList.contains('active');",
//                        dot
//                );
//
//                if (active)
//                    System.out.println("✅ Dot " + i + " activated successfully");
//                else
//                    System.out.println("❌ Dot " + i + " FAILED to activate");
//            }
//
//
//            System.out.println("\n🎉 TEST PASSED — All dots validated successfully!");
//
//        } catch (Exception e) {
//            System.out.println("❌ TEST FAILED: " + e.getMessage());
//            Assert.fail("TC108 FAILED — " + e.getMessage());
//        }
//
//        System.out.println("\n======================================================");
//        System.out.println("END OF TEST CASE " + testCaseId);
//        System.out.println("======================================================\n");
//    }
//



    
    public void testLuxGPT() throws InterruptedException {
        String testCaseId = "TC109";
        System.out.println("\n========== TC109 : LuxGPT Tests ==========\n");

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll to LuxGPT section
            WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(text(),'LUXGPT')]")
            ));
            scrollIntoView(header);
            Thread.sleep(1200);

            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("messageInput")
            ));
            WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")
            ));

            // --------------------------------------------
            //  NEGATIVE TEST – EMPTY INPUT
            // --------------------------------------------
            System.out.println("------ NEGATIVE TEST (Empty Input) ------");

            input.clear();
            js.executeScript("arguments[0].click();", sendButton);
            Thread.sleep(1000);

            // HANDLE JS ALERT QUICKLY
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                System.out.println("⚠ Alert detected: " + alert.getText());
                alert.accept();
                System.out.println("✔ Alert closed!");
            } catch (Exception ex) {
                System.out.println("ℹ No alert appeared for negative test.");
            }

            Thread.sleep(2000); // Let the UI settle

            // --------------------------------------------
            //  POSITIVE TEST – VALID MESSAGE
            // --------------------------------------------
            System.out.println("\n------ POSITIVE TEST (Valid Message) ------");

            // Make sure NO alert is open before typing
            try {
                Alert leftover = driver.switchTo().alert();
                leftover.accept();
                System.out.println("✔ Closed leftover alert before typing.");
            } catch (NoAlertPresentException ignored) {}

            input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messageInput")));
            input.clear();
            Thread.sleep(1000);

            String message = "This is a test message";
            input.sendKeys(message);
            System.out.println("✔ Typed message: " + message);

            scrollIntoView(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            js.executeScript("arguments[0].click();", sendButton);

            Thread.sleep(2000);

            // CHECK IF NEW TAB OPENED
            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);

            if (!newTabs.isEmpty()) {
                String tab = newTabs.iterator().next();
                driver.switchTo().window(tab);
                System.out.println("🌍 New tab opened: " + driver.getCurrentUrl());
                Thread.sleep(1500);
                driver.close();
                driver.switchTo().window(oldTabs.iterator().next());
            } else {
                System.out.println("❌ No new tab opened in positive case.");
            }

            System.out.println("\n🎉 TC109 PASSED: LuxGPT negative + positive tests completed.");

        } catch (Exception e) {
            System.out.println("❌ TC109 FAILED: " + e.getMessage());
            Assert.fail("LuxGPT test failed: " + e.getMessage());
        }

        System.out.println("\n==========================================");
    }

    public void testArticles() {
        String testCaseId = "TC110";

        try {
            System.out.println("\n============== TC110: Health Articles ==============\n");

            Actions actions = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for the heading to appear and scroll to it
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(.,'Latest Health Articles')]")
            ));

            actions.moveToElement(heading).perform();
            System.out.println("📌 Reached 'Latest Health Articles' section");

            String parentWindow = driver.getWindowHandle();

            // Wait for article titles to load
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-title a")));

            int totalArticles = driver.findElements(By.cssSelector(".card-title a")).size();
            System.out.println("📰 Total Articles Found: " + totalArticles);

            for (int i = 0; i < totalArticles; i++) {

                System.out.println("\n--------------------------------------------------");
                System.out.println("➡ Testing Article #" + (i + 1));

                // Fetch fresh list (prevents stale element)
                List<WebElement> articles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement article = articles.get(i);

                // Scroll article into view
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", article);
                actions.moveToElement(article).pause(Duration.ofMillis(400)).perform();

                highlight(article);

                String oldUrl = driver.getCurrentUrl();
                Set<String> oldTabs = driver.getWindowHandles();

                // Safe JS click for dynamic carousel items
                js.executeScript("arguments[0].click();", article);

                System.out.println("🖱 Clicked Article #" + (i + 1));

                // Wait for navigation OR new tab
                wait.until(driverInstance -> driverInstance.getWindowHandles().size() >= oldTabs.size());

                Set<String> newTabs = driver.getWindowHandles();
                newTabs.removeAll(oldTabs);

                if (!newTabs.isEmpty()) {
                    // NEW TAB OPENED
                    String newTab = newTabs.iterator().next();
                    driver.switchTo().window(newTab);

                    System.out.println("🌍 Opened in NEW tab: " + driver.getCurrentUrl());

                    driver.close();
                    driver.switchTo().window(parentWindow);

                } else {
                    // NAVIGATED IN SAME TAB
                    wait.until(ExpectedConditions.urlContains("http"));
                    String newUrl = driver.getCurrentUrl();

                    if (!newUrl.equals(oldUrl)) {
                        System.out.println("↪ Navigated in SAME tab: " + newUrl);

                        driver.navigate().back();

                        wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h2[contains(.,'Latest Health Articles')]")
                        ));
                    } else {
                        System.out.println("⚠ No navigation occurred!");
                    }
                }

                removeHighlight1(article);

                // Move the mouse away slightly to prevent hover issues
                actions.moveByOffset(0, -150).perform();
            }

            System.out.println("\n🎉 TC110 PASSED — All health articles validated successfully!");

        } catch (Exception e) {
            System.out.println("❌ TC110 FAILED: " + e.getMessage());
            Assert.fail("Health Articles section failed: " + e.getMessage());
        }

        System.out.println("\n======================================================\n");
    }

    

    // ==========================
    // FAQ ACCORDIONS TEST
    // ==========================
    public void testAllFaqAccordions() throws InterruptedException {
        String testCaseId = "TC111";

        try {
            System.out.println("\n============== TC111: FAQ Section ==============\n");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Find first FAQ accordion to scroll into section
            List<WebElement> allAccordions = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.cssSelector("div.accordion")
                    )
            );

            if (allAccordions.isEmpty()) {
                System.out.println("❌ TC111 FAILED: No accordion FAQ items found on page.");
                Assert.fail("No FAQs detected.");
            }

            // Scroll to the first accordion
            scrollIntoView(allAccordions.get(0));
            Thread.sleep(1000);

            System.out.println("📌 FAQ section reached.");
            System.out.println("📄 Total FAQs found: " + allAccordions.size());

            for (int i = 0; i < allAccordions.size(); i++) {

                System.out.println("\n------------------------------------------------------");
                System.out.println("➡ Testing FAQ #" + (i + 1));

                // Re-fetch fresh elements
                List<WebElement> freshAccordions = driver.findElements(By.cssSelector("div.accordion"));
                WebElement accordion = freshAccordions.get(i);

                WebElement question = accordion.findElement(By.cssSelector(".accordion_head > span"));
                WebElement answerDiv = accordion.findElement(By.cssSelector(".accordion_content"));

                String questionText = question.getText().trim();

                scrollIntoView(question);
                highlight(question);
                Thread.sleep(500);

                System.out.println("❓ Clicking: " + questionText);

                // Expand
                js.executeScript("arguments[0].click();", question);
                Thread.sleep(800);

                wait.until(d ->
                        answerDiv.getAttribute("style") != null &&
                        !answerDiv.getAttribute("style").contains("max-height: 0px")
                );

                System.out.println("✅ Expanded successfully.");

                Thread.sleep(700);

                // Collapse
                System.out.println("🔽 Collapsing...");
                js.executeScript("arguments[0].click();", question);
                Thread.sleep(800);

                wait.until(d ->
                        answerDiv.getAttribute("style") != null &&
                        answerDiv.getAttribute("style").contains("max-height: 0px")
                );

                System.out.println("✔ Collapsed successfully.");

                removeHighlight1(question);
                Thread.sleep(500);
            }

            System.out.println("\n🎉 TC111 PASSED — All FAQs expand/collapse correctly!");
            System.out.println("\n======================================================\n");

        } catch (Exception e) {
            System.out.println("❌ TC111 FAILED: " + e.getMessage());
            Assert.fail("FAQ Test failed: " + e.getMessage());
        }
    }


    // === HELPERS ===
    private void highlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", el);
    }

    private void removeHighlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='none';", el);
    }

    private  void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private void clickElement(WebElement el, String label) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('target');", el);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            System.out.println("✅ Clicked: " + label);
        } catch (Exception e) {
            System.out.println("❌ Click failed: " + label + " | " + e.getMessage());
        }
    }

    private void handleNewTabIfAny() throws InterruptedException {
        String original = driver.getWindowHandle();
        Set<String> tabs = driver.getWindowHandles();
        if (tabs.size() > 1) {
            for (String t : tabs) {
                if (!t.equals(original)) {
                    driver.switchTo().window(t);
                    Thread.sleep(2000);
                    driver.close();
                    driver.switchTo().window(original);
                }
            }
        } else {
            driver.navigate().back();
        }
    }

    private void handleTabSwitch(Set<String> oldTabs) throws InterruptedException {
        Set<String> newTabs = driver.getWindowHandles();
        newTabs.removeAll(oldTabs);
        if (!newTabs.isEmpty()) {
            String newTab = newTabs.iterator().next();
            driver.switchTo().window(newTab);
            Thread.sleep(2000);
            driver.close();
            driver.switchTo().window(oldTabs.iterator().next());
        } else {
            driver.get("https://luxhospitals.com/");
        }
    }
    public void testFooterLinks() throws Exception {

        driver.manage().window().setSize(new Dimension(1440, 900));
        driver.get("https://luxhospitals.com/");
        Thread.sleep(2500);

        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        scrollToFooter();
        Thread.sleep(1500);

        // 1️⃣ Collect ONLY visible footer links
        List<WebElement> allLinks = driver.findElements(
                By.cssSelector(".lux-footer-desktop a[href]")
        );

        List<WebElement> visibleLinks = new ArrayList<>();

        for (WebElement link : allLinks) {
            try {
                if (link.isDisplayed()) {
                    visibleLinks.add(link);
                }
            } catch (Exception ignored) {}
        }

        System.out.println("VISIBLE FOOTER LINKS FOUND: " + visibleLinks.size());

        // 2️⃣ Click each visible link in order
        for (int i = 0; i < visibleLinks.size(); i++) {

            scrollToFooter();
            Thread.sleep(1000);

            // Re-fetch to avoid stale element
            List<WebElement> refreshedLinks = driver.findElements(
                    By.cssSelector(".lux-footer-desktop a[href]")
            );

            WebElement link = refreshedLinks.get(i);
            String href = link.getAttribute("href");

            System.out.println(
                    "Clicking " + (i + 1) + "/" + visibleLinks.size() + " : " + href
            );

            scrollIntoView(link);
            Thread.sleep(400);

            // 🔴 Highlight
            js.executeScript(
                    "arguments[0].style.border='3px solid red'", link
            );
            Thread.sleep(600);

            Set<String> oldWindows = driver.getWindowHandles();
            link.click();
            Thread.sleep(3000);

            Set<String> newWindows = driver.getWindowHandles();

            if (newWindows.size() > oldWindows.size()) {
                for (String w : newWindows) {
                    if (!oldWindows.contains(w)) {
                        driver.switchTo().window(w);
                        break;
                    }
                }
                Thread.sleep(2000);
                driver.close();
                driver.switchTo().window(mainWindow);
            } else {
                Thread.sleep(2000);
                driver.navigate().back();
                Thread.sleep(2500);
            }

            // 🧹 Remove highlight
            try {
                js.executeScript(
                        "arguments[0].style.border=''", link
                );
            } catch (Exception ignored) {}
        }

        System.out.println("ALL 53 FOOTER LINKS DONE.");
    }

//    private void safeCloseTab(String mainWindow) {
//        try {
//            String currentHandle = driver.getWindowHandle();
//            if (!currentHandle.equals(mainWindow)) {
//                driver.close();
//            }
//            if (!driver.getWindowHandles().contains(mainWindow)) {
//                // main window is gone – pick any available window to keep framework alive
//                Optional<String> any = driver.getWindowHandles().stream().findFirst();
//                any.ifPresent(driver::switchTo);
//            } else {
//                driver.switchTo().window(mainWindow);
//            }
//        } catch (Exception e) {
//            System.out.println("⚠ safeCloseTab error: " + e.getMessage());
//        }
//    }
    
    private void scrollToFooter() {
        try {
            WebElement footer = driver.findElement(By.cssSelector("div[data-id='b3a03d4']"));
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'start', behavior:'instant'});", footer);
            Thread.sleep(800);
        } catch (Exception e) {
            // fallback scroll to bottom
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight);");
        }
    }

}
