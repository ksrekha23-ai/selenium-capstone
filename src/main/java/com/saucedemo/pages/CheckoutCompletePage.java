// ABOUTME: Page object for the SauceDemo checkout complete (order confirmation) page.
// ABOUTME: Verifies order success by checking the confirmation header and message text.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

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
