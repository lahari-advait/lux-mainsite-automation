
package org.example.tests;

import org.example.pages.treatments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class treatmentsTest {

    private WebDriver driver;
    private treatments tr;

    @BeforeClass
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        tr = new treatments(driver);

        tr.openWebsite();
    }

    // ------------------ Tests ------------------

    @Test(priority = 1)
    public void testClickFirstMenu() throws InterruptedException {
        tr.clickFirstMenuLink();
    }

    @Test(priority = 2)
    public void testWhatsAppBooking() throws InterruptedException {
        tr.clickDesktopBookAppointment();
    }

    @Test(priority = 3)
    public void testCenterOfExcellenceLinks() throws InterruptedException {
        tr.processCenterOfExcellence();
    }

    @Test(priority = 4)
    public void testTopDoctorsSection() throws InterruptedException {
        tr.processTopDoctors();
    }

    @Test(priority = 5)
    public void testReviewSection() throws InterruptedException {
        tr.processReviewSection();
    }

//    @Test(priority = 7)
//    public void testWhatsAppPositiveScenario() {
//        tr.testWhatsAppEnquiryPositive();
//    }
//
//    @Test(priority = 6)
//    public void testWhatsAppNegativeScenario() {
//        tr.testWhatsAppEnquiryNegative();
//    }

    // ------------------ Teardown ------------------

    @AfterClass

    public void tearDown() {
        tr.printFinalReport();   // 👈 this prints the daily summary
        driver.quit();
    }

}
