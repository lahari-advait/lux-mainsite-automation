package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.DoctorsSidebar;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class doctorsDropdownTest extends BaseTest {

    @Test
    public void clickAllDoctorSidebarItems() throws InterruptedException {
        DoctorsSidebar sidebar = new DoctorsSidebar(driver);

        sidebar.hoverDoctorsMenu();

        sidebar.clickDoctorItems();
    }
}
