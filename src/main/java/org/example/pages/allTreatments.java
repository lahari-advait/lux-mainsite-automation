package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class allTreatments {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By treatmentsMenu = By.linkText("Treatments");
    private By bookAppointmentButton = By.xpath("//a[contains(@href,'whatsapp')]");
    private By faqAccordions = By.cssSelector("div.accordion");

    public allTreatments(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // ================== Hover & Navigation ==================
    public void hoverOverTreatments() {
        Actions actions = new Actions(driver);
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(treatmentsMenu));
        actions.moveToElement(menu).pause(Duration.ofMillis(500)).perform();
        System.out.println("✅ Hovered over 'Treatments' menu");
    }

    public void clickTreatmentCategory(String categoryName) {
        By categoryLocator = By.xpath("//span[text()='" + categoryName + "']");
        WebElement category = wait.until(ExpectedConditions.elementToBeClickable(categoryLocator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", category);
        category.click();
        System.out.println("✅ Clicked treatment category: " + categoryName);
    }

    public void clickSpecificTreatment(String treatmentName) {
        By treatmentLink = By.linkText(treatmentName);
        WebElement treatment = wait.until(ExpectedConditions.elementToBeClickable(treatmentLink));
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", treatment);
        treatment.click();
        System.out.println("✅ Clicked on treatment: " + treatmentName);
    }

    // ================== Book Appointment ==================
    public void clickBookAppointment() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(bookAppointmentButton));
            js.executeScript("arguments[0].scrollIntoView({block:'center'})", button);
            js.executeScript("arguments[0].click();", button);

            String originalTab = driver.getWindowHandle();
            wait.until(d -> driver.getWindowHandles().size() > 1);

            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            wait.until(ExpectedConditions.urlContains("https"));
            System.out.println("🟢 Opened WhatsApp URL: " + driver.getCurrentUrl());

            driver.close();
            driver.switchTo().window(originalTab);
        } catch (Exception e) {
            System.out.println("❌ Failed to open WhatsApp URL: " + e.getMessage());
        }
    }

    // ================== FAQ Section ==================
    public void testAllFaqAccordions() {
        List<WebElement> accordions = driver.findElements(faqAccordions);
        for (WebElement accordion : accordions) {
            try {
                WebElement question = accordion.findElement(By.cssSelector(".accordion_head > span"));
                js.executeScript("arguments[0].scrollIntoView({block:'center'})", question);
                wait.until(ExpectedConditions.elementToBeClickable(question));
                js.executeScript("arguments[0].click();", question);

                WebElement answer = accordion.findElement(By.cssSelector(".accordion_content .faq_answer"));
                wait.until(ExpectedConditions.visibilityOf(answer));

                System.out.println("FAQ: " + question.getText() + " => Answer: " + answer.getText());
            } catch (Exception e) {
                System.out.println("⚠️ Skipped an accordion due to error: " + e.getMessage());
            }
        }
    }

    // ================== Testimonials ==================
    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        By testimonialsHeader = By.xpath("//h2[contains(text(),'Testimonials')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(testimonialsHeader));

        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", header);
        System.out.println("✅ Scrolled to Testimonials section.");
        Thread.sleep(2000);

        By dots = By.cssSelector(".testimonial-dots span.dot");
        List<WebElement> dotElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dots));

        for (int i = 0; i < dotElements.size(); i++) {
            try {
                WebElement dot = dotElements.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'})", dot);
                js.executeScript("arguments[0].click();", dot);
                System.out.println("🟢 Clicked dot #" + (i + 1));
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("⚠️ Could not click dot #" + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    // ================== Latest Health Articles ==================
    public void scrollToLatestHealthArticles() throws InterruptedException {
        try {
            By headingLocator = By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", heading);
            System.out.println("✅ Scrolled to 'Latest Health Articles by Lux' section.");
            Thread.sleep(1500);

            List<WebElement> titles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-title a")));
            System.out.println("✅ Found " + titles.size() + " article cards.");

            String parentWindow = driver.getWindowHandle();

            for (int i = 0; i < titles.size(); i++) {
                titles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement title = titles.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'})", title);
                wait.until(ExpectedConditions.elementToBeClickable(title));

                Set<String> oldWindows = driver.getWindowHandles();
                js.executeScript("arguments[0].click();", title);
                wait.until(d -> d.getWindowHandles().size() > oldWindows.size());

                Set<String> newWindows = driver.getWindowHandles();
                newWindows.removeAll(oldWindows);
                String newTab = newWindows.iterator().next();

                driver.switchTo().window(newTab);
                wait.until(wd -> js.executeScript("return document.readyState").equals("complete"));
                System.out.println("🟢 Opened article #" + (i + 1) + ": " + driver.getCurrentUrl());

                driver.close();
                driver.switchTo().window(parentWindow);
            }

            System.out.println("🎉 Finished checking all articles.");
        } catch (Exception e) {
            System.out.println("❌ Failed in 'scrollToLatestHealthArticles': " + e.getMessage());
        }
    }

    // ================== Click All Section Links One by One ==================
    public void clickAllSectionLinksOneByOne() throws InterruptedException {
        try {
            By indexLinks = By.cssSelector(".index-scroll-container a.clickable");
            List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(indexLinks));

            System.out.println("✅ Found " + links.size() + " section links in the index container.");
            Thread.sleep(1500);

            for (int i = 0; i < links.size(); i++) {
                links = driver.findElements(indexLinks);
                WebElement link = links.get(i);

                String linkText = link.getText().trim();
                String href = link.getAttribute("href");

                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", link);
                wait.until(ExpectedConditions.elementToBeClickable(link));

                js.executeScript("arguments[0].click();", link);
                System.out.println("🟢 Clicked section link: " + linkText + " (" + href + ")");

                Thread.sleep(2500);

                if (i == links.size() - 1) {
                    System.out.println("✨ Last section link clicked — running FAQ accordion tests...");
                    testAllFaqAccordions();
                }
            }

            System.out.println("🎉 Finished clicking all index section links one by one!");
        } catch (Exception e) {
            System.out.println("❌ Failed while clicking index links: " + e.getMessage());
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
    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void highlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px solid red'", element);
    }

    private void removeHighlight(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
    }
    public void handleAllDoctorCards() throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            js.executeScript("window.scrollBy(0, 800);");
            Thread.sleep(1000);

            // 🩺 Find doctor cards (both mobile & desktop, visible only)
            List<WebElement> cards = driver.findElements(
                    By.xpath("//div[contains(@class,'doctor-card-mobile-main') or contains(@class,'doctor-card-desktop-main')]")
            );

            // Filter only visible cards (avoid duplicates)
            List<WebElement> visibleCards = new ArrayList<>();
            for (WebElement card : cards) {
                if (card.isDisplayed()) {
                    visibleCards.add(card);
                }
            }

            if (visibleCards.isEmpty()) {
                System.out.println("⚠️ No visible doctor cards found on this page.");
                return;
            }

            System.out.println("👩‍⚕️ Found " + visibleCards.size() + " visible doctor card(s).");
            String parentWindow = driver.getWindowHandle();

            // 🎯 Loop through visible cards only
            for (int i = 0; i < visibleCards.size(); i++) {
                WebElement card = visibleCards.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                Thread.sleep(800);
                System.out.println("➡️ Handling doctor card #" + (i + 1));

                // 1️⃣ --- Book Appointment ---
                try {
                    WebElement bookBtn = card.findElement(
                            By.xpath(".//button[contains(@class,'doctor-card-book-btn-desktop') or contains(@class,'doctor-card-book-btn-mobile')]")
                    );
                    if (bookBtn.isDisplayed()) {
                        js.executeScript("arguments[0].click();", bookBtn);
                        System.out.println("🩺 Clicked Book Appointment...");
                        Thread.sleep(2000);
                        closeNewTabAndReturn(parentWindow);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ No visible Book Appointment button for card #" + (i + 1));
                }

                Thread.sleep(1000);

                // 2️⃣ --- View Profile ---
                try {
                    WebElement profileBtn = card.findElement(
                            By.xpath(".//button[contains(@class,'doctor-card-appointment-btn-mobile') or contains(@class,'doctor-card-view-profile-btn')]")
                    );
                    if (profileBtn.isDisplayed()) {
                        js.executeScript("arguments[0].click();", profileBtn);
                        System.out.println("👩‍⚕️ Clicked View Profile...");
                        Thread.sleep(2000);
                        closeNewTabAndReturn(parentWindow);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ No visible View Profile button for card #" + (i + 1));
                }

                System.out.println("✅ Finished doctor card #" + (i + 1));
            }

            System.out.println("🎯 All visible doctor cards handled successfully.");

        } catch (Exception e) {
            System.out.println("❌ Error while handling doctor cards: " + e.getMessage());
        }
    }

    /**
     * Helper – close new tab (if opened) and switch back to parent window
     */
    private void closeNewTabAndReturn(String parentWindow) {
        try {
            Set<String> allWindows = driver.getWindowHandles();
            for (String w : allWindows) {
                if (!w.equals(parentWindow)) {
                    driver.switchTo().window(w);
                    System.out.println("🔗 New tab opened → " + driver.getCurrentUrl());
                    Thread.sleep(1500);
                    driver.close();
                    driver.switchTo().window(parentWindow);
                    System.out.println("🔙 Closed new tab and returned to parent.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ No new tab detected to close: " + e.getMessage());
        }
    }
    public void clickViewMore(int departmentIndex) {
        try {
            Actions actions = new Actions(driver);

            // Step 0: Hover over Treatments
            hoverTreatments(actions);

            // Step 1: Get department list items dynamically
            List<WebElement> departments = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("#departments-list li.department___listitem"))
            );

            if (departmentIndex >= departments.size()) {
                throw new RuntimeException("Index " + departmentIndex + " is out of range. Count: " + departments.size());
            }

            WebElement departmentItem = departments.get(departmentIndex);
            String deptName = departmentItem.findElement(By.tagName("span")).getText();

            // Step 2: Click department
            departmentItem.click();
            System.out.println("✅ Selected: " + deptName);

            // Step 3: Wait for active right box
            String activeBoxClass = departmentItem.getAttribute("data-target");
            By activeBoxLocator = By.cssSelector("div." + activeBoxClass + ".active");
            WebElement activeBox = wait.until(ExpectedConditions.visibilityOfElementLocated(activeBoxLocator));

            // Step 4: Scroll + click View More
            WebElement viewMoreLink = activeBox.findElement(By.cssSelector("div.right__view___more a"));
            js.executeScript("arguments[0].scrollIntoView({block:'center'})", viewMoreLink);
            Thread.sleep(300);

            actions.moveToElement(viewMoreLink).click().perform();
            System.out.println("✅ Clicked ‘View More’ for: " + deptName);

            // Step 5: Wait for new page to load
            Thread.sleep(1500);

            // Step 6: Navigate back to the previous page
            driver.navigate().back();
            System.out.println("🔙 Navigated back");

            // Step 7: Wait for page to load + hover again
            wait.until(ExpectedConditions.visibilityOfElementLocated(treatmentsMenu));
            Thread.sleep(700);

            hoverTreatments(actions);
            System.out.println("🔁 Hovered Treatments again");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper Method
    private void hoverTreatments(Actions actions) {
        WebElement treatmentsMenuElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(treatmentsMenu)
        );

        actions.moveToElement(treatmentsMenuElement)
                .pause(Duration.ofMillis(500))
                .perform();

        System.out.println("🟩 Hovered ‘Treatments’");
    }


}
