package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class proctology_analfissure {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

   
   
	public static void main(String[] args) throws InterruptedException {
        proctology_analfissure test = new proctology_analfissure();
       test.setUp();
       test.hoverTreatments();
       test.clickAnalFissureTreatment();
       Thread.sleep(2000);
//       test.clickBookAppointmentButton();
//       Thread.sleep(1000);
//       test.clickAllSectionLinks();
//       Thread.sleep(4000);
//       test.testAllFaqAccordions();
//       Thread.sleep(1000);
//       test .testTestimonialsCarousel();
//       test.testLatestHealthArticles();
       test.testDoctorProfileSection();
       test.testSecondDoctorProfileSection();
      
  
    }

    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://luxhospitals.com");

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    public void hoverTreatments() throws InterruptedException {
        WebElement treatmentsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[text()='Treatments']")
        ));
        actions.moveToElement(treatmentsLink).perform();
        Thread.sleep(1000);
        System.out.println("👆 Hovered over 'Treatments'");
    }

    public void clickAnalFissureTreatment() {
        WebElement analFissureLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[text()='Anal Fissure']/ancestor::a")));
        analFissureLink.click();
        System.out.println("💊 Clicked on 'Anal Fissure' treatment");
    }

   public void clickBookAppointmentButton() throws InterruptedException {
        WebElement bookButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[1]/div[2]/div[4]/div/div/a")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", bookButton);
        Thread.sleep(2000);

        String originalTab = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(bookButton)).click();
        System.out.println("📅 Clicked 'Book Appointment'");
        Thread.sleep(2000);

        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("🌐 WhatsApp URL: " + driver.getCurrentUrl());
        Thread.sleep(2000);

        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Switched back to original tab");
    }

    public void clickAllSectionLinks() throws InterruptedException {
        String[] sections = {"#overview", "#CausesSymptoms", "#Treatments", "#WhyChooseUs", "#Faqs"};

        for (String section : sections) {
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[href='" + section + "']")));

            scrollToMenuBar(); // Keep nav in view
            Thread.sleep(500);

            link.click();
            System.out.println("➡️ Clicked section: " + section);

            Thread.sleep(1000); // Simulate user reading
        }
    }

    public void testAllFaqAccordions() throws InterruptedException {

        // Locate all FAQ accordions
        List<WebElement> allAccordions = driver.findElements(By.cssSelector("div.accordion"));
        System.out.println("🔍 Total FAQs found: " + allAccordions.size());

        // Loop through each FAQ
        for (int i = 0; i < allAccordions.size(); i++) {
            WebElement accordion = allAccordions.get(i);

            WebElement questionSpan = accordion.findElement(By.cssSelector(".accordion_head > span"));
            String questionText = questionSpan.getText().trim();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", questionSpan);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", questionSpan);
            System.out.println("❓ Clicked: " + questionText);

            WebElement answerDiv = accordion.findElement(By.cssSelector(".accordion_content"));
            wait.until(driver -> answerDiv.getAttribute("style").contains("max-height") &&
                                 !answerDiv.getAttribute("style").contains("max-height: 0px"));

            WebElement answerParagraph = answerDiv.findElement(By.cssSelector(".faq_answer"));
            String answerText = answerParagraph.getText().trim();
            System.out.println("✅ Answer: " + answerText);

            Thread.sleep(1000);
        }

        System.out.println("🎉 All FAQs tested successfully.");
    }
    public void testTestimonialsCarousel() throws InterruptedException {
        // Scroll to Testimonials heading
        WebElement testimonialsHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Testimonials')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", testimonialsHeading);
        System.out.println("🧭 Scrolled to 'Testimonials' section");
        Thread.sleep(1000);

        // Find all dots (carousel indicators)
        List<WebElement> dots = driver.findElements(By.cssSelector("span.dot"));
        System.out.println("🔵 Total testimonial dots found: " + dots.size());

        if (dots.size() == 0) {
            System.out.println("❌ No testimonial dots found.");
            return;
        }

        // Click each dot and wait to simulate user viewing testimonial
        for (int i = 0; i < dots.size(); i++) {
            WebElement dot = dots.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dot);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dot);
            System.out.println("🔘 Clicked testimonial dot: " + (i + 1));
            Thread.sleep(2000); // simulate reading time
        }

        System.out.println("🌟 All testimonials tested successfully.");
    }
   /* public void testLatestHealthArticles() throws InterruptedException {
        // Scroll to the Latest Health Articles heading
        WebElement articlesHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", articlesHeading);
        System.out.println("📖 Scrolled to 'Latest Health Articles by Lux' section");
        Thread.sleep(1000);

        // Find all blog dots
        List<WebElement> blogDots = driver.findElements(By.cssSelector("span.blog-dot"));
        System.out.println("🟣 Total blog dots found: " + blogDots.size());

        if (blogDots.size() == 0) {
            System.out.println("❌ No blog dots found.");
            return;
        }

        // Loop through and click each blog dot
        for (int i = 0; i < blogDots.size(); i++) {
            WebElement blogDot = blogDots.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", blogDot);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", blogDot);
            System.out.println("📝 Clicked blog dot: " + (i + 1));
            Thread.sleep(2000); // simulate reading/view time
        }

        System.out.println("✅ All health article dots tested successfully.");
    }*/
    public void testLatestHealthArticles() throws InterruptedException {
        // Scroll to "Latest Health Articles by Lux" section
        WebElement articlesHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", articlesHeading);
        System.out.println("📚 Scrolled to 'Latest Health Articles' section");
        Thread.sleep(1000);

        // Find all article cards
        List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.cssSelector("div.card")
        ));

        System.out.println("🧾 Total article cards found: " + cards.size());

        String originalTab = driver.getWindowHandle();

        // Iterate through all cards
        for (int i = 0; i < cards.size(); i++) {
            WebElement currentCard = cards.get(i);
            WebElement link = currentCard.findElement(By.cssSelector("a"));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
            Thread.sleep(500);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            System.out.println("📰 Clicked article card #" + (i + 1));

            // Wait for new tab to open
            wait.until(driver -> driver.getWindowHandles().size() > 1);

            // Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Print article URL
            System.out.println("🔗 Opened Article URL: " + driver.getCurrentUrl());
            Thread.sleep(2000); // Simulate reading

            // Close article tab and return
            driver.close();
            driver.switchTo().window(originalTab);
            System.out.println("↩️ Returned to main page");
            Thread.sleep(1000);
        }

        System.out.println("✅ All health articles tested successfully.");
    }

    public void testDoctorProfileSection() throws InterruptedException {
        // Scroll to Dr. Samhitha Reddy's profile image (anchor point)
        WebElement doctorImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[1]/div/div/div")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", doctorImage);
        System.out.println("👩‍⚕️ Scrolled to Dr. Samhitha Reddy's profile");
        Thread.sleep(1000);

        String originalTab = driver.getWindowHandle();

        // --- Click "Book Appointment" (WhatsApp) ---
        WebElement bookAppointmentBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[1]/div/div/div/div/div[2]/a[1]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookAppointmentBtn);
        System.out.println("📞 Clicked 'Book Appointment'");

        // Wait for WhatsApp tab and switch
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("🌐 WhatsApp URL: " + driver.getCurrentUrl());
        Thread.sleep(2000); // Simulate reading
        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Returned from WhatsApp tab");

        Thread.sleep(1000);

        // --- Click "View Profile" ---
     // --- Click "View Profile" ---
        WebElement viewProfileBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[1]/div/div/div/div/div[2]/a[2]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProfileBtn);
        System.out.println("🧾 Clicked 'View Profile'");


        // Wait for Profile tab and switch
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("👨‍⚕️ Profile Page URL: " + driver.getCurrentUrl());
        Thread.sleep(2000); // Simulate reading
        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Returned from profile tab");

        System.out.println("✅ Doctor profile section tested successfully.");
    }
    
    public void testSecondDoctorProfileSection() throws InterruptedException {
        // Scroll to Dr. Abhishek Katha's profile image
        WebElement doctorImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[2]/div/div/div")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", doctorImage);
        System.out.println("👨‍⚕️ Scrolled to Dr. Abhishek Katha's profile");
        Thread.sleep(1000);

        String originalTab = driver.getWindowHandle();

        // --- Click "Book Appointment" ---
        WebElement bookAppointmentBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[2]/div/div/div/div/div[2]/a[1]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookAppointmentBtn);
        System.out.println("📅 Clicked 'Book Appointment' for Dr. Abhishek");

        // Switch to WhatsApp tab
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("🌐 WhatsApp URL: " + driver.getCurrentUrl());
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Returned from WhatsApp tab");

        Thread.sleep(1000);

        // --- Click "View Profile" ---
        WebElement viewProfileBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[2]/div/div/div/div/div[2]/a[2]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProfileBtn);
        System.out.println("🧾 Clicked 'View Profile' for Dr. Abhishek");

        // Switch to profile tab
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("👨‍⚕️ Profile URL: " + driver.getCurrentUrl());
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Returned from profile tab");

        System.out.println("✅ Dr. Abhishek's section tested successfully.");
    }


    public void scrollToMenuBar() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 300);"); // Adjust if needed
    }

   
}
