package tests;

import dvvtesttask.utils.BrowserController;
import dvvtesttask.utils.ConfigurationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        log.info("Browser '{}' initiation ", ConfigurationProvider.getConfiguration().browser());
        BrowserController.initBrowser(ConfigurationProvider.getConfiguration().browser(), ConfigurationProvider.getConfiguration().headless());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        log.info("Closing Browser '{}'", ConfigurationProvider.getConfiguration().browser());
        BrowserController.closeBrowser();
    }
}
