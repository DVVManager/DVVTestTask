package dvvtesttask.utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:project.properties",
        "classpath:browser.properties"})
public interface Configuration extends Config {

    String browser();
    Boolean headless();
    int timeout();
    @Key("action.delay")
    int delay();
    @Key("url.base")
    String baseUrl();
    @Key("url.mainpage")
    String mainPageUrl();
    @Key("mainuser.username")
    String username();
    @Key("mainuser.password")
    String password();
    @Key("pause.low")
    int pauseTimeLow();
}
