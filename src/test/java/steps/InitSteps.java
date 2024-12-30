package steps;

import dvvtesttask.utils.BrowserController;
import dvvtesttask.utils.ConfigurationProvider;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitSteps {

    protected static final Logger log = LoggerFactory.getLogger(InitSteps.class);

    @Before
    public void beforeTest(){
        log.info("Browser '{}' initiation ",ConfigurationProvider.getConfiguration().browser());
        BrowserController.initBrowser(ConfigurationProvider.getConfiguration().browser(),ConfigurationProvider.getConfiguration().headless());
    }

    @After
    public void afterTest(){
        log.info("Closing Browser '{}'",ConfigurationProvider.getConfiguration().browser());
        BrowserController.closeBrowser();
    }
}
