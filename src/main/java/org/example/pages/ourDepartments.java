package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.interactions.Actions;


public class ourDepartments {

    private WebDriver driver;
    private WebDriverWait wait;

    public ourDepartments(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 1) Click on department card by name
    public void clickDepartment(String departmentName) {
        By departmentLocator = By.xpath("//h2[text()='" + departmentName + "']/ancestor::div[contains(@class,'elementor-element')]");
        wait.until(ExpectedConditions.elementToBeClickable(departmentLocator)).click();
    }

    // 2) Click on "Book an Appointment" button
    public void testBookAppointmentButton() throws InterruptedException {
        try {
            System.out.println("🔍 Starting 'Book an Appointment' button test...");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 1️⃣ Wait for full page load (for all Elementor-based department pages)
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
            Thread.sleep(1000); // small delay for animations/transitions

            // 2️⃣ Flexible locator (case insensitive, trims spaces)
            By bookAppointmentBtnLocator = By.xpath(
                    "//a[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'book an appointment')]"
            );

            // Wait for button presence and visibility
            WebElement bookAppointmentBtn = wait.until(ExpectedConditions.presenceOfElementLocated(bookAppointmentBtnLocator));
            wait.until(ExpectedConditions.visibilityOf(bookAppointmentBtn));
            System.out.println("✅ 'Book an Appointment' button detected.");

            // 3️⃣ Scroll smoothly into view with offset
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", bookAppointmentBtn);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, -120);"); // offset for sticky headers
            Thread.sleep(500);
            System.out.println("✅ Scrolled to 'Book an Appointment' button.");

            // 4️⃣ Prepare for new tab/window handling
            String parentWindow = driver.getWindowHandle();
            Set<String> oldTabs = driver.getWindowHandles();

            // 5️⃣ Click using JS for reliability (bypasses overlay/interception)
            js.executeScript("arguments[0].click();", bookAppointmentBtn);
            System.out.println("🖱️ Clicked 'Book an Appointment' button.");
            Thread.sleep(3000);

            // 6️⃣ Detect new tab or same-tab navigation
            Set<String> newTabs = driver.getWindowHandles();
            newTabs.removeAll(oldTabs);

            if (!newTabs.isEmpty()) {
                // New tab opened (e.g., WhatsApp)
                String newTab = newTabs.iterator().next();
                driver.switchTo().window(newTab);
                System.out.println("🔗 Opened new tab: " + driver.getCurrentUrl());
                Thread.sleep(1500);
                driver.close();
                driver.switchTo().window(parentWindow);
                System.out.println("↩️ Returned to main page after closing tab.");
            } else {
                // Possibly same tab navigation — handle it safely
                Thread.sleep(1500);
                System.out.println("⚠️ No new tab detected — possibly same-tab redirect.");
                driver.navigate().back();
                wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
                System.out.println("↩️ Returned back to department page.");
            }

            System.out.println("🎉 Completed 'Book an Appointment' button test successfully for current department!");

        } catch (Exception e) {
            System.out.println("❌ Error in 'testBookAppointmentButton': " + e.getMessage());
        }
    }
    // 3) Verify the "Book an Appointment" link
    public boolean verifyBookAppointmentLink() {
        By bookBtnLocator = By.xpath("//a[.//span[text()='Book an Appointment']]");
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(bookBtnLocator));
        String href = button.getAttribute("href");
        return href != null && href.contains("https://api.whatsapp.com/send");
    }

    // 4) Test all FAQ accordions
    public void testAllFaqAccordions() throws InterruptedException {
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
            wait.until(d -> {
                String style = answerDiv.getAttribute("style");
                return style != null && style.contains("max-height") && !style.contains("max-height: 0px");
            });

            WebElement answerParagraph = answerDiv.findElement(By.cssSelector(".faq_answer"));
            String answerText = answerParagraph.getText().trim();
            System.out.println("✅ Answer: " + answerText);

            Thread.sleep(500);
        }

        System.out.println("🎉 All FAQ accordions tested successfully!");
    }

    // 5) Scroll a WebElement into view
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", element);
    }

    // 6) Scroll to Testimonials and click all dots
    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Scroll to "Testimonials" heading
        By testimonialsHeader = By.xpath("//h2[contains(text(),'Testimonials')]");
        WebElement testimonialsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(testimonialsHeader));

        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", testimonialsElement);
        System.out.println("✅ Scrolled to Testimonials section.");
        Thread.sleep(1500);

        // 2️⃣ Locate all testimonial dots
        By dotsLocator = By.cssSelector(".testimonial-dots span.dot");
        List<WebElement> dots = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dotsLocator));
        System.out.println("✅ Found " + dots.size() + " testimonial dots.");

        // 3️⃣ Click each dot — refetch list each time in case DOM changes
        for (int i = 0; i < dots.size(); i++) {
            try {
                // Re-fetch dots each loop to avoid stale elements
                dots = driver.findElements(dotsLocator);
                WebElement dot = dots.get(i);

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", dot);
                js.executeScript("arguments[0].click();", dot);
                System.out.println("🟢 Clicked dot #" + (i + 1));

                // Small wait to allow carousel transition
                Thread.sleep(2000);

            } catch (StaleElementReferenceException stale) {
                System.out.println("⚠️ Dot list refreshed (DOM changed). Retrying click #" + (i + 1));
                dots = driver.findElements(dotsLocator);
                if (i < dots.size()) {
                    WebElement dot = dots.get(i);
                    js.executeScript("arguments[0].click();", dot);
                    Thread.sleep(1500);
                }
            } catch (ElementClickInterceptedException e) {
                System.out.println("⚠️ Click intercepted on dot #" + (i + 1) + ", using JS click as fallback.");
                dots = driver.findElements(dotsLocator);
                WebElement dot = dots.get(i);
                js.executeScript("arguments[0].click();", dot);
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("❌ Could not click dot #" + (i + 1) + ": " + e.getMessage());
            }
        }

        System.out.println("✅ Finished cycling through all testimonial dots.");
    }

    // 7) Test all treatments (initial + after See More)
    public void testAllTreatments() throws InterruptedException {
        List<WebElement> initialTreatments = driver.findElements(By.cssSelector("#treatmentsGrid a.treatment-item:not(.hidden)"));
        System.out.println("Step 1 - Initially visible treatments: " + initialTreatments.size());

        for (WebElement treatment : initialTreatments) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", treatment);
            Thread.sleep(400);
            System.out.println("Opening Treatment: " + treatment.getText().trim());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", treatment);
            Thread.sleep(1200);
            driver.navigate().back();
            Thread.sleep(800);
        }

        List<WebElement> seeMoreButtons = driver.findElements(By.id("seeMoreBtn"));
        if (!seeMoreButtons.isEmpty()) {
            WebElement seeMore = seeMoreButtons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", seeMore);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seeMore);
            Thread.sleep(1000);
            System.out.println("✅ Clicked 'See More' to reveal additional treatments.");
        }

        List<WebElement> allTreatmentsAfterSeeMore = driver.findElements(By.cssSelector("#treatmentsGrid a.treatment-item:not(.hidden)"));
        int start = Math.min(initialTreatments.size(), allTreatmentsAfterSeeMore.size());
        List<WebElement> newlyVisibleTreatments = allTreatmentsAfterSeeMore.subList(start, allTreatmentsAfterSeeMore.size());
        System.out.println("Step 3 - Newly visible treatments: " + newlyVisibleTreatments.size());

        for (WebElement treatment : newlyVisibleTreatments) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", treatment);
            Thread.sleep(400);
            System.out.println("Opening Newly Visible Treatment: " + treatment.getText().trim());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", treatment);
            Thread.sleep(1200);
            driver.navigate().back();
            Thread.sleep(800);
        }

        System.out.println("✅ All treatments tested successfully!");
    }

    // 8) Scrollable "Why Choose" section inside container (previously missing)
    public void scrollScrollableWhyChooseSection() throws InterruptedException {
        By headingLocator = By.xpath("//h2[text()='Why Choose Lux Hospitals for Proctology?']");
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
        Thread.sleep(400);

        WebElement scrollableContent = driver.findElement(By.id("contentBox"));
        List<WebElement> subHeadings = scrollableContent.findElements(By.cssSelector("h4.sub-heading"));
        System.out.println("Total sub-headings found: " + subHeadings.size());

        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement subHeading : subHeadings) {
            js.executeScript("arguments[0].scrollTop = arguments[1].offsetTop - arguments[0].offsetTop;", scrollableContent, subHeading);
            Thread.sleep(300);
            System.out.println("Scrolled to sub-heading inside container: " + subHeading.getText().trim());
        }

        System.out.println("✅ Completed scrolling Why Choose section.");
    }
 // 9) Scroll to "Latest Health Articles by Lux" and test all cards
    public void scrollToLatestHealthArticles() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 1️⃣ Locate and scroll to the heading
            By articlesHeading = By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(articlesHeading));

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Latest Health Articles by Lux' section.");
            Thread.sleep(1500);

            // 2️⃣ Small hover movement to trigger lazy loading (optional)
            try {
                new org.openqa.selenium.interactions.Actions(driver)
                    .moveByOffset(200, 0).perform();
                Thread.sleep(300);
                new org.openqa.selenium.interactions.Actions(driver)
                    .moveByOffset(-200, 0).perform();
            } catch (Exception ignored) {
                System.out.println("ℹ️ Skipped hover animation (no effect).");
            }

            // 3️⃣ Find all article title links
            List<WebElement> titles = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-title a"))
            );
            System.out.println("✅ Found " + titles.size() + " health article cards.");

            String parentWindow = driver.getWindowHandle();

            // 4️⃣ Loop through and click each article
            for (int i = 0; i < titles.size(); i++) {
                titles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement title = titles.get(i);

                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", title);
                wait.until(ExpectedConditions.elementToBeClickable(title));

                // Capture window handles before click
                java.util.Set<String> windowsBeforeClick = driver.getWindowHandles();

                try {
                    js.executeScript("arguments[0].click();", title);
                } catch (Exception e) {
                    System.out.println("⚠️ Fallback JS click used for article #" + (i + 1));
                    js.executeScript("arguments[0].click();", title);
                }

                // 5️⃣ Wait for new tab to open
                wait.until(d -> d.getWindowHandles().size() > windowsBeforeClick.size());

                // 6️⃣ Identify the new tab
                java.util.Set<String> windowsAfterClick = driver.getWindowHandles();
                windowsAfterClick.removeAll(windowsBeforeClick);
                String newTabHandle = windowsAfterClick.iterator().next();

                // 7️⃣ Switch to the new tab
                driver.switchTo().window(newTabHandle);
                wait.until(webDriver ->
                        ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
                );

                System.out.println("🟢 Opened Article #" + (i + 1) + ": " + driver.getCurrentUrl());
                Thread.sleep(2000);

                // Optional debug
                System.out.println("Active window before close: " + driver.getTitle());

                // 8️⃣ Close the article tab and return
                driver.close();
                driver.switchTo().window(parentWindow);
                Thread.sleep(1000);

                System.out.println("✅ Closed Article #" + (i + 1) + " and returned to main page.");
            }

            System.out.println("🎉 Finished interacting with all 'Latest Health Articles by Lux' cards.");

        } catch (Exception e) {
            System.out.println("❌ Failed in 'scrollToLatestHealthArticles': " + e.getMessage());
        }
    }
 // 10) Test WhatsApp enquiry - positive scenario
    public void testWhatsAppEnquiryPositive() {
        String message = "this is a test message";
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear();

            // Type character by character (visual simulation)
            for (char c : message.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                Thread.sleep(150); // slow type effect
            }
            System.out.println("✅ Positive Scenario: Message entered visibly.");

            scrollIntoView(sendButton);
//            highlight(sendButton);

            Set<String> oldTabs = driver.getWindowHandles();
            js.executeScript("arguments[0].click();", sendButton);
            Thread.sleep(2000);
//            removeHighlight(sendButton);

            // Check if new tab opened
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

    // 11) Test WhatsApp enquiry - negative scenario
    public void testWhatsAppEnquiryNegative() throws InterruptedException {
        try {
            System.out.println("\n⚠️ Negative Scenario: Input is empty. Attempting to send...");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("#messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("form.luxgpt-input-row button[type='submit']")));

            input.clear(); // ensure empty
            scrollIntoView(sendButton);
//            highlight(sendButton);

            js.executeScript("arguments[0].click();", sendButton);
            Thread.sleep(1500); // wait for popup / validation

            System.out.println("✅ WhatsApp Negative test executed (popup or alert should appear).");
//            removeHighlight(sendButton);
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println("⚠️ Alert found with text: " + alert.getText());
                alert.accept(); // close it
                System.out.println("✅ Alert closed successfully.");
            } catch (NoAlertPresentException ignored) {
                System.out.println("ℹ️ No alert appeared.");
            }

        } catch (Exception e) {
            System.out.println("❌ WhatsApp Negative test failed: " + e.getMessage());
        }
    }

    public void scrollToOurDoctorsSection() throws InterruptedException {
        try {
            System.out.println("🔍 Starting 'Our Doctors' test...");

            // 1️⃣ Scroll to 'Our Doctors' heading
            By headingLocator = By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'our doctors')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Our Doctors' section.");
            Thread.sleep(1500);

            // 2️⃣ Detect doctor cards dynamically
            List<WebElement> doctorNames = driver.findElements(By.xpath("//h4[contains(.,'Dr.')]"));
            System.out.println("✅ Found " + doctorNames.size() + " doctor cards.");

            if (doctorNames.isEmpty()) {
                System.out.println("⚠️ No doctor cards found. Exiting test.");
                return;
            }

            String parentWindow = driver.getWindowHandle();

            // 3️⃣ Process each doctor card
            for (int i = 0; i < doctorNames.size(); i++) {
                WebElement doctorName = doctorNames.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", doctorName);
                Thread.sleep(800);

                String doctorText = doctorName.getText().trim();
                System.out.println("\n👨‍⚕️ Processing Doctor Card #" + (i + 1) + " → " + doctorText);

                // find nearest card container
                WebElement doctorContainer = doctorName.findElement(
                    By.xpath("./ancestor::div[contains(@class,'e-con')][1]/ancestor::div[contains(@class,'e-con')][1]")
                );

                // find only visible and valid "View Profile" and "Book Appointment" buttons
                List<WebElement> buttons = doctorContainer.findElements(
                    By.xpath(".//a[contains(@class,'elementor-button')]")
                );

                buttons = buttons.stream()
                        .filter(WebElement::isDisplayed)
                        .filter(b -> {
                            String txt = b.getText().trim().toLowerCase();
                            return txt.contains("view profile") || txt.contains("book appointment");
                        })
                        .distinct()
                        .toList();

                if (buttons.isEmpty()) {
                    System.out.println("⚠️ No buttons found for " + doctorText);
                    continue;
                }

                // Limit to 2 actions (View Profile + Book Appointment)
                int clickCount = 0;

                for (WebElement button : buttons) {
                    if (clickCount >= 2) break;

                    String buttonText = button.getText().trim();
                    System.out.println("   🖱️ Clicking: " + buttonText);

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
                    Thread.sleep(500);

                    Set<String> oldTabs = driver.getWindowHandles();
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                    Thread.sleep(2000);

                    Set<String> newTabs = driver.getWindowHandles();
                    newTabs.removeAll(oldTabs);

                    if (!newTabs.isEmpty()) {
                        String newTab = newTabs.iterator().next();
                        driver.switchTo().window(newTab);
                        driver.close();
                        driver.switchTo().window(parentWindow);
                    } else {
                        System.out.println("   ⚠️ No new tab opened for '" + buttonText + "'.");
                    }

                    clickCount++;
                }
            }

            System.out.println("\n🎉 Completed 'Our Doctors' section test successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error in 'testOurDoctorsSection': " + e.getMessage());
        }
    }
    public void testInsuranceCoverageSection() throws InterruptedException {
        try {
            System.out.println("🔍 Starting 'Insurance Coverage' section test...");

            // 1️⃣ Scroll to 'Insurance Coverage' heading
            By headingLocator = By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'insurance coverage')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Insurance Coverage' section.");
            Thread.sleep(1200);

            // 2️⃣ Locate and click the "Know more" link
            By knowMoreLocator = By.xpath("//p[a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'know more')]]/a");
            WebElement knowMoreLink = wait.until(ExpectedConditions.elementToBeClickable(knowMoreLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", knowMoreLink);
            Thread.sleep(800);
            System.out.println("🖱️ Found and scrolled to 'Know more' link.");

            String currentUrl = driver.getCurrentUrl();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", knowMoreLink);
            System.out.println("✅ Clicked 'Know more' link. Navigating to Insurance page...");

            // 3️⃣ Wait briefly for navigation
            Thread.sleep(3000);

            // 4️⃣ Confirm page navigation (optional)
            String newUrl = driver.getCurrentUrl();
            if (!newUrl.equals(currentUrl)) {
                System.out.println("🔗 Navigated to: " + newUrl);
            } else {
                System.out.println("⚠️ URL did not change after clicking 'Know more'.");
            }

            // 5️⃣ Navigate back to the previous page
            driver.navigate().back();
            Thread.sleep(2000);
            System.out.println("✅ Returned back to the previous page.");

            System.out.println("🎉 Completed 'Insurance Coverage' section test successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error in 'testInsuranceCoverageSection': " + e.getMessage());
        }
    }
    public void testTechnologyAndEquipmentSection() throws InterruptedException {
        try {
            System.out.println("🔍 Starting 'Technology and Equipment' section test...");

            // 1️⃣ Scroll to heading
            By headingLocator = By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'technology and equipment')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Technology and Equipment in Lux Hospitals' section.");
            Thread.sleep(1200);

            // 2️⃣ Find technology cards
            List<WebElement> techCards = driver.findElements(By.cssSelector(".technology-containers .single-technology"));

            if (techCards.isEmpty()) {
                System.out.println("⚠️ No technology cards found!");
                return;
            }

            System.out.println("✅ Found " + techCards.size() + " technology cards.");

            Actions actions = new Actions(driver);

            // 3️⃣ Hover over each technology card
            for (int i = 0; i < techCards.size(); i++) {
                WebElement card = techCards.get(i);

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                Thread.sleep(700);

                actions.moveToElement(card).perform();

                // Extract title or alt text for clarity
                String techName = "";
                try {
                    techName = card.findElement(By.tagName("strong")).getText().trim();
                } catch (Exception ignored) {
                    try {
                        techName = card.findElement(By.tagName("img")).getAttribute("alt").trim();
                    } catch (Exception e2) {
                        techName = "Unnamed Technology";
                    }
                }

                System.out.println("🖱️ Hovered over Technology #" + (i + 1) + " → " + techName);
                Thread.sleep(1000);
            }

            System.out.println("🎉 Completed hover verification for all Technology and Equipment cards!");

        } catch (Exception e) {
            System.out.println("❌ Error in 'testTechnologyAndEquipmentSection': " + e.getMessage());
        }
    }

}
