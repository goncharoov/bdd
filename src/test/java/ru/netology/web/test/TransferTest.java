package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class TransferTest {
    DashboardPage dashboardPage;
    String numberFirstCard = String.valueOf(DataHelper.getFirstCard().getNumber());
    String numberSecondCard = String.valueOf(DataHelper.getSecondCard().getNumber());

    @BeforeEach
    void setUpAll() {
//        open("http://localhost:9999");
//        LoginPage loginPage = new LoginPage();
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromFirstCard() {
        val dataCardFirst = DataHelper.getFirstCard();
        val transferPage = dashboardPage.selectCard(numberSecondCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardFirst, 5000);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(5000, balanceAfterTransferFirstCard);
        assertEquals(15000, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldTransferFromSecondCard() {
        val dataCardSecond = DataHelper.getSecondCard();
        val transferPage = dashboardPage.selectCard(numberFirstCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardSecond, 5000);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(15000, balanceAfterTransferFirstCard);
        assertEquals(5000, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldTransferZeroFromFirstCard() {
        val dataCardFirst = DataHelper.getFirstCard();
        val transferPage = dashboardPage.selectCard(numberSecondCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardFirst, 0);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(10000, balanceAfterTransferFirstCard);
        assertEquals(10000, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldTransferZeroFromSecondCard() {
        val dataCardSecond = DataHelper.getSecondCard();
        val transferPage = dashboardPage.selectCard(numberFirstCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardSecond, 0);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(10000, balanceAfterTransferFirstCard);
        assertEquals(10000, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldTransferMaximumFromFirstCard() {
        val dataCardFirst = DataHelper.getFirstCard();
        val transferPage = dashboardPage.selectCard(numberSecondCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardFirst, 10000);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(20000, balanceAfterTransferSecondCard);
        assertEquals(0, balanceAfterTransferFirstCard);
    }

    @Test
    void shouldTransferMaximumFromSecondCard() {
        val dataCardSecond = DataHelper.getSecondCard();
        val transferPage = dashboardPage.selectCard(numberFirstCard);
        transferPage.transferMoneyFromCardToAnotherCard(dataCardSecond, 10000);
        $(withText("Ваши карты")).shouldBe(visible);
        int balanceAfterTransferFirstCard = dashboardPage.getBalanceCard(numberFirstCard);
        int balanceAfterTransferSecondCard = dashboardPage.getBalanceCard(numberSecondCard);
        assertEquals(0, balanceAfterTransferSecondCard);
        assertEquals(20000, balanceAfterTransferFirstCard);
    }

}

