package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class gastroInflammatoryBowelDisease {

    private WebDriver driver;
    private String parentWindow;
    public allTreatments allTreatments;

    // Locators (for reference)
    private By treatmentsMenu = By.xpath("//a[contains(text(), 'Treatments')]");
    private By gastroSubmenu = By.xpath("//a[contains(text(), 'Proctology')]");
    private By ibdLink = By.xpath("//a[contains(text(), 'Inflammatory Bowel Disease (IBD)')]");

    public gastroInflammatoryBowelDisease(WebDriver driver) {
        this.driver = driver;
        this.allTreatments = new allTreatments(driver);
    }

    // ✅ Full flow: Treatments → Gastroenterology → Inflammatory Bowel Disease
    public void hoverAndOpen() throws InterruptedException {
        parentWindow = driver.getWindowHandle();

        allTreatments.hoverOverTreatments();
        allTreatments.clickTreatmentCategory("Proctology");
        allTreatments.clickSpecificTreatment("Inflammatory Bowel Disease (IBD)");

        System.out.println("✅ Opened Gastroenterology → Inflammatory Bowel Disease treatment page");
        Thread.sleep(1500);
    }

    // ✅ Click all section links one by one
    public void clickAllSectionLinksOneByOne() throws InterruptedException {
        allTreatments.clickAllSectionLinksOneByOne();
    }

    // ✅ Scroll to Testimonials & click navigation dots
    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        allTreatments.scrollToTestimonialsAndClickDots();
    }

    // ✅ Scroll to “Latest Health Articles”
    public void scrollToLatestHealthArticles() throws InterruptedException {
        allTreatments.scrollToLatestHealthArticles();
    }

    // ✅ Click “Book Appointment” (WhatsApp button)
    public void clickBookAppointment() {
        allTreatments.clickBookAppointment();
    }

    // ✅ Test FAQ accordion expansion
    public void testAllFaqAccordions() {
        allTreatments.testAllFaqAccordions();
    }

    // ✅ WhatsApp Enquiry – Negative Scenario
    public void testWhatsAppEnquiryNegative() throws InterruptedException {
        allTreatments.testWhatsAppEnquiryNegative();
    }

    // ✅ WhatsApp Enquiry – Positive Scenario
    public void testWhatsAppEnquiryPositive() throws InterruptedException {
        allTreatments.testWhatsAppEnquiryPositive();
    }

    // ✅ Switch back to main tab if new one opened
    public void closeCurrentWindowAndSwitchBack() {
        if (parentWindow != null) {
            driver.close();
            driver.switchTo().window(parentWindow);
            System.out.println("🔙 Switched back to parent window successfully.");
        } else {
            System.out.println("⚠️ Parent window handle is not set. Cannot switch back!");
        }
    }

    // ✅ Handle all doctor cards
    public void handleAllDoctorCards() throws InterruptedException {
        allTreatments.handleAllDoctorCards();
    }
}
