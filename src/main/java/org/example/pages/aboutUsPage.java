package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class aboutUsPage {

    static WebDriver driver;
    static WebDriverWait wait;
    static Actions actions;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Drivers\\chromedriver-win64 (5)\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);

        try {
            driver.get("https://luxhospitals.com/");
            driver.manage().window().maximize();

            // ✅ Step 1: Loop through all dropdown items
              loopThroughAboutUsDropdowns();

           //  ✅ Step 2: Individual page testing
            testVisionAndMissionPage();
            testCoreValuesPage();
            testInfrastructurePage();
            testAccreditationsPage();
            testAdvisoryTeamPage();
            testSuccessStoriesVideosSequential();
            testCareersPage();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // ===============================
    // Step 1: Loop through all dropdowns
    // ===============================
    public static void loopThroughAboutUsDropdowns() throws InterruptedException {
        // Hover over "About Us"
        WebElement aboutUsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[normalize-space(text())='About Us']")
        ));
        actions.moveToElement(aboutUsLink).perform();
        Thread.sleep(1000);

        // Capture all dropdown items
        List<WebElement> dropdownItems = driver.findElements(
                By.xpath("//span[normalize-space(text())='About Us']/ancestor::li//ul//a")
        );

        System.out.println("Found " + dropdownItems.size() + " dropdown items.");

        for (int i = 0; i < dropdownItems.size(); i++) {
            // Hover About Us again before each click
            aboutUsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space(text())='About Us']")
            ));
            actions.moveToElement(aboutUsLink).perform();
            Thread.sleep(500);

            // Refetch dropdown items to avoid StaleElementReferenceException
            dropdownItems = driver.findElements(
                    By.xpath("//span[normalize-space(text())='About Us']/ancestor::li//ul//a")
            );

            WebElement item = dropdownItems.get(i);
            String linkText = item.getText().trim();

            System.out.println("Clicking (Loop): " + linkText);

            // Click using JS for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);

            Thread.sleep(2000);

            if (driver.getTitle().contains(linkText) || driver.getPageSource().contains(linkText)) {
                System.out.println("✅ " + linkText + " page loaded successfully.");
            } else {
                System.out.println("⚠️ " + linkText + " page may not have loaded correctly.");
            }

            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space(text())='About Us']")
            ));
        }
    }

    // ===============================
    // Step 2: Individual Page Tests
    // ===============================
    public static void testVisionAndMissionPage() throws InterruptedException {
        // Navigate to Vision & Mission page
        testAboutUsDropdownItem("Vision & Mission");

        // 1️⃣ Book Appointment button
        WebElement bookAppointmentBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[1]/main/div/article/div/div/div[1]/div/div[1]/div[4]/div/div/a")
                ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookAppointmentBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookAppointmentBtn);

        // Handle new tab
        String currentWindow = driver.getWindowHandle();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentWindow)) {
                driver.switchTo().window(handle);
                System.out.println("Navigated to: " + driver.getCurrentUrl());
                driver.close();
                driver.switchTo().window(currentWindow);
            }
        }

       
     // 2️⃣ TextGPT input/button
        WebElement input = driver.findElement(By.cssSelector("input.textgpt-input"));
        WebElement sendButton = driver.findElement(By.cssSelector("button.textgpt-button"));

        scrollIntoView(input);
        input.clear();
        input.sendKeys("This is a test enquiry from automation.");
        Thread.sleep(1000);

        scrollIntoView(sendButton);
        

        Set<String> oldTabs = driver.getWindowHandles();
        sendButton.click();
        System.out.println("✅ Clicked: TextGPT Send Button");
        Thread.sleep(3000);
        

        // Close the new TextGPT tab and switch back
        Set<String> newTabs = driver.getWindowHandles();
        for (String handle : newTabs) {
            if (!oldTabs.contains(handle)) {
                driver.switchTo().window(handle);
                driver.close();  // close the TextGPT tab
            }
        }
        driver.switchTo().window(oldTabs.iterator().next());

        // Navigate back to homepage
        driver.get("https://luxhospitals.com/");

    }


    private static void handleTabSwitch(Set<String> oldTabs) {
		// TODO Auto-generated method stub
		
	}

	private static void scrollIntoView(WebElement input) {
		// TODO Auto-generated method stub
		
	}

	// Other individual pages
    public static void testCoreValuesPage() throws InterruptedException {
        testAboutUsDropdownItem("Core Values");
        WebElement input = driver.findElement(By.cssSelector("input.textgpt-input"));
        WebElement sendButton = driver.findElement(By.cssSelector("button.textgpt-button"));

        scrollIntoView(input);
        input.clear();
        input.sendKeys("This is a test enquiry from automation.");
        Thread.sleep(1000);

        scrollIntoView(sendButton);
        

        Set<String> oldTabs = driver.getWindowHandles();
        sendButton.click();
        System.out.println("✅ Clicked: TextGPT Send Button");
        Thread.sleep(3000);
        

        // Close the new TextGPT tab and switch back
        Set<String> newTabs = driver.getWindowHandles();
        for (String handle : newTabs) {
            if (!oldTabs.contains(handle)) {
                driver.switchTo().window(handle);
                driver.close();  // close the TextGPT tab
            }
        }
        driver.switchTo().window(oldTabs.iterator().next());

        // Navigate back to homepage
        driver.get("https://luxhospitals.com/");

    }

  

    public static void testInfrastructurePage() throws InterruptedException {
        testAboutUsDropdownItem("Infrastructure");
        WebElement input = driver.findElement(By.cssSelector("input.textgpt-input"));
        WebElement sendButton = driver.findElement(By.cssSelector("button.textgpt-button"));

        scrollIntoView(input);
        input.clear();
        input.sendKeys("This is a test enquiry from automation.");
        Thread.sleep(1000);

        scrollIntoView(sendButton);
        

        Set<String> oldTabs = driver.getWindowHandles();
        sendButton.click();
        System.out.println("✅ Clicked: TextGPT Send Button");
        Thread.sleep(3000);
        

        // Close the new TextGPT tab and switch back
        Set<String> newTabs = driver.getWindowHandles();
        for (String handle : newTabs) {
            if (!oldTabs.contains(handle)) {
                driver.switchTo().window(handle);
                driver.close();  // close the TextGPT tab
            }
        }
        driver.switchTo().window(oldTabs.iterator().next());

        // Navigate back to homepage
        driver.get("https://luxhospitals.com/");

    }


    public static void testAccreditationsPage() throws InterruptedException {
        testAboutUsDropdownItem("Accreditations");
        WebElement input = driver.findElement(By.cssSelector("input.textgpt-input"));
        WebElement sendButton = driver.findElement(By.cssSelector("button.textgpt-button"));

        scrollIntoView(input);
        input.clear();
        input.sendKeys("This is a test enquiry from automation.");
        Thread.sleep(1000);

        scrollIntoView(sendButton);
        

        Set<String> oldTabs = driver.getWindowHandles();
        sendButton.click();
        System.out.println("✅ Clicked: TextGPT Send Button");
        Thread.sleep(3000);
        

        // Close the new TextGPT tab and switch back
        Set<String> newTabs = driver.getWindowHandles();
        for (String handle : newTabs) {
            if (!oldTabs.contains(handle)) {
                driver.switchTo().window(handle);
                driver.close();  // close the TextGPT tab
            }
        }
        driver.switchTo().window(oldTabs.iterator().next());

        // Navigate back to homepage
        driver.get("https://luxhospitals.com/");

    }

    public static void testAdvisoryTeamPage() throws InterruptedException {
        testAboutUsDropdownItem("Advisory Team");
    }

    public static void testSuccessStoriesVideosSequential() throws InterruptedException {
        testAboutUsDropdownItem("Success Stories");

        // Find all videos with class "elementor-video" inside #post-30976
        List<WebElement> videos = driver.findElements(By.cssSelector("#post-30976 .elementor-video"));

        System.out.println("Found " + videos.size() + " videos.");

        for (int i = 0; i < videos.size(); i++) {
            WebElement video = videos.get(i);

            // Scroll video into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", video);
            Thread.sleep(500);

            // Click to play
            video.click();
            Thread.sleep(2000);

            // Check if video is playing
            Boolean isPlaying = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "return !arguments[0].paused && !arguments[0].ended && arguments[0].readyState > 2;", video
            );

            if (isPlaying) {
                System.out.println("✅ Video " + (i + 1) + " is playing.");
            } else {
                // Fallback: force play via JS
                ((JavascriptExecutor) driver).executeScript("arguments[0].play();", video);
                Thread.sleep(1000);
                isPlaying = (Boolean) ((JavascriptExecutor) driver).executeScript(
                    "return !arguments[0].paused && !arguments[0].ended && arguments[0].readyState > 2;", video
                );
                System.out.println(isPlaying
                        ? "✅ Video " + (i + 1) + " started playing via JS fallback."
                        : "❌ Video " + (i + 1) + " did not play."
                );
            }

            // Pause video after testing
            ((JavascriptExecutor) driver).executeScript("arguments[0].pause();", video);
            Thread.sleep(1000);
        }

        // Navigate back after testing
        driver.navigate().back();
        Thread.sleep(1000);
    }



    public static void testCareersPage() throws InterruptedException {
        testAboutUsDropdownItem("Careers"); // Navigate to Careers page

        // Find all "Apply Now" buttons
        List<WebElement> applyButtons = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector("a.elementor-button.elementor-button-link.elementor-size-sm")
                ));

        System.out.println("Found " + applyButtons.size() + " Apply Now buttons.");

        for (int i = 0; i < applyButtons.size(); i++) {
            WebElement btn = applyButtons.get(i);

            // Scroll button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            Thread.sleep(500);

            // Click the button to ensure it is clickable
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            System.out.println("✅ Apply Now button " + (i + 1) + " clicked successfully.");

            // Wait a bit in case it opens in new tab or triggers mailto
            Thread.sleep(1000);

            // Switch back if a new tab opened
            String currentWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            for (String handle : allWindows) {
                if (!handle.equals(currentWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                    driver.switchTo().window(currentWindow);
                }
            }
        }
    }

    // ===============================
    // Shared helper method
    // ===============================
    public static void testAboutUsDropdownItem(String linkText) throws InterruptedException {
        try {
            // Hover About Us menu
            WebElement aboutUsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[normalize-space(text())='About Us']")));
            actions.moveToElement(aboutUsLink).perform();
            Thread.sleep(500);

            // Click specific dropdown item using JS
            WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[normalize-space(text())='About Us']/ancestor::li//ul//a[normalize-space(text())='" + linkText + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menuItem);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem);
            System.out.println("✅ Clicked '" + linkText + "' successfully.");
            Thread.sleep(1500);

            // Validate page load
            if (driver.getTitle().contains(linkText) || driver.getPageSource().contains(linkText)) {
                System.out.println("✅ " + linkText + " page loaded correctly.");
            } else {
                System.out.println("⚠️ " + linkText + " page might not have loaded correctly.");
            }

        } catch (Exception e) {
            System.out.println("❌ Could not click '" + linkText + "': " + e.getMessage());
        }
    }
}
