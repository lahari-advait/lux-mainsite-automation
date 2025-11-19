package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.homePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class homePageTest extends BaseTest {

    private homePage page;

    @BeforeMethod
    public void initPage() {
        page = new homePage(driver);
    }
    @Test(priority=1)
    public void  verifyWebsiteHomepageElements() {
    	page. verifyWebsiteLoadsCorrectly();
    }
    
    @Test(priority = 2)
    public void verifyNavbarLinks() throws Exception {
    page.verifyNavbarLinks();
    }

    @Test(priority = 3)
    public void verifyHeaderButton() throws Exception {
        page.testHeaderButton();
    }

    @Test(priority = 4)
    public void verifyStickyIcons() throws Exception {
        page.testStickyIcons();
    }


    @Test(priority = 5)
    public void verifyImageCTAs() throws Exception {
        page.testImageCTA(".second-section-card1", "Healthplix CTA");
        page.testImageCTA(".second-section-card2", "WhatsApp CTA");
        page.testImageCTA(".second-section-card3", "Second Opinion CTA");
        page.testImageCTA(".second-section-card4", "Insurance CTA");
    }

    @Test(priority = 6)
    public void verifyDepartmentCards() throws Exception {
        page.testDepartmentCards();
    }
    @Test(priority = 7)
    public void verifyDoctorProfiles() throws Exception {
        page.testDoctorProfiles();
    }

    @Test(priority = 8)
    public void verifyPatientTalksDots() throws Exception {
        page.testPatientTalksDots();
    }

    @Test(priority = 9)
    public void verifyLuxGPT() throws Exception {
        page.testLuxGPT();
    }
    
    @Test(priority = 10)
    public void verifyArticles() throws Exception {
        page.testArticles();
    }

    @Test(priority = 11)
    public void verifyFAQAccordions() throws Exception {
        page.testAllFaqAccordions();
    }
    @Test(priority = 12)
    public void verifyFooter() throws Exception {
        page.testFooterLinks();
    }
}
