// ABOUTME: Tests for the SauceDemo shopping cart functionality.
// ABOUTME: Covers adding, removing, verifying items, and continue shopping navigation.
package com.saucedemo.tests;

import com.google.gson.JsonObject;
import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    private static final JsonObject TEST_DATA = TestDataReader.load("cart-test-data.json");

    private ProductsPage loginAndGoToProducts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
        return new ProductsPage(driver);
    }

    @Test(groups = {"regression", "cart"})
    public void cartIsEmptyBeforeAddingItems() {
        ExtentReportManager.createTest("cartIsEmptyBeforeAddingItems");
        ProductsPage productsPage = loginAndGoToProducts();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty before any products are added");
    }

    @Test(groups = {"regression", "cart"})
    public void addSingleItem() {
        ExtentReportManager.createTest("addSingleItem");
        ProductsPage productsPage = loginAndGoToProducts();

        String backpackName = TestDataReader.getString(TEST_DATA, "products.backpack.name");
        productsPage.addProductToCartByName(backpackName);
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain exactly 1 item");

        List<String> names = cartPage.getItemNames();
        Assert.assertTrue(names.contains(backpackName),
                "Cart should contain " + backpackName);
    }

    @Test(groups = {"regression", "cart"})
    public void addMultipleItems() {
        ExtentReportManager.createTest("addMultipleItems");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName(
                TestDataReader.getString(TEST_DATA, "products.backpack.name"));
        productsPage.addProductToCartByName(
                TestDataReader.getString(TEST_DATA, "products.bikeLight.name"));
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should contain exactly 2 items");
    }

    @Test(groups = {"regression", "cart"})
    public void removeItemFromCart() {
        ExtentReportManager.createTest("removeItemFromCart");
        ProductsPage productsPage = loginAndGoToProducts();

        String backpackName = TestDataReader.getString(TEST_DATA, "products.backpack.name");
        productsPage.addProductToCartByName(backpackName);
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item before removal");

        cartPage.removeItemByName(backpackName);
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing the item");
    }

    @Test(groups = {"regression", "cart"})
    public void verifyItemDetailsInCart() {
        ExtentReportManager.createTest("verifyItemDetailsInCart");
        ProductsPage productsPage = loginAndGoToProducts();

        String backpackName = TestDataReader.getString(TEST_DATA, "products.backpack.name");
        String backpackPrice = TestDataReader.getString(TEST_DATA, "products.backpack.price");
        productsPage.addProductToCartByName(backpackName);
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        List<String> names = cartPage.getItemNames();
        Assert.assertTrue(names.get(0).contains("Backpack"),
                "First item name should contain 'Backpack'");

        List<String> prices = cartPage.getItemPrices();
        Assert.assertEquals(prices.get(0), backpackPrice,
                "Backpack price should be " + backpackPrice);
    }

    @Test(groups = {"regression", "cart"})
    public void continueShopping() {
        ExtentReportManager.createTest("continueShopping");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName(
                TestDataReader.getString(TEST_DATA, "products.backpack.name"));
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickContinueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains(
                        TestDataReader.getString(TEST_DATA, "urlFragments.inventoryPage")),
                "Should navigate back to products page");
    }
}
