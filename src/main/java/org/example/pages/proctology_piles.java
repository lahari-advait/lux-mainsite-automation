package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class proctology_piles {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators
    private By treatmentsMenu = By.linkText("Treatments");
    private By proctologyItem = By.cssSelector("li[data-target='for__proctology__parent___box'] span");
    private By pilesLink = By.linkText("Piles");
    private By bookAppointmentButton = By.xpath("//a[contains(@href,'whatsapp')]");
    private By faqAccordions = By.cssSelector("div.accordion");

    public proctology_piles(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    
    public void hoverOverTreatments() {
        Actions actions = new Actions(driver);
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(treatmentsMenu));
        actions.moveToElement(menu)
               .pause(Duration.ofMillis(500))
               .perform();
        System.out.println("Hovered over 'Treatments'");
    }

    public void clickProctology() {
        WebElement proctology = wait.until(ExpectedConditions.elementToBeClickable(proctologyItem));
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", proctology);
        proctology.click();
        System.out.println("Clicked on 'Proctology'");
    }

    public void clickPilesTreatment() {
        WebElement piles = wait.until(ExpectedConditions.elementToBeClickable(pilesLink));
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", piles);
        piles.click();
        System.out.println("Clicked on 'Piles' treatment");
    }

    public void clickBookAppointment() {
        try {
            // Wait for button and scroll
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(bookAppointmentButton));
            js.executeScript("arguments[0].scrollIntoView({block:'center'})", button);
            js.executeScript("arguments[0].click();", button); // JS click is more reliable

            // Store original tab
            String originalTab = driver.getWindowHandle();

            // Wait for a new tab to appear
            wait.until(d -> driver.getWindowHandles().size() > 1);

            // Switch to the new tab
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Optional: wait for URL or page load
            wait.until(ExpectedConditions.urlContains("https")); // adjust as needed

            System.out.println("Opened WhatsApp URL: " + driver.getCurrentUrl());

            // Close new tab and switch back
            driver.close();
            driver.switchTo().window(originalTab);
        } catch (Exception e) {
            System.out.println("❌ Failed to open WhatsApp URL: " + e.getMessage());
        }
    }


    public void testAllFaqAccordions() {
        List<WebElement> accordions = driver.findElements(faqAccordions);

        for (WebElement accordion : accordions) {
            WebElement question = accordion.findElement(By.cssSelector(".accordion_head > span"));
            js.executeScript("window.scrollTo({top: arguments[0] - 150, behavior: 'smooth'});", question.getLocation().getY());
            wait.until(ExpectedConditions.elementToBeClickable(question));
            js.executeScript("arguments[0].click();", question);
            WebElement answer = accordion.findElement(By.cssSelector(".accordion_content .faq_answer"));
            wait.until(ExpectedConditions.visibilityOf(answer));
            System.out.println("FAQ: " + question.getText() + " => Answer: " + answer.getText());
        }
    }

  

    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        By testimonialsHeader = By.xpath("//h2[contains(text(),'Testimonials')]");
        WebElement testimonialsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(testimonialsHeader));

        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", testimonialsElement);
        System.out.println("✅ Scrolled to Testimonials section.");
        Thread.sleep(2000);

        By testimonialDots = By.cssSelector(".testimonial-dots span.dot");
        List<WebElement> dots = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(testimonialDots));

        System.out.println("✅ Found " + dots.size() + " testimonial dots.");

        for (int i = 0; i < dots.size(); i++) {
            try {
                WebElement dot = dots.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'})", dot);
                js.executeScript("arguments[0].click();", dot);
                System.out.println("🟢 Clicked dot #" + (i + 1));
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("⚠️ Could not click dot #" + (i + 1) + ": " + e.getMessage());
            }
        }

        System.out.println("✅ Finished cycling through all testimonial dots.");
    }

    public void scrollToLatestHealthArticles() throws InterruptedException {
        try {
            By articlesHeading = By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(articlesHeading));

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", heading);
            System.out.println("✅ Scrolled to 'Latest Health Articles by Lux' section.");
            Thread.sleep(1500);

            Actions actions = new Actions(driver);
            actions.moveByOffset(200, 0).perform();
            Thread.sleep(300);
            actions.moveByOffset(-200, 0).perform();

            List<WebElement> titles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-title a")));
            System.out.println("✅ Found " + titles.size() + " health article cards.");

            String parentWindow = driver.getWindowHandle();

            for (int i = 0; i < titles.size(); i++) {
                titles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement title = titles.get(i);
                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", title);
                wait.until(ExpectedConditions.elementToBeClickable(title));

                Set<String> windowsBeforeClick = driver.getWindowHandles();
                js.executeScript("arguments[0].click();", title);

                wait.until(d -> d.getWindowHandles().size() > windowsBeforeClick.size());
                Set<String> windowsAfterClick = driver.getWindowHandles();
                windowsAfterClick.removeAll(windowsBeforeClick);
                String newTabHandle = windowsAfterClick.iterator().next();

                driver.switchTo().window(newTabHandle);
                wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

                System.out.println("🟢 Opened Article #" + (i + 1) + ": " + driver.getCurrentUrl());
                Thread.sleep(2000);

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
 // ================== WhatsApp Enquiry Methods ==================
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
            System.out.println("\n⚠️ Negative Scenario: Input is empty. Attempting to send...");

            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#messageInput")));
            WebElement sendButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form.luxgpt-input-row button[type='submit']")));

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

    // ================== Helper Methods ==================
    private void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", element);
    }

    private void highlight(WebElement element) {
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    private void removeHighlight(WebElement element) {
        js.executeScript("arguments[0].style.border=''", element);
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
                Thread.sleep(1000);
                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", link);
                System.out.println("🔽 Scrolled to: " + linkText);
                Thread.sleep(1500);

                wait.until(ExpectedConditions.elementToBeClickable(link));
                highlight(link);

                js.executeScript("arguments[0].click();", link);
                System.out.println("🟢 Clicked on section link: " + linkText + " (" + href + ")");

                Thread.sleep(2500);
                removeHighlight(link);

                // Last link check
                if (i == links.size() - 1) {
                    System.out.println("✨ Last section link clicked: executing FAQ tests...");
                    testAllFaqAccordions();
                }
            }

            Thread.sleep(1500);
            System.out.println("🎉 Successfully clicked all index section links one by one!");
        } catch (Exception e) {
            System.out.println("❌ Failed while clicking index links: " + e.getMessage());
        }
    }



}
