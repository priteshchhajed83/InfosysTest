package com.securian.tests;

import com.securian.pages.preRetirementCalculatorPage;
import config.DriverConfig;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.TestUtil;

import java.util.HashMap;

@Listeners(listeners.TestNGListener.class)
public class preRetirementCalculatorTest extends DriverConfig {

    public WebDriver driver;
    public static String testID= "SecurianTest1";
    private String expResult = "Results";

//    public static String testID;

//    extentReportClass extentReport;

    public static HashMap<String, String> testData = new HashMap<>();

    private Logger log = LogManager.getLogger(this.getClass());

//    public preRetirementCalculatorTest(String testID){
//        this.testID = testID;
//        extentReport = new extentReportClass();
//    }

    public preRetirementCalculatorTest(){
//        this.testID = testID;
//        extentReport = new extentReportClass();
    }

    @BeforeTest
    public void setup(){
        driver = initializeDriver();
        testData = TestUtil.getDataFromExcel(testID);
//        extentReport.startReport(testID);
    }

    @Test
    public void createRetirementPlan(){
        try {
            preRetirementCalculatorPage page = new preRetirementCalculatorPage(driver);
            String actualResult = page.createPlan(testData);
            Assert.assertEquals(expResult, actualResult, "There is different output we got");
//            extentReport.testPass(testID);
        }catch (Exception e){
            log.error("Exception in takeScreenshot method: " + e.getMessage());
        }
    }



    @AfterTest
    public void tearDown(){
//        extentReport.endReport();
        driver.close();
    }
}
