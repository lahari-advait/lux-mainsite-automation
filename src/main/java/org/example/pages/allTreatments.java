package org.example.pages;
import java.util.*;

import org.example.base.BasePage;
import data.TreatmentData;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AllTreatments extends BasePage {

    // ---------------- LOCATORS ----------------
    private final By treatmentsMenu = By.linkText("Treatments");
    private final By departments = By.cssSelector("#departments-list li.department___listitem");
    private final By activeDepartmentBox = By.cssSelector(".department-box.active");
    private final By activeSubDepartments = By.cssSelector(".department-box.active .inside__parent__box > a");
    private final Set<Integer> whatsappClickedCards = new HashSet<>();
    private final By bookAppointmentBtn = By.xpath(
    	    "//a[contains(normalize-space(.),'Book Appointment') and not(contains(@style,'display:none'))]"
    	);
    

    // ---------------- CONSTRUCTOR ----------------
    public AllTreatments(WebDriver driver) {
        super(driver);
    }

    public void hoverOverTreatments() {

        WebElement menu = wait.until(
            ExpectedConditions.visibilityOfElementLocated(treatmentsMenu)
        );

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            menu
        );

        // 🔴 HIGHLIGHT Treatments
        highlight(menu);

        // JS hover (stable)
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mouseover', {" +
            "bubbles:true,cancelable:true,view:window}));",
            menu
        );

        // Physical hover fallback
        new Actions(driver)
            .moveToElement(menu)
            .pause(Duration.ofMillis(400))
            .perform();

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        System.out.println("✅ Hovered Treatments menu (highlighted)");
    }

//    private void highlight(WebElement element) {
//        js.executeScript(
//            "arguments[0].style.outline='3px solid red';" +
//            "arguments[0].style.background='#fff3cd';",
//            element
//        );
//    }

    private void openTreatment(String department, String subDepartment) {

        String deptSlug = department.toLowerCase().replace(" ", "-");
        String subSlug  = subDepartment.toLowerCase().replace(" ", "-");

        String url = "https://luxhospitals.com/treatments/"
                     + deptSlug + "/" + subSlug + "/";

        driver.get(url);

        wait.until(ExpectedConditions.urlContains(subSlug));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    private void ensureOnHomePage() {
        if (!driver.getCurrentUrl().equals("https://luxhospitals.com/")) {
            driver.get("https://luxhospitals.com/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(treatmentsMenu));
        }
    }
    private void clickBookAppointmentIfPresent(String parentWindow) {

        try {
            List<WebElement> buttons = driver.findElements(bookAppointmentBtn);

            if (buttons.isEmpty()) {
                System.out.println("     ⚠ Book Appointment CTA not found on this page");
                return;
            }

            WebElement btn = buttons.get(0);

            js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                btn
            );
            Thread.sleep(500);

            highlight(btn);
            System.out.println("     ➜ Clicking Book Appointment CTA");

            Set<String> beforeHandles = driver.getWindowHandles();
            jsClick(btn);

            String newTab = waitForNewTab(beforeHandles, 6);

            if (newTab != null) {
                driver.switchTo().window(newTab);
                Thread.sleep(800);
                driver.close();
                driver.switchTo().window(parentWindow);
            }

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        } catch (Exception e) {
            System.out.println("     ⚠ Book Appointment CTA skipped safely");
        }
    }



    // ---------------- DATA-DRIVEN RUNNER ----------------
    public void runTestsFromData(List<TreatmentData> data, String parentWindow) throws Exception {

        for (TreatmentData td : data) {

            System.out.println("\n▶ " + td.getDepartment() + " -> " + td.getSubDepartment());

            openTreatmentByUrl(td);   // ✅ CORRECT

            runInsideSubDepartmentTests(parentWindow);
        }
    }

    private void openTreatmentByUrl(TreatmentData td) {

        System.out.println("➡ Opening URL: " + td.getUrl());

        driver.get(td.getUrl());

        wait.until(ExpectedConditions.urlToBe(td.getUrl()));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }



    // ---------------- CLICK DEPARTMENT BY NAME ----------------
//    private void clickDepartmentByName(String name) {
//
//        // ✅ ENSURE menu is open before reading departments
//        hoverOverTreatments();
//
//        List<WebElement> depts = driver.findElements(departments);
//
//        for (WebElement d : depts) {
//            String text = d.getText().trim();
//            if (text.equalsIgnoreCase(name)) {
//                WebElement box = d.findElement(By.cssSelector(".flexx___box"));
//                jsClick(box);
//                wait.until(ExpectedConditions.visibilityOfElementLocated(activeDepartmentBox));
//                return;
//            }
//        }
//
//        throw new RuntimeException("Department not found: " + name);
//    }
//
//
//    // ---------------- CLICK SUB-DEPARTMENT BY NAME ----------------
//    private void clickSubDepartmentByName(String name) {
//
//        List<WebElement> subs = driver.findElements(activeSubDepartments);
//
//        for (WebElement s : subs) {
//            String text = s.getText().trim();
//            if (text.equalsIgnoreCase(name)) {
//                String beforeUrl = driver.getCurrentUrl();
//                jsClick(s);
//                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(beforeUrl)));
//                return;
//            }
//        }
//        throw new RuntimeException("Sub-department not found under active department: " + name);
//    }
    private void runInsideSubDepartmentTests(String parentWindow) throws Exception {
    	 System.out.println("     ▶ Running inside tests");
         Thread.sleep(1000);

         // 1️⃣ DOCTOR CARDS — CONTENT FIRST
         // Use the handleAllDoctorCards method to handle all locator logic (primary, fallback, secondary)
         handleAllDoctorCards(parentWindow);
         
         // After handling the doctor cards, we add a delay to simulate the processing time
         Thread.sleep(1500); // ⏸ after doctor cards


        // 2️⃣ SECTION NAVIGATION
        clickAllSectionLinksOneByOne();
        Thread.sleep(1500); // ⏸ after section navigation

        // 3️⃣ TESTIMONIALS
        if (!driver.findElements(By.cssSelector(".testimonial-dots span.dot")).isEmpty()) {
            scrollToTestimonialsAndClickDots();
            Thread.sleep(1500); // ⏸ after testimonials
        }

        // 4️⃣ ARTICLES
        if (!driver.findElements(By.cssSelector(".card-title a")).isEmpty()) {
            scrollToLatestHealthArticles(parentWindow);
            Thread.sleep(1500); // ⏸ after articles
        }

        // 5️⃣ BOOK APPOINTMENT — LAST (NAV MUTATION)
        handleBookAppointment(parentWindow);
        Thread.sleep(1500); // ⏸ after final book appointment
    }




    // ---------------- JS CLICK ----------------
    public void jsClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    // ---------------- DOCTOR CARDS ----------------
//    public void handleAllDoctorCards(String parentWindow) {
//        try {
//            By cardLocator = By.cssSelector("div[class$='-doctor-card']");
//            By bookBtn = By.cssSelector("button[class*='doctor-card-book-btn']");
//            By profileBtn = By.cssSelector("button[class*='doctor-card-appointment-btn']");
//
//            List<WebElement> visibleCards = driver.findElements(cardLocator)
//                    .stream().filter(WebElement::isDisplayed).toList();
//
//            if (visibleCards.isEmpty()) {
//                System.out.println("     ➜ Doctor cards: 0 visible (skipping)");
//                return;
//            }
//
//            System.out.println("     ➜ Doctor cards (visible): " + visibleCards.size());
//
//            for (int i = 0; i < visibleCards.size(); i++) {
//                clickDoctorButtonVisible(cardLocator, i, bookBtn, parentWindow);     // WhatsApp once per card
//                clickDoctorButtonVisible(cardLocator, i, profileBtn, parentWindow); // View Profile once per card
//            }
//
//        } catch (Exception e) {
//            System.out.println("❌ Doctor card test failed: " + e.getMessage());
//        }
//    }
//
//    private void clickDoctorButtonVisible(By cardLocator, int visibleIndex, By buttonLocator, String parentWindow) {
//        try {
//            List<WebElement> visibleCards =
//                    driver.findElements(cardLocator).stream().filter(WebElement::isDisplayed).toList();
//
//            if (visibleIndex >= visibleCards.size()) return;
//
//            WebElement card = visibleCards.get(visibleIndex);
//            List<WebElement> buttons = card.findElements(buttonLocator);
//            if (buttons.isEmpty()) return;
//
//            WebElement button = buttons.get(0);
//            String returnUrl = driver.getCurrentUrl();
//            Set<String> beforeHandles = driver.getWindowHandles();
//
//            js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
//            Thread.sleep(150);
//            jsClick(button);
//
//            String newTab = waitForNewTab(beforeHandles, 6);
//
//            if (newTab != null) {
//                driver.switchTo().window(newTab);
//                Thread.sleep(400);
//                driver.close();
//                driver.switchTo().window(parentWindow);
//                wait.until(ExpectedConditions.urlToBe(returnUrl));
//            } else {
//                if (!driver.getCurrentUrl().equals(returnUrl)) {
//                    driver.get(returnUrl);
//                    wait.until(ExpectedConditions.urlToBe(returnUrl));
//                }
//            }
//
//            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardLocator));
//
//        } catch (Exception e) {
//            System.out.println("⚠ Doctor action failed: " + e.getMessage());
//        }
//    }

    private String waitForNewTab(Set<String> oldHandles, int timeoutSeconds) {
        for (int i = 0; i < timeoutSeconds * 2; i++) {
            Set<String> current = driver.getWindowHandles();
            if (current.size() > oldHandles.size()) {
                current.removeAll(oldHandles);
                return current.iterator().next();
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        return null;
    }

    // ---------------- SECTION LINKS ----------------
    public void clickAllSectionLinksOneByOne() {
        try {
            By linkLocator = By.cssSelector(".index-scroll-container a.clickable");
            int linkCount = driver.findElements(linkLocator).size();

            for (int i = 0; i < linkCount; i++) {
                List<WebElement> links = driver.findElements(linkLocator);
                WebElement link = links.get(i);

                String text = link.getText().trim().toLowerCase();

                jsClick(link);
                Thread.sleep(500);

                if (text.contains("faq")) testAllFaqAccordions();
                if (text.contains("testimonial")) scrollToTestimonialsAndClickDots();
                if (text.contains("article")) scrollToLatestHealthArticles(driver.getWindowHandle());

                js.executeScript("window.scrollTo(0,0)");
                Thread.sleep(250);
            }
        } catch (Exception e) {
            System.out.println("⚠ Section test skipped: " + e.getMessage());
        }
    }

    // ---------------- FAQ ----------------
    public void testAllFaqAccordions() {
        List<WebElement> accordions = driver.findElements(By.cssSelector("div.accordion"));
        for (WebElement acc : accordions) {
            try {
                WebElement q = acc.findElement(By.cssSelector(".accordion_head > span"));
                jsClick(q);
                Thread.sleep(200);
            } catch (Exception ignored) {}
        }
    }

    // ---------------- TESTIMONIALS ----------------
    public void scrollToTestimonialsAndClickDots() throws Exception {
        List<WebElement> dots = driver.findElements(By.cssSelector(".testimonial-dots span.dot"));
        for (WebElement d : dots) {
            jsClick(d);
            Thread.sleep(500);
        }
    }

    // ---------------- ARTICLES ----------------
    public void scrollToLatestHealthArticles(String parentWindow) {
        try {
            List<WebElement> links = driver.findElements(By.cssSelector(".card-title a"));
            for (int i = 0; i < links.size(); i++) {
                links = driver.findElements(By.cssSelector(".card-title a"));
                jsClick(links.get(i));
                Thread.sleep(900);
                switchToNewTabAndReturn(parentWindow);
            }
        } catch (Exception ignored) {}
    }

    // ---------------- TAB HANDLER ----------------
    private void switchToNewTabAndReturn(String parentWindow) {
        try {
            for (String h : driver.getWindowHandles()) {
                if (!h.equals(parentWindow)) {
                    driver.switchTo().window(h);
                    Thread.sleep(500);
                    driver.close();
                    driver.switchTo().window(parentWindow);
                    return;
                }
            }
        } catch (Exception ignored) {}
    }
    public void handleAllDoctorCards(String parentWindow) {
        try {
            // RESET STATE
            whatsappClickedCards.clear();

            // PRIMARY (best)
            By primaryCardLocator = By.cssSelector("div[class$='-doctor-card']");

            // FALLBACK (only if primary finds none)
            By fallbackCardLocator = By.cssSelector(
                "div[class*='doctor-card']:not([class$='-doctor-card'])"
            );

            // Start with primary
            By cardLocator = primaryCardLocator;

            List<WebElement> allCards = driver.findElements(cardLocator);
            List<WebElement> visibleCards = allCards.stream()
                    .filter(WebElement::isDisplayed)
                    .toList();

            System.out.println("➜ Cards found using PRIMARY: " + allCards.size());
            System.out.println("➜ Visible cards using PRIMARY: " + visibleCards.size());

            // Switch to fallback ONLY if primary yields no visible cards
            if (visibleCards.isEmpty()) {
                System.out.println("➜ No visible cards with PRIMARY. Switching to FALLBACK...");

                cardLocator = fallbackCardLocator;

                allCards = driver.findElements(cardLocator);
                visibleCards = allCards.stream()
                        .filter(WebElement::isDisplayed)
                        .toList();

                System.out.println("➜ Cards found using FALLBACK: " + allCards.size());
                System.out.println("➜ Visible cards using FALLBACK: " + visibleCards.size());
            }

            if (visibleCards.isEmpty()) {
                System.out.println("➜ Doctor cards: 0 visible (skipping)");
                return;
            }

            System.out.println("➜ Doctor cards (visible): " + visibleCards.size());

            // Button locators (robust text-based)
            By bookBtn = By.xpath(".//*[contains(normalize-space(.), 'Book Appointment')]");
            By profileBtn = By.xpath(".//*[contains(normalize-space(.), 'View Profile')]");

            for (int i = 0; i < visibleCards.size(); i++) {

                // Re-fetch each loop to avoid stale DOM issues
                List<WebElement> freshVisibleCards = driver.findElements(cardLocator)
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .toList();

                if (i >= freshVisibleCards.size()) break;

                WebElement card = freshVisibleCards.get(i);

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                Thread.sleep(300);

                System.out.println("➜ Doctor Card #" + (i + 1));

                // Book Appointment once per card
                if (!whatsappClickedCards.contains(i)) {
                    clickDoctorButtonVisible(cardLocator, i, bookBtn, parentWindow);
                    whatsappClickedCards.add(i);
                }

                Thread.sleep(800);

                // View Profile always
                clickDoctorButtonVisible(cardLocator, i, profileBtn, parentWindow);
            }

            System.out.println("✔ Doctor cards processed — returning");

        } catch (Exception e) {
            System.out.println("❌ Doctor card test failed: " + e.getMessage());
        }
    }


    private void clickDoctorButtonVisible(
            By cardLocator,
            int visibleIndex,
            By buttonLocator,
            String parentWindow
    ) {

        try {
            List<WebElement> visibleCards =
                driver.findElements(cardLocator).stream()
                      .filter(WebElement::isDisplayed)
                      .toList();

            if (visibleIndex >= visibleCards.size()) return;

            WebElement card = visibleCards.get(visibleIndex);
            WebElement targetElement = null;

            String actionText =
                buttonLocator.toString().contains("Book Appointment")
                    ? "Book Appointment"
                    : "View Profile";

            /* ===============================
               IF–ELSE: BUTTON → ELSE SPAN
               =============================== */

            // IF <button>Action</button>
            List<WebElement> buttons = card.findElements(
                By.xpath(".//button[normalize-space()='" + actionText + "']")
            );

            if (!buttons.isEmpty()) {
                targetElement = buttons.get(0);
            }

            // ELSE IF <span>Action</span>
            else {
                List<WebElement> spans = card.findElements(
                    By.xpath(".//span[normalize-space()='" + actionText + "']")
                );

                if (!spans.isEmpty()) {
                    targetElement = spans.get(0);
                }
            }

            if (targetElement == null) return;

            /* ===============================
               TAB HANDLING — UNCHANGED
               =============================== */

            String returnUrl = driver.getCurrentUrl();
            Set<String> beforeHandles = driver.getWindowHandles();

            js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                targetElement
            );
            Thread.sleep(200);
            jsClick(targetElement);

            String newTab = waitForNewTab(beforeHandles, 6);

            if (newTab != null) {
                driver.switchTo().window(newTab);
                Thread.sleep(600);
                driver.close();
                driver.switchTo().window(parentWindow);
                wait.until(ExpectedConditions.urlToBe(returnUrl));
            } else {
                if (!driver.getCurrentUrl().equals(returnUrl)) {
                    driver.get(returnUrl);
                    wait.until(ExpectedConditions.urlToBe(returnUrl));
                }
            }

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardLocator));

        } catch (Exception e) {
            System.out.println("⚠ Doctor button failed: " + e.getMessage());
        }
    }

    private void resetToTreatmentsContext() {

        // Force clean state (IMPORTANT)
        driver.get("https://luxhospitals.com/");

        // Wait until Treatments menu is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.linkText("Treatments")
        ));

        // Restore hover state
//        hoverOverTreatments();
    }

    private void hoverAndHighlight(By locator) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        // Hover
        new Actions(driver)
            .moveToElement(el)
            .pause(Duration.ofMillis(400))
            .perform();

        // Visual highlight (debug aid)
        js.executeScript(
            "arguments[0].style.outline='3px solid #8e44ad';" +
            "arguments[0].style.backgroundColor='#f5e6ff';",
            el
        );

        try { Thread.sleep(400); } catch (InterruptedException ignored) {}
    }
    private void clickDepartmentByName(String name) {

        hoverOverTreatments(); // ALWAYS reopen dropdown

        List<WebElement> depts = driver.findElements(departments);

        for (WebElement d : depts) {

            WebElement label =
                d.findElement(By.cssSelector("span"));

            if (label.getText().trim().equalsIgnoreCase(name)) {

                WebElement box =
                    d.findElement(By.cssSelector(".flexx___box"));

                highlight(box);
                jsClick(box);

                wait.until(
                    ExpectedConditions.visibilityOfElementLocated(activeDepartmentBox)
                );

                System.out.println("➡ Highlighted Department: " + name);
                return;
            }
        }

        throw new RuntimeException("Department not found: " + name);
    }
    private void clickSubDepartmentByName(String name) {

        List<WebElement> subs =
            driver.findElements(activeSubDepartments);

        for (WebElement s : subs) {

            WebElement label = s.findElement(By.tagName("p"));

            if (label.getText().trim().equalsIgnoreCase(name)) {

                highlight(s);

                String beforeUrl = driver.getCurrentUrl();
                jsClick(s);

                wait.until(
                    ExpectedConditions.not(
                        ExpectedConditions.urlToBe(beforeUrl)
                    )
                );

                System.out.println("   ➜ Highlighted Sub-department: " + name);
                return;
            }
        }

        throw new RuntimeException("Sub-department not found: " + name);
    }
    private void handleBookAppointment(String parentWindow) {

        List<WebElement> buttons = driver.findElements(bookAppointmentBtn);

        if (buttons.isEmpty()) {
            System.out.println("     ➜ Book Appointment not present");
            return;
        }

        WebElement button = buttons.get(0);
        highlight(button);

        String returnUrl = driver.getCurrentUrl();
        Set<String> beforeHandles = driver.getWindowHandles();

        jsClick(button);

        // ⏳ Wait for JS / redirect / tab
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}

        Set<String> afterHandles = driver.getWindowHandles();

        // 🟢 CASE 1: New tab
        if (afterHandles.size() > beforeHandles.size()) {

            afterHandles.removeAll(beforeHandles);
            String newTab = afterHandles.iterator().next();

            driver.switchTo().window(newTab);
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            driver.close();
            driver.switchTo().window(parentWindow);

            driver.get(returnUrl); // 🔒 HARD RESET
            wait.until(ExpectedConditions.urlToBe(returnUrl));

            System.out.println("     ✔ Book Appointment → new tab closed & reset");
            return;
        }

        // 🔵 CASE 2: Same tab redirect
        if (!driver.getCurrentUrl().equals(returnUrl)) {

            driver.get(returnUrl); // 🔒 FORCE reset (not back)
            wait.until(ExpectedConditions.urlToBe(returnUrl));

            System.out.println("     ✔ Book Appointment → same tab reset");
            return;
        }

        // 🟡 CASE 3: JS tries again (block it)
        js.executeScript(
            "document.querySelectorAll('a.triger-carecansole').forEach(e => e.onclick = null);"
        );

        System.out.println("     ✔ Book Appointment → JS trigger disabled");
    }
    private void highlight(WebElement element) {

        js.executeScript(
            "arguments[0].style.outline='3px solid #ff5722';" +
            "arguments[0].style.background='rgba(255,87,34,0.15)';" +
            "arguments[0].style.transition='all 0.2s ease';",
            element
        );

        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
    }

    private void clearHighlight(WebElement element) {
        js.executeScript(
            "arguments[0].style.outline='';" +
            "arguments[0].style.backgroundColor='';",
            element
        );
    }
    private List<WebElement> getVisibleUniqueDoctorCards() {

        // View Profile links are the best unique doctor identifier on your pages
        By profileLinks = By.xpath("//a[contains(@href,'/doctors/') and @target='_blank']");

        List<WebElement> links = driver.findElements(profileLinks);

        // Use LinkedHashMap to keep order + dedupe by href
        Map<String, WebElement> uniqueCards = new LinkedHashMap<>();

        for (WebElement a : links) {
            try {
                if (!a.isDisplayed()) continue;

                String href = a.getAttribute("href");
                if (href == null || href.isBlank()) continue;

                // Find the nearest container that looks like a doctor card block
                WebElement card = a.findElement(By.xpath(
                    "ancestor::div[" +
                    "contains(@class,'doctor-card') or " +
                    "contains(@class,'-doctor-card') or " +
                    "contains(@class,'abishek-katha-doctor-card') or " +
                    "contains(@class,'samhitha-reddy-doctor-card')" +
                    "][1]"
                ));

                if (card != null && card.isDisplayed()) {
                    uniqueCards.putIfAbsent(href, card); // ✅ dedupe by doctor profile url
                }

            } catch (Exception ignored) {}
        }

        return new ArrayList<>(uniqueCards.values());
    }



}
