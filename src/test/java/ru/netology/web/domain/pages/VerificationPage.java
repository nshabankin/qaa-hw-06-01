package ru.netology.web.domain.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    public DashboardPage validVerify(String verificationCode) {
        $("[data-test-id='code'] input").setValue(verificationCode).shouldBe(visible);
        $("[data-test-id='action-verify']").click();
        return new DashboardPage();
    }
}
