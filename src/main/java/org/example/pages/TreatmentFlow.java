package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TreatmentFlow {

    WebDriver driver;
    WebDriverWait wait;

    private final String URL = "https://luxhospitals.com/treatments/";

    public TreatmentFlow(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void testTreatment(String department, String treatment) {

        // ALWAYS reset page
        navigateToStart();

        // STEP 1: Click department (left side)
        clickDepartment(department);

        // STEP 2: Click treatment using NEW stable locator
        clickTreatment(department, treatment);

        // STEP 3: Validate landing page
        validateTreatmentPage(treatment);

        // STEP 4: Go back and prepare for next dataset
        navigateToStart();
    }


    /* ====================================
       PAGE NAVIGATION
       ==================================== */
    private void navigateToStart() {
        driver.get(URL);
        waitForPageLoad();
        sleep(500);
    }


    /* ====================================
       CLICK DEPARTMENT
       ==================================== */
    private void clickDepartment(String departmentName) {

        String deptXpath = "//ul[@id='departments-list']//span[normalize-space()='" + departmentName + "']";

        WebElement dept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(deptXpath)));

        // Always expand the department – even if already selected
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dept);
        dept.click();

        // Wait until the box becomes visible
        String parentClass = getDepartmentTarget(departmentName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'" + parentClass + "')]")));

        sleep(400);
    }



    /* ====================================
       CLICK TREATMENT – NEW FIXED LOGIC
       ==================================== */
    private void clickTreatment(String departmentName, String treatmentName) {

        String parentClass = getDepartmentTarget(departmentName);

        String treatmentXpath =
                "//div[contains(@class,'" + parentClass + "')]//*[self::p or self::a or self::span or self::div][normalize-space()='" + treatmentName + "']";

        WebElement treatment = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(treatmentXpath)));
        treatment.click();

        waitForPageLoad();
        sleep(100);
    }


    private String getDepartmentTarget(String department) {

        switch (department) {
            case "Proctology":
                return "for__proctology__parent___box";

            case "General and Laparoscopic":
                return "for__general__parent___box";

            case "Gynaecology":
                return "for__gynecology__parent___box";

            case "Urology":
                return "for__urology__parent___box";

            case "Plastic and Cosmetic":
                return "for__plastic__parent___box";

            case "Orthopaedic":
                return "for__ortho__parent___box";

            default:
                throw new RuntimeException("❌ Unknown department: " + department);
        }
    }


    /* ====================================
       VALIDATION
       ==================================== */
    private void validateTreatmentPage(String treatmentName) {

        String header = "//h1[contains(translate(text()," +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), " +
                "translate('" + treatmentName + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))]";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(header)));

        System.out.println("✓ Validated: " + treatmentName);
    }


    /* ====================================
       UTILITIES
       ==================================== */
    private void waitForPageLoad() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {}
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }
}

