package org.example.tests;

import org.example.base.BaseTest;
import org.example.handlers.treatmentsHandlers;
import org.example.pages.allTreatments;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class allTreatmentsTest extends BaseTest {

    private treatmentsHandlers treatmentsHandler;

    @BeforeClass
    public void setUpTreatmentsHandler() {
        treatmentsHandler = new treatmentsHandlers(driver);
    }

//    @Test(priority = 1)
//    public void testProctologyPilesMethods() throws InterruptedException {
//        treatmentsHandler.proctologyPilesMethods();
//    }
//    @Test(priority = 2)
//    public void testproctologyAnalFistulaMethods() throws InterruptedException {
//        treatmentsHandler.proctologyAnalFistulaMethods();
//    }
//    @Test(priority = 3)
//    public void testproctologyAnalFissureMethods() throws InterruptedException {
//        treatmentsHandler.proctologyAnalFissureMethods();
//    }
//    @Test(priority = 4)
//    public void proctologyAnalFissureMethods() throws InterruptedException {
//        treatmentsHandler.proctologyAnalFissureMethods();
//    }
//    @Test(priority = 5)
//    public void proctologyPerianalAbscessMethods() throws InterruptedException {
//        treatmentsHandler.proctologyPerianalAbscessMethods();
//    }
//    @Test(priority = 6)
//    public void proctologyAnalPolypRemovalTest() throws InterruptedException {
//        treatmentsHandler.proctologyAnalPolypRemovalMethods();
//    }
//    @Test(priority = 7)
//    public void proctologyPilonidalSinusTest() throws InterruptedException {
//        treatmentsHandler.proctologyPilonidalSinusMethods();
//    }
//    @Test(priority = 8)
//    public void proctologyFecalIncontinenceTest() throws InterruptedException {
//        treatmentsHandler.proctologyFecalIncontinenceMethods();
//    }
//    @Test(priority = 9)
//    public void gastroIrritableBowelSyndromeTest() throws InterruptedException {
//        treatmentsHandler.gastroIrritableBowelSyndromeMethods();
//    }
//    @Test(priority = 10)
//    public void gynPelvicFloorDysfunctionTest() throws InterruptedException {
//        treatmentsHandler.gynPelvicFloorDysfunctionMethods();
//    }
//
//    @Test(priority = 11)
//    public void gastroChronicConstipationTest() throws InterruptedException {
//        treatmentsHandler.gastroChronicConstipationMethods();
//    }
//    @Test(priority = 12)
//    public void gastroUlcerativeColitisTest() throws InterruptedException {
//        treatmentsHandler.gastroUlcerativeColitisMethods();
//    }
//    @Test(priority = 13)
//    public void gastroInflammatoryBowelDiseaseTest() throws InterruptedException {
//        treatmentsHandler.gastroInflammatoryBowelDiseaseMethods();
//    }
    @Test(priority = 14)
    public void testClickViewMore() {
        allTreatments allTreatments = new allTreatments(driver);

        int departmentIndex = 0; // Click the first department dynamically
        allTreatments.clickViewMore(departmentIndex);
    }



}
