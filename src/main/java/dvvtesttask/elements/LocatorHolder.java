package dvvtesttask.elements;

import com.microsoft.playwright.Locator;
import dvvtesttask.utils.BrowserController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

public class LocatorHolder<T extends BaseElement> {

    private final String label;
    private Locator locator;
    private final Class<T> customClass;
    private Supplier<Locator> locatorProvider;

    public LocatorHolder(String label, Locator locator, Class<T> customClass) {
        this.label = label;
        this.locator = locator;
        this.customClass = customClass;
    }

    public LocatorHolder(String label, Class<T> customClass, Supplier<Locator> locatorProvider) {
        this.label = label;
        this.locatorProvider = locatorProvider;
        //this.locator = locatorProvider.get();
        this.customClass = customClass;
    }

    public LocatorHolder(String label, Class<T> customClass) {
        this(label, null, customClass);
    }

    private Locator getLocator() {
        return locator;
    }

    public String getLabel() {
        return label;
    }

    public Locator locator() {
        //return Optional.ofNullable(this.getLocator()).orElseGet(()->BrowserController.getPage().getByLabel(this.label));
        Locator locator = Optional.ofNullable(this.locatorProvider).map(Supplier::get)
                .orElseGet(()->BrowserController.getPage().getByLabel(this.label));
        return locator;
    }

    public T get() {
        try {
            return customClass.getDeclaredConstructor(Locator.class).newInstance(this.locator());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Something goes wrong on custom web element instantiation -> ".concat(e.toString()));
        }
    }
}
