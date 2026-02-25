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
//    public void openDoctorsPage() {
//        By doctorsNavLink = By.xpath("//a[contains(text(),'Doctors')]");
//        WebElement doctorsLink = wait.until(ExpectedConditions.elementToBeClickable(doctorsNavLink));
//        doctorsLink.click();
//        wait.until(ExpectedConditions.urlContains("/doctors"));
//    }
    public void openDoctorsPage() {
        // ✅ If already on doctors page, don't click menu again
        if (driver.getCurrentUrl().contains("/doctors")) {
            System.out.println("✅ Already on Doctors page: " + driver.getCurrentUrl());
            return;
        }

        By doctorsNavLink = By.xpath("//a[contains(text(),'Doctors')]");
        WebElement doctorsLink = wait.until(ExpectedConditions.elementToBeClickable(doctorsNavLink));
        doctorsLink.click();

        wait.until(ExpectedConditions.urlContains("/doctors"));
        System.out.println("✅ Doctors page loaded: " + driver.getCurrentUrl());
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
    public void clickBookAppointmentAndCloseWhatsApp() {

        By bookAppointmentBtn = By.xpath(
            "//a[contains(@class,'triger-carecansole') and normalize-space()='Book Appointment']"
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Ensure doctor profile page
        wait.until(ExpectedConditions.urlContains("/doctors/"));

        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(bookAppointmentBtn));

        String doctorProfileWindow = driver.getWindowHandle();
        String doctorProfileUrl = driver.getCurrentUrl();
        Set<String> windowsBeforeClick = driver.getWindowHandles();

        // Trigger JS-bound CTA
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true,cancelable:true}));",
            btn
        );

        // Wait for WhatsApp (same tab OR new tab)
        wait.until(d ->
            d.getWindowHandles().size() > windowsBeforeClick.size()
            || d.getCurrentUrl().contains("api.whatsapp.com")
        );

        /* ───────── CASE 1: WhatsApp opened in SAME TAB ───────── */
        if (driver.getCurrentUrl().contains("api.whatsapp.com")) {

            // ❌ DO NOT driver.close()
            // ✅ Navigate back to doctor profile
            driver.navigate().to(doctorProfileUrl);

            wait.until(ExpectedConditions.urlToBe(doctorProfileUrl));

            System.out.println("🟢 WhatsApp opened in same tab. Navigated back to doctor profile.");
            return;
        }

        /* ───────── CASE 2: WhatsApp opened in NEW TAB ───────── */
        for (String win : driver.getWindowHandles()) {
            if (!windowsBeforeClick.contains(win)) {
                driver.switchTo().window(win);
                driver.close(); // Safe here
                break;
            }
        }

        driver.switchTo().window(doctorProfileWindow);

        System.out.println("🟢 WhatsApp tab closed. Doctor profile retained.");
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

//    public void clickAllDoctorCards() {
//
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//
//            WebElement heading = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//h2[contains(text(),'Other Doctors')]")
//                )
//            );
//            js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);
//
//            List<WebElement> dots = wait.until(
//                ExpectedConditions.visibilityOfAllElementsLocatedBy(
//                    By.cssSelector(".swiper-pagination-bullet")
//                )
//            );
//
//            for (int i = 0; i < dots.size(); i++) {
//
//                dots = wait.until(
//                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
//                        By.cssSelector(".swiper-pagination-bullet")
//                    )
//                );
//
//                WebElement dot = dots.get(i);
//                js.executeScript("arguments[0].click();", dot);
//                Thread.sleep(800);
//
//                WebElement activeCard = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                        By.cssSelector(".swiper-slide-active .elementor-element.e-con-full")
//                    )
//                );
//
//                BiConsumer<WebElement, String> safeClick = (link, label) -> {
//                    try {
//                        String parentWindow = driver.getWindowHandle();
//                        Set<String> beforeWindows = driver.getWindowHandles();
//                        String returnUrl = driver.getCurrentUrl();
//                        Long scrollY = (Long) js.executeScript("return window.pageYOffset;");
//
//                        js.executeScript("arguments[0].scrollIntoView({block:'center'});", link);
//                        js.executeScript("arguments[0].click();", link);
//
//                        WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(8));
//                        smallWait.until(d ->
//                            d.getWindowHandles().size() > beforeWindows.size()
//                            || !d.getCurrentUrl().equals(returnUrl)
//                        );
//
//                        // ⏳ WAIT AFTER OPEN
//                        Thread.sleep(2000);
//
//                        Set<String> afterWindows = driver.getWindowHandles();
//
//                        if (afterWindows.size() > beforeWindows.size()) {
//                            afterWindows.removeAll(beforeWindows);
//                            String newTab = afterWindows.iterator().next();
//                            driver.switchTo().window(newTab);
//
//                            Thread.sleep(2000); // wait inside new tab
//
//                            driver.close();
//                            driver.switchTo().window(parentWindow);
//                        } else {
//                            Thread.sleep(2000); // wait in same tab
//                            driver.navigate().back();
//                        }
//
//                        smallWait.until(ExpectedConditions.urlToBe(returnUrl));
//                        js.executeScript("window.scrollTo(0, arguments[0]);", scrollY);
//
//                    } catch (Exception ignored) {}
//                };
//
//                try {
//                    WebElement viewProfile = activeCard.findElement(
//                        By.xpath(".//a[contains(normalize-space(.),'View Profile')]")
//                    );
//                    safeClick.accept(viewProfile, "View Profile");
//                } catch (NoSuchElementException ignored) {}
//
//                activeCard = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(
//                        By.cssSelector(".swiper-slide-active .elementor-element.e-con-full")
//                    )
//                );
//
//                try {
//                    WebElement bookBtn = activeCard.findElement(
//                        By.xpath(".//a[contains(normalize-space(.),'Book Appointment')]")
//                    );
//                    safeClick.accept(bookBtn, "Book Appointment");
//                } catch (NoSuchElementException ignored) {}
//
//                Thread.sleep(600);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    public void clickAllDoctorCards() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 1) Scroll to heading
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(normalize-space(.),'Other Doctors')]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);

            // 2) Locate cards under Other Doctors by anchoring on View Profile
            By cardRoot = By.xpath(
                    "//h2[contains(normalize-space(.),'Other Doctors')]" +
                            "/following::a[normalize-space(.)='View Profile']" +
                            "/ancestor::div[contains(@class,'e-con')][1]"
            );

            // 3) Get all cards, then filter only visible
            List<WebElement> allCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardRoot));
            List<WebElement> visibleCards = new ArrayList<>();
            for (WebElement c : allCards) {
                if (c.isDisplayed()) visibleCards.add(c);
            }

            System.out.println("Visible Other Doctors cards: " + visibleCards.size());

            // 4) Loop by index (re-find each time to avoid stale)
            for (int i = 0; i < visibleCards.size(); i++) {

                // re-scroll to section each iteration so "back" doesn’t leave you at top
                heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(normalize-space(.),'Other Doctors')]")
                ));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);

                // re-fetch + re-filter visible to avoid stale references
                allCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardRoot));
                visibleCards = new ArrayList<>();
                for (WebElement c : allCards) {
                    if (c.isDisplayed()) visibleCards.add(c);
                }

                if (i >= visibleCards.size()) break; // safety

                WebElement card = visibleCards.get(i);
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);

                // Optional: doctor name for logs
                String doctorName = "";
                try {
                    doctorName = card.findElement(By.cssSelector("h4.elementor-heading-title")).getText().trim();
                } catch (Exception ignored) {}
                System.out.println("Card " + (i + 1) + ": " + doctorName);

                // ---- Click View Profile, then BACK (or close tab) ----
                try {
                    WebElement viewProfile = card.findElement(
                            By.xpath(".//a[normalize-space(.)='View Profile']")
                    );
                    clickAndReturnToSamePage(viewProfile, wait, js, 2500); // ✅ stay 2.5s
                } catch (NoSuchElementException ignored) {}

                // After returning, re-scroll again to keep context
                heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[contains(normalize-space(.),'Other Doctors')]")
                ));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", heading);

                // Re-fetch the card again (DOM might have changed after back)
                allCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardRoot));
                visibleCards = new ArrayList<>();
                for (WebElement c : allCards) {
                    if (c.isDisplayed()) visibleCards.add(c);
                }
                if (i >= visibleCards.size()) break;
                card = visibleCards.get(i);

                // ---- Click Book Appointment, then BACK (or close modal/tab) ----
                try {
                    // Desktop trigger (your HTML shows this)
                    WebElement bookDesktop = card.findElement(
                            By.xpath(".//div[contains(@class,'triger-carecansole')]//a[normalize-space(.)='Book Appointment']")
                    );
                    clickBookAppointmentAndReturn(bookDesktop, wait, js, 2500); // ✅ stay 2.5s
                } catch (NoSuchElementException e) {
                    // Mobile tel: link fallback
                    try {
                        WebElement bookMobile = card.findElement(
                                By.xpath(".//a[starts-with(@href,'tel:') and normalize-space(.)='Book Appointment']")
                        );
                        clickAndReturnToSamePage(bookMobile, wait, js, 2500); // ✅ stay 2.5s
                    } catch (NoSuchElementException ignored) {}
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Click element, wait a few seconds on the opened page/tab, then ALWAYS return.
     * - If new tab: switch, wait, close, return.
     * - If same tab navigation: wait, then back.
     */
    private void clickAndReturnToSamePage(WebElement link, WebDriverWait wait, JavascriptExecutor js, long stayMs) {
        try {
            String parentWindow = driver.getWindowHandle();
            Set<String> beforeWindows = driver.getWindowHandles();
            String returnUrl = driver.getCurrentUrl();

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", link);
            js.executeScript("arguments[0].click();", link);

            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            smallWait.until(d ->
                    d.getWindowHandles().size() > beforeWindows.size()
                            || !d.getCurrentUrl().equals(returnUrl)
            );

            Set<String> afterWindows = driver.getWindowHandles();

            // New tab opened
            if (afterWindows.size() > beforeWindows.size()) {
                afterWindows.removeAll(beforeWindows);
                String newTab = afterWindows.iterator().next();
                driver.switchTo().window(newTab);

                // ✅ Stay on new tab/page for a few seconds
                Thread.sleep(stayMs);

                driver.close();
                driver.switchTo().window(parentWindow);
            } else {
                // ✅ Same tab navigation -> stay then go back
                Thread.sleep(stayMs);

                driver.navigate().back();
                wait.until(ExpectedConditions.urlToBe(returnUrl));
            }

        } catch (Exception ignored) {}
    }

    /**
     * Book Appointment often opens a JS modal (href="#").
     * - If it navigates: wait a few seconds, then back.
     * - If it opens a modal: wait a few seconds, then close it.
     */
    private void clickBookAppointmentAndReturn(WebElement bookBtn, WebDriverWait wait, JavascriptExecutor js, long stayMs) {
        try {
            String returnUrl = driver.getCurrentUrl();

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", bookBtn);
            js.executeScript("arguments[0].click();", bookBtn);

            // If it navigates away, we wait then go back.
            // If it doesn't navigate, it likely opened a modal — wait then close it.
            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(6));
            boolean navigated = false;

            try {
                smallWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(returnUrl)));
                navigated = true;
            } catch (Exception ignored) {}

            if (navigated) {
                // ✅ stay on navigated page for a few seconds
                Thread.sleep(stayMs);

                driver.navigate().back();
                wait.until(ExpectedConditions.urlToBe(returnUrl));
                return;
            }

            // Modal case (MOST LIKELY):
            // ✅ stay with modal open for a few seconds
            Thread.sleep(stayMs);

            // Update selectors if your popup uses different close button
            By modalClose = By.cssSelector(".dialog-close-button, .elementor-popup-modal .dialog-close-button");
            List<WebElement> closeBtns = driver.findElements(modalClose);
            if (!closeBtns.isEmpty() && closeBtns.get(0).isDisplayed()) {
                js.executeScript("arguments[0].click();", closeBtns.get(0));
            }

        } catch (Exception ignored) {}
    }

    public void clickParentWhatsAppButtonForDoctor(String doctorName) {

        try {
            // Locate doctor card
            By doctorCard = By.xpath(
                "//div[@class='doctor-card'][.//h3[contains(normalize-space(),'" + doctorName + "')]]"
            );
            WebElement card = wait.until(
                ExpectedConditions.visibilityOfElementLocated(doctorCard)
            );

            // Correct WhatsApp button selector (BUTTON, not A)
            By whatsappBtn = By.xpath(
                ".//button[contains(@class,'doctor-book-btn') and normalize-space()='Book Appointment']"
            );
            WebElement button = card.findElement(whatsappBtn);

            // Capture state BEFORE click
            String returnUrl = driver.getCurrentUrl();
            Long scrollY = (Long) js.executeScript("return window.pageYOffset;");

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);

            // Dispatch real click event (JS-bound)
            js.executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true,cancelable:true}));",
                button
            );

            System.out.println("🟢 Clicked WhatsApp Book Appointment for: " + doctorName);

            // Wait until WhatsApp loads in SAME TAB
            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            smallWait.until(d -> d.getCurrentUrl().contains("api.whatsapp.com"));

            // Navigate back to previous page
            driver.navigate().to(returnUrl);
            smallWait.until(ExpectedConditions.urlToBe(returnUrl));

            // Restore scroll position (same section)
            js.executeScript("window.scrollTo(0, arguments[0]);", scrollY);

            System.out.println("✅ Returned to same section for doctor: " + doctorName);

        } catch (Exception e) {
            System.out.println("❌ Failed WhatsApp click for " + doctorName + ": " + e.getMessage());
        }
    }



 // 1️⃣ Switch to a newly opened window
    public String switchToNewWindow() {
        String parent = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parent)) {
                driver.switchTo().window(handle);
                System.out.println("🔄 Switched to new window: " + driver.getCurrentUrl());
                return handle;
            }
        }
        return parent;
    }


 // 2️⃣ Close current window and switch back to parent
 public void closeCurrentWindowAndSwitchBack(String parentWindow) {
     driver.close();
     driver.switchTo().window(parentWindow);
     System.out.println("✅ Closed current window and switched back to parent: " + parentWindow);
 }
 

}



