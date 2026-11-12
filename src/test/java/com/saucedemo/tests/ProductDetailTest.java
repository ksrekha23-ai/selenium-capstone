// ABOUTME: Tests for the SauceDemo product detail page covering navigation and data consistency.
// ABOUTME: Verifies detail page content matches listing data, add-to-cart works, and back navigation.
package com.saucedemo.tests;

import com.google.gson.JsonObject;
import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductDetailPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductDetailTest extends BaseTest {

    private static final JsonObject TEST_DATA = TestDataReader.load("product-detail-test-data.json");

    private void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
    }

    @Test(groups = {"regression", "products"})
    public void navigateToDetailPage() {
        ExtentReportManager.createTest("navigateToDetailPage");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        String firstProductName = productsPage.getProductNames().get(0);
        productsPage.clickProductByName(firstProductName);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        Assert.assertFalse(detailPage.getProductName().isEmpty(),
                "Product name should be displayed on detail page");
        Assert.assertTrue(driver.getCurrentUrl().contains(
                        TestDataReader.getString(TEST_DATA, "urlFragments.inventoryItem")),
                "URL should contain 'inventory-item' on detail page");
    }

    @Test(groups = {"regression", "products"})
    public void unauthenticatedUserCannotOpenProductDetailPage() {
        ExtentReportManager.createTest("unauthenticatedUserCannotOpenProductDetailPage");
        driver.get(ConfigReader.getBaseUrl()
                + TestDataReader.getString(TEST_DATA, "restrictedPaths.inventoryItemPage"));

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Unauthenticated users should be redirected to the login page");
        Assert.assertFalse(driver.getCurrentUrl().contains(
                        TestDataReader.getString(TEST_DATA, "urlFragments.inventoryItem")),
                "Unauthenticated users should not remain on the product detail page");
    }

    @Test(groups = {"regression", "products"})
    public void verifyDetailMatchesListing() {
        ExtentReportManager.createTest("verifyDetailMatchesListing");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        List<String> listingNames = productsPage.getProductNames();
        List<Double> listingPrices = productsPage.getProductPrices();

        String expectedName = listingNames.get(0);
        double expectedPrice = listingPrices.get(0);

        productsPage.clickProductByName(expectedName);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        Assert.assertEquals(detailPage.getProductName(), expectedName,
                "Product name on detail page should match listing");

        double detailPrice = Double.parseDouble(detailPage.getProductPrice().replace("$", ""));
        Assert.assertEquals(detailPrice, expectedPrice,
                "Product price on detail page should match listing");
    }

    @Test(groups = {"regression", "products"})
    public void addToCartFromDetailPage() {
        ExtentReportManager.createTest("addToCartFromDetailPage");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        String firstProductName = productsPage.getProductNames().get(0);
        productsPage.clickProductByName(firstProductName);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.addToCart();

        Assert.assertTrue(detailPage.isRemoveButtonDisplayed(),
                "Remove button should appear after adding item to cart from detail page");
    }

    @Test(groups = {"regression", "products"})
    public void backToProducts() {
        ExtentReportManager.createTest("backToProducts");
        loginAsStandardUser();

        ProductsPage productsPage = new ProductsPage(driver);
        String firstProductName = productsPage.getProductNames().get(0);
        productsPage.clickProductByName(firstProductName);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.goBackToProducts();

        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Should return to products page after clicking back button");
    }
}
