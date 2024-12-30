package dvvtesttask.elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import dvvtesttask.utils.BrowserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BaseElement {
    Logger log = LoggerFactory.getLogger(BaseElement.class);

    protected Locator locator;

    public BaseElement(final Locator locator) {
        this.locator = locator;
    }

    public Page mainPage(){
        return BrowserController.getPage();
    }

    public String toString() {
        return "element of type: ".concat(this.getClass().getSimpleName()).concat(" located by : ".concat(this.locator.toString()));
    }

    public boolean isVisible() {
        boolean isVisible = false;
        try {
            log.info("Check {} being visible ",this);
            isVisible = locator.isVisible();
        } catch (PlaywrightException e) {
            log.warn("Failed to wait {} being visible ",this);
        }
        return isVisible;
    }

    public boolean isEnabled(){
        boolean isEnabled = false;
        try {
            log.info("Check {} being enabled ",this);
            isEnabled = locator.isEnabled();
        } catch (PlaywrightException e) {
            log.warn("Failed to wait {} being enabled ",this);
        }
        return isEnabled;
    }

    public boolean isDisabled(){
        boolean isDisabled = false;
        try {
            log.info("Check {} being disabled ",this);
            isDisabled = locator.isEnabled();
        } catch (PlaywrightException e) {
            log.warn("Failed to wait {} being disabled ",this);
        }
        return isDisabled;
    }

    public boolean waitForDisappearance() {
        try {
            log.info("Waiting {} to disappear ",this);
            locator.waitFor(new Locator.WaitForOptions().setTimeout(Duration.ofSeconds(1).toMillis()).setState(WaitForSelectorState.HIDDEN));
            return true;
        } catch(PlaywrightException e) {
            log.warn("Failed to wait {} to disappear ",this);

        }
        return false;
    }

    public boolean waitForAppearance() {
        try {
            log.info("Waiting {} to appear ",this);
            locator.waitFor(new Locator.WaitForOptions().setTimeout(Duration.ofSeconds(1).toMillis()).setState(WaitForSelectorState.VISIBLE));
            return true;
        } catch(PlaywrightException e) {
            log.warn("Failed to wait {} to appear ",this);
        }
        return false;
    }

    public String getAttribute(String attribute) {
        try {
            log.info("Fetching attribute {} of {} ",attribute,this);
            return locator.getAttribute(attribute);
        } catch (PlaywrightException e) {
            log.info("Failed to fetch attribute {} of {} ",attribute,this);
        }
        return "";
    }

    public void hoverElement() {
        try {
            log.info("Hover {} ",this);
            locator.hover();
        } catch (PlaywrightException e) {
            log.info("Failed to hover {} ",this);
        }
    }
}
