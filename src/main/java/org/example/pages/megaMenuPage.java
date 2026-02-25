package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import java.util.*;

public class megaMenuPage {

    private WebDriver driver;

    private By treatmentsMenu = By.xpath("//a[contains(text(),'Treatments')]");
    private By departments = By.cssSelector("ul#departments-list li");

    private By treatmentItemsInsideDept = By.cssSelector("div.treatment-ui div.flex_boxes_treatmet a");

    public megaMenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void hoverTreatments() {
        Actions act = new Actions(driver);
        act.moveToElement(driver.findElement(treatmentsMenu)).perform();
    }

    public List<WebElement> getDepartments() {
        return driver.findElements(departments);
    }

    public void clickDepartment(WebElement dep) {
        dep.click();
    }

    public List<WebElement> getTreatmentsInsideDepartment() {
        return driver.findElements(treatmentItemsInsideDept);
    }

    public void clickTreatment(WebElement treatment) {
        treatment.click();
    }
}
