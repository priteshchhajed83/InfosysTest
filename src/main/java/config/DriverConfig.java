package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.manager.SeleniumManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverConfig {
	private WebDriver driver;


	Properties props = new Properties();
	private Logger log = LogManager.getLogger(this.getClass());

	// resource paths
	private final String propsFilepath = "/resources/config.properties";

	/**
	 * initialize driver
	 * 
	 * @return - WebDriver
	 */
	public WebDriver initializeDriver() {
		try {
			loadProperty();
			log.debug("Running on browser: " + getBrowser());
			switch (getBrowser()) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
//			SeleniumManager.getInstance();
					driver = new ChromeDriver();
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
//			SeleniumManager.getInstance();
					driver = new FirefoxDriver();
					break;
				case "edge":
					SeleniumManager.getInstance();
					driver = new EdgeDriver();
					break;
				default:
					log.fatal("Invalid browser/browser config doesnt exist");
			}
			log.debug("Driver initialized");
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//			extentReportClass.report = new ExtentReports(reportPath+this.getClass().getSimpleName()+".html", false);
			String url = getUrl("url");
			log.info("Launching URL: " + url);
			driver.get(url);
		}catch (TimeoutException e){
			log.info("Application got Timeout Exception");
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * load properties file
	 * 
	 */
	public void loadProperty() {
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + propsFilepath);
			log.debug("Loading properties");
			props.load(fis);
		} catch (IOException e) {
			log.error("Exception in loadProperty method: " + e.getMessage());
		}
	}

	/**
	 * get url value from properties
	 * 
	 * @param url - key can be google-url, bing-url etc.
	 * @return - String
	 */
	public String getUrl(String url) {
		return props.getProperty(url);
	}

	/**
	 * get browser value from properties
	 * 
	 * @return - String
	 */
	public String getBrowser() {
		return props.getProperty("browser");
	}

}
