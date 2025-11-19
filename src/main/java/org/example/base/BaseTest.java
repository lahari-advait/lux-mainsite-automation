package org.example.base;



import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;
    JavascriptExecutor js;


    @BeforeClass
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver",
//                "C:\\Drivers\\chromedriver-win64 (5)\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://luxhospitals.com/");
        
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
//        driver.manage().window().setSize(new Dimension(430,932));

        // ✅ Always open the site
        
    }

//  @BeforeClass
//	 public void setupAndLogin() throws Exception {
//	     String USERNAME = "vineethan_Fh7pDl";
//	     String ACCESS_KEY = "HYmqoUUzqePxanTxR4Ds";
//	     String browserStackURL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
//
//	     try {
//	         // ✅ Step 1: BrowserStack capabilities for real device
//	         DesiredCapabilities caps = new DesiredCapabilities();
//	         caps.setCapability("browserName", "chrome");
//
//	         Map<String, Object> bstackOptions = new HashMap<>();
//	        // bstackOptions.put("profile.default_content_setting_values.geolocation", 1); // 1 = allow, 2 = block
//
//	         bstackOptions.put("deviceName", "Samsung Galaxy S22");
//	         bstackOptions.put("osVersion", "13.0");
//	         bstackOptions.put("realMobile", "true");
//	         bstackOptions.put("buildName", "lux Mobile Tests");
//	         bstackOptions.put("sessionName", "Mobile Browser Testing");
//	         bstackOptions.put("interactiveDebugging", true);
//	         bstackOptions.put("appiumVersion", "2.0.0");
//	       //  ((Map<String, Object>) caps).put("profile.default_content_setting_values.geolocation", 2); // block
//
//	         // ✅ Step 2: Disable location permission
//	         bstackOptions.put("gpsLocation", "0,0");
//
//	         // ✅ Step 3: Combine and connect
//	         caps.setCapability("bstack:options", bstackOptions);
//
//	         System.out.println("Connecting to BrowserStack...");
//	         driver = new RemoteWebDriver(new URL(browserStackURL), caps);
//	         System.out.println("✅ Session started: " + ((RemoteWebDriver) driver).getSessionId());
//
//	         // ✅ Step 4: Open site
//	         driver.get("https://luxhospitals.com/");
//	         Thread.sleep(4000);
//
//	     } catch (Exception e) {
//	         System.err.println("❌ setupAndLogin failed: " + e);
//	         if (e.getCause() != null)
//	             System.err.println("Cause: " + e.getCause());
//	         e.printStackTrace();
//	         throw e;
//	     }
//	 }
//  
    
}
