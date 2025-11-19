package org.example.handlers;

import org.example.pages.drSamhitha;
import org.example.pages.drAbhishek;
import org.example.pages.drSamhithaAlukur;
import org.example.pages.drHarshitaKakarla;
import org.example.pages.drPPragnia;
import org.example.pages.drPriyankSalecha;
import org.example.pages.drChandanaGuduru;
import org.example.pages.drRamPrabhu;
import org.example.pages.drMadhanMohan;
import org.example.pages.drSaiKishanSirasala;
import org.example.pages.drBathiniHithesh;
import org.example.pages.dtKruthiGoud;
import org.openqa.selenium.WebDriver;

public class doctorsHandlers {

    private WebDriver driver;
    public static drSamhitha drSamhitha; 
    public static drAbhishek drAbhishek;
    public static drSamhithaAlukur drSamhithaAlukur;
    public static drHarshitaKakarla drHarshitaKakarla;
    public static drPPragnia drPPragnia;
    public static drPriyankSalecha drPriyankSalecha;
    public static drChandanaGuduru drChandanaGuduru;
    public static drRamPrabhu drRamPrabhu;
    public static drMadhanMohan drMadhanMohan;
    public static drSaiKishanSirasala drSaiKishanSirasala;
    public static drBathiniHithesh drBathiniHithesh;
    public static dtKruthiGoud dtKruthiGoud;




    public doctorsHandlers(WebDriver driver) {
        this.driver = driver;
        this.drSamhitha = new drSamhitha(driver);
        this.drAbhishek=new drAbhishek(driver);
        this.drSamhithaAlukur=new drSamhithaAlukur(driver);
        this.drHarshitaKakarla=new drHarshitaKakarla(driver);
        this.drPPragnia = new drPPragnia(driver);
        this.drPriyankSalecha = new drPriyankSalecha(driver);
        this.drChandanaGuduru = new drChandanaGuduru(driver);
        this.drRamPrabhu = new drRamPrabhu(driver);
        this.drMadhanMohan = new drMadhanMohan(driver);
        this.drSaiKishanSirasala = new drSaiKishanSirasala(driver);
        this.drBathiniHithesh = new drBathiniHithesh(driver);
        this.dtKruthiGoud = new dtKruthiGoud(driver);






    }
    public static void drSamhithaMethods() throws InterruptedException {
    	drSamhitha.openProfileAndBookAppointment();
    	Thread.sleep(2000);
//    	drSamhitha.switchToNewWindow1();
//    	Thread.sleep(5000);
    	
//    	drSamhitha.viewTestimonials();
//    	Thread.sleep(2000);
//    	drSamhitha.clickAllDoctorCards();
//    	Thread.sleep(2000);
//    	drSamhitha.scrollToArticlesSection();
//    	Thread.sleep(2000);
    	drSamhitha.scrollToFAQAndTestAccordions();
    	Thread.sleep(2000);
    	drSamhitha.closeCurrentWindowAndSwitchBack1();
    	 Thread.sleep(1000);
         drSamhitha.clickParentWhatsAppButton1();
         Thread.sleep(1000);
   }
public static void drAbhishekMethods() throws InterruptedException {
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
public static void drSamhithaAlukurMethods() throws InterruptedException {
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

public static void drHarshitaKakarlaMethods() throws InterruptedException {
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
public static void drPPragniaMethods() throws InterruptedException {
    drPPragnia.openProfileAndBookAppointment();
    Thread.sleep(2000);

    drPPragnia.viewTestimonials();
    Thread.sleep(2000);
    drPPragnia.clickAllDoctorCards();
    Thread.sleep(2000);
    drPPragnia.scrollToArticlesSection();
    Thread.sleep(2000);
    drPPragnia.scrollToFAQAndTestAccordions();
    Thread.sleep(2000);
    drPPragnia.closeCurrentWindowAndSwitchBack1();
    Thread.sleep(1000);
    drPPragnia.clickParentWhatsAppButton1();
    Thread.sleep(1000);
}
public static void drPriyankSalechaMethods() throws InterruptedException {
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
public static void drChandanaGuduruMethods() throws InterruptedException {
    drChandanaGuduru.openProfileAndBookAppointment();
    Thread.sleep(2000);
    drChandanaGuduru.viewTestimonials();
    Thread.sleep(2000);
    drChandanaGuduru.clickAllDoctorCards();
    Thread.sleep(2000);
    drChandanaGuduru.scrollToArticlesSection();
    Thread.sleep(2000);
    drChandanaGuduru.scrollToFAQAndTestAccordions();
    Thread.sleep(2000);
    drChandanaGuduru.closeCurrentWindowAndSwitchBack1();
    Thread.sleep(1000);
    drChandanaGuduru.clickParentWhatsAppButton1();
    Thread.sleep(1000);
}
public static void drRamPrabhuMethods() throws InterruptedException {
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
public static void drMadhanMohanMethods() throws InterruptedException {
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
public static void drSaiKishanSirasalaMethods() throws InterruptedException {
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
public static void drBathiniHitheshMethods() throws InterruptedException {
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
public static void dtKruthiGoudMethods() throws InterruptedException {
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



}
