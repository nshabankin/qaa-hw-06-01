package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class CardNumberList {
    private String card1Number;
    private String card2Number;
  }

  public static CardNumberList getCardNumberList(AuthInfo authInfo) {
    return new CardNumberList("5559 0000 0000 0001", "5559 0000 0000 0002");
  }
}