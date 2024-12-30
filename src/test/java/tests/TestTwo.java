package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class TestTwo extends BaseTest {


    @Test
    @Description("User login and consequent logout")
    @Feature("Authentication")
    public void testSuccessfulLoginLogout() {

        LoginPage loginPage = new LoginPage().open().login("problem_user", "secret_sauce");
        HomePage homePage = new HomePage();
        Assertions.assertThat(homePage.isOpened()).withFailMessage("Home page was not opened").isTrue();
        homePage.logOut();
        Assertions.assertThat(loginPage.isOpened()).withFailMessage("User was not logged out").isTrue();
    }

    @Test
    @Description("User login with locked User")
    @Feature("Authentication")
    public void testLoginWithLockedUser() {
        String expectedWarning = "Epic sadface: Sorry, this user has been locked out.";
        LoginPage loginPage = new LoginPage().open().login("locked_out_user", "secret_sauce");
        Assertions.assertThat(loginPage.isOpened()).withFailMessage("Home page was not opened").isTrue();
        Assertions.assertThat(LoginPage.Locators.epicSadFaceError.get().getText())
                .withFailMessage("Expected warning didn't appear {}",expectedWarning)
                .containsIgnoringCase(expectedWarning);
    }
}
