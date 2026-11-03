// ABOUTME: Page object for the SauceDemo sidebar navigation menu.
// ABOUTME: Handles hamburger menu interactions including logout, reset, and navigation links.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationPage extends BasePage {

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By closeMenuButton = By.id("react-burger-cross-btn");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By resetLink = By.id("reset_sidebar_link");
    private final By menuWrap = By.className("bm-menu-wrap");

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void openMenu() {
        click(menuButton);
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
