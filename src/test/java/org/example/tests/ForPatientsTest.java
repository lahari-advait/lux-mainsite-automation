package org.example.tests;

import org.example.pages.ForPatientsPage;
import org.example.pages.InsurancePage;
import org.example.pages.BlogsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ForPatientsTest {

    private WebDriver driver;

    @BeforeClass
    public void setUpOnce() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

//    @AfterClass
//    public void tearDownOnce() {
//        if (driver != null) driver.quit();
//    }

    @BeforeMethod
    public void goHome() {
        driver.get("https://luxhospitals.com/");
    }

    // Priority 1
    @Test(priority=1)
    public void verifyForPatientsDropdownAndInsuranceInternalTesting() {
        ForPatientsPage forPatients = new ForPatientsPage(driver);

        Assert.assertTrue(forPatients.isDropdownVisible(), "'For Patients' dropdown did not display");

        InsurancePage insurancePage = forPatients.openInsurancePage();
        Assert.assertTrue(insurancePage.isAt(), "Insurance page did not open");

        insurancePage.scrollToMajorInsuranceAndToggleSeeMoreAndSeeLess();
        insurancePage.expandAndCollapseAllFaqs();
    }

    // Priority 2
    @Test(priority=2)
    
    public void verifyForPatientsDropdownAndBlogsInternalTesting() {
        driver.get("https://luxhospitals.com/");

        ForPatientsPage forPatients = new ForPatientsPage(driver);

        Assert.assertTrue(forPatients.isDropdownVisible(), "'For Patients' dropdown did not display");

        BlogsPage blogsPage = forPatients.openBlogsPage();
        Assert.assertTrue(blogsPage.isAt(), "Blogs page did not open");

        // ✅ Run same flow for all 7 categories
        blogsPage.runFlowForAllCategories(2);
    }
}