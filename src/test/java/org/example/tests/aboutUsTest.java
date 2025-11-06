package org.example.tests;

import org.example.pages.aboutUsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class aboutUsTest {
    WebDriver driver;
    aboutUsPage aboutUsPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\\\Drivers\\\\chromedriver-win64 (5)\\\\chromedriver-win64\\\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://luxhospitals.com/");
        aboutUsPage = new aboutUsPage(driver);
    }

    @Test(priority = 1)
    public void testDropdownLoop() throws InterruptedException {
        aboutUsPage.loopThroughDropdowns();
    }

    @Test(priority = 2)
    public void testVisionAndMissionPage() throws InterruptedException {
        aboutUsPage.clickDropdownItem("Vision & Mission");
        aboutUsPage.testTextGPT("This is a test enquiry from automation.");
    }

    @Test(priority = 3)
    public void testCoreValuesPage() throws InterruptedException {
        aboutUsPage.clickDropdownItem("Core Values");
        aboutUsPage.testTextGPT("Testing Core Values enquiry.");
    }

    @Test(priority = 4)
    public void testInfrastructurePage() throws InterruptedException {
        aboutUsPage.clickDropdownItem("Infrastructure");
        aboutUsPage.testTextGPT("Testing Infrastructure enquiry.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
