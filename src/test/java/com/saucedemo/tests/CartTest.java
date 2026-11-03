// ABOUTME: Tests for the SauceDemo shopping cart functionality.
// ABOUTME: Covers adding, removing, verifying items, and continue shopping navigation.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    private ProductsPage loginAndGoToProducts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());
        return new ProductsPage(driver);
    }

    @Test(groups = {"regression", "cart"})
    public void addSingleItem() {
        ExtentReportManager.createTest("addSingleItem");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain exactly 1 item");

        List<String> names = cartPage.getItemNames();
        Assert.assertTrue(names.contains("Sauce Labs Backpack"),
                "Cart should contain Sauce Labs Backpack");
    }

    @Test(groups = {"regression", "cart"})
    public void addMultipleItems() {
        ExtentReportManager.createTest("addMultipleItems");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.addProductToCartByName("Sauce Labs Bike Light");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should contain exactly 2 items");
    }

    @Test(groups = {"regression", "cart"})
    public void removeItemFromCart() {
        ExtentReportManager.createTest("removeItemFromCart");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item before removal");

        cartPage.removeItemByName("Sauce Labs Backpack");
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing the item");
    }

    @Test(groups = {"regression", "cart"})
    public void verifyItemDetailsInCart() {
        ExtentReportManager.createTest("verifyItemDetailsInCart");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        List<String> names = cartPage.getItemNames();
        Assert.assertTrue(names.get(0).contains("Backpack"),
                "First item name should contain 'Backpack'");

        List<String> prices = cartPage.getItemPrices();
        Assert.assertEquals(prices.get(0), "$29.99", "Backpack price should be $29.99");
    }

    @Test(groups = {"regression", "cart"})
    public void continueShopping() {
        ExtentReportManager.createTest("continueShopping");
        ProductsPage productsPage = loginAndGoToProducts();

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickContinueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "Should navigate back to products page");
    }
}
