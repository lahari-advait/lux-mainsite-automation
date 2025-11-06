package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.treatments;
import org.testng.annotations.Test;

public class treatmentsTest extends BaseTest {

    @Test
    public void verifyTreatmentsFlow() throws InterruptedException {
        treatments treatmentsPage = new treatments(driver);

        treatmentsPage.clickFirstMenuLink();
        treatmentsPage.clickWhatsAppBooking();
        treatmentsPage.processCenterOfExcellence();
        treatmentsPage.processTopDoctors();
        treatmentsPage.processReviewSection();
        treatmentsPage.testWhatsAppEnquiryNegative();
        treatmentsPage.testWhatsAppEnquiryPositive();

    }
}
