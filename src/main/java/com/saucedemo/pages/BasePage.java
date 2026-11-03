// ABOUTME: Base class for all page objects providing common interaction methods.
// ABOUTME: Wraps WebDriver operations with explicit waits for reliable element interaction.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitHelper waitHelper;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    protected void click(By locator) {
        waitHelper.waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitHelper.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitHelper.waitForVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitHelper.waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> findElements(By locator) {
        return waitHelper.waitForAllVisible(locator);
    }

    protected void selectDropdownByValue(By locator, String value) {
        new org.openqa.selenium.support.ui.Select(
                waitHelper.waitForVisible(locator)).selectByValue(value);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
