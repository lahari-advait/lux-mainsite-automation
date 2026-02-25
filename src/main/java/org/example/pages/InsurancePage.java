package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InsurancePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public InsurancePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForPageToLoad();
    }

    /* ========= LOCATORS ========= */

    private final String urlKeyword = "insurance"; // adjust if needed

    private final By desktopBookAppointmentBtn =
            By.cssSelector("a.desktop-cta-btn.triger-carecansole");

    private final By majorInsuranceHeading =
            By.xpath("//*[normalize-space()='Major Health Insurance Companies Accepted']");

    private final By faqAccordions = By.cssSelector("div.accordion");
    private final By faqQuestion = By.cssSelector(".accordion_head > span");
    private final By faqAnswer = By.cssSelector(".accordion_content");

    private final By toggleBtnBy = By.cssSelector("button#seeMoreBtn, #seeMoreBtn"); // handles button or any element with id

    /* ========= PAGE LOAD CONTRACT ========= */

    private void waitForPageToLoad() {
        wait.until(d -> d.getCurrentUrl().toLowerCase().contains(urlKeyword));
        wait.until(ExpectedConditions.visibilityOfElementLocated(majorInsuranceHeading));
    }

    public boolean isAt() {
        return driver.getCurrentUrl().toLowerCase().contains(urlKeyword)
                && !driver.findElements(majorInsuranceHeading).isEmpty()
                && driver.findElement(majorInsuranceHeading).isDisplayed();
    }

    /* ========= ACTIONS ========= */

    public void clickBookAppointment() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(desktopBookAppointmentBtn));
        button.click();
    }

    public void scrollToMajorInsuranceAndToggleSeeMoreAndSeeLess() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        final int HEADER_OFFSET = 140;
        final int MAX_RETRIES = 3;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(majorInsuranceHeading));

            js.executeScript(
                    "const el = arguments[0];" +
                            "const y = el.getBoundingClientRect().top + window.pageYOffset - arguments[1];" +
                            "window.scrollTo(0, y);",
                    heading, HEADER_OFFSET
            );

            Boolean headingInView = (Boolean) js.executeScript(
                    "const r = arguments[0].getBoundingClientRect();" +
                            "return r.top >= 0 && r.top <= window.innerHeight * 0.7;",
                    heading
            );
            if (headingInView == null || !headingInView) continue;

            WebElement toggleBtn = wait.until(d -> {
                List<WebElement> els = d.findElements(toggleBtnBy);
                for (WebElement e : els) {
                    if (e.isDisplayed() && e.isEnabled()) return e;
                }
                return null;
            });

            if (toggleBtn == null) throw new RuntimeException("No visible/enabled See More/See Less button found.");

            js.executeScript(
                    "const el = arguments[0];" +
                            "const y = el.getBoundingClientRect().top + window.pageYOffset - (window.innerHeight/2);" +
                            "window.scrollTo(0, y);",
                    toggleBtn
            );

            clickSafely(toggleBtn);

            // Expanded
            wait.until(d -> {
                WebElement b = d.findElements(toggleBtnBy).stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
                if (b == null) return false;
                String txt = (b.getText() == null) ? "" : b.getText().trim().toLowerCase();
                String aria = b.getAttribute("aria-expanded");
                return txt.contains("see less") || "true".equalsIgnoreCase(aria);
            });

            WebElement toggleBtnAfterExpand = wait.until(d ->
                    d.findElements(toggleBtnBy).stream().filter(WebElement::isDisplayed).findFirst().orElse(null)
            );

            clickSafely(toggleBtnAfterExpand);

            // Collapsed
            wait.until(d -> {
                WebElement b = d.findElements(toggleBtnBy).stream().filter(WebElement::isDisplayed).findFirst().orElse(null);
                if (b == null) return false;
                String txt = (b.getText() == null) ? "" : b.getText().trim().toLowerCase();
                String aria = b.getAttribute("aria-expanded");
                return txt.contains("see more") || "false".equalsIgnoreCase(aria) || aria == null;
            });

            return; // success
        }

        throw new RuntimeException("Failed to expand/collapse Major Insurance section after retries.");
    }

    private void clickSafely(WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (Exception e1) {
            try {
                js.executeScript("arguments[0].click();", el);
            } catch (Exception e2) {
                js.executeScript(
                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable:true, view:window}));",
                        el
                );
            }
        }
    }

    public void expandAndCollapseAllFaqs() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> accordions =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(faqAccordions));

        if (accordions.isEmpty()) {
            throw new RuntimeException("No FAQ accordions found on Insurance page");
        }

        js.executeScript("arguments[0].scrollIntoView({block:'start'});", accordions.get(0));

        for (int i = 0; i < accordions.size(); i++) {
            List<WebElement> freshAccordions = driver.findElements(faqAccordions);
            WebElement accordion = freshAccordions.get(i);

            WebElement question = accordion.findElement(faqQuestion);
            WebElement answer = accordion.findElement(faqAnswer);

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", question);

            // Expand (wait for answer visible)
            js.executeScript("arguments[0].click();", question);
            wait.until(ExpectedConditions.visibilityOf(answer));

            // Collapse (wait for answer hidden)
            js.executeScript("arguments[0].click();", question);
            wait.until(ExpectedConditions.invisibilityOf(answer));
        }
    }
}