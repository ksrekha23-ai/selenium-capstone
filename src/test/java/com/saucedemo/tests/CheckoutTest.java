// ABOUTME: Tests for the SauceDemo checkout flow including form validation and order summary.
// ABOUTME: Covers complete checkout, missing field errors, order summary, and price calculations.
package com.saucedemo.tests;

import com.google.gson.JsonObject;
import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.*;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTest extends BaseTest {

    private static final JsonObject TEST_DATA = TestDataReader.load("checkout-test-data.json");

    private CheckoutStepOnePage loginAddItemAndGoToCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getStandardUsername(), ConfigReader.getStandardPassword());

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCartByName(
                TestDataReader.getString(TEST_DATA, "products.backpack"));
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        return new CheckoutStepOnePage(driver);
    }

    @Test(groups = {"regression", "checkout"})
    public void completeCheckoutFlow() {
        ExtentReportManager.createTest("completeCheckoutFlow");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.fillShippingInfo(
                TestDataReader.getString(TEST_DATA, "shippingInfo.firstName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.lastName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.zipCode"));
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        stepTwo.clickFinish();

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Assert.assertEquals(completePage.getCompleteHeader(),
                TestDataReader.getString(TEST_DATA, "confirmationHeader"),
                "Order confirmation header should match");
    }

    @Test(groups = {"regression", "checkout"})
    public void emptyFormSubmission() {
        ExtentReportManager.createTest("emptyFormSubmission");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.clickContinue();

        Assert.assertTrue(stepOne.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertEquals(stepOne.getErrorMessage(),
                TestDataReader.getString(TEST_DATA, "errorMessages.firstNameRequired"),
                "Error should indicate first name is required");
    }

    @Test(groups = {"regression", "checkout"})
    public void missingLastName() {
        ExtentReportManager.createTest("missingLastName");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.enterFirstName(
                TestDataReader.getString(TEST_DATA, "shippingInfo.firstName"));
        stepOne.clickContinue();

        Assert.assertTrue(stepOne.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertEquals(stepOne.getErrorMessage(),
                TestDataReader.getString(TEST_DATA, "errorMessages.lastNameRequired"),
                "Error should indicate last name is required");
    }

    @Test(groups = {"regression", "checkout"})
    public void missingZipCode() {
        ExtentReportManager.createTest("missingZipCode");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.enterFirstName(
                TestDataReader.getString(TEST_DATA, "shippingInfo.firstName"));
        stepOne.enterLastName(
                TestDataReader.getString(TEST_DATA, "shippingInfo.lastName"));
        stepOne.clickContinue();

        Assert.assertTrue(stepOne.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertEquals(stepOne.getErrorMessage(),
                TestDataReader.getString(TEST_DATA, "errorMessages.postalCodeRequired"),
                "Error should indicate postal code is required");
    }

    @Test(groups = {"regression", "checkout"})
    public void verifyOrderSummary() {
        ExtentReportManager.createTest("verifyOrderSummary");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.fillShippingInfo(
                TestDataReader.getString(TEST_DATA, "shippingInfo.firstName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.lastName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.zipCode"));
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        List<String> itemNames = stepTwo.getItemNames();

        Assert.assertEquals(itemNames.size(), 1, "Order summary should have 1 item");
        Assert.assertTrue(itemNames.get(0).contains("Backpack"),
                "Order summary should contain Sauce Labs Backpack");
    }

    @Test(groups = {"regression", "checkout"})
    public void priceTotalCalculation() {
        ExtentReportManager.createTest("priceTotalCalculation");
        CheckoutStepOnePage stepOne = loginAddItemAndGoToCheckout();

        stepOne.fillShippingInfo(
                TestDataReader.getString(TEST_DATA, "shippingInfo.firstName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.lastName"),
                TestDataReader.getString(TEST_DATA, "shippingInfo.zipCode"));
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        double subtotal = stepTwo.getSubtotal();
        double tax = stepTwo.getTax();
        double total = stepTwo.getTotal();

        Assert.assertEquals(total, subtotal + tax, 0.01,
                "Total should equal subtotal + tax");
    }
}
