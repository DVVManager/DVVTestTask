package dvvtesttask.elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;

public class TextBox extends BaseElement implements TestDataFiller{

    public TextBox(Locator locator) {
        super(locator);
    }

    @Override
    public void fillTestData(JsonNode data) {
        this.type(data.asText());
    }

    public boolean type(String value) {
        try {
            log.info("Setting value {} to {}",value,this);
            locator.clear();
            locator.fill(value);

            return true;
        } catch (PlaywrightException e) {
            log.warn("Failed to set value {} to {}",value,this);
        }
        return false;
    }
}
