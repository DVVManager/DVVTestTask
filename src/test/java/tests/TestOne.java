package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class TestOne extends BaseTest {

    @Test
    @Description("Authentication with valid credentials")
    @Feature("Authentication")
    public void testSuccessfulLogin() {
        new LoginPage().open().loginWithDefaultTestData();
        Assertions.assertThat(new HomePage().isOpened())
                .withFailMessage("Home page was not opened").isTrue();
    }
}
