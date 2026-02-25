package org.example.pages;

import org.openqa.selenium.WebDriver;

public class drTagoreGrandhi {

    private WebDriver driver;
    private String parentWindow;
    public allDoctorCards allDoctorCards;

    public drTagoreGrandhi(WebDriver driver) {
        this.driver = driver;
        this.allDoctorCards = new allDoctorCards(driver);
    }

    // Main flow: homepage → doctors → profile → book appointment
    public void openProfileAndBookAppointment() throws InterruptedException {

        // Capture parent handle before opening any tab
        parentWindow = driver.getWindowHandle();

        allDoctorCards.openDoctorsPage();
        allDoctorCards.viewDoctorProfile("Dr. Tagore Grandhi");

        // Switch to doctor profile (child window)
        allDoctorCards.switchToNewWindow();

        // Click Book Appointment (opens WhatsApp, closes it, stays on child)
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
            System.out.println("⚠️ Parent window handle not set — cannot switch back!");
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
        allDoctorCards.clickParentWhatsAppButtonForDoctor("Dr. Tagore Grandhi");
    }
}
