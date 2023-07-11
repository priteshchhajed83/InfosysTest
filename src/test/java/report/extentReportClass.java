package report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.CommonFunctions;

import java.io.File;

public class extentReportClass {

    public static ExtentReports extent;
    public static ExtentTest test;

    CommonFunctions functions;

    @BeforeTest
    public void startReport(String testName){
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/SecurianTestReport.html", true);
        test = extent.startTest(testName);
    }

    @Test
    public void testPass(){
        functions.takeScreenshot();
        test.log(LogStatus.PASS, "Test has been passed");
    }

    @Test
    public void testFail(){
        functions.takeScreenshot();
        test.log(LogStatus.FAIL, "Test has been passed");
    }

    @AfterTest
    public void endReport(){
        extent.endTest(test);
        extent.flush();
        extent.close();
    }







//    report = new ExtentReports(reportPath+this.getClass().getSimpleName()+".html", false);

}
