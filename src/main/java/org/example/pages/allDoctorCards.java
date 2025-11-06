package org.example.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class allDoctorCards {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public allDoctorCards(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    // Open homepage
//    public void openHomePage() {
//        driver.get("https://luxhospitals.com/");
//        driver.manage().window().maximize();
//    }

    // Navigate to Doctors page
    public void openDoctorsPage() {
        By doctorsNavLink = By.xpath("//a[contains(text(),'Doctors')]");
        WebElement doctorsLink = wait.until(ExpectedConditions.elementToBeClickable(doctorsNavLink));
        doctorsLink.click();
        wait.until(ExpectedConditions.urlContains("/doctors"));
    }

    // Click View Profile and switch to child window
    public void viewDoctorProfile(String doctorName) throws InterruptedException {
        Thread.sleep(2000);
        By doctorCard = By.xpath("//div[@class='doctor-card'][.//h3[contains(text(),'" + doctorName + "')]]");
        WebElement doctorCardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(doctorCard));

        WebElement viewProfileButton = doctorCardElement.findElement(
            By.xpath(".//button[contains(., 'View Profile') or contains(., 'Appointment')]")
        );

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", viewProfileButton);
        wait.until(ExpectedConditions.elementToBeClickable(viewProfileButton));

        // Click the button to open child window
        try {
            viewProfileButton.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", viewProfileButton);
        }

        // Switch to newly opened child window
//        wait.until(d -> driver.getWindowHandles().size() > 1);
//        Set<String> allWindows = driver.getWindowHandles();
//        String currentWindow = driver.getWindowHandle();
//        for (String window : allWindows) {
//            if (!window.equals(currentWindow)) {
//                driver.switchTo().window(window);
//                break;
//           }
//       }
   }

    // Click Book Appointment on the child window
    public void clickBookAppointmentAndCloseWhatsApp() throws InterruptedException {
        By bookAppointmentBtn = By.xpath("//a[contains(@class,'elementor-button')]//span[normalize-space()='Book Appointment']/ancestor::a[1]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(bookAppointmentBtn));

        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", button);
        Thread.sleep(1000);

        // Get current window before click
        String originalWindow = driver.getWindowHandle();
        Set<String> windowsBefore = driver.getWindowHandles();

        // JS click to open WhatsApp
        js.executeScript("arguments[0].click();", button);

        // Wait for new tab to open
        WebDriverWait tabWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        tabWait.until(d -> d.getWindowHandles().size() > windowsBefore.size());

        // Identify and switch to new tab
        Set<String> windowsAfter = driver.getWindowHandles();
        windowsAfter.removeAll(windowsBefore);
        String newTab = windowsAfter.iterator().next();

        driver.switchTo().window(newTab);
        System.out.println("✅ Switched to WhatsApp tab: " + driver.getCurrentUrl());

        Thread.sleep(5000); // just to observe the tab

        // ✅ Always check before closing
        if (driver.getCurrentUrl().contains("whatsapp")) {
            System.out.println("✅ WhatsApp opened successfully!");
        } else {
            System.out.println("⚠️ WhatsApp may not have loaded correctly.");
        }

        // ✅ Close WhatsApp tab
        driver.close();

        // ✅ Important: switch back to original window if it still exists
        driver.switchTo().window(originalWindow);
        System.out.println("✅ Switched back to original page: " + driver.getTitle());
    }


    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        // 1️⃣ Scroll to the "Testimonials" heading
        By testimonialsHeader = By.xpath("//h2[contains(text(),'Testimonials')]");
        WebElement testimonialsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(testimonialsHeader));

        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", testimonialsElement);
        System.out.println("✅ Scrolled to Testimonials section.");
        Thread.sleep(2000); // wait to observe

        // 2️⃣ Locate all testimonial dots
        By testimonialDots = By.cssSelector(".testimonial-dots span.dot");
        java.util.List<WebElement> dots = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(testimonialDots));

        System.out.println("✅ Found " + dots.size() + " testimonial dots.");

        // 3️⃣ Click each dot with a short delay so you can see it change
        for (int i = 0; i < dots.size(); i++) {
            try {
                WebElement dot = dots.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", dot);
                js.executeScript("arguments[0].click();", dot);
                System.out.println("🟢 Clicked dot #" + (i + 1));
                Thread.sleep(2000); // wait to see the slide change
            } catch (Exception e) {
                System.out.println("⚠️ Could not click dot #" + (i + 1) + ": " + e.getMessage());
            }
        }

        System.out.println("✅ Finished cycling through all testimonial dots.");
    }

    public void scrollToLatestHealthArticles() {
        try {
            // 1️⃣ Locate and scroll to the heading
            By articlesHeading = By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(articlesHeading));

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Latest Health Articles by Lux' section.");
            Thread.sleep(1500);

            // 2️⃣ Small hover movement to trigger lazy loading (optional)
            Actions actions = new Actions(driver);
            actions.moveByOffset(200, 0).perform();
            Thread.sleep(300);
            actions.moveByOffset(-200, 0).perform();

            // 3️⃣ Find all article title links
            List<WebElement> titles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".card-title a")));
            System.out.println("✅ Found " + titles.size() + " health article cards.");

            String parentWindow = driver.getWindowHandle();

            // 4️⃣ Loop through and click each article
            for (int i = 0; i < titles.size(); i++) {
                titles = driver.findElements(By.cssSelector(".card-title a"));
                WebElement title = titles.get(i);

                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", title);
                wait.until(ExpectedConditions.elementToBeClickable(title));

                // Capture current windows before click
                Set<String> windowsBeforeClick = driver.getWindowHandles();

                try {
                    js.executeScript("arguments[0].click();", title);
                } catch (Exception e) {
                    System.out.println("⚠️ Fallback JS click used for article #" + (i + 1));
                    js.executeScript("arguments[0].click();", title);
                }

                // 5️⃣ Wait for new tab to open
                wait.until(d -> d.getWindowHandles().size() > windowsBeforeClick.size());

                // 6️⃣ Identify the new tab reliably
                Set<String> windowsAfterClick = driver.getWindowHandles();
                windowsAfterClick.removeAll(windowsBeforeClick);
                String newTabHandle = windowsAfterClick.iterator().next();

                // 7️⃣ Switch to the new tab
                driver.switchTo().window(newTabHandle);
                wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));

                System.out.println("🟢 Opened Article #" + (i + 1) + ": " + driver.getCurrentUrl());
                Thread.sleep(2000);

                // Optional debug line
                System.out.println("Active window before close: " + driver.getTitle());

                // 8️⃣ Close the article tab and switch back
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
    public void scrollToFAQAndTestAccordions() {
        try {
            // 1️⃣ Scroll to the FAQ section heading
            By faqHeading = By.xpath("//h2[contains(text(),'Frequently Asked Questions')]");
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(faqHeading));

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
            System.out.println("✅ Scrolled to 'Frequently Asked Questions' section.");
            Thread.sleep(1500);

            // 2️⃣ Locate all FAQ accordion elements
            List<WebElement> allAccordions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div.accordion")));
            System.out.println("🔍 Total FAQs found: " + allAccordions.size());

            // 3️⃣ Iterate through each accordion
            for (int i = 0; i < allAccordions.size(); i++) {
                WebElement accordion = allAccordions.get(i);

                WebElement questionSpan = accordion.findElement(By.cssSelector(".accordion_head > span"));
                String questionText = questionSpan.getText().trim();

                js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", questionSpan);
                Thread.sleep(500);

                // Click to expand (JS click ensures it works even if overlayed)
                try {
                    js.executeScript("arguments[0].click();", questionSpan);
                } catch (Exception e) {
                    System.out.println("⚠️ JS click fallback for question: " + questionText);
                    js.executeScript("arguments[0].click();", questionSpan);
                }

                System.out.println("❓ Clicked: " + questionText);

                // Wait for the accordion to expand
                WebElement answerDiv = accordion.findElement(By.cssSelector(".accordion_content"));
                wait.until(d -> answerDiv.getAttribute("style").contains("max-height") &&
                        !answerDiv.getAttribute("style").contains("max-height: 0px"));

                // Extract and print the answer
                WebElement answerParagraph = answerDiv.findElement(By.cssSelector(".faq_answer"));
                String answerText = answerParagraph.getText().trim();
                System.out.println("✅ Answer: " + answerText);

                Thread.sleep(1000); // Pause for readability
            }

            System.out.println("🎉 All FAQ accordions tested successfully!");

        } catch (Exception e) {
            System.out.println("❌ FAQ Accordion test failed: " + e.getMessage());
        }
    }
    
//        String[] doctorNames = {
//            "Dr. Samhitha Reddy",
//            "Dr. Abhishek Katha",
//            "Dr. Samhitha Alukur",
//            "Dr. Harshita Kakarla",
//            "Dr. P Pragnia",
//            "Dr. Priyank Salecha",
//            "Dr. Chandana Guduru",
//            "Dr. M. Ram Prabhu",
//            "Dr. Madhan Mohan",
//            "Dr. Sai Kishan Sirasala",
//            "Dt. Kruthi Goud"
//        };

    public void clickAllDoctorCards() {
    	 try {
    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    	        JavascriptExecutor js = (JavascriptExecutor) driver;

    	        // Scroll to "Other Doctors" section
    	        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	                By.xpath("//h2[contains(text(),'Other Doctors')]")));
    	        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);
    	        System.out.println("✅ Scrolled to 'Other Doctors' section.");

    	        final String mainWindow = driver.getWindowHandle();

    	        // Find total doctor cards via carousel dots
    	        List<WebElement> dots = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
    	                By.cssSelector(".swiper-pagination-bullet")));
    	        int totalCards = dots.size();
    	        System.out.println("🟢 Found " + totalCards + " doctor cards.");

    	        for (int i = 0; i < totalCards; i++) {
    	            System.out.println("\n➡️ Processing doctor card " + (i + 1));

    	            // Ensure main window focus
    	            try { driver.switchTo().window(mainWindow); } catch (Exception ignored) {}

    	            // Re-fetch dots to avoid stale references
    	            dots = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
    	                    By.cssSelector(".swiper-pagination-bullet")));
    	            WebElement dot = dots.get(i);

    	            // Click the dot (scroll into view + single JS click)
    	            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", dot);
    	            js.executeScript("arguments[0].click();", dot);

    	            // small sleep to allow carousel to animate; explicit wait below also helps
    	            Thread.sleep(900);

    	            // Active card
    	            WebElement activeCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	                    By.cssSelector(".swiper-slide-active .elementor-element.e-con-full")));

    	            // Helper inline: safely click a link that may open a new tab and return
    	            BiConsumer<WebElement, String> safeClickAndCloseNewTab = (link, name) -> {
    	                if (link == null) {
    	                    System.out.println("⚠️ " + name + " link element is null.");
    	                    return;
    	                }
    	                try {
    	                    // record handles before click
    	                    Set<String> handlesBefore = driver.getWindowHandles();

    	                    // single JS click (avoids double clicks)
    	                    js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", link);
    	                    js.executeScript("arguments[0].click();", link);

    	                    // wait for a new window handle to appear (timeout ~8s)
    	                    WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(8));
    	                    boolean newHandleAppeared = smallWait.until(d -> driver.getWindowHandles().size() > handlesBefore.size());

    	                    if (!newHandleAppeared) {
    	                        System.out.println("⚠️ '" + name + "' did not open a new tab/window for this card.");
    	                        // Still ensure we are back to main window
    	                        try { driver.switchTo().window(mainWindow); } catch (Exception ignored) {}
    	                        return;
    	                    }

    	                    // find the new handle
    	                    Set<String> handlesAfter = driver.getWindowHandles();
    	                    handlesAfter.removeAll(handlesBefore);
    	                    if (handlesAfter.isEmpty()) {
    	                        System.out.println("⚠️ Unable to identify new tab handle for '" + name + "'.");
    	                        try { driver.switchTo().window(mainWindow); } catch (Exception ignored) {}
    	                        return;
    	                    }

    	                    String newHandle = handlesAfter.iterator().next();

    	                    // Defensive: don't close mainWindow if it was somehow returned
    	                    if (mainWindow.equals(newHandle)) {
    	                        System.out.println("⚠️ New handle equals main window for '" + name + "'. Skipping close.");
    	                        try { driver.switchTo().window(mainWindow); } catch (Exception ignored) {}
    	                        return;
    	                    }

    	                    // Switch to new tab, wait for load, then close
    	                    driver.switchTo().window(newHandle);
    	                    // wait until document complete (defensive)
    	                    WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	                    pageLoadWait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    	                    Thread.sleep(600); // tiny stabilizer
    	                    driver.close();
    	                } catch (TimeoutException te) {
    	                    System.out.println("⚠️ Timeout while waiting for new tab for '" + name + "': " + te.getMessage());
    	                } catch (Exception ex) {
    	                    System.out.println("⚠️ Error handling new tab for '" + name + "': " + ex.getMessage());
    	                } finally {
    	                    // ALWAYS return to main window handle
    	                    try { driver.switchTo().window(mainWindow); } catch (Exception swEx) {
    	                        System.out.println("❌ Failed to switch back to main window: " + swEx.getMessage());
    	                    }
    	                }
    	            };

    	            // 1) Click "View Profile" if present
    	            try {
    	                WebElement viewProfile = activeCard.findElement(By.xpath(".//a[contains(normalize-space(.),'View Profile')]"));
    	                safeClickAndCloseNewTab.accept(viewProfile, "View Profile");
    	                System.out.println("✅ View Profile processed for card " + (i + 1));
    	            } catch (NoSuchElementException e) {
    	                System.out.println("⚠️ No 'View Profile' button on card " + (i + 1));
    	            }

    	            // 2) Click "Book Appointment" if present
    	            try {
    	                // re-fetch active card to avoid stale reference
    	                activeCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
    	                        By.cssSelector(".swiper-slide-active .elementor-element.e-con-full")));
    	                WebElement bookBtn = activeCard.findElement(By.xpath(".//a[contains(normalize-space(.),'Book Appointment')]"));
    	                safeClickAndCloseNewTab.accept(bookBtn, "Book Appointment");
    	                System.out.println("✅ Book Appointment processed for card " + (i + 1));
    	            } catch (NoSuchElementException e) {
    	                System.out.println("⚠️ No 'Book Appointment' button on card " + (i + 1));
    	            }

    	            // small pause before next iteration
    	            Thread.sleep(600);
    	        }

    	        System.out.println("\n🎯 Completed all doctor cards successfully!");
    	    } catch (Exception e) {
    	        System.out.println("❌ Automation failed: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	}
    
    public void clickParentWhatsAppButtonForDoctor(String doctorName) throws InterruptedException {
        try {
            // scroll to the doctor’s card on the main Doctors page
            By doctorCard = By.xpath("//div[@class='doctor-card'][.//h3[contains(text(),'" + doctorName + "')]]");
            WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(doctorCard));

            // locate the WhatsApp link/button inside that card
            By whatsappBtn = By.xpath(".//a[contains(@href,'api.whatsapp.com')]/button[contains(.,'Book Appointment')]");
            WebElement button = card.findElement(whatsappBtn);

            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", button);
            Thread.sleep(1000);

            // record current window before clicking
            String parentWindow = driver.getWindowHandle();
            Set<String> beforeClick = driver.getWindowHandles();

            js.executeScript("arguments[0].click();", button);
            System.out.println("🟢 Clicked WhatsApp 'Book Appointment' for: " + doctorName);

            // wait for new WhatsApp tab
            wait.until(d -> d.getWindowHandles().size() > beforeClick.size());

            // switch to new tab
            Set<String> afterClick = driver.getWindowHandles();
            afterClick.removeAll(beforeClick);
            String newTab = afterClick.iterator().next();
            driver.switchTo().window(newTab);
            //System.out.println("✅ Switched to WhatsApp tab: " + driver.getCurrentUrl());

            Thread.sleep(3000); // optional wait to observe WhatsApp

            // close WhatsApp tab and return to parent
            driver.close();
            driver.switchTo().window(parentWindow);
            System.out.println("✅ Closed WhatsApp tab and returned to parent: " + doctorName);

        } catch (Exception e) {
            System.out.println("❌ Failed to click WhatsApp for " + doctorName + ": " + e.getMessage());
        }
    }


 // 1️⃣ Switch to a newly opened window
 public void switchToNewWindow() {
	   // Switch to newly opened child window
   wait.until(d -> driver.getWindowHandles().size() > 1);
   Set<String> allWindows = driver.getWindowHandles();
   String currentWindow = driver.getWindowHandle();
   for (String window : allWindows) {
       if (!window.equals(currentWindow)) {
           driver.switchTo().window(window);
           break;
        }
    }
 }

 // 2️⃣ Close current window and switch back to parent
 public void closeCurrentWindowAndSwitchBack(String parentWindow) {
     driver.close();
     driver.switchTo().window(parentWindow);
     System.out.println("✅ Closed current window and switched back to parent: " + parentWindow);
 }

}


