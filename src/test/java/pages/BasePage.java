package pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import dvvtesttask.elements.AbstractLocators;
import dvvtesttask.elements.BaseElement;
import dvvtesttask.elements.LocatorHolder;
import dvvtesttask.elements.TestDataFiller;
import dvvtesttask.utils.BrowserController;
import dvvtesttask.utils.TestDataReader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);
    private final List<LocatorHolder> locatorHolderList;

    public BasePage(Class<? extends AbstractLocators> locatorsClass) {
        locatorHolderList = Collections.unmodifiableList(getLocatorHolders(locatorsClass));
    }

    protected static Page mainPage() {
        return BrowserController.getPage();
    }

    protected List<LocatorHolder> getLocatorHolders(Class<? extends AbstractLocators> clazz) {
        return Stream.of(clazz.getDeclaredFields()).filter((f) -> Modifier.isStatic(f.getModifiers())
                        && LocatorHolder.class.isAssignableFrom(f.getType()))
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        return (LocatorHolder) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to collect list of locators for class "
                                .concat(this.getClass().getSimpleName()));
                    }
                })
                .collect(Collectors.toList());
    }

    public List<LocatorHolder> getLocatorHolderList() {
        return this.locatorHolderList;
    }

    public boolean navigate(String url) {
        try {
            mainPage().navigate(url);
        } catch (PlaywrightException e) {
            log.error("Failed to navigate url {}. Error {}", url, e.getMessage());
        }
        return false;
    }

    public void fillPage(String testDataPath, String testDataSection) {
        JsonNode rootNode = TestDataReader.readTestData(testDataPath, testDataSection);
        List<LocatorHolder> pageLocators = this.getLocatorHolderList();
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            JsonNode value = field.getValue();
            log.info("Trying to find element with label {} and provide {}", key, value);
            LocatorHolder locatorHolder = pageLocators.stream().filter(l -> l.getLabel().equalsIgnoreCase(key))
                    .findFirst().orElseThrow(() -> new RuntimeException(String.format("There is no element in locator holders with label %s", key)));
            if(locatorHolder.get() instanceof TestDataFiller fillableElement)
                fillableElement.fillTestData(value);
        }
    }
}
