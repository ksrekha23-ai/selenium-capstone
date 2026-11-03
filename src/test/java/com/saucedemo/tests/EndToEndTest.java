// ABOUTME: End-to-end happy path test covering the full SauceDemo purchase flow.
// ABOUTME: Validates login, product selection, cart, checkout, and order confirmation in sequence.
package com.saucedemo.tests;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutStepOnePage;
import com.saucedemo.pages.CheckoutStepTwoPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    @Test(groups = {"smoke", "e2e"})
    public void fullHappyPath() {
        ExtentReportManager.createTest("fullHappyPath");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Should be on products page after login");

        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.addProductToCartByName("Sauce Labs Bike Light");
        Assert.assertEquals(productsPage.getCartItemCount(), 2,
                "Cart badge should show 2 after adding two items");

        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Cart should contain 2 items");

        cartPage.clickCheckout();

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        stepOne.fillShippingInfo("Test", "User", "12345");
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        Assert.assertEquals(stepTwo.getItemCount(), 2,
                "Checkout overview should show 2 items");

        double subtotal = stepTwo.getSubtotal();
        double tax = stepTwo.getTax();
        double total = stepTwo.getTotal();
        double expectedTotal = Math.round((subtotal + tax) * 100.0) / 100.0;
        Assert.assertEquals(total, expectedTotal,
                "Total should equal subtotal + tax");

        stepTwo.clickFinish();

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Assert.assertTrue(completePage.isOrderComplete(),
                "Order confirmation page should be displayed");
        Assert.assertEquals(completePage.getCompleteHeader(), "Thank you for your order!",
                "Confirmation header should show thank you message");

        completePage.clickBackHome();

        Assert.assertTrue(productsPage.isOnProductsPage(),
                "Should be back on products page after clicking Back Home");
    }
}
