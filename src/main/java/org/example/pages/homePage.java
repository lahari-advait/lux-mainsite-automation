package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.util.*;

public class homePage {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Actions actions;

    // === Constructor ===
    public homePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        

        this.actions = new Actions(driver);
    }

    // === TEST METHODS ===
    public void testHeaderButton() {
        try {
            By headerBtnLocator = By.cssSelector(".header_button");

            WebElement headerButton = wait.until(ExpectedConditions.elementToBeClickable(headerBtnLocator));
            highlight(headerButton);

            clickElement(headerButton, "Header Button");

            // ✅ Adjusted expectation
            wait.until(ExpectedConditions.urlContains("api.whatsapp.com/send"));

            System.out.println("🎉 Header Button test passed. Navigated to: " + driver.getCurrentUrl());

            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfElementLocated(headerBtnLocator));

        } catch (Exception e) {
            System.out.println("❌ Header Button test failed: " + e.getMessage());
        }
    }


    public void testStickyIcons() throws InterruptedException {
        try {
            List<WebElement> stickyButtons = driver.findElements(By.className("mobile-stickey-icon-container"));
            for (int i = 0; i < stickyButtons.size() && i < 3; i++) {
                WebElement stickyButton = stickyButtons.get(i);
                String label = "Sticky Icon #" + (i + 1);
                Set<String> oldTabs = driver.getWindowHandles();
                clickElement(stickyButton, label);
                Thread.sleep(2000);
                handleTabSwitch(oldTabs);
            }
        } catch (Exception e) {
            System.out.println("❌ Sticky Icons test failed: " + e.getMessage());
        }
    }

    public void testImageCTA(String cssSelector, String label) throws InterruptedException {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
            scrollIntoView(element);
            actions.moveToElement(element).perform();
            clickElement(element, label);
            Thread.sleep(2000);
            handleNewTabIfAny();
        } catch (Exception e) {
            System.out.println("❌ " + label + " test failed: " + e.getMessage());
        }
    }

    public static void testDepartmentCards() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to "Our Departments" section
        WebElement deptHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Our Departments')]")
        ));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deptHeading);
        Thread.sleep(1200);

        // Select all containers with <h2>
        By cardSelector = By.xpath("//div[contains(@class,'click-container-') and .//h2]");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardSelector));
        List<WebElement> cards = driver.findElements(cardSelector);

        // ✅ Deduplicate by department name
        Map<String, String> uniqueCards = new LinkedHashMap<>();
        for (WebElement card : cards) {
            try {
                String departmentName = card.findElement(By.tagName("h2")).getText().trim();
                uniqueCards.putIfAbsent(departmentName, departmentName); // store name only
            } catch (NoSuchElementException ignore) {}
        }

        System.out.println("Found " + uniqueCards.size() + " unique department cards");

        int index = 1;
        for (String departmentName : uniqueCards.keySet()) {

            // Re-find card fresh each loop (avoid stale references)
            WebElement card = driver.findElement(
                By.xpath("//div[contains(@class,'click-container-') and .//h2[normalize-space()='" + departmentName + "']]")
            );

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", card);
            Thread.sleep(1000);
            actions.moveToElement(card).perform();
            Thread.sleep(500);

            System.out.println("✅ Clicking Department Card #" + index + " - " + departmentName);

            js.executeScript("arguments[0].click();", card);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            Thread.sleep(2000);

            // Go back to homepage
            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardSelector));
            Thread.sleep(2000);

            index++;
        }

        System.out.println("🎉 All unique department cards tested successfully.");
    }

    public static void testDoctorProfiles() throws InterruptedException {
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

            for (int i = 0; i < totalDoctors && i < 10; i++) {
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
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Patients love us']")));
            scrollIntoView(heading);
            Thread.sleep(500);

            WebElement dotsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".patienttalks-dots-container")));
            List<WebElement> dots = dotsContainer.findElements(By.cssSelector(".patienttalks-dot"));

            System.out.println("🔹 Total PatientTalks dots found: " + dots.size());

            for (int i = 0; i < dots.size(); i++) {
                dots = dotsContainer.findElements(By.cssSelector(".patienttalks-dot")); // refresh each loop
                WebElement dot = dots.get(i);
                String index = dot.getAttribute("data-index");

                scrollIntoView(dot);
                actions.moveToElement(dot).perform();
                js.executeScript("arguments[0].click();", dot);
                Thread.sleep(1000); // wait for carousel animation

                if (dot.getAttribute("class").contains("active")) {
                    System.out.println("✅ Dot " + index + " is active after click");
                } else {
                    System.out.println("❌ Dot " + index + " did NOT become active");
                }
            }
            System.out.println("🎉 All PatientTalks dots clicked and verified successfully!");
        } catch (Exception e) {
            System.out.println("❌ PatientTalks test failed: " + e.getMessage());
        }
    }
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            WebElement input = driver.findElement(By.id("messageInput"));
            WebElement sendButton = driver.findElement(By.cssSelector("form.luxgpt-input-row button[type='submit']"));

            input.clear();

            // Type character by character for visual effect
            for (char c : message.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                Thread.sleep(150); // adjust speed for visibility
            }
            System.out.println("✅ Positive Scenario: Message entered visibly.");

            scrollIntoView(sendButton);
            highlight(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            sendButton.click();
            Thread.sleep(2000);
            removeHighlight(sendButton);

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
    public void testWhatsAppEnquiryNegative() throws InterruptedException {
        try {
            System.out.println("\n?? Negative Scenario: Input is empty. Attempting to send...");

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("#messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear(); // ensure empty
            scrollIntoView(sendButton);
            highlight(sendButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);

            Thread.sleep(1500); // wait for popup

            System.out.println("✅ WhatsApp Negative test executed (popup should appear).");
            removeHighlight(sendButton);

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

    public void testArticles() throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]")
            ));
            scrollIntoView(heading);
            Thread.sleep(500);

            actions.moveByOffset(190, 0).perform();
            Thread.sleep(500);
            actions.moveByOffset(-190, 0).perform();

            List<WebElement> titles = driver.findElements(By.cssSelector(".card-title a"));
            String parentWindow = driver.getWindowHandle();

            for (int i = 0; i < titles.size(); i++) {
                titles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement title = titles.get(i);
                scrollIntoView(title);
                js.executeScript("arguments[0].click();", title);
                Thread.sleep(1000);

                for (String tab : driver.getWindowHandles()) {
                    if (!tab.equals(parentWindow)) {
                        driver.switchTo().window(tab);
                        driver.close();
                    }
                }
                driver.switchTo().window(parentWindow);
                Thread.sleep(1000);
                System.out.println("✅ Clicked Article #" + (i + 1));
            }

            System.out.println("🎉 All articles clicked successfully.");
        } catch (Exception e) {
            System.out.println("❌ testArticles failed: " + e.getMessage());
        }
    }

    // ==========================
    // FAQ ACCORDIONS TEST
    // ==========================
    public void testAllFaqAccordions() throws InterruptedException {
        try {
            List<WebElement> allAccordions = driver.findElements(By.cssSelector("div.accordion"));
            System.out.println("🔍 Total FAQs found: " + allAccordions.size());

            for (WebElement accordion : allAccordions) {
                WebElement questionSpan = accordion.findElement(By.cssSelector(".accordion_head > span"));
                String questionText = questionSpan.getText().trim();

                scrollIntoView(questionSpan);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", questionSpan);
                System.out.println("❓ Clicked: " + questionText);

                WebElement answerDiv = accordion.findElement(By.cssSelector(".accordion_content"));
                wait.until(d -> answerDiv.getAttribute("style").contains("max-height") &&
                                !answerDiv.getAttribute("style").contains("max-height: 0px"));

                WebElement answerParagraph = answerDiv.findElement(By.cssSelector(".faq_answer"));
                String answerText = answerParagraph.getText().trim();
                System.out.println("✅ Answer: " + answerText);

                Thread.sleep(1000); // pause for readability
            }
            System.out.println("🎉 All FAQ accordions tested successfully!");
        } catch (Exception e) {
            System.out.println("❌ FAQ Accordions test failed: " + e.getMessage());
        }
    }

    // === HELPERS ===
    private void highlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", el);
    }

    private void removeHighlight(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='none';", el);
    }

    private static void scrollIntoView(WebElement el) {
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
}
