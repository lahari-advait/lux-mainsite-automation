package org.example.pages;

import org.openqa.selenium.WebDriver;

public class drHarshitaKakarla {

    private WebDriver driver;
    private String parentWindow;
    public allDoctorCards allDoctorCards;

    public drHarshitaKakarla(WebDriver driver) {
        this.driver = driver;
        this.allDoctorCards = new allDoctorCards(driver);
    }

    // ✅ Single flow: homepage -> profile -> book appointment (stays on child)
    public void openProfileAndBookAppointment() throws InterruptedException {
//        allDoctorCards.openHomePage();

        // ✅ Capture parent window handle BEFORE opening any new tab
        parentWindow = driver.getWindowHandle();

        allDoctorCards.openDoctorsPage();
        allDoctorCards.viewDoctorProfile("Dr. Harshita Kakarla");

        // Switch to doctor profile (child tab)
        allDoctorCards.switchToNewWindow();

        // Click Book Appointment (opens WhatsApp tab, closes it, stays on child)
        allDoctorCards.clickBookAppointmentAndCloseWhatsApp();
    }

    public void viewTestimonials() throws InterruptedException {
        allDoctorCards.scrollToTestimonialsAndClickDots();
    }

    public void switchToNewWindow1() {
        allDoctorCards.switchToNewWindow();
    }

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
        allDoctorCards.clickParentWhatsAppButtonForDoctor("Dr. Harshita Kakarla");
    }
}
