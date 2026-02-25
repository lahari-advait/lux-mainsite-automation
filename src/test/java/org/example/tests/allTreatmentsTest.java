package org.example.tests;

import data.TreatmentTestData;
import org.example.pages.AllTreatments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class allTreatmentsTest {

    private WebDriver driver;
    private AllTreatments allTreatments;
    private String parentWindow;

    

    @BeforeClass(alwaysRun = true)
    public void setUp() {

       
        // WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get("https://luxhospitals.com/");

        parentWindow = driver.getWindowHandle();

        allTreatments = new AllTreatments(driver);
    }

    // ---------------- MAIN TEST ----------------

    @Test(priority = 1)
    public void testAllTreatmentsInOrder() throws Exception {


        allTreatments.runTestsFromData(
            TreatmentTestData.getAll(),
      
            parentWindow
        );
    }

    // ---------------- TEARDOWN ----------------

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}