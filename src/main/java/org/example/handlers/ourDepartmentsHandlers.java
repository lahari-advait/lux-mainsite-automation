package org.example.handlers;

import org.example.pages.ourDepartments;

import org.example.pages.*;

import org.openqa.selenium.WebDriver;

public class ourDepartmentsHandlers {

    private WebDriver driver;
    private proctologyDepartment proctology;
    private generalAndLaparoscopicDepartment generalAndLaparoscopic;
    

    public ourDepartmentsHandlers(WebDriver driver) {
        this.driver = driver;
        this.proctology = new proctologyDepartment(driver);
        this.generalAndLaparoscopic= new generalAndLaparoscopicDepartment(driver);
      
    }

    
    	public void proctologyMethods() throws InterruptedException {
    	    System.out.println("🔹 Starting Proctology department test flow...");

    	    // Scroll to "Our Departments" first
    	    proctology.scrollToOurDepartments();
    	    Thread.sleep(500);

    	    // Open Proctology
    	    proctology.open();
    	    Thread.sleep(2000);
    	    proctology.testBookAppointmentButton();
    	    Thread.sleep(2000);
    	    proctology.scrollScrollableWhyChooseSection();
    	    Thread.sleep(1000);
    	    proctology.scrollToOurDoctorsSection();
    	    Thread.sleep(1000);
    	    proctology.testAllTreatments();
    	    Thread.sleep(1000);
    	    proctology.testTechnologyAndEquipmentSection();
    	    Thread.sleep(1000);
    	    proctology.testInsuranceCoverageSection();
    	    Thread.sleep(1000);
    	    proctology.testWhatsAppEnquiryNegative();
    	    Thread.sleep(1000);
    	    proctology.testWhatsAppEnquiryPositive();
    	    Thread.sleep(1000);
    	    proctology.scrollToTestimonialsAndClickDots();
    	    Thread.sleep(1000);
    	    proctology.scrollToLatestHealthArticles();
    	    Thread.sleep(1000);
    	    proctology.testAllFaqAccordions();
    	    System.out.println("🎉 Completed Proctology department test flow successfully!");
    	}

    	public void generalAndLaparoscopicMethods() throws InterruptedException {
    	    System.out.println("🔹 Starting General and Laparoscopic Surgery department test flow...");

    	    // Scroll to "Our Departments"
    	    generalAndLaparoscopic.scrollToOurDepartments();
    	    Thread.sleep(500);

    	    // Open the department
    	    generalAndLaparoscopic.open();
    	    Thread.sleep(2000);

    	    // Step-by-step test sequence
//    	    generalAndLaparoscopic.testBookAppointmentButton();
//    	    Thread.sleep(2000);

    	    generalAndLaparoscopic.scrollScrollableWhyChooseSection();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.scrollToOurDoctorsSection();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testAllTreatments();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testTechnologyAndEquipmentSection();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testInsuranceCoverageSection();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testWhatsAppEnquiryNegative();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testWhatsAppEnquiryPositive();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.scrollToTestimonialsAndClickDots();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.scrollToLatestHealthArticles();
    	    Thread.sleep(1000);

    	    generalAndLaparoscopic.testAllFaqAccordions();

    	    System.out.println("🎉 Completed General and Laparoscopic Surgery department test flow successfully!");
    	}

    
}
