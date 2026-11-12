// ABOUTME: Page object for the SauceDemo sidebar navigation menu.
// ABOUTME: Handles hamburger menu interactions including logout, reset, and navigation links.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationPage extends BasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetLink;

    @FindBy(className = "bm-menu-wrap")
    private WebElement menuWrap;

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void openMenu() {
        click(menuButton);
        waitHelper.waitFor(d -> {
            String transform = menuWrap.getCssValue("transform");
            return "none".equals(transform) || transform.matches(".*,\\s*0,\\s*0\\)$");
        });
    }

    public void closeMenu() {
        click(closeMenuButton);
    }

    public boolean isMenuOpen() {
        try {
            String ariaHidden = waitHelper.waitForVisible(menuWrap)
                    .getAttribute("aria-hidden");
            return "false".equals(ariaHidden);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAllItems() {
        click(allItemsLink);
    }

    public void clickAbout() {
        click(aboutLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickResetAppState() {
        click(resetLink);
    }
}
