// ABOUTME: Tests for the SauceDemo sidebar navigation menu functionality.
// ABOUTME: Covers menu open/close, all navigation links, logout, and app state reset.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.NavigationPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
    }

    @Test(groups = {"regression", "navigation"})
    public void sidebarOpenAndClose() {
        ExtentReportManager.createTest("sidebarOpenAndClose");
        login();

        NavigationPage nav = new NavigationPage(driver);
        nav.openMenu();
        Assert.assertTrue(nav.isMenuOpen(), "Sidebar menu should be open after clicking the menu button");

        nav.closeMenu();
    }

    @Test(groups = {"regression", "navigation"})
    public void allItemsLink() {
        ExtentReportManager.createTest("allItemsLink");
        login();

        NavigationPage nav = new NavigationPage(driver);
        nav.openMenu();
        nav.clickAllItems();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Clicking 'All Items' should navigate to the products page");
    }

    @Test(groups = {"regression", "navigation"})
    public void aboutLink() {
        ExtentReportManager.createTest("aboutLink");
        login();

        NavigationPage nav = new NavigationPage(driver);
        nav.openMenu();
        nav.clickAbout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"),
                "Clicking 'About' should navigate to the Sauce Labs website");
    }

    @Test(groups = {"regression", "navigation"})
    public void logoutLink() {
        ExtentReportManager.createTest("logoutLink");
        login();

        NavigationPage nav = new NavigationPage(driver);
        nav.openMenu();
        nav.clickLogout();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Clicking 'Logout' should return to the login page");
    }

    @Test(groups = {"regression", "navigation"})
    public void resetAppState() {
        ExtentReportManager.createTest("resetAppState");
        login();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        Assert.assertEquals(productsPage.getCartItemCount(), 1,
                "Cart badge should show 1 after adding an item");

        NavigationPage nav = new NavigationPage(driver);
        nav.openMenu();
        nav.clickResetAppState();
        nav.closeMenu();

        Assert.assertEquals(productsPage.getCartItemCount(), 0,
                "Cart badge should be 0 after resetting app state");
    }
}
