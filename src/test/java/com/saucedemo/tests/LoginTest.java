// ABOUTME: Tests for the SauceDemo login page covering valid, invalid, and edge-case scenarios.
// ABOUTME: Verifies authentication behavior including locked users, empty fields, and session handling.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.NavigationPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "login"})
    public void validLogin() {
        ExtentReportManager.createTest("validLogin");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Products page should be displayed after valid login");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "URL should contain 'inventory' after successful login");
    }

    @Test(groups = {"smoke", "login"})
    public void invalidPassword() {
        ExtentReportManager.createTest("invalidPassword");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error should mention mismatched credentials");
    }

    @Test(groups = {"smoke", "login"})
    public void emptyFields() {
        ExtentReportManager.createTest("emptyFields");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for empty fields");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error should indicate username is required");
    }

    @Test(groups = {"smoke", "login"})
    public void emptyPassword() {
        ExtentReportManager.createTest("emptyPassword");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.getStandardUsername());
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for empty password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "Error should indicate password is required");
    }

    @Test(groups = {"smoke", "login"})
    public void lockedOutUser() {
        ExtentReportManager.createTest("lockedOutUser");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getLockedUsername(), ConfigReader.getStandardPassword());

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for locked out user");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Error should mention the user is locked out");
    }

    @Test(groups = {"smoke", "login"})
    public void logoutAndBackButton() {
        ExtentReportManager.createTest("logoutAndBackButton");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Should be on products page after login");

        NavigationPage navigationPage = new NavigationPage(driver);
        navigationPage.openMenu();
        navigationPage.clickLogout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should be back on login page after logout");

        driver.navigate().back();

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Should not be able to access inventory page after logout via back button");
    }
}
