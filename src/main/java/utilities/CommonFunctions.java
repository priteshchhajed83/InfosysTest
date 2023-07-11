package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions {
	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	private Logger log = LogManager.getLogger(this.getClass());

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public CommonFunctions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	/**
	 * helper method to wait for element
	 *
	 * @param ele - WebElement
	 * @return - boolean
	 */
	public boolean waitForElement(WebElement ele) {
		return wait.until(ExpectedConditions.elementToBeClickable(ele)) != null ? true : false;
	}

	/**
	 * element enter the text
	 */

	public void enterText(WebElement ele, String text){
		ele.sendKeys(text);
	}

	/**
	 * element click using js executor
	 *
	 * @param ele - WebElement
	 */
	public void clickSafelyJS(WebElement ele) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
		js.executeScript("arguments[0].click();", ele);

	}

	/**
	 * enter text using js executor
	 *
	 * @param ele
	 * @param text
	 */
	public void sendKeysSafelyJS(WebElement ele, String text) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
		js.executeScript("window.focus();");
//		js.executeScript("arguments[0].setAttribute('value', '"+ ""+"')", ele);
		js.executeScript("arguments[0].setAttribute('value', '" + text + "')", ele);
	}

	/**
	 * take screenshot
	 *
	 */
	public void takeScreenshot() {
		File scrnFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try {
			FileUtils.copyFile(scrnFile, new File(currentDir + "/screenshots" + formatTimeSDF() + ".png"));
		} catch (IOException e) {
			log.error("Exception in takeScreenshot method: " + e.getMessage());
		}
	}

	/**
	 * take screenshot and name file with test name + time stamp
	 *
	 * @param testName
	 */
	public void takeScreenshot(String testName) {
		File scrnFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try {
			FileUtils.copyFile(scrnFile,
					new File(currentDir + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			log.error("Exception in takeScreenshot method: " + e.getMessage());
		}
	}

	/**
	 * format date/time
	 *
	 * @return - String
	 */
	public static String formatTimeSDF() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}


	public void setValues(WebElement ele, String value){
		String elementType = ele.getAttribute("type");
		switch (elementType) {
			case "text":
			case "textarea":
				sendKeysSafelyJS(ele, value);
				break;
			case "Select":
				break;
			case "Checkbox":
				break;
			case "button":
			case "radio":
				clickSafelyJS(ele);
				break;

		}
	}
}
