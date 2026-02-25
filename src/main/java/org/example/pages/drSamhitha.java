package org.example.pages;

import org.openqa.selenium.WebDriver;

public class drSamhitha {

    static WebDriver driver;
    private String parentWindow; 
    public allDoctorCards allDoctorCards;

    public drSamhitha(WebDriver driver) {
        this.driver = driver;
        this.allDoctorCards = new allDoctorCards(driver);
    }

    // ✅ Single flow: homepage -> profile -> book appointment (stays on child)
    public void openProfileAndBookAppointment() throws InterruptedException {

        parentWindow = driver.getWindowHandle();

        allDoctorCards.openDoctorsPage();
        allDoctorCards.viewDoctorProfile("Dr. Samhitha Reddy");

        // ⭐ Switch to new profile tab
        String profileTab = allDoctorCards.switchToNewWindow();

        // ⭐ You are now ON the profile tab — you should remain here
        allDoctorCards.clickBookAppointmentAndCloseWhatsApp();

        // ⭐ Only close when YOU want:
        // driver.close();
        // driver.switchTo().window(parentWindow);
    }



    public void viewTestimonials() throws InterruptedException {
        allDoctorCards.scrollToTestimonialsAndClickDots();
    }

//    public void switchToNewWindow1() {
//        allDoctorCards.switchToNewWindow();
//    }

    
    public void closeCurrentWindowAndSwitchBack1() {
        if (parentWindow != null) {
            allDoctorCards.closeCurrentWindowAndSwitchBack(parentWindow);
        } else {
            System.out.println("⚠️ Parent window handle is not set. Cannot switch back!");
        }
    }

    public void scrollToArticlesSection() throws InterruptedException {
        allDoctorCards.scrollToLatestHealthArticles();
    }

    public void scrollToFAQAndTestAccordions() throws InterruptedException {
        allDoctorCards.scrollToFAQAndTestAccordions();
    }

    public void clickAllDoctorCards() throws InterruptedException {
        allDoctorCards.clickAllDoctorCards();
    }

    public void clickParentWhatsAppButton1() throws InterruptedException {
        allDoctorCards.clickParentWhatsAppButtonForDoctor("Dr. Samhitha Reddy");
    }

}
