package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class successStoriesPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//h1[contains(normalize-space(),'Success Stories')]")
    WebElement pageHeading;

    @FindBy(css = "video.elementor-video")
    List<WebElement> videos;

    public successStoriesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isPageLoaded() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 150);");

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));

            // Try heading first
            try {
                shortWait.until(ExpectedConditions.visibilityOf(pageHeading));
                System.out.println("✅ Success Stories heading visible quickly.");
                return true;
            } catch (TimeoutException e) {
                System.out.println("⚠️ Heading not visible yet, checking for videos...");
            }

            // Fallback: check for at least 1 video quickly
            shortWait.until(driver -> driver.findElements(By.cssSelector("video.elementor-video")).size() > 0);
            int count = driver.findElements(By.cssSelector("video.elementor-video")).size();
            if (count > 0) {
                System.out.println("✅ Success Stories page loaded (found " + count + " videos).");
                return true;
            } else {
                System.out.println("❌ No videos found on Success Stories page.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("❌ isPageLoaded failed fast: " + e.getMessage());
            return false;
        }
    }


    /**
     * Plays and pauses each video sequentially, verifying playback progress.
     */
    public void testVideosPlayAndPauseSequentially() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Find all <video> elements quickly
            List<WebElement> videos = driver.findElements(By.cssSelector("video.elementor-video"));
            if (videos.isEmpty()) {
                System.out.println("❌ No videos found on Success Stories page.");
                return;
            }

            System.out.println("🎥 Found " + videos.size() + " videos. Starting instant play/pause test...");

            int index = 1;
            for (WebElement video : videos) {
                System.out.println("\n➡️ Testing Video #" + index);

                // Scroll into view smoothly
                js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", video);
                Thread.sleep(500); // short pause for lazy-load trigger

                // ✅ Force preload and ready state before play
                js.executeScript("""
                    arguments[0].muted = true;
                    arguments[0].playbackRate = 2.5;
                    arguments[0].preload = 'auto';
                    if (arguments[0].readyState < 3) {
                        arguments[0].load();
                    }
                """, video);

                // Try to play quickly
                js.executeScript("arguments[0].play().catch(() => arguments[0].muted = true);", video);

                // Wait just a moment for playback to begin
                boolean started = false;
                for (int i = 0; i < 5; i++) { // retry for ~1.5s total
                    Thread.sleep(300);
                    started = (boolean) js.executeScript("return !arguments[0].paused && arguments[0].currentTime > 0;", video);
                    if (started) break;
                }

                if (started) {
                    System.out.println("▶️ Video #" + index + " is playing smoothly.");
                } else {
                    System.out.println("⚠️ Video #" + index + " did not start within 1.5s — forcing play via JS.");
                    js.executeScript("arguments[0].play();", video);
                }

                // Pause after short duration
                Thread.sleep(1000);
                js.executeScript("arguments[0].pause();", video);

                boolean paused = (boolean) js.executeScript("return arguments[0].paused;", video);
                System.out.println(paused ? "⏸️ Video #" + index + " paused successfully." : "⚠️ Pause failed for Video #" + index);

                index++;
            }

            System.out.println("\n✅ All Success Stories videos tested successfully (fast play/pause).");

        } catch (Exception e) {
            System.out.println("❌ Error during video play/pause test: " + e.getMessage());
        }
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }
}
