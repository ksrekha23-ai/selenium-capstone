// ABOUTME: Page object for the SauceDemo checkout step two (overview) page.
// ABOUTME: Provides access to order summary, item total, tax, grand total, and finish action.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutStepTwoPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
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

    public double getSubtotal() {
        String text = getText(subtotalLabel);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTax() {
        String text = getText(taxLabel);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTotal() {
        String text = getText(totalLabel);
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public void clickFinish() {
        click(finishButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }
}
