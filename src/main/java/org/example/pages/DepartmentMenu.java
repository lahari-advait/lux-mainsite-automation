package org.example.pages;

import java.time.Duration;

import org.example.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DepartmentMenu extends BasePage {

    private By treatmentsMenu = By.linkText("Treatments");

    public DepartmentMenu(WebDriver driver) {
        super(driver);
    }

    public void openDepartment(String departmentName) {
        Actions actions = new Actions(driver);

        // Hover Treatments
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(treatmentsMenu));
        actions.moveToElement(menu).pause(Duration.ofMillis(300)).perform();

        // Click Department
        By dept = By.xpath("//span[text()='" + departmentName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dept)).click();

        System.out.println("➡ Opened Department: " + departmentName);
    }
}
