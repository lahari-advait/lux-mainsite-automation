package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.doctorsPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class doctorsTest extends BaseTest {

    @Test
    public void testDoctorsPageFlow() {
        doctorsPage doctorsPage = new doctorsPage(driver);

        // ✅ Open Doctors page
        doctorsPage.openDoctorsPage();

        List<WebElement> cards = doctorsPage.getDoctorCards();

        for (String name : doctorsPage.DOCTORS) {
            WebElement card = doctorsPage.findDoctorCardByName(cards, name);
            if (card == null) {
                System.out.println("⚠️ Doctor not found: " + name);
                continue;
            }

            doctorsPage.scrollIntoView(card);

            // View Profile
            doctorsPage.clickViewProfile(card, name);

            // Re-fetch after navigation
            cards = doctorsPage.getDoctorCards();
            card = doctorsPage.findDoctorCardByName(cards, name);
            // Book Appointment
            doctorsPage.scrollIntoView(card);
            doctorsPage.clickBookAppointment(card, name);
        }

        // ✅ WhatsApp Negative test
        doctorsPage.testWhatsAppEnquiryNegative();
        
        
        // ✅ WhatsApp Positive test
        doctorsPage.testWhatsAppEnquiryPositive();

      

        // Dropdown (if needed after refresh)
        driver.navigate().refresh();
//        doctorsPage.clickDoctorsDropdown();
    }
}
