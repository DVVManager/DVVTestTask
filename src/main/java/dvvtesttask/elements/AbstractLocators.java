package dvvtesttask.elements;


public abstract class AbstractLocators {
    /*protected static LocatorHolder define(String label, Locator locator) {
        return new LocatorHolder(label, locator);
    }

    protected static LocatorHolder define(String label) {
        return new LocatorHolder(label);
    }

    public static List<LocatorHolder> getLocatorHolders(Class<? extends AbstractLocators> clazz) {
        return Stream.of(clazz.getDeclaredFields()).filter((f) -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(LocatorHolder.class))
                .map(f -> {
                    try {
                        return (LocatorHolder) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }*/
}
