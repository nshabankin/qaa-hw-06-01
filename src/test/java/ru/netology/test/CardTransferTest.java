package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.domain.pages.DashboardPage;
import ru.netology.domain.pages.LoginPage;
import ru.netology.domain.pages.VerificationPage;
import ru.netology.domain.pages.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTransferTest {

    // User input test data
    String validLogin = "vasya";
    String validPassword = "qwerty123";
    String validVerificationCode = "12345";
    String card1Number = "5559 0000 0000 0001";
    String card2Number = "5559 0000 0000 0002";

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should transfer money from Card 1 to Card 2")
    void shouldTransferMoneyFromCard1ToCard2() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(0);
        int initialBalanceDestinationCard = dashboardPage.getCardBalance(1);

        int amountToTransfer = (int) (initialBalanceOriginCard * 0.25);

        TransferPage transferPage = dashboardPage.selectDestinationCard(1);
        dashboardPage = transferPage.makeTransfer(card1Number, amountToTransfer);

        int finalBalanceOriginCard = dashboardPage.getCardBalance(0);
        int finalBalanceDestinationCard = dashboardPage.getCardBalance(1);

        // Origin card final balance must be less by transfer amount
        assertEquals(initialBalanceOriginCard - amountToTransfer, finalBalanceOriginCard);

        // Destination card final balance must be more by transfer amount
        assertEquals(initialBalanceDestinationCard + amountToTransfer, finalBalanceDestinationCard);
    }

    @Test
    @DisplayName("Should transfer money from Card 2 to Card 1")
    void shouldTransferMoneyFromCard2ToCard1() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(1);
        int initialBalanceDestinationCard = dashboardPage.getCardBalance(0);

        int amountToTransfer = (int) (initialBalanceOriginCard * 0.25);

        TransferPage transferPage = dashboardPage.selectDestinationCard(0);
        dashboardPage = transferPage.makeTransfer(card2Number, amountToTransfer);

        int finalBalanceOriginCard = dashboardPage.getCardBalance(1);
        int finalBalanceDestinationCard = dashboardPage.getCardBalance(0);

        // Origin card final balance must be less by transfer amount
        assertEquals(initialBalanceOriginCard - amountToTransfer, finalBalanceOriginCard);

        // Destination card final balance must be more by transfer amount
        assertEquals(initialBalanceDestinationCard + amountToTransfer, finalBalanceDestinationCard);
    }

    @Test
    @DisplayName("Should not allow transfer of amount exceeding balance from Card 1 to Card 2")
    void shouldNotAllowTransferExceedingBalanceFromCard1ToCard2() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(0);
        int initialBalanceDestinationCard = dashboardPage.getCardBalance(1);

        int amountToTransfer = (int) (initialBalanceOriginCard + initialBalanceOriginCard * 0.25); // Exceeding balance

        TransferPage transferPage = dashboardPage.selectDestinationCard(1);
        dashboardPage = transferPage.makeTransfer(card1Number, amountToTransfer);

        // Transfer should fail, re-fetching balances
        int finalBalanceOriginCard = dashboardPage.getCardBalance(0);
        int finalBalanceDestinationCard = dashboardPage.getCardBalance(1);

        assertEquals(initialBalanceOriginCard, finalBalanceOriginCard, "Balance of Card 1 should remain unchanged");
        assertEquals(initialBalanceDestinationCard, finalBalanceDestinationCard, "Balance of Card 2 should remain unchanged");
    }

    @Test
    @DisplayName("Should not allow transfer of amount exceeding balance from Card 2 to Card 1")
    void shouldNotAllowTransferExceedingBalanceFromCard2ToCard1() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(1);
        int initialBalanceDestinationCard = dashboardPage.getCardBalance(0);

        int amountToTransfer = (int) (initialBalanceOriginCard + initialBalanceOriginCard * 0.25); // Exceeding balance

        TransferPage transferPage = dashboardPage.selectDestinationCard(0);
        dashboardPage = transferPage.makeTransfer(card2Number, amountToTransfer);

        // Transfer should fail, re-fetching balances
        int finalBalanceOriginCard = dashboardPage.getCardBalance(1);
        int finalBalanceDestinationCard = dashboardPage.getCardBalance(0);

        assertEquals(initialBalanceOriginCard, finalBalanceOriginCard, "Balance of Card 1 should remain unchanged");
        assertEquals(initialBalanceDestinationCard, finalBalanceDestinationCard, "Balance of Card 2 should remain unchanged");
    }

    @Test
    @DisplayName("Should not allow transfer from Card 1 to Card 1")
    void shouldNotAllowTransferFromCard1ToItself() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(0);

        int amountToTransfer = (int) (initialBalanceOriginCard * 0.25);

        TransferPage transferPage = dashboardPage.selectDestinationCard(0);
        dashboardPage = transferPage.makeTransfer(card1Number, amountToTransfer);

        // Transfer should fail, re-fetching balances
        int finalBalanceOriginCard = dashboardPage.getCardBalance(0);

        assertEquals(initialBalanceOriginCard, finalBalanceOriginCard, "Balance of Card 1 should remain unchanged");
    }

    @Test
    @DisplayName("Should not allow transfer from Card 2 to Card 2")
    void shouldNotAllowTransferFromCard2ToItself() {

        // PageObject actions
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        DashboardPage dashboardPage = verificationPage.validVerify(validVerificationCode);

        int initialBalanceOriginCard = dashboardPage.getCardBalance(1);

        int amountToTransfer = (int) (initialBalanceOriginCard * 0.25);

        TransferPage transferPage = dashboardPage.selectDestinationCard(1);
        dashboardPage = transferPage.makeTransfer(card2Number, amountToTransfer);

        // Transfer should fail, re-fetching balances
        int finalBalanceOriginCard = dashboardPage.getCardBalance(1);

        assertEquals(initialBalanceOriginCard, finalBalanceOriginCard, "Balance of Card 2 should remain unchanged");
    }
}
