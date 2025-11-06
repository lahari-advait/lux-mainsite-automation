package org.example.pages;

import org.openqa.selenium.WebDriver;

public class drSaiKishanSirasala {

    private WebDriver driver;
    private String parentWindow;
    public allDoctorCards allDoctorCards;

    public drSaiKishanSirasala(WebDriver driver) {
        this.driver = driver;
        this.allDoctorCards = new allDoctorCards(driver);
    }

    // ✅ Main flow: home → doctors → profile → book appointment (stay on child)
    public void openProfileAndBookAppointment() throws InterruptedException {
//        allDoctorCards.openHomePage();

        // Save parent window handle
        parentWindow = driver.getWindowHandle();

        allDoctorCards.openDoctorsPage();
        allDoctorCards.viewDoctorProfile("Dr. Sai Kishan Sirasala");

        // Switch to profile tab
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
        allDoctorCards.clickParentWhatsAppButtonForDoctor("Dr. Sai Kishan Sirasala");
    }
}
