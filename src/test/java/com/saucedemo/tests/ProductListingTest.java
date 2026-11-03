// ABOUTME: Tests for the SauceDemo products listing page covering count and sort behavior.
// ABOUTME: Verifies product display count and correct ordering for all four sort options.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductListingTest extends BaseTest {

    private void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
    }

    @Test(groups = {"regression", "products"})
    public void verifyProductCount() {
        ExtentReportManager.createTest("verifyProductCount");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Products page should display exactly 6 products");
    }

    @Test(groups = {"regression", "products"})
    public void sortByNameAZ() {
        ExtentReportManager.createTest("sortByNameAZ");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy("az");

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
        productsPage.sortBy("za");

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
        productsPage.sortBy("lohi");

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
        productsPage.sortBy("hilo");

        List<Double> prices = productsPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i + 1),
                    "Prices should be sorted high to low: $" + prices.get(i) + " should be >= $" + prices.get(i + 1));
        }
    }
}
