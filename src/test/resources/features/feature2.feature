Feature: F2. Authentication2

  Background:
    Given I open my website page

  Scenario: Scenario1. Login with locked user
    When I try to login with credentials: username "locked_out_user" and password "secret_sauce"
    Then Home page is not opened
    And Following login warning appears "Epic sadface: Sorry, this user has been locked out."

  Scenario Outline: Scenario1. Login with valid.invalid credentials
    When I try to login with credentials: username "<username>" and password "<password>"
    Then Login is "<isSuccessful>"
    Examples:
      | username      | password     | isSuccessful |
      | locked_out_user  | secret_sauce | false        |
      | standard_user | secret_sauce | true         |