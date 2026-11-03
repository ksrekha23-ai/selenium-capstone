// ABOUTME: Base test class providing shared browser setup, teardown, and reporting hooks.
// ABOUTME: All test classes extend this to get automatic browser management and failure screenshots.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.ScreenshotHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ExtentReportManager.initReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupBrowser() {
        String browser = ConfigReader.getBrowser().toLowerCase();

        switch (browser) {
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                driver = new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                driver = new EdgeDriver(options);
            }
            default -> {
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getPageLoadTimeoutSeconds()));
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (ExtentReportManager.getTest() != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                if (driver != null) {
                    String screenshotPath = ScreenshotHelper.capture(driver, result.getName());
                    ExtentReportManager.getTest().fail("Test failed: " + result.getThrowable())
                            .addScreenCaptureFromPath(screenshotPath);
                } else {
                    ExtentReportManager.getTest().fail("Test failed: " + result.getThrowable());
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                ExtentReportManager.getTest().pass("Test passed");
            } else {
                ExtentReportManager.getTest().skip("Test skipped: " + result.getThrowable());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentReportManager.flushReport();
    }
}
