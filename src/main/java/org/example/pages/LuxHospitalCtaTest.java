package org.example.pages;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

public class LuxHospitalCtaTest {

    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", 
			    "C:\\Drivers\\chromedriver-win64 (5)\\chromedriver-win64\\chromedriver.exe");     
    	WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://luxhospitals.com/");
            driver.manage().window().maximize();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

          //   === BUTTON 1: HEADER BUTTON ===
            List<WebElement> headerButtons = driver.findElements(By.className("header_button"));
            if (!headerButtons.isEmpty()) {
                WebElement headerButton = headerButtons.get(0);
                highlight(driver, headerButton);
                clickElement(driver, headerButton, "Header Button");
                removeHighlight(driver, headerButton);
                Thread.sleep(4000);
                driver.get("https://luxhospitals.com/");
                Thread.sleep(3000);
            }

            // === BUTTONS 2, 3, 4: MOBILE STICKY ICONS ===
            List<WebElement> stickyButtons = driver.findElements(By.className("mobile-stickey-icon-container"));
            for (int i = 0; i < stickyButtons.size() && i < 3; i++) {
                WebElement stickyButton = stickyButtons.get(i);
                String label = "Sticky Icon #" + (i + 1);
                String originalTab = driver.getWindowHandle();
                Set<String> oldTabs = driver.getWindowHandles();

                highlight(driver, stickyButton);
                clickElement(driver, stickyButton, label);
                removeHighlight(driver, stickyButton);
                Thread.sleep(3000);

                handleTabSwitch(driver, oldTabs, originalTab);
                Thread.sleep(3000);
            }

            // === BUTTON 5: HEALTHPLIX IMAGE CTA ===
            
            testImageCtaCSS(driver, "#post-13 > div > div > div.elementor-element.elementor-element-7f484f6.e-con-full.e-flex.e-con.e-parent > div.elementor-element.elementor-element-de760c3.e-con-full.e-flex.e-con.e-child > div.elementor-element.elementor-element-d50212e.e-con-full.second-section-card1.e-flex.e-con.e-child", 
            	    "Healthplix Image CTA (Button 5)");


            // === BUTTON 6: WHATSAPP IMAGE CTA ===
            testImageCtaCSS(
            	    driver,
            	    "#post-13 > div > div > div.elementor-element.elementor-element-7f484f6.e-con-full.e-flex.e-con.e-parent > div.elementor-element.elementor-element-de760c3.e-con-full.e-flex.e-con.e-child > div.elementor-element.elementor-element-994057d.e-con-full.second-section-card2.e-flex.e-con.e-child",
            	    "WhatsApp Image CTA (Button 6)"
            	);

            // === BUTTON 7: SECOND OPINION CTA ===
            testImageCtaCSS(
            	    driver,
            	    ".second-section-card3",
            	    "Second Opinion CTA (Button 7)"
            	);

            // === BUTTON 8: INSURANCE CTA ===
            testImageCtaCSS(
            	    driver,
            	    "#post-13 > div > div > div.elementor-element.elementor-element-7f484f6.e-con-full.e-flex.e-con.e-parent > div.elementor-element.elementor-element-3f3aafe.e-con-full.e-flex.e-con.e-child > div.elementor-element.elementor-element-ff22733.e-con-full.second-section-card4.e-flex.e-con.e-child",
            	    "Insurance CTA (Button 8)"
            	);

         // === BUTTON 9.1: KNOW MORE LINKS IN OUR DEPARTMENTS SECTION ===
            try {
                // Scroll to "Our Departments" section before collecting links
                WebElement deptHeading = driver.findElement(By.xpath("//h2[contains(text(),'Our Departments')]"));
                scrollIntoView(driver, deptHeading);
                Thread.sleep(2000); // Give it time to settle

                // Define valid department URLs to filter
                List<String> departmentLinks = Arrays.asList(
                    "https://luxhospitals.com/specialities/proctology/",
                    "https://luxhospitals.com/specialities/general-laparoscopic/",
                    "https://luxhospitals.com/orthopedic/",
                    "https://luxhospitals.com/specialities/urology-andrology/",
                    "https://luxhospitals.com/specialities/gynecological-disorders/",
                    "https://luxhospitals.com/specialities/plastic-cosmetic-surgery/"
                );

                List<WebElement> allLinks = driver.findElements(By.xpath("//a[text()='Know more']"));
                int clickCount = 1;

                for (WebElement link : allLinks) {
                    String href = link.getAttribute("href");
                    if (departmentLinks.contains(href)) {
                        scrollIntoView(driver, link);
                        highlight(driver, link);
                        clickElement(driver, link, "Department Know More #" + clickCount);
                        removeHighlight(driver, link);
                        Thread.sleep(3000);
                        handleNewTabIfAny(driver);
                        Thread.sleep(2000);
                        clickCount++;
                    }
                }

                // After testing all links, return to home
                driver.get("https://luxhospitals.com/");
                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("❌ Department Know More buttons error: " + e.getMessage());
            }


         // === BUTTON 9: DOCTOR PROFILE - VIEW PROFILE CTA ===

         // Scroll to "Our Top Doctors" section before testing
         try {
             WebElement doctorSection = driver.findElement(By.xpath("//h2[contains(text(),'Our Top Doctors')]"));
             scrollIntoView(driver, doctorSection);
             Thread.sleep(1000);
         } catch (Exception e) {
             System.out.println("⚠️ Could not scroll to 'Our Top Doctors' section: " + e.getMessage());
         }

         try {
             List<WebElement> profileButtons = driver.findElements(By.className("doctor-appointment-btn"));
             int doctorIndex = 1;

             for (WebElement btn : profileButtons) {
                 if (doctorIndex > 10) break;
                 scrollIntoView(driver, btn);
                 highlight(driver, btn);
                 String label = "Doctor View Profile #" + doctorIndex;
                 clickElement(driver, btn, label);
                 removeHighlight(driver, btn);
                 Thread.sleep(3000);
                 handleNewTabIfAny(driver);
                 Thread.sleep(2000);
                 doctorIndex++;
             }

             driver.get("https://luxhospitals.com/");
             Thread.sleep(2000);

         } catch (Exception e) {
             System.out.println("❌ Error testing all Doctor Profile buttons: " + e.getMessage());
         }
          
         // === BUTTON 10: WHATSAPP ENQUIRY SEND BUTTON ===
            try {
                WebElement enquiryInput = driver.findElement(By.cssSelector("input.textgpt-input"));
                WebElement sendButton = driver.findElement(By.cssSelector("button.textgpt-button"));

                scrollIntoView(driver, enquiryInput);
                enquiryInput.clear();
                enquiryInput.sendKeys("This is a test enquiry from automation.");
                Thread.sleep(1000);

                scrollIntoView(driver, sendButton);
                highlight(driver, sendButton);

                String originalTab = driver.getWindowHandle();
                Set<String> oldTabs = driver.getWindowHandles();

                sendButton.click();
                System.out.println("✅ Clicked: WhatsApp Enquiry Send Button (Button 10)");
                Thread.sleep(3000);
                removeHighlight(driver, sendButton);

                // Handle possible tab change after click
                handleTabSwitch(driver, oldTabs, originalTab);

                // Return to home after sending
                driver.get("https://luxhospitals.com/");
                Thread.sleep(3000);

            } catch (Exception e) {
                System.out.println("❌ WhatsApp Enquiry Send Button (Button 10) error: " + e.getMessage());
            }
         // === BUTTON 11: SOCIAL MEDIA ICONS CTA ===
            try {
                System.out.println("🌐 Testing social media icons...");

                String[] socialClasses = {
                    "e-fab-facebook",
                    "e-fab-instagram",
                    "e-fab-youtube"
                };

                for (int i = 0; i < socialClasses.length; i++) {
                    String className = socialClasses[i];
                    String label = "Social Icon #" + (i + 1) + " (" + className + ")";

                    WebElement svgIcon = driver.findElement(By.className(className));
                    WebElement parentLink = svgIcon.findElement(By.xpath("ancestor::a"));

                    scrollIntoView(driver, parentLink);  // ✅ Scroll to clickable anchor tag
                    Thread.sleep(1000);
                    highlight(driver, svgIcon);          // ✅ Highlight the SVG icon itself

                    clickElement(driver, parentLink, label);
                    Thread.sleep(3000);

                    handleNewTabIfAny(driver);
                    Thread.sleep(1000);
                    removeHighlight(driver, svgIcon);
                }

                driver.get("https://luxhospitals.com/");
                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("❌ Error testing social media icons: " + e.getMessage());
            }



        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    public static void testImageCtaCSS(WebDriver driver, String cssSelector, String label) {
        try {
            WebElement element = driver.findElement(By.cssSelector(cssSelector));
            scrollIntoView(driver, element);
            highlight(driver, element);
            clickElement(driver, element, label);
            removeHighlight(driver, element);
            Thread.sleep(3000);
            handleNewTabIfAny(driver);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ " + label + " error: " + e.getMessage());
        }
    }



    public static void highlight(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
        } catch (Exception e) {
            System.out.println("⚠️ Highlight failed: " + e.getMessage());
        }
    }

    public static void removeHighlight(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='none';", element);
        } catch (Exception e) {
            System.out.println("⚠️ Remove highlight failed: " + e.getMessage());
        }
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", element);
    }

    public static void clickElement(WebDriver driver, WebElement element, String label) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('target');", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("✅ Clicked: " + label);
        } catch (Exception e) {
            System.out.println("❌ Click failed for " + label + ": " + e.getMessage());
        }
    }

    public static void handleNewTabIfAny(WebDriver driver) throws InterruptedException {
        String originalTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        if (allTabs.size() > 1) {
            for (String tab : allTabs) {
                if (!tab.equals(originalTab)) {
                    driver.switchTo().window(tab);
                    System.out.println("🔀 Switched to new tab");
                    Thread.sleep(3000);
                    driver.close();
                    driver.switchTo().window(originalTab);
                    return;
                }
            }
        } else {
            System.out.println("✅ Opened in same tab");
            driver.navigate().back();
        }
    }

    public static void handleTabSwitch(WebDriver driver, Set<String> oldTabs, String originalTab) throws InterruptedException {
        Set<String> newTabs = driver.getWindowHandles();
        newTabs.removeAll(oldTabs);
        if (!newTabs.isEmpty()) {
            String newTab = newTabs.iterator().next();
            driver.switchTo().window(newTab);
            System.out.println("🔀 Switched to new tab");
            Thread.sleep(3000);
            driver.close();
            driver.switchTo().window(originalTab);
        } else {
            System.out.println("⚠️ No new tab opened, returning home");
            driver.get("https://luxhospitals.com/");
        }
    }
}
