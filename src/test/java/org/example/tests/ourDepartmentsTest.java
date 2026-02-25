//package org.example.tests;
//
//import org.example.base.BaseTest;
//import org.example.handlers.ourDepartmentsHandlers;
//import org.testng.annotations.Test;
//
//public class ourDepartmentsTest extends BaseTest {
//
//    private ourDepartmentsHandlers handler;
//
//    @Test(priority = 1)
//    public void testProctologyDepartment() throws InterruptedException {
//        // Initialize handler with driver from BaseTest
//        handler = new ourDepartmentsHandlers(driver);
//
//        // Execute the Proctology flow
//        handler.proctologyMethods();
//    }
//
//    @Test(priority = 2)
//    public void testGeneralAndLaparoscopicDepartment() throws InterruptedException {
//        // Initialize handler with driver from BaseTest
//        handler = new ourDepartmentsHandlers(driver);
//
//        // Execute the General and Laparoscopic flow
//        handler.generalAndLaparoscopicMethods();
//    }
//}
