package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;
import org.assertj.core.api.Assertions;
import pages.HomePage;
import pages.LoginPage;

public class LoginSteps {
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();

    @Given("I open my website page")
    public void openLoginPage() {
        loginPage.open();
    }

    @When("I try to login with main user")
    public void loginWithMainUser() {
        loginPage.loginWithDefaultTestData();
    }

    @Then("Home page is opened")
    public void homePageIsOpened() {
        Assertions.assertThat(new HomePage().isOpened())
                .withFailMessage("Home page was not opened").isTrue();
    }

    @Then("User is redirected to login page")
    public void checkUserIsOnLoginPage() {
        Assertions.assertThat(loginPage.isOpened()).withFailMessage("User was not logged out").isTrue();
    }

    @And("I click logout")
    public void logOutApp() {
        homePage.logOut();

    }

    @When("I try to login with credentials: username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        loginPage.login(username,password);
    }


    @Then("Home page is not opened")
    public void checkHomePageIsNotOpened() {
        Assertions.assertThat(new HomePage().isOpened())
                .withFailMessage("Home page was opened").isFalse();
    }

    @And("Following login warning appears {string}")
    public void followingLoginWarningAppears(String warningText) {
        Assertions.assertThat(LoginPage.Locators.epicSadFaceError.get().getText())
                .withFailMessage("Expected warning didn't appear {}",warningText)
                .containsIgnoringCase(warningText);
    }


    @Then("Login is {string}")
    public void loginIsSuccessful(String isSuccessful) {
        if(Boolean.parseBoolean(isSuccessful)){
            Assertions.assertThat(new HomePage().isOpened())
                    .withFailMessage("Home page was not opened").isTrue();
        }else {
            String expectedWarning = "Epic sadface: Sorry, this user has been locked out.";
            Assertions.assertThat(LoginPage.Locators.epicSadFaceError.get().getText())
                    .withFailMessage("Expected warning didn't appear {}",expectedWarning)
                    .containsIgnoringCase(expectedWarning);
        }
    }
}
