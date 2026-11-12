// ABOUTME: Tests for the SauceDemo products listing page covering count and sort behavior.
// ABOUTME: Verifies product display count and correct ordering for all four sort options.
package com.saucedemo.tests;

import com.google.gson.JsonObject;
import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductListingTest extends BaseTest {

    private static final JsonObject TEST_DATA = TestDataReader.load("product-listing-test-data.json");

    private void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
    }

    @Test(groups = {"regression", "products"})
    public void verifyProductCount() {
        ExtentReportManager.createTest("verifyProductCount");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertEquals(productsPage.getProductCount(),
                TestDataReader.getInt(TEST_DATA, "expectedProductCount"),
                "Products page should display the expected number of products");
    }

    @Test(groups = {"regression", "products"})
    public void unauthenticatedUserCannotOpenProductsPage() {
        ExtentReportManager.createTest("unauthenticatedUserCannotOpenProductsPage");
        driver.get(ConfigReader.getBaseUrl()
                + TestDataReader.getString(TEST_DATA, "restrictedPaths.inventoryPage"));

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Unauthenticated users should be redirected to the login page");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Unauthenticated users should not remain on the products page");
    }

    @Test(groups = {"regression", "products"})
    public void sortByNameAZ() {
        ExtentReportManager.createTest("sortByNameAZ");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy(TestDataReader.getString(TEST_DATA, "sortOptions.nameAZ"));

        List<String> names = productsPage.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0,
                    "Products should be sorted A-Z: '" + names.get(i) + "' should come before '" + names.get(i + 1) + "'");
        }
    }

    @Test(groups = {"regression", "products"})
    public void sortByNameZA() {
        ExtentReportManager.createTest("sortByNameZA");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy(TestDataReader.getString(TEST_DATA, "sortOptions.nameZA"));

        List<String> names = productsPage.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(names.get(i).compareToIgnoreCase(names.get(i + 1)) >= 0,
                    "Products should be sorted Z-A: '" + names.get(i) + "' should come after '" + names.get(i + 1) + "'");
        }
    }

    @Test(groups = {"regression", "products"})
    public void sortByPriceLowToHigh() {
        ExtentReportManager.createTest("sortByPriceLowToHigh");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy(TestDataReader.getString(TEST_DATA, "sortOptions.priceLowHigh"));

        List<Double> prices = productsPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Prices should be sorted low to high: $" + prices.get(i) + " should be <= $" + prices.get(i + 1));
        }
    }

    @Test(groups = {"regression", "products"})
    public void sortByPriceHighToLow() {
        ExtentReportManager.createTest("sortByPriceHighToLow");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy(TestDataReader.getString(TEST_DATA, "sortOptions.priceHighLow"));

        List<Double> prices = productsPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i + 1),
                    "Prices should be sorted high to low: $" + prices.get(i) + " should be >= $" + prices.get(i + 1));
        }
    }
}
