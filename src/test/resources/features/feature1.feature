@Smoke
Feature: F1. Authentication

  Background:
    Given I open my website page

  Scenario: Scenario1. Login with default user
    When I try to login with main user
    Then Home page is opened

  Scenario: Scenario2. Login with default user and logout
    When I try to login with main user
    Then Home page is opened
    And I click logout
    Then User is redirected to login page

