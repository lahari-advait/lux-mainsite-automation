package org.example.handlers;

import org.example.pages.proctologyPiles;
import org.example.pages.proctologyPilonidalSinus;
import org.example.pages.proctologyRectalProlapse;
import org.example.pages.proctologyAnalFistula;
import org.example.pages.proctologyAnalPolypRemoval;
import org.example.pages.proctologyFecalIncontinence;
import org.example.pages.proctologyPerianalAbscess;
import org.example.pages.allTreatments;
import org.example.pages.gastroChronicConstipation;
import org.example.pages.gastroInflammatoryBowelDisease;
import org.example.pages.gastroIrritableBowelSyndrome;
import org.example.pages.gastroObstructiveDefecationSyndrome;
import org.example.pages.gastroUlcerativeColitis;
import org.example.pages.gynPelvicFloorDysfunction;
import org.example.pages.proctologyAnalFissure;
import org.openqa.selenium.WebDriver;

public class treatmentsHandlers {

    private WebDriver driver;
    private proctologyPiles proctologyPiles; 
    private proctologyAnalFistula proctologyAnalFistula;
    private proctologyAnalFissure proctologyAnalFissure;
    private proctologyRectalProlapse proctologyRectalProlapse;
    private proctologyPerianalAbscess proctologyPerianalAbscess;
    private proctologyAnalPolypRemoval proctologyAnalPolypRemoval;
    private proctologyPilonidalSinus proctologyPilonidalSinus;
    private proctologyFecalIncontinence proctologyFecalIncontinence;
    private gastroIrritableBowelSyndrome gastroIrritableBowelSyndrome;
    private gynPelvicFloorDysfunction gynPelvicFloorDysfunction;
    private gastroChronicConstipation gastroChronicConstipation;
    private gastroUlcerativeColitis gastroUlcerativeColitis;
    private gastroInflammatoryBowelDisease gastroInflammatoryBowelDisease;
    private gastroObstructiveDefecationSyndrome gastroObstructiveDefecationSyndrome;
    

   
    public treatmentsHandlers(WebDriver driver) {
        this.driver = driver;
        this.proctologyPiles = new proctologyPiles(driver);
        this.proctologyAnalFistula=new proctologyAnalFistula(driver);
        this.proctologyAnalFissure=new proctologyAnalFissure(driver);
        this.proctologyRectalProlapse=new proctologyRectalProlapse(driver);
        this.proctologyPerianalAbscess=new proctologyPerianalAbscess(driver);
        this.proctologyAnalPolypRemoval=new proctologyAnalPolypRemoval(driver);
        this.proctologyPilonidalSinus=new proctologyPilonidalSinus(driver);
        this.proctologyPilonidalSinus=new proctologyPilonidalSinus(driver);
        this.proctologyFecalIncontinence=new proctologyFecalIncontinence(driver);
        this.gastroIrritableBowelSyndrome=new gastroIrritableBowelSyndrome(driver);
        this.gynPelvicFloorDysfunction=new gynPelvicFloorDysfunction(driver);
        this.gastroChronicConstipation=new gastroChronicConstipation(driver);
        this.gastroUlcerativeColitis=new gastroUlcerativeColitis(driver);
        this.gastroInflammatoryBowelDisease=new gastroInflammatoryBowelDisease(driver);
        this.gastroObstructiveDefecationSyndrome=new gastroObstructiveDefecationSyndrome(driver);
    }

    public void proctologyPilesMethods() throws InterruptedException {
        System.out.println("🔹 Starting Proctology → Piles test flow...");
        proctologyPiles.hoverAndOpen();
        Thread.sleep(1500);
        proctologyPiles.clickBookAppointment();
        Thread.sleep(1500);
        proctologyPiles.handleAllDoctorCards();
        Thread.sleep(1000);
        proctologyPiles.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);
        proctologyPiles.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);
        proctologyAnalFistula. testWhatsAppEnquiryPositive();
        Thread.sleep(1000);
        proctologyPiles.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);
        proctologyPiles.scrollToLatestHealthArticles();
        Thread.sleep(1500);
        System.out.println("🎉 Completed Piles treatment test flow successfully!");
    }
    public void proctologyAnalFistulaMethods() throws InterruptedException {
        proctologyAnalFistula.hoverAndOpen();
        Thread.sleep(1500);
        proctologyAnalFistula.clickBookAppointment();
        Thread.sleep(1500);
        proctologyAnalFistula.handleAllDoctorCards();
        Thread.sleep(1000);
        proctologyAnalFistula.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);
        proctologyAnalFistula.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);
        proctologyAnalFistula. testWhatsAppEnquiryPositive();
        Thread.sleep(1000);
        proctologyAnalFistula.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);
        proctologyAnalFistula.scrollToLatestHealthArticles();
        Thread.sleep(1500);
        System.out.println("🎉 Completed Anal Fistula treatment test flow successfully!");
    }
    public void proctologyAnalFissureMethods() throws InterruptedException {
        proctologyAnalFissure.hoverAndOpen();
        Thread.sleep(1500);

        proctologyAnalFissure.clickBookAppointment();
        Thread.sleep(1500);

        proctologyAnalFissure.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyAnalFissure.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyAnalFissure.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyAnalFissure.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyAnalFissure.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyAnalFissure.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Anal Fissure treatment test flow successfully!");
    }
    public void proctologyRectalProlapseMethods() throws InterruptedException {
        proctologyRectalProlapse.hoverAndOpen();
        Thread.sleep(1500);

        proctologyRectalProlapse.clickBookAppointment();
        Thread.sleep(1500);

        proctologyRectalProlapse.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyRectalProlapse.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyRectalProlapse.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyRectalProlapse.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyRectalProlapse.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyRectalProlapse.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Rectal Prolapse treatment test flow successfully!");
    }
    public void proctologyPerianalAbscessMethods() throws InterruptedException {
        proctologyPerianalAbscess.hoverAndOpen();
        Thread.sleep(1500);

        proctologyPerianalAbscess.clickBookAppointment();
        Thread.sleep(1500);

        proctologyPerianalAbscess.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyPerianalAbscess.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyPerianalAbscess.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyPerianalAbscess.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyPerianalAbscess.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyPerianalAbscess.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Perianal Abscess treatment test flow successfully!");
    }
    public void proctologyAnalPolypRemovalMethods() throws InterruptedException {
        proctologyAnalPolypRemoval.hoverAndOpen();
        Thread.sleep(1500);

        proctologyAnalPolypRemoval.clickBookAppointment();
        Thread.sleep(1500);

        proctologyAnalPolypRemoval.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyAnalPolypRemoval.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyAnalPolypRemoval.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyAnalPolypRemoval.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyAnalPolypRemoval.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyAnalPolypRemoval.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Anal Polyp Removal treatment test flow successfully!");
    }
    public void proctologyPilonidalSinusMethods() throws InterruptedException {
        proctologyPilonidalSinus.hoverAndOpen();
        Thread.sleep(1500);

        proctologyPilonidalSinus.clickBookAppointment();
        Thread.sleep(1500);

        proctologyPilonidalSinus.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyPilonidalSinus.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyPilonidalSinus.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyPilonidalSinus.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyPilonidalSinus.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyPilonidalSinus.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Pilonidal Sinus treatment test flow successfully!");
    }
    public void proctologyFecalIncontinenceMethods() throws InterruptedException {
        proctologyFecalIncontinence.hoverAndOpen();
        Thread.sleep(1500);

        proctologyFecalIncontinence.clickBookAppointment();
        Thread.sleep(1500);

        proctologyFecalIncontinence.handleAllDoctorCards();
        Thread.sleep(1000);

        proctologyFecalIncontinence.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        proctologyFecalIncontinence.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        proctologyFecalIncontinence.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        proctologyFecalIncontinence.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        proctologyFecalIncontinence.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Fecal Incontinence treatment test flow successfully!");
    }
    public void gastroIrritableBowelSyndromeMethods() throws InterruptedException {
        gastroIrritableBowelSyndrome.hoverAndOpen();
        Thread.sleep(1500);

        gastroIrritableBowelSyndrome.clickBookAppointment();
        Thread.sleep(1500);

        gastroIrritableBowelSyndrome.handleAllDoctorCards();
        Thread.sleep(1000);

        gastroIrritableBowelSyndrome.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gastroIrritableBowelSyndrome.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gastroIrritableBowelSyndrome.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gastroIrritableBowelSyndrome.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gastroIrritableBowelSyndrome.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Irritable Bowel Syndrome treatment test flow successfully!");
    }
    public void gynPelvicFloorDysfunctionMethods() throws InterruptedException {
        gynPelvicFloorDysfunction.hoverAndOpen();
        Thread.sleep(1500);

        gynPelvicFloorDysfunction.clickBookAppointment();
        Thread.sleep(1500);

        gynPelvicFloorDysfunction.handleAllDoctorCards();
        Thread.sleep(1000);

        gynPelvicFloorDysfunction.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gynPelvicFloorDysfunction.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gynPelvicFloorDysfunction.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gynPelvicFloorDysfunction.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gynPelvicFloorDysfunction.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Pelvic Floor Dysfunction treatment test flow successfully!");
    }
    public void gastroChronicConstipationMethods() throws InterruptedException {
        gastroChronicConstipation.hoverAndOpen();
        Thread.sleep(1500);

        gastroChronicConstipation.clickBookAppointment();
        Thread.sleep(1500);

        gastroChronicConstipation.handleAllDoctorCards();
        Thread.sleep(1000);

        gastroChronicConstipation.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gastroChronicConstipation.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gastroChronicConstipation.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gastroChronicConstipation.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gastroChronicConstipation.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Chronic Constipation treatment test flow successfully!");
    }
    public void gastroUlcerativeColitisMethods() throws InterruptedException {
        gastroUlcerativeColitis.hoverAndOpen();
        Thread.sleep(1500);

        gastroUlcerativeColitis.clickBookAppointment();
        Thread.sleep(1500);

        gastroUlcerativeColitis.handleAllDoctorCards();
        Thread.sleep(1000);

        gastroUlcerativeColitis.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gastroUlcerativeColitis.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gastroUlcerativeColitis.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gastroUlcerativeColitis.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gastroUlcerativeColitis.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Ulcerative Colitis treatment test flow successfully!");
    }

    public void gastroInflammatoryBowelDiseaseMethods() throws InterruptedException {
        gastroInflammatoryBowelDisease.hoverAndOpen();
        Thread.sleep(1500);

        gastroInflammatoryBowelDisease.clickBookAppointment();
        Thread.sleep(1500);

        gastroInflammatoryBowelDisease.handleAllDoctorCards();
        Thread.sleep(1000);

        gastroInflammatoryBowelDisease.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gastroInflammatoryBowelDisease.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gastroInflammatoryBowelDisease.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gastroInflammatoryBowelDisease.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gastroInflammatoryBowelDisease.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Inflammatory Bowel Disease treatment test flow successfully!");
    }
    public void gastroObstructiveDefecationSyndromeMethods() throws InterruptedException {
        gastroObstructiveDefecationSyndrome.hoverAndOpen();
        Thread.sleep(1500);

        gastroObstructiveDefecationSyndrome.clickBookAppointment();
        Thread.sleep(1500);

        gastroObstructiveDefecationSyndrome.handleAllDoctorCards();
        Thread.sleep(1000);

        gastroObstructiveDefecationSyndrome.clickAllSectionLinksOneByOne();
        Thread.sleep(1500);

        gastroObstructiveDefecationSyndrome.testWhatsAppEnquiryNegative();
        Thread.sleep(1000);

        gastroObstructiveDefecationSyndrome.testWhatsAppEnquiryPositive();
        Thread.sleep(1000);

        gastroObstructiveDefecationSyndrome.scrollToTestimonialsAndClickDots();
        Thread.sleep(1500);

        gastroObstructiveDefecationSyndrome.scrollToLatestHealthArticles();
        Thread.sleep(1500);

        System.out.println("🎉 Completed Obstructive Defecation Syndrome treatment test flow successfully!");
    }
    
}
