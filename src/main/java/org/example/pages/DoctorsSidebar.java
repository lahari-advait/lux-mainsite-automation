package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DoctorsSidebar {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    public DoctorsSidebar(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // ---------- Locators ----------
    private By doctorsMenu = By.cssSelector("li[data-menu='doctors'] > a");

    private By doctorItem(String name) {
        return By.xpath("//li[contains(@class,'department___listitem__for__doctors')]//span[normalize-space(text())='" + name + "']");
    }


    // ---------- Actions ----------
    public void hoverDoctorsMenu() throws InterruptedException {
    	Thread.sleep(3000);
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(doctorsMenu));
        js.executeScript("arguments[0].scrollIntoView(true);", menu);
        Thread.sleep(500);  // small pause for scroll
        actions.moveToElement(menu).perform();

        // Wait until at least one submenu item is visible
//        wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[contains(@class,'flexx___box')]//span")
//        ));
    }
    
    public void clickDoctorItems() throws InterruptedException {
    	List<String> items = Arrays.asList(
                "Proctologist",
                "General and Laparoscopic Surgeon",
                "Gynaecologist",
                "Urologist",
                "Plastic and Cosmetic Surgeon",
                "Orthopaedic Surgeon"
            );
        for (String itemName : items) {
            // Hover the doctors menu first
            hoverDoctorsMenu();
            Thread.sleep(300); // small pause for dropdown animation

            // Wait for the doctor item to be clickable
            WebElement item = wait.until(ExpectedConditions.elementToBeClickable(doctorItem(itemName)));

            // Click using JavaScript to avoid overlay/hover issues
            js.executeScript("arguments[0].click();", item);
            System.out.println("🖱️ Clicked on: " + itemName);

            Thread.sleep(500); // brief pause after click
        }
    }
    
    



}
