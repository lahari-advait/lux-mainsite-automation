package org.example.tests;

import org.example.base.BaseTest;
import org.example.handlers.doctorsHandlers;
import org.example.pages.allDoctorCards;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class alldoctorsTest extends BaseTest {

    private doctorsHandlers doctorsHandler;
    private allDoctorCards page;
    WebDriver driver;

    @BeforeClass
    public void openSiteOnce() {
        driver = BaseTest.getDriver();
        driver.manage().window().maximize();
        driver.get("https://luxhospitals.com/");
        page = new allDoctorCards(driver);
        doctorsHandler = new doctorsHandlers(driver);
    }

//    @BeforeMethod
//    public void initPage() {
//        driver = BaseTest.getDriver();
//
//        // ✅ Always start from Doctors page directly
//        driver.get("https://luxhospitals.com/doctors/");
//
//        page = new allDoctorCards(driver);
//    }

    @Test(priority = 1)
    public void testDoctorsPageLoad() {
        page.openDoctorsPage();
    }

    @Test(priority = 2)
    public void testDrSamhithaMethods() throws InterruptedException {
        doctorsHandler.drSamhithaMethods();
    }

    @Test(priority = 3)
    public void testDrAbhishekMethods() throws InterruptedException {
        doctorsHandler.drAbhishekMethods();
    }

    // 🔹 NEW DOCTORS INSERTED HERE
    @Test(priority = 4)
    public void testDrTagoreGrandhiMethods() throws InterruptedException {
        doctorsHandler.drTagoreGrandhiMethods();
    }

    @Test(priority = 5)
    public void testDrPallaviPallapuMethods() throws InterruptedException {
        doctorsHandler.drPallaviPallapuMethods();
    }

    // 🔹 CONTINUING SEQUENCE
    @Test(priority = 6)
    public void testDrSamhithaAlukurMethods() throws InterruptedException {
        doctorsHandler.drSamhithaAlukurMethods();
    }

    @Test(priority = 7)
    public void testDrHarshitaKakarlaMethods() throws InterruptedException {
        doctorsHandler.drHarshitaKakarlaMethods();
    }

    @Test(priority = 8)
    public void testDrPPragniaMethods() throws InterruptedException {
        doctorsHandler.drPPragniaMethods();
    }

    @Test(priority = 9)
    public void testDrPriyankSalechaMethods() throws InterruptedException {
        doctorsHandler.drPriyankSalechaMethods();
    }

    @Test(priority = 10)
    public void testDrNaveenchandraMethods() throws InterruptedException {
        doctorsHandler.drNaveenchandraMethods();
    }

    @Test(priority = 11)
    public void testDrRamPrabhuMethods() throws InterruptedException {
        doctorsHandler.drRamPrabhuMethods();
    }

    @Test(priority = 12)
    public void testDrMadhanMohanMethods() throws InterruptedException {
        doctorsHandler.drMadhanMohanMethods();
    }

    @Test(priority = 13)
    public void testDrSaiKishanSirasalaMethods() throws InterruptedException {
        doctorsHandler.drSaiKishanSirasalaMethods();
    }

    @Test(priority = 14)
    public void testDrBathiniHitheshMethods() throws InterruptedException {
        doctorsHandler.drBathiniHitheshMethods();
    }

    @Test(priority = 15)
    public void testDtKruthiGoudMethods() throws InterruptedException {
        doctorsHandler.dtKruthiGoudMethods();
    }

    @Test(priority = 16)
    public void testDrDevendarGMethods() throws InterruptedException {
        doctorsHandler.drDevendarGMethods();
    }
}
