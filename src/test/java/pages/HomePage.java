package pages;

import dvvtesttask.elements.AbstractLocators;
import dvvtesttask.elements.Button;
import dvvtesttask.elements.LocatorHolder;
import dvvtesttask.utils.ConfigurationProvider;
import io.qameta.allure.Step;

public class HomePage extends BasePage implements Navigational {

    public HomePage() {
        super(Locators.class);
    }

    @Step("Verify if Main page is opened")
    @Override
    public boolean isOpened() {
        return mainPage().url().equalsIgnoreCase(ConfigurationProvider.getConfiguration().mainPageUrl());
    }

    @Step("Logout user")
    public void logOut() {
        selectBurgerAction("Logout");
    }

    @Step("Open {burgerActionTextValue} from left menu")
    public void selectBurgerAction(String burgerActionTextValue) {
        Locators.burgerButton.get().click();
        mainPage().getByText(burgerActionTextValue).click();
    }

    public static class Locators extends AbstractLocators {
        public static LocatorHolder<Button> burgerButton = new LocatorHolder<>("Burger Button", Button.class, ()->mainPage().getByText("Open Menu"));

    }
}
