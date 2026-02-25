package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BlogsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BlogsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForPageToLoad();
    }

    /* ========= LOCATORS ========= */

    private final By filterBar = By.id("lux-filter-bar");
    private final By categoryButtons = By.cssSelector("#lux-filter-bar a.lux-cat-btn");

    private final By readMoreLinks = By.cssSelector("a.blog-card__readmore");
    private final By loadMoreBtn = By.cssSelector("button.lux-loadmore-btn");

    private final String urlKeyword = "/blog";

    /* ========= PAGE LOAD ========= */

    private void waitForPageToLoad() {
        wait.until(d -> d.getCurrentUrl().toLowerCase().contains(urlKeyword));
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterBar));
        waitForDomToSettle();
    }

    public boolean isAt() {
        return driver.getCurrentUrl().toLowerCase().contains(urlKeyword)
                && !driver.findElements(filterBar).isEmpty()
                && driver.findElement(filterBar).isDisplayed();
    }

    /* ========= HELPERS ========= */

    private void waitForDomToSettle() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(d -> (Boolean) js.executeScript("return document.readyState === 'complete'"));
    }

    private void safeClick(WebElement el) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el);

        // ensure it is inside viewport
        wait.until(d -> (Boolean) js.executeScript(
                "const r = arguments[0].getBoundingClientRect();" +
                        "return r.top > 0 && r.bottom < window.innerHeight;", el));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", el);
        }
    }

    private void safeClick(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        safeClick(el);
    }

    private WebElement findCategoryButtonByText(String categoryName) {
        List<WebElement> cats = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryButtons));
        for (WebElement c : cats) {
            if (c.getText() != null && c.getText().trim().equalsIgnoreCase(categoryName.trim())) {
                return c;
            }
        }
        throw new RuntimeException("Category button not found: " + categoryName);
    }

    public List<String> getAllCategoryNames() {
        List<WebElement> cats = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryButtons));
        List<String> names = new ArrayList<>();
        for (WebElement c : cats) {
            String txt = (c.getText() == null) ? "" : c.getText().trim();
            if (!txt.isEmpty()) names.add(txt);
        }
        return names;
    }

    /* ========= CATEGORY ACTIONS ========= */

    /** Click category by visible label (Proctology, Gynecology, etc.) */
    public void clickCategory(String categoryName) {
        waitForDomToSettle();
        WebElement cat = findCategoryButtonByText(categoryName);
        safeClick(cat);

        // Wait for navigation/refresh to complete and posts to be available
        waitForDomToSettle();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(readMoreLinks));
    }

    /** Click first N Read More links and validate each detail page opens, then go back */
    public void openFirstNReadMoreArticles(int count) {
        for (int i = 0; i < count; i++) {
            waitForDomToSettle();

            List<WebElement> links = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(readMoreLinks));
            if (links.isEmpty()) throw new RuntimeException("No 'Read More' links found for this category.");

            int index = Math.min(i, links.size() - 1);
            WebElement link = links.get(index);

            String href = link.getAttribute("href");
            if (href == null || href.isEmpty()) throw new RuntimeException("'Read More' link has no href.");

            safeClick(link);

            // Detail page opened
            wait.until(d -> d.getCurrentUrl().equalsIgnoreCase(href));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            // Back to listing
            driver.navigate().back();

            waitForDomToSettle();
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(readMoreLinks));
        }
    }

    /** Click Load More (if present) and verify more posts appear */
    public void clickLoadMoreIfPresentAndVerifyMorePosts() {
        waitForDomToSettle();
        int beforeCount = driver.findElements(readMoreLinks).size();

        List<WebElement> btns = driver.findElements(loadMoreBtn);
        if (btns.isEmpty() || !btns.get(0).isDisplayed()) return; // no more pages

        WebElement loadMore = wait.until(ExpectedConditions.visibilityOfElementLocated(loadMoreBtn));
        safeClick(loadMore);

        // Wait for additional posts appended
        wait.until(d -> d.findElements(readMoreLinks).size() > beforeCount);
    }

    /* ========= HIGH-LEVEL FLOWS ========= */

    /** Runs your exact flow for one category */
    public void runCategoryFlow(String categoryName, int readMoreClicks) {
        clickCategory(categoryName);
        openFirstNReadMoreArticles(readMoreClicks);
        clickLoadMoreIfPresentAndVerifyMorePosts();
    }

    /** Runs the same flow for every category button in the filter bar */
    public void runFlowForAllCategories(int readMoreClicksPerCategory) {
        List<String> categories = getAllCategoryNames();
        for (String cat : categories) {
            runCategoryFlow(cat, readMoreClicksPerCategory);
        }
    }
}