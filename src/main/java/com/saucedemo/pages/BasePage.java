// ABOUTME: Base class for all page objects providing common interaction methods.
// ABOUTME: Wraps WebDriver operations with explicit waits for reliable element interaction.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitHelper waitHelper;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    protected void click(By locator) {
        waitHelper.waitForClickable(locator).click();
    }

    protected void click(WebElement element) {
        waitHelper.waitForClickable(element).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitHelper.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void type(WebElement element, String text) {
        WebElement visible = waitHelper.waitForVisible(element);
        visible.clear();
        visible.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitHelper.waitForVisible(locator).getText();
    }

    protected String getText(WebElement element) {
        return waitHelper.waitForVisible(element).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitHelper.waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return waitHelper.waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> findElements(By locator) {
        return waitHelper.waitForAllVisible(locator);
    }

    protected List<WebElement> findElements(List<WebElement> elements) {
        return waitHelper.waitForAllVisible(elements);
    }

    protected void selectDropdownByValue(By locator, String value) {
        new org.openqa.selenium.support.ui.Select(
                waitHelper.waitForVisible(locator)).selectByValue(value);
    }

    protected void selectDropdownByValue(WebElement element, String value) {
        new org.openqa.selenium.support.ui.Select(
                waitHelper.waitForVisible(element)).selectByValue(value);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
