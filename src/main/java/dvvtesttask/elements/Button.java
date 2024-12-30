package dvvtesttask.elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;

public class Button extends BaseElement implements TestDataFiller{

    public Button(Locator locator) {
        super(locator);
    }

    @Override
    public void fillTestData(JsonNode data) {
        if(data.asText().equalsIgnoreCase("click"))this.click();
    }

    public boolean click() {
        try {
            log.info("Clicking {}", this);
            locator.click();

            return true;
        } catch (PlaywrightException e) {
            log.warn("Failed to click {}", this);
            e.printStackTrace();
        }
        return false;
    }
}
