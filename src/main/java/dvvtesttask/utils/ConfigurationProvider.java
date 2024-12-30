package dvvtesttask.utils;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationProvider {

    public static Configuration getConfiguration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }
}
