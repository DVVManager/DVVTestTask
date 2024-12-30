package dvvtesttask.elements;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Locator;

public class StaticTextField extends BaseElement{

    public StaticTextField(Locator locator) {
        super(locator);
    }

    public String getValue(){
       return locator.inputValue();
    }

    public String getText(){
        return locator.innerText();
    }
}
