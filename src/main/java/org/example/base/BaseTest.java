package org.example.base;

import java.time.Duration;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	

   public static WebDriver driver;
    public JavascriptExecutor js;
    
    
    public static WebDriver getDriver() {
    	if(driver==null) {
    	 driver=new ChromeDriver();
    }
    	return driver;
    }

  /*  @BeforeClass
    public void setUp() {

        // Setup ChromeDriver automatically
      
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("protocol_handler.excluded_schemes.whatsapp", false);
        prefs.put("profile.default_content_setting_values.protocol_handlers", 1);

        options.setExperimentalOption("prefs", prefs);

        // CREATE DRIVER
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();

        // OPEN SITE
        driver.get("https://luxhospitals.com/");

        js = (JavascriptExecutor) driver;

        System.out.println("✅ WebDriver initialized successfully");
    }
*/
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver quit successfully");
        }
    }
}
