// ABOUTME: Page object for the SauceDemo shopping cart page.
// ABOUTME: Handles cart item verification, removal, and navigation to checkout or back to shopping.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return findElements(cartItems).size();
    }

    public List<String> getItemNames() {
        return findElements(itemNames).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getItemPrices() {
        return findElements(itemPrices).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getItemQuantity(int index) {
        return findElements(itemQuantities).get(index).getText();
    }

    public void removeItemByName(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(buttonId));
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    public boolean isCartEmpty() {
        try {
            return cartItems.isEmpty();
        } catch (Exception e) {
            return true;
        }
    }
}
