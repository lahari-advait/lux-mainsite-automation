package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class aboutUsPage {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    @FindBy(xpath = "//li[contains(@class,'dropdown__item')]//span[normalize-space()='About Us']/ancestor::li")
    WebElement aboutUsMenuItem;

    public aboutUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        actions = new Actions(driver);
    }

    /**
     * Generic helper to open About Us dropdown and click submenu by visible text.
     */
    public void clickSubMenu(String submenuText) {
        try {
            System.out.println("[aboutUsPage] clickSubMenu: trying to click '" + submenuText + "'");
            // ensure parent is present & visible
            wait.until(ExpectedConditions.visibilityOf(aboutUsMenuItem));

            // scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", aboutUsMenuItem);

            // hover using Actions (real mouse movement)
            actions.moveToElement(aboutUsMenuItem).pause(Duration.ofMillis(800)).perform();

            // JS fallback: force menu visible (some sites hide via CSS)
            ((JavascriptExecutor) driver).executeScript(
                    "var menu=document.querySelector('li.dropdown__item ul.dropdown__menu');" +
                    "if(menu){menu.style.opacity='1';menu.style.visibility='visible';menu.style.pointerEvents='auto';}"
            );

            // build locator for submenu link by exact visible text
            By subLinkLocator = By.xpath("//ul[contains(@class,'dropdown__menu')]//a[normalize-space()='" + submenuText + "']");

            // wait until clickable
            WebElement subLink = wait.until(ExpectedConditions.elementToBeClickable(subLinkLocator));

            // scroll to and click via JS for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", subLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subLink);

            System.out.println("[aboutUsPage] clickSubMenu: clicked '" + submenuText + "' successfully.");

        } catch (TimeoutException te) {
            throw new RuntimeException("Timeout while trying to click submenu '" + submenuText + "'. " +
                    "Check locator and whether dropdown opened. " + te.getMessage(), te);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click submenu '" + submenuText + "': " + e.getMessage(), e);
        }
    }

    // convenience methods
    public void clickVisionAndMission() { clickSubMenu("Vision & Mission"); }
    public void clickCoreValues() { clickSubMenu("Core Values"); }
    public void clickInfrastructure() { clickSubMenu("Infrastructure");} 
	public void clickAccreditations() { clickSubMenu("Accreditations");}
	public void clickAdvisoryTeam() { clickSubMenu("Advisory Team"); }
	public void clickSuccessStories() { clickSubMenu("Success Stories"); }
	public void clickCareers() { clickSubMenu("Careers"); }




}
