package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.aboutUsPage;
import org.example.pages.accreditationsPage;
import org.example.pages.careersPage;
import org.example.pages.coreValuesPage;
import org.example.pages.infrastructurePage;
import org.example.pages.successStoriesPage;
import org.example.pages.visionAndMissionPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class aboutUsTest extends BaseTest {

    aboutUsPage aboutUs;
    visionAndMissionPage visionPage;
    coreValuesPage corePage;

    @BeforeClass
    public void setUpPage() {
        aboutUs = new aboutUsPage(driver);
        visionPage = new visionAndMissionPage(driver);
        corePage = new coreValuesPage(driver);
    }

    // 🔹 1️⃣ Vision & Mission End-to-End Flow
    @Test(priority=1,description = "Verify Vision & Mission page and WhatsApp enquiry flow")
    public void verifyVisionAndMissionFlow() throws InterruptedException {
        aboutUs.clickVisionAndMission();
        Assert.assertTrue(visionPage.isPageLoaded(), "❌ Vision & Mission page did not load properly.");

        // Click Book Appointment (opens WhatsApp tab)
        boolean newTabOpened = visionPage.clickBookAppointment();
        if (newTabOpened) {
            visionPage.closeNewTabAndReturn();
        }

//        WhatsApp enquiry tests
        
        visionPage.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);
        visionPage.testWhatsAppEnquiryPositive();

        System.out.println("✅ Completed Vision & Mission + WhatsApp enquiry flow successfully!");
    }

//    // 🔹 2️⃣ Navigate to Core Values (runs after Vision & Mission)
    @Test(priority=2,dependsOnMethods = "verifyVisionAndMissionFlow", description = "Navigate to Core Values page and verify load")
    public void goToCoreValuesAndVerify() throws InterruptedException {
        aboutUs.clickCoreValues();
        Thread.sleep(1500); // optional small wait for navigation
        Assert.assertTrue(corePage.isPageLoaded(), "❌ Core Values page did not load properly.");
        System.out.println("✅ Core Values page loaded successfully. URL: " + driver.getCurrentUrl());
    }

//     🔹 3️⃣ WhatsApp Enquiry Tests on Core Values page (depends on previous)
    @Test(dependsOnMethods = "goToCoreValuesAndVerify", description = "Verify WhatsApp enquiry tests on Core Values page")
    public void verifyCoreValuesWhatsAppTests() throws InterruptedException {
        
        corePage.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);
        corePage.testWhatsAppEnquiryPositive();
        System.out.println("✅ Core Values WhatsApp tests completed successfully!");
    }
    @Test(priority=3)
    public void verifyInfrastructureWhatsAppTests() throws InterruptedException {
        aboutUs.clickInfrastructure();
        Thread.sleep(1500);

        infrastructurePage infraPage = new infrastructurePage(driver);
        Assert.assertTrue(infraPage.isPageLoaded(), "❌ Infrastructure page not loaded.");

        infraPage.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);        
        infraPage.testWhatsAppEnquiryNegative();

        System.out.println("✅ Infrastructure WhatsApp tests completed successfully!");
    }
    @Test(priority = 4, description = "Navigate to Accreditations page and verify WhatsApp enquiry flow")
    public void verifyAccreditationsWhatsAppTests() throws InterruptedException {
        aboutUs.clickAccreditations();
        Thread.sleep(1500);

        accreditationsPage accredPage = new accreditationsPage(driver);
        Assert.assertTrue(accredPage.isPageLoaded(), "❌ Accreditations page not loaded.");

        accredPage.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);
        accredPage.testWhatsAppEnquiryPositive();

        System.out.println("✅ Accreditations WhatsApp tests completed successfully!");
    }

    @Test(priority = 5, description = "Verify Advisory Team page opens and returns back")
    public void verifyAdvisoryTeamPage() throws InterruptedException {
        aboutUs.clickAdvisoryTeam();
        Thread.sleep(1500);

        org.example.pages.advisoryTeamPage advisoryPage = new org.example.pages.advisoryTeamPage(driver);
        Assert.assertTrue(advisoryPage.isPageLoaded(), "❌ Advisory Team page did not load properly.");
        System.out.println("✅ Advisory Team page loaded successfully. URL: " + driver.getCurrentUrl());

        advisoryPage.goBackToHome();
    }
    @Test(priority = 6, description = "Verify each Success Stories video plays and pauses sequentially")
    public void verifySuccessStoriesVideosSequentially() throws InterruptedException {
        aboutUs.clickSuccessStories();
        Thread.sleep(2000);

        successStoriesPage successPage = new successStoriesPage(driver);
        Assert.assertTrue(successPage.isPageLoaded(), 
        	    "❌ Success Stories page did not load — heading or videos missing.");


        successPage.testVideosPlayAndPauseSequentially();
    }

    @Test(priority = 7, description = "Verify Careers page and Apply Now buttons")
    public void verifyCareersApplyNowButtons() throws InterruptedException {
        aboutUs.clickCareers(); // You’ll add this helper in aboutUsPage
        Thread.sleep(1500);

        careersPage careers = new careersPage(driver);
        Assert.assertTrue(careers.isPageLoaded(), "❌ Careers page did not load properly.");

        careers.testApplyNowButtons();
        System.out.println("✅ Careers Apply Now buttons tested successfully!");
    }

}
