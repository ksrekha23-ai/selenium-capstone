// ABOUTME: Page object for the SauceDemo checkout complete (order confirmation) page.
// ABOUTME: Verifies order success by checking the confirmation header and message text.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    public String getCompleteText() {
        return getText(completeText);
    }

    public boolean isOrderComplete() {
        return isDisplayed(completeHeader);
    }

    public void clickBackHome() {
        click(backHomeButton);
    }
}
