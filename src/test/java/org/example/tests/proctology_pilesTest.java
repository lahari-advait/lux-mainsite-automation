package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.proctology_piles;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class proctology_pilesTest extends BaseTest {

    private proctology_piles page;

    @BeforeClass
    public void initPage() throws InterruptedException {
        page = new proctology_piles(driver);
        Thread.sleep(2000);    }

    @Test(priority = 1)
    public void navigateToPilesUnderProctology() {
        page.hoverOverTreatments();
        page.clickProctology();
        page.clickPilesTreatment();
    }

    @Test(priority = 2)
    public void bookAppointment() {
        page.clickBookAppointment();
       
    }
    
    @Test(priority = 3)
    public void testClickAllSectionLinks() throws InterruptedException {
        page.clickAllSectionLinksOneByOne();
    }
    
    @Test(priority = 4)
    public void whatsappNegativeTest() throws InterruptedException {
        page.testWhatsAppEnquiryNegative();
    }

    @Test(priority = 5)
    public void whatsappPositiveTest() {
        page.testWhatsAppEnquiryPositive();
    }

  
    @Test(priority = 6)
    public void testTestimonialsCarousel() throws InterruptedException {
        page.scrollToTestimonialsAndClickDots();
    }

    @Test(priority = 7)
    public void testLatestHealthArticles() throws InterruptedException {
        page.scrollToLatestHealthArticles();
    }

   
  

}
