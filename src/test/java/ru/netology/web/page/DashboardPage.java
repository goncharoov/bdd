package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id = dashboard]");
    private SelenideElement firstCard = $("[data-test-id = '92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement secondCard = $("[data-test-id = '0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private String numberFirstCard = String.valueOf(DataHelper.getFirstCard().getNumber());
    private String numberSecondCard = String.valueOf(DataHelper.getSecondCard().getNumber());

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage selectCard(String numberCard) {
        if (numberCard == numberFirstCard) {
            firstCard.$(".button").click();
        }
        if (numberCard == numberSecondCard) {
            secondCard.$(".button").click();
        }
        return new TransferPage();
    }

    public int getBalanceCard(String numberCard) {
        String text = "";
        if (numberCard == numberFirstCard) {
            text = firstCard.getText();
        }
        if (numberCard == numberSecondCard) {
            text = secondCard.getText();
        }
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}