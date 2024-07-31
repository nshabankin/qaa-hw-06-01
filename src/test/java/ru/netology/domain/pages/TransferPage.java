package ru.netology.domain.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    public DashboardPage makeTransfer(String cardNumber, int amount) {
        $("[data-test-id='from'] input").setValue(cardNumber).shouldBe(visible);
        $("[data-test-id='amount'] input").setValue(String.valueOf(amount)).shouldBe(visible);
        $("[data-test-id='action-transfer']").click();
        // Add debug message
        System.out.println("Transfer button clicked.");
        return new DashboardPage();
    }
}