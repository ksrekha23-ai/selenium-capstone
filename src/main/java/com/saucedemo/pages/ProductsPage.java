// ABOUTME: Page object for the SauceDemo products listing page.
// ABOUTME: Handles product browsing, sorting, adding to cart, and navigating to product details.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By inventoryItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnProductsPage() {
        return getText(pageTitle).equals("Products");
    }

    public int getProductCount() {
        return findElements(inventoryItems).size();
    }

    public List<String> getProductNames() {
        return findElements(productNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return findElements(productPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public void sortBy(String value) {
        selectDropdownByValue(sortDropdown, value);
    }

    public void addProductToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    public void removeProductFromCartByName(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    public void clickProductByName(String productName) {
        List<WebElement> names = findElements(this.productNames);
        for (WebElement name : names) {
            if (name.getText().equals(productName)) {
                name.click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToCart() {
        click(cartLink);
    }
}
