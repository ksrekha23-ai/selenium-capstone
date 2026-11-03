// ABOUTME: Page object for the SauceDemo individual product detail page.
// ABOUTME: Provides access to product name, price, description, and add-to-cart from detail view.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends BasePage {

    private final By productName = By.className("inventory_details_name");
    private final By productPrice = By.className("inventory_details_price");
    private final By productDescription = By.className("inventory_details_desc");
    private final By addToCartButton = By.cssSelector("[data-test^='add-to-cart']");
    private final By removeButton = By.cssSelector("[data-test^='remove']");
    private final By backButton = By.id("back-to-products");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public String getProductDescription() {
        return getText(productDescription);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public boolean isRemoveButtonDisplayed() {
        return isDisplayed(removeButton);
    }

    public void goBackToProducts() {
        click(backButton);
    }
}
