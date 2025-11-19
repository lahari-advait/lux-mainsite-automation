package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class proctologyDepartment {

    private WebDriver driver;
    private ourDepartments departmentsPage;

    public proctologyDepartment(WebDriver driver) {
        this.driver = driver;
        this.departmentsPage = new ourDepartments(driver);
    }

    // Scroll to "Our Departments" section
    public void scrollToOurDepartments() {
        By headingLocator = By.xpath("//h2[text()='Our Departments']");
        WebElement heading = driver.findElement(headingLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", heading);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Open the Proctology department card
    public void open() {
        By deptLocator = By.xpath("//div[h2[text()='Proctology']]");
        WebElement departmentCard = driver.findElement(deptLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", departmentCard);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", departmentCard);
        System.out.println("✅ Opened the Proctology Department page.");
    }

    // Click "Book an Appointment" button
    public void testBookAppointmentButton() throws InterruptedException {
        departmentsPage.testBookAppointmentButton();
    }

    // Wrapper delegations
    public void testAllTreatments() throws InterruptedException {
        departmentsPage.testAllTreatments();
    }

    public void scrollScrollableWhyChooseSection() throws InterruptedException {
        departmentsPage.scrollScrollableWhyChooseSection();
    }

    public void testAllFaqAccordions() throws InterruptedException {
        departmentsPage.testAllFaqAccordions();
    }

    public void scrollToTestimonialsAndClickDots() throws InterruptedException {
        departmentsPage.scrollToTestimonialsAndClickDots();
    }
    public void scrollToLatestHealthArticles(){
    	departmentsPage.scrollToLatestHealthArticles();
    	}
    public void testWhatsAppEnquiryNegative() throws InterruptedException {
    	departmentsPage.testWhatsAppEnquiryNegative();
    
    }
    public void testWhatsAppEnquiryPositive() throws InterruptedException {
    	departmentsPage.testWhatsAppEnquiryPositive();
    
    }
   public void scrollToOurDoctorsSection() throws InterruptedException {
	   departmentsPage.scrollToOurDoctorsSection();
   }
   public void testInsuranceCoverageSection() throws InterruptedException {
	   departmentsPage.testInsuranceCoverageSection();
   
   }
   public void testTechnologyAndEquipmentSection() throws InterruptedException {
	   departmentsPage.testTechnologyAndEquipmentSection();
   }
}