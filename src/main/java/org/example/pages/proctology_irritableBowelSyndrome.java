package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class proctology_irritableBowelSyndrome {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public static void main(String[] args) throws InterruptedException {
        proctology_irritableBowelSyndrome test = new proctology_irritableBowelSyndrome();
        test.setUp();
        test.hoverTreatments();
        test. clickIrritableBowelSyndromeTreatment();
        Thread.sleep(1000);
        test.clickBookAppointmentButton();
        Thread.sleep(1000);
        test.clickAllSectionLinks();
        Thread.sleep(4000);
        test.testAllFaqAccordions();
        Thread.sleep(1000);
        test.testTestimonialsCarousel();
        test.testLatestHealthArticles();
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
            By.xpath("//a[text()='Treatments']")));
        actions.moveToElement(treatmentsLink).perform();
        Thread.sleep(1000);
        System.out.println("👆 Hovered over 'Treatments'");
    }

    public void clickIrritableBowelSyndromeTreatment() {
        WebElement ibsLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//p[text()='Irritable Bowel Syndrome']/ancestor::a")));
        ibsLink.click();
        System.out.println("💊 Clicked on 'Irritable Bowel Syndrome' treatment");
    }




    public void clickBookAppointmentButton() throws InterruptedException {
        WebElement bookButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[1]/div[2]/div[3]/div/div/a")
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

            scrollToMenuBar();
            Thread.sleep(500);

            link.click();
            System.out.println("➡️ Clicked section: " + section);
            Thread.sleep(1000);
        }
    }

    public void testAllFaqAccordions() throws InterruptedException {
        List<WebElement> allAccordions = driver.findElements(By.cssSelector("div.accordion"));
        System.out.println("🔍 Total FAQs found: " + allAccordions.size());

        for (WebElement accordion : allAccordions) {
            WebElement questionSpan = accordion.findElement(By.cssSelector(".accordion_head > span"));
            String questionText = questionSpan.getText().trim();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", questionSpan);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", questionSpan);
            System.out.println("❓ Clicked: " + questionText);

            WebElement answerDiv = accordion.findElement(By.cssSelector(".accordion_content"));
            wait.until(driver -> answerDiv.getAttribute("style").contains("max-height")
                             && !answerDiv.getAttribute("style").contains("max-height: 0px"));

            WebElement answerParagraph = answerDiv.findElement(By.cssSelector(".faq_answer"));
            System.out.println("✅ Answer: " + answerParagraph.getText().trim());
            Thread.sleep(1000);
        }

        System.out.println("🎉 All FAQs tested successfully.");
    }

    public void testTestimonialsCarousel() throws InterruptedException {
        WebElement testimonialsHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Testimonials')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", testimonialsHeading);
        System.out.println("🧭 Scrolled to 'Testimonials' section");
        Thread.sleep(1000);

        List<WebElement> dots = driver.findElements(By.cssSelector("span.dot"));
        System.out.println("🔵 Total testimonial dots found: " + dots.size());

        for (int i = 0; i < dots.size(); i++) {
            WebElement dot = dots.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dot);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dot);
            System.out.println("🔘 Clicked testimonial dot: " + (i + 1));
            Thread.sleep(2000);
        }

        System.out.println("🌟 All testimonials tested successfully.");
    }

    public void testLatestHealthArticles() throws InterruptedException {
        WebElement articlesHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[contains(text(),'Latest Health Articles by Lux')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", articlesHeading);
        System.out.println("📚 Scrolled to 'Latest Health Articles' section");
        Thread.sleep(1000);

        List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.cssSelector("div.card")
        ));
        System.out.println("🧾 Total article cards found: " + cards.size());

        String originalTab = driver.getWindowHandle();

        for (int i = 0; i < cards.size(); i++) {
            WebElement link = cards.get(i).findElement(By.cssSelector("a"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
            System.out.println("📰 Clicked article card #" + (i + 1));

            wait.until(driver -> driver.getWindowHandles().size() > 1);
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            System.out.println("🔗 Opened Article URL: " + driver.getCurrentUrl());
            Thread.sleep(2000);
            driver.close();
            driver.switchTo().window(originalTab);
            System.out.println("↩️ Returned to main page");
            Thread.sleep(1000);
        }

        System.out.println("✅ All health articles tested successfully.");
    }

    public void testDoctorProfileSection() throws InterruptedException {
        WebElement doctorImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("img[src*='dr-samitha-3-1.webp']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", doctorImage);
        System.out.println("👩‍⚕️ Scrolled to Dr. Samhitha Reddy's profile");
        Thread.sleep(1000);

        String originalTab = driver.getWindowHandle();

        WebElement bookAppointmentBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Book Appointment']/ancestor::a")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookAppointmentBtn);
        System.out.println("📞 Clicked 'Book Appointment'");

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

        WebElement viewProfileBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div[1]/main/div/article/div/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/a/span/span")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProfileBtn);
        System.out.println("🧾 Clicked 'View Profile'");

        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        System.out.println("👨‍⚕️ Profile Page URL: " + driver.getCurrentUrl());
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(originalTab);
        System.out.println("↩️ Returned from profile tab");

        System.out.println("✅ Doctor profile section tested successfully.");
    }

    public void testSecondDoctorProfileSection() throws InterruptedException {
        WebElement doctorImage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("img[src*='dr-abhishek-2.webp']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", doctorImage);
        System.out.println("👨‍⚕️ Scrolled to Dr. Abhishek Katha's profile");
        Thread.sleep(1000);

        String originalTab = driver.getWindowHandle();

        WebElement bookAppointmentBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//img[contains(@src, 'dr-abhishek-2.webp')]/ancestor::div[contains(@class, 'elementor-widget-image')]/following::a[span/span[text()='Book Appointment']]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookAppointmentBtn);
        System.out.println("📅 Clicked 'Book Appointment' for Dr. Abhishek");

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

        WebElement viewProfileBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//img[contains(@src, 'dr-abhishek-2.webp')]/ancestor::div[contains(@class, 'elementor-widget-image')]/following::a[span/span[text()='View Profile']]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProfileBtn);
        System.out.println("🧾 Clicked 'View Profile' for Dr. Abhishek");

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
        js.executeScript("window.scrollTo(0, 300);");
    }
}
