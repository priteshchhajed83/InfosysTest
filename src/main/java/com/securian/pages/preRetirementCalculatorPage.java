package com.securian.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.CommonFunctions;

import java.util.HashMap;


public class preRetirementCalculatorPage {

    private WebDriver driver;
    CommonFunctions functions;
    private Logger log = LogManager.getLogger(this.getClass());

    @FindBy(id="current-age")
    private WebElement currentAge;

    @FindBy(id="retirement-age")
    private WebElement retirementAge;

    @FindBy(id="current-income")
    private WebElement currentIncome;

    @FindBy(id="spouse-income")
    private WebElement spouseIncome;

    @FindBy(id="current-total-savings")
    private WebElement retirementSavingsBalance;

    @FindBy(id="current-annual-savings")
    private WebElement retirementSavingsAnnualBalance;

    @FindBy(id="savings-increase-rate")
    private WebElement savingsIncreaseRate;

    @FindBy(id="yes-social-benefits")
    private WebElement socialSecurityBenefit;

    @FindBy(id="married")
    private WebElement maritalStatus;

    @FindBy(id="social-security-override")
    private WebElement socialSecurityOverride;

    @FindBy(xpath = "//a[text()='Adjust default values']")
    private WebElement adjustDefaultValues;

    @FindBy(id = "additional-income")
    private WebElement additionalIncome;

    @FindBy(id = "retirement-duration")
    private WebElement retirementDuration;

    @FindBy(id = "include-inflation")
    private WebElement postRetirementInflation;

    @FindBy(id = "expected-inflation-rate")
    private WebElement expectedInflationRate;

    @FindBy(id = "retirement-annual-income")
    private WebElement retirementAnnualIncome;

    @FindBy(id = "pre-retirement-roi")
    private WebElement preRetirementInvestReturn;

    @FindBy(id = "post-retirement-roi")
    private WebElement postRetirementInvestReturn;

    @FindBy(xpath = "//button[text()='Save changes']")
    private WebElement saveChangesBtn;

    @FindBy(xpath = "//button[text()='Calculate']")
    private WebElement calculateBtn;

    @FindBy(xpath = "//button[text()='Clear form']")
    private WebElement clearFormBtn;

    @FindBy(xpath = "//*[@id='calculator-results-container']//h3")
    private WebElement resultText;

    public preRetirementCalculatorPage(WebDriver driver){
        this.driver = driver;
        functions = new CommonFunctions(driver);
        PageFactory.initElements(driver, this);
    }

    public String createPlan(HashMap<String, String> testData){
        try {
            functions.enterText(currentAge, testData.get("Current Age"));
            functions.enterText(retirementAge, testData.get("Retirement Age"));
            functions.enterText(currentIncome, testData.get("Current Annual Income"));
            functions.enterText(spouseIncome, testData.get("Spouse Annual Income"));
            functions.enterText(retirementSavingsBalance, testData.get("Current Reti Savings"));
            functions.enterText(retirementSavingsAnnualBalance, testData.get("Current Retirement Contri"));
            functions.enterText(savingsIncreaseRate, testData.get("Annual Retirement contri Increase"));
            functions.setValues(socialSecurityBenefit, "");
            functions.setValues(maritalStatus, "");
            functions.enterText(socialSecurityOverride, testData.get("Social Security Override"));
            functions.clickSafelyJS(adjustDefaultValues);
            functions.waitForElement(additionalIncome);
            functions.enterText(additionalIncome, testData.get("Additional/other income"));
            functions.setValues(retirementDuration, testData.get("No. of Yrs Retirement"));
            functions.setValues(postRetirementInflation, "");
            functions.enterText(expectedInflationRate, testData.get("Expected Inflation Rate"));
            functions.enterText(retirementAnnualIncome, testData.get("% Final Annual Income"));
            functions.enterText(preRetirementInvestReturn, testData.get("Pre Retirement Investment Return"));
            functions.enterText(postRetirementInvestReturn, testData.get("Post Retirement Investment Return"));
            functions.setValues(saveChangesBtn, "");
            functions.waitForElement(calculateBtn);
            functions.setValues(calculateBtn, "");
            functions.waitForElement(resultText);
        }catch (Exception e){
            log.info("Exception has occurred while filling the form");
            e.printStackTrace();
        }
        return resultText.getText();
    }
}
