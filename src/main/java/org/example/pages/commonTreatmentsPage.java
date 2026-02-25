package org.example.pages;

import core.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class commonTreatmentsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    public commonTreatmentsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void clickDepartment(String deptName) {

        // Hover over Treatments menu
        WebElement treatmentsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Treatments')]")
        ));
        actions.moveToElement(treatmentsMenu).perform();

        // Wait for dropdown to appear, then click department
        WebElement department = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[normalize-space()='" + deptName + "']")
        ));
        department.click();

        System.out.println("✔ Department clicked: " + deptName);
    }


    public void clickTreatment(String treatmentName) {

        By locator = By.xpath(
            "//div[contains(@class,'department-boxes')]//a[.//p[normalize-space()='" + treatmentName + "']]"
        );

        WebElement treatment = wait.until(ExpectedConditions.elementToBeClickable(locator));

        js.executeScript("arguments[0].scrollIntoView({block:'center'})", treatment);
        js.executeScript("arguments[0].click();", treatment);  // JS click avoids hidden overlays

        System.out.println("✔ Treatment clicked: " + treatmentName);
    }





    public void clickBookAppointment() {
        try {
            WebElement btn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'whatsapp')]"))
            );
            btn.click();
        } catch (Exception e) {
            System.out.println("⚠ WhatsApp button not clickable: " + e.getMessage());
        }
    }

    public void handleDoctors() {
        try {
            List<WebElement> cards = driver.findElements(By.cssSelector(".doctor_card"));
            System.out.println(cards.size() + " doctor cards found.");
        } catch (Exception e) {
            System.out.println("⚠ Doctor cards issue: " + e.getMessage());
        }
    }

    public void clickAllSectionLinksOneByOne() {
        List<WebElement> links = driver.findElements(By.cssSelector(".index-section a"));
        for (WebElement link : links) link.click();
    }

    public void runFAQFlow() {
        List<WebElement> faqs = driver.findElements(By.cssSelector(".faq-item"));
        for (WebElement q : faqs) q.click();
    }
}
