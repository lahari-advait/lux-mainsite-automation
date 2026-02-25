package org.example.handlers;

import org.example.pages.*;
import org.openqa.selenium.WebDriver;

public class doctorsHandlers {

    private WebDriver driver;

    public drSamhitha drSamhitha; 
    public drAbhishek drAbhishek;
    public drSamhithaAlukur drSamhithaAlukur;
    public drHarshitaKakarla drHarshitaKakarla;
    public drPPragnia drPPragnia;
    public drPriyankSalecha drPriyankSalecha;
    public DrNaveenchandra DrNaveenchandra;
    public drRamPrabhu drRamPrabhu;
    public drMadhanMohan drMadhanMohan;
    public drSaiKishanSirasala drSaiKishanSirasala;
    public drBathiniHithesh drBathiniHithesh;
    public dtKruthiGoud dtKruthiGoud;
    public DrDevendarG DrDevendarG;
    //new doctors
    public drTagoreGrandhi drTagoreGrandhi;
    public drPallaviPallapu drPallaviPallapu;

    public doctorsHandlers(WebDriver driver) {
        this.driver = driver;

        this.drSamhitha = new drSamhitha(driver);
        this.drAbhishek = new drAbhishek(driver);
        this.drSamhithaAlukur = new drSamhithaAlukur(driver);
        this.drHarshitaKakarla = new drHarshitaKakarla(driver);
        this.drPPragnia = new drPPragnia(driver);
        this.drPriyankSalecha = new drPriyankSalecha(driver);
        this.DrNaveenchandra = new DrNaveenchandra(driver);
        this.drRamPrabhu = new drRamPrabhu(driver);
        this.drMadhanMohan = new drMadhanMohan(driver);
        this.drSaiKishanSirasala = new drSaiKishanSirasala(driver);
        this.drBathiniHithesh = new drBathiniHithesh(driver);
        this.dtKruthiGoud = new dtKruthiGoud(driver);
        this.DrDevendarG=new DrDevendarG(driver);
        this.drTagoreGrandhi = new drTagoreGrandhi(driver);
        this.drPallaviPallapu = new drPallaviPallapu(driver);

    }

    public void drSamhithaMethods() throws InterruptedException {
        drSamhitha.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drSamhitha.viewTestimonials();
        Thread.sleep(2000);
        drSamhitha.clickAllDoctorCards();
        Thread.sleep(2000);
        drSamhitha.scrollToArticlesSection();
        Thread.sleep(2000);
        drSamhitha.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drSamhitha.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drSamhitha.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drAbhishekMethods() throws InterruptedException {
        drAbhishek.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drAbhishek.viewTestimonials();
        Thread.sleep(2000);
        drAbhishek.clickAllDoctorCards();
        Thread.sleep(2000);
        drAbhishek.scrollToArticlesSection();
        Thread.sleep(2000);
        drAbhishek.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drAbhishek.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drAbhishek.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drSamhithaAlukurMethods() throws InterruptedException {
        drSamhithaAlukur.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drSamhithaAlukur.viewTestimonials();
        Thread.sleep(2000);
        drSamhithaAlukur.clickAllDoctorCards();
        Thread.sleep(2000);
        drSamhithaAlukur.scrollToArticlesSection();
        Thread.sleep(2000);
        drSamhithaAlukur.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drSamhithaAlukur.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drSamhithaAlukur.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drHarshitaKakarlaMethods() throws InterruptedException {
        drHarshitaKakarla.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drHarshitaKakarla.viewTestimonials();
        Thread.sleep(2000);
        drHarshitaKakarla.clickAllDoctorCards();
        Thread.sleep(2000);
        drHarshitaKakarla.scrollToArticlesSection();
        Thread.sleep(2000);
        drHarshitaKakarla.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drHarshitaKakarla.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drHarshitaKakarla.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drPPragniaMethods() throws InterruptedException {
        drPPragnia.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drPPragnia.viewTestimonials();
        Thread.sleep(2000);
//        drPPragnia.clickAllDoctorCards();
//        Thread.sleep(2000);
        drPPragnia.scrollToArticlesSection();
        Thread.sleep(2000);
        drPPragnia.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drPPragnia.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drPPragnia.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drPriyankSalechaMethods() throws InterruptedException {
        drPriyankSalecha.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drPriyankSalecha.viewTestimonials();
        Thread.sleep(2000);
        drPriyankSalecha.clickAllDoctorCards();
        Thread.sleep(2000);
        drPriyankSalecha.scrollToArticlesSection();
        Thread.sleep(2000);
        drPriyankSalecha.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drPriyankSalecha.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drPriyankSalecha.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

  public void drNaveenchandraMethods() throws InterruptedException {
  DrNaveenchandra.openProfileAndBookAppointment();
  Thread.sleep(2000);
  DrNaveenchandra.viewTestimonials();
  Thread.sleep(2000);
  DrNaveenchandra.clickAllDoctorCards();
  Thread.sleep(2000);
  DrNaveenchandra.scrollToArticlesSection();
  Thread.sleep(2000);
  DrNaveenchandra.scrollToFAQAndTestAccordions();
  Thread.sleep(2000);
  DrNaveenchandra.closeCurrentWindowAndSwitchBack1();
  Thread.sleep(1000);
  DrNaveenchandra.clickParentWhatsAppButton1();
  Thread.sleep(1000);
}


    public void drRamPrabhuMethods() throws InterruptedException {
        drRamPrabhu.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drRamPrabhu.viewTestimonials();
        Thread.sleep(2000);
        drRamPrabhu.clickAllDoctorCards();
        Thread.sleep(2000);
        drRamPrabhu.scrollToArticlesSection();
        Thread.sleep(2000);
        drRamPrabhu.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drRamPrabhu.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drRamPrabhu.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drMadhanMohanMethods() throws InterruptedException {
        drMadhanMohan.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drMadhanMohan.viewTestimonials();
        Thread.sleep(2000);
        drMadhanMohan.clickAllDoctorCards();
        Thread.sleep(2000);
        drMadhanMohan.scrollToArticlesSection();
        Thread.sleep(2000);
        drMadhanMohan.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drMadhanMohan.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drMadhanMohan.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drSaiKishanSirasalaMethods() throws InterruptedException {
        drSaiKishanSirasala.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drSaiKishanSirasala.viewTestimonials();
        Thread.sleep(2000);
        drSaiKishanSirasala.clickAllDoctorCards();
        Thread.sleep(2000);
        drSaiKishanSirasala.scrollToArticlesSection();
        Thread.sleep(2000);
        drSaiKishanSirasala.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drSaiKishanSirasala.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drSaiKishanSirasala.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void drBathiniHitheshMethods() throws InterruptedException {
        drBathiniHithesh.openProfileAndBookAppointment();
        Thread.sleep(2000);
        drBathiniHithesh.viewTestimonials();
        Thread.sleep(2000);
        drBathiniHithesh.clickAllDoctorCards();
        Thread.sleep(2000);
        drBathiniHithesh.scrollToArticlesSection();
        Thread.sleep(2000);
        drBathiniHithesh.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        drBathiniHithesh.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        drBathiniHithesh.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }

    public void dtKruthiGoudMethods() throws InterruptedException {
        dtKruthiGoud.openProfileAndBookAppointment();
        Thread.sleep(2000);
        dtKruthiGoud.viewTestimonials();
        Thread.sleep(2000);
        dtKruthiGoud.clickAllDoctorCards();
        Thread.sleep(2000);
        dtKruthiGoud.scrollToArticlesSection();
        Thread.sleep(2000);
        dtKruthiGoud.scrollToFAQAndTestAccordions();
        Thread.sleep(2000);
        dtKruthiGoud.closeCurrentWindowAndSwitchBack1();
        Thread.sleep(1000);
        dtKruthiGoud.clickParentWhatsAppButton1();
        Thread.sleep(1000);
    }
  public void drDevendarGMethods() throws InterruptedException {
  DrDevendarG.openProfileAndBookAppointment();
  Thread.sleep(2000);
  DrDevendarG.viewTestimonials();
  Thread.sleep(2000);
  DrDevendarG.clickAllDoctorCards();
  Thread.sleep(2000);
  DrDevendarG.scrollToArticlesSection();
  Thread.sleep(2000);
  DrDevendarG.scrollToFAQAndTestAccordions();
  Thread.sleep(2000);
  DrDevendarG.closeCurrentWindowAndSwitchBack1();
  Thread.sleep(1000);
  DrDevendarG.clickParentWhatsAppButton1();
  Thread.sleep(1000);
}

  public void drTagoreGrandhiMethods() throws InterruptedException {
	    drTagoreGrandhi.openProfileAndBookAppointment();
	    Thread.sleep(2000);
	    drTagoreGrandhi.viewTestimonials();
	    Thread.sleep(2000);
	    drTagoreGrandhi.clickAllDoctorCards();
	    Thread.sleep(2000);
	    drTagoreGrandhi.scrollToArticlesSection();
	    Thread.sleep(2000);
	    drTagoreGrandhi.scrollToFAQAndTestAccordions();
	    Thread.sleep(2000);
	    drTagoreGrandhi.closeCurrentWindowAndSwitchBack1();
	    Thread.sleep(1000);
	    drTagoreGrandhi.clickParentWhatsAppButton1();
	    Thread.sleep(1000);
	}

	public void drPallaviPallapuMethods() throws InterruptedException {
	    drPallaviPallapu.openProfileAndBookAppointment();
	    Thread.sleep(2000);
	    drPallaviPallapu.viewTestimonials();
	    Thread.sleep(2000);
	    drPallaviPallapu.clickAllDoctorCards();
	    Thread.sleep(2000);
	    drPallaviPallapu.scrollToArticlesSection();
	    Thread.sleep(2000);
	    drPallaviPallapu.scrollToFAQAndTestAccordions();
	    Thread.sleep(2000);
	    drPallaviPallapu.closeCurrentWindowAndSwitchBack1();
	    Thread.sleep(1000);
	    drPallaviPallapu.clickParentWhatsAppButton1();
	    Thread.sleep(1000);
	}

}
