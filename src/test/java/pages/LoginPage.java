package pages;

import dvvtesttask.elements.*;
import dvvtesttask.utils.ConfigurationProvider;
import io.qameta.allure.Step;

public class LoginPage extends BasePage implements Navigational {

    public LoginPage() {
        super(Locators.class);
    }

    @Step("Open login page")
    public LoginPage open() {
        navigate(ConfigurationProvider.getConfiguration().baseUrl());
        return this;
    }

    @Step("Verify if Login page is opened")
    @Override
    public boolean isOpened() {
        return mainPage().url().equalsIgnoreCase(ConfigurationProvider.getConfiguration().baseUrl());
    }

    @Step("Login with user {username}")
    public LoginPage login(String username, String password) {

        Locators.username.get().type(username);
        Locators.password.get().type(password);
        Locators.loginButton.get().click();
        Locators.loginButton.get().waitForDisappearance();
        //mainPage().waitForURL(ConfigurationProvider.getConfiguration().mainPageUrl());

        /*//With default Locator
        Locators.username.fill(username);
        Locators.password.fill(password);
        Locators.loginButton.click();*/

        /*//With custom elements
        new TextBox(Locators.username).type(username);
        new TextBox(Locators.password).type(password);
        new Button(Locators.loginButton).click()
        new Button(Locators.loginButton).waitForDisappearance();*/
        return this;
    }

    @Step("Login with default user")
    public LoginPage login(){
        return login(ConfigurationProvider.getConfiguration().username(),ConfigurationProvider.getConfiguration().password());
    }

    @Step("Login with default Test Data")
    public LoginPage loginWithDefaultTestData(){
        this.fillPage("testdata/Login.json", "TestData");
        return this;
    }


    public static class Locators extends AbstractLocators {
        public static LocatorHolder<TextBox> username = new LocatorHolder<>("Username", TextBox.class, () -> mainPage().locator("input#user-name"));
        public static LocatorHolder<TextBox> password = new LocatorHolder<>("Password", TextBox.class, () -> mainPage().getByPlaceholder("Password"));
        public static LocatorHolder<Button> loginButton = new LocatorHolder<>("Login", Button.class, () -> mainPage().locator("//input[@id='login-button']"));
        public static LocatorHolder<StaticTextField> epicSadFaceError = new LocatorHolder<>("Epic sadface", StaticTextField.class,
                () -> mainPage().locator("//div[contains(@class,'error-message-container')]"));

        /*public static LocatorHolder<TextBox> username = new LocatorHolder("Username", mainPage().locator("input#user-name"), TextBox.class);
        public static LocatorHolder<TextBox> password = new LocatorHolder("Password", mainPage().getByPlaceholder("Password"), TextBox.class);
        public static LocatorHolder<Button> loginButton = new LocatorHolder("Login", mainPage().locator("//input[@id='login-button']"), Button.class);*/

        /*public static Locator username = mainPage().locator("input#user-name");
        public static Locator password = mainPage().getByPlaceholder("Password");
        public static Locator loginButton = mainPage().locator("//input[@id='login-button']");*/
    }
}
