package org.example.base;



import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver",
//                "C:\\Drivers\\chromedriver-win64 (5)\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://luxhospitals.com/");
        
        driver.manage().window().maximize();
//        driver.manage().window().setSize(new Dimension(430,932));

        // ✅ Always open the site
        
    }

   
    
}
