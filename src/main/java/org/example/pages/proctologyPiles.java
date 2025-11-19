package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class proctologyPiles {

    private WebDriver driver;
    private String parentWindow;
    public allTreatments allTreatments;
    
    private By treatmentsMenu = By.xpath("//a[contains(text(), 'Treatments')]");
    private By proctologySubmenu = By.xpath("//a[contains(text(), 'Proctology')]");
    private By pilesLink = By.xpath("//a[contains(text(), 'Piles')]");

    public proctologyPiles(WebDriver driver) {
        this.driver = driver;
        this.allTreatments = new allTreatments(driver);
    }

    // ✅ Full flow: Treatments menu → Proctology → Piles → interactions
    public void hoverAndOpen() throws InterruptedException {
        // Capture parent window before navigation
        parentWindow = driver.getWindowHandle();

        allTreatments.hoverOverTreatments();
        allTreatments.clickTreatmentCategory("Proctology");
        allTreatments.clickSpecificTreatment("Piles");

        System.out.println("✅ Opened Proctology → Piles treatment page");
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
    
    public void testWhatsAppEnquiryNegative() throws InterruptedException {
    	allTreatments.testWhatsAppEnquiryNegative();
    	}
    public void testWhatsAppEnquiryPositive() throws InterruptedException {
    	allTreatments.testWhatsAppEnquiryPositive();
    	}
    public void handleAllDoctorCards() throws InterruptedException {
    	allTreatments.handleAllDoctorCards();
    }

    // ✅ Switch back to main tab if a new one opened
    public void closeCurrentWindowAndSwitchBack() {
        if (parentWindow != null) {
            driver.close();
            driver.switchTo().window(parentWindow);
            System.out.println("🔙 Switched back to parent window successfully.");
        } else {
            System.out.println("⚠️ Parent window handle is not set. Cannot switch back!");
        }
    }
}
