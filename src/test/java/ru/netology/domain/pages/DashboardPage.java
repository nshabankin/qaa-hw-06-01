package ru.netology.domain.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    // Make card elements collection
    private final ElementsCollection cards = $$(".list__item div[data-test-id]");

    // Get card item text by index in the collection
    public int getCardBalance(int index) {
        String cardText = cards.get(index).shouldBe(visible).getText();
        return extractBalance(cardText);
    }

    // Select card by index in the collection
    public TransferPage selectDestinationCard(int index) {
        SelenideElement card = cards.get(index).shouldBe(visible);
        card.$("[data-test-id='action-deposit']").click();
        // Add debug message
        System.out.println("Card selected for transfer.");
        return new TransferPage();
    }

    // Extract balance from the item text
    private int extractBalance(String text) {
        String balancePart = text.split(", баланс: ")[1].split(" ")[0];
        // Add debug message
        System.out.println("Parsed balance: " + balancePart);
        return Integer.parseInt(balancePart);
    }
}