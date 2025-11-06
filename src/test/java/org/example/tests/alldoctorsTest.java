package org.example.tests;


import org.example.base.BaseTest;
import org.example.handlers.doctorsHandlers;
import org.example.pages.allDoctorCards;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class alldoctorsTest extends BaseTest {

    private doctorsHandlers doctorsHandler;

    @BeforeClass
    public void setUpDoctorsHandler() {
       
        doctorsHandler = new doctorsHandlers(driver);
    }

    @Test(priority=1)
    public void testDrSamhithaMethods() throws InterruptedException {
        doctorsHandlers.drSamhithaMethods();
    }
    @Test(priority=2)
    public void testdrAbhishekMethods() throws InterruptedException {
        doctorsHandlers.drAbhishekMethods();
    }
    @Test(priority = 3)
    public void testDrSamhithaAlukurMethods() throws InterruptedException {
        doctorsHandlers.drSamhithaAlukurMethods();
    }
    @Test(priority = 4)
    public void testDrHarshitaKakarlaMethods() throws InterruptedException {
        doctorsHandlers.drHarshitaKakarlaMethods();
    }
    @Test(priority = 5)
    public void testDrPPragniaMethods() throws InterruptedException {
        doctorsHandlers.drPPragniaMethods();
    }
    @Test(priority = 6)
    public void testDrPriyankSalechaMethods() throws InterruptedException {
        doctorsHandlers.drPriyankSalechaMethods();
    }
    @Test(priority = 7)
    public void testDrChandanaGuduruMethods() throws InterruptedException {
        doctorsHandlers.drChandanaGuduruMethods();
    }
    @Test(priority = 8)
    public void testDrRamPrabhuMethods() throws InterruptedException {
        doctorsHandlers.drRamPrabhuMethods();
    }
    @Test(priority = 9)
    public void testDrMadhanMohanMethods() throws InterruptedException {
        doctorsHandlers.drMadhanMohanMethods();
    }
    @Test(priority = 10)
    public void testDrSaiKishanSirasalaMethods() throws InterruptedException {
        doctorsHandlers.drSaiKishanSirasalaMethods();
    }
    @Test(priority = 11)
    public void testDrBathiniHitheshMethods() throws InterruptedException {
        doctorsHandlers.drBathiniHitheshMethods();
    }

    @Test(priority = 12)
    public void testDtKruthiGoudMethods() throws InterruptedException {
        doctorsHandlers.dtKruthiGoudMethods();
    }
   

}
