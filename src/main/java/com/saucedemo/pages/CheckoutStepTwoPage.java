// ABOUTME: Page object for the SauceDemo checkout step two (overview) page.
// ABOUTME: Provides access to order summary, item total, tax, grand total, and finish action.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutStepTwoPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

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
