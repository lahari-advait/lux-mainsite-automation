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
        driver = BaseTest.getDriver();
        driver.manage().window().maximize();
        driver.get("https://luxhospitals.com/");
        aboutUs = new aboutUsPage(driver);
        visionPage = new visionAndMissionPage(driver);
        corePage = new coreValuesPage(driver);
    }

    // 🔹 1️⃣ Vision & Mission 
    @Test(priority = 1, description = "Verify Vision & Mission page loads correctly")
    public void verifyVisionAndMissionFlow() throws InterruptedException {
        aboutUs.openVisionAndMission();
        Assert.assertTrue(
            visionPage.isPageLoaded(),
            "❌ Vision & Mission page did not load properly."
        );

        // ❌ WhatsApp Book Appointment flow disabled
        // boolean newTabOpened = visionPage.clickBookAppointment();
        // if (newTabOpened) {
        //     visionPage.closeNewTabAndReturn();
        // }

        // ❌ WhatsApp enquiry tests disabled
        // visionPage.testWhatsAppEnquiryNegative();
        // Thread.sleep(1000);
        // visionPage.testWhatsAppEnquiryPositive();
    }

    // 🔹 2️⃣ Navigate to Core Values (WITHOUT WhatsApp)
    @Test(
        priority = 2,
        dependsOnMethods = "verifyVisionAndMissionFlow",
        description = "Navigate to Core Values page and verify load"
    )
    public void goToCoreValuesAndVerify() throws InterruptedException {
        aboutUs.openCoreValues();
        Thread.sleep(1500);

        Assert.assertTrue(
            corePage.isPageLoaded(),
            "❌ Core Values page did not load properly."
        );

        System.out.println(
            "✅ Core Values page loaded successfully. URL: " + driver.getCurrentUrl()
        );
    }

    // ❌ WhatsApp enquiry tests on Core Values disabled
    // @Test(dependsOnMethods = "goToCoreValuesAndVerify")
    // public void verifyCoreValuesWhatsAppTests() throws InterruptedException {
    //     corePage.testWhatsAppEnquiryNegative();
    //     Thread.sleep(1000);
    //     corePage.testWhatsAppEnquiryPositive();
    // }

    // 🔹 3️⃣ Infrastructure Page (WITHOUT WhatsApp)
    @Test(priority = 3)
    public void verifyInfrastructurePageLoads() throws InterruptedException {
        aboutUs.openInfrastructure();
        Thread.sleep(1500);

        infrastructurePage infraPage = new infrastructurePage(driver);
        Assert.assertTrue(
            infraPage.isPageLoaded(),
            "❌ Infrastructure page not loaded."
        );

        // ❌ WhatsApp tests disabled
        // infraPage.testWhatsAppEnquiryPositive();
        // Thread.sleep(1000);
        // infraPage.testWhatsAppEnquiryNegative();
    }

    // 🔹 4️⃣ Accreditations Page (WITHOUT WhatsApp)
    @Test(priority = 4, description = "Navigate to Accreditations page and verify load")
    public void verifyAccreditationsPageLoads() throws InterruptedException {
        aboutUs.openAccreditations();
        Thread.sleep(1500);

        accreditationsPage accredPage = new accreditationsPage(driver);
        Assert.assertTrue(
            accredPage.isPageLoaded(),
            "❌ Accreditations page not loaded."
        );

        // ❌ WhatsApp tests disabled
        // accredPage.testWhatsAppEnquiryNegative();
        // Thread.sleep(1000);
        // accredPage.testWhatsAppEnquiryPositive();
    }

    // 🔹 5️⃣ Advisory Team Page
    @Test(priority = 5, description = "Verify Advisory Team page opens and returns back")
    public void verifyAdvisoryTeamPage() throws InterruptedException {
        aboutUs.openAdvisoryTeam();
        Thread.sleep(1500);

        org.example.pages.advisoryTeamPage advisoryPage =
            new org.example.pages.advisoryTeamPage(driver);

        Assert.assertTrue(
            advisoryPage.isPageLoaded(),
            "❌ Advisory Team page did not load properly."
        );

        System.out.println(
            "✅ Advisory Team page loaded successfully. URL: " + driver.getCurrentUrl()
        );

        advisoryPage.goBackToHome();
    }

    //  6️ Success Stories Videos 
    @Test(priority = 6, description = "Verify each Success Stories video plays and pauses sequentially")
    public void verifySuccessStoriesVideosSequentially() throws InterruptedException {
        aboutUs.openSuccessStories();
        Thread.sleep(2000);

        successStoriesPage successPage = new successStoriesPage(driver);

        Assert.assertTrue(
            successPage.isPageLoaded(),
            "❌ Success Stories page did not load — heading or videos missing."
        );

        successPage.testVideosPlayAndPauseSequentially();
    }

    // 🔹 7️ Careers Page 
    @Test(priority = 7, description = "Verify Careers page and Apply Now buttons")
    public void verifyCareersApplyNowButtons() throws InterruptedException {
        aboutUs.openCareers();
        Thread.sleep(1500);

        careersPage careers = new careersPage(driver);
        Assert.assertTrue(
            careers.isPageLoaded(),
            "❌ Careers page did not load properly."
        );

        careers.testApplyNowButtons();
        System.out.println("✅ Careers Apply Now buttons tested successfully!");
    }
}

