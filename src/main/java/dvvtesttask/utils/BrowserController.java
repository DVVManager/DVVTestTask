package dvvtesttask.utils;

import com.microsoft.playwright.*;

import java.util.List;
import java.util.Optional;

public class BrowserController {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> mainPage = new ThreadLocal<>();
    private static ThreadLocal<List<Page>> pageList = new ThreadLocal<>();
    // http://192.168.49.2:31338
    // http://192.168.49.2:32722
    // http://127.0.0.1:54430
    // http://127.0.0.1:54431

    //moon            LoadBalancer   10.96.139.240   <pending>     4444:31788/TCP,8080:32567/TCP

    public static void initBrowser(String browserType, boolean isHeadless) {
        setupBrowser(browserType, isHeadless,true);
        browserContext.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(null).setIgnoreHTTPSErrors(true)));
        Page page = getBrowserContext().newPage();
        page.setDefaultTimeout(ConfigurationProvider.getConfiguration().pauseTimeLow());
        page.setDefaultNavigationTimeout(ConfigurationProvider.getConfiguration().pauseTimeLow());
        mainPage.set(page);
        pageList.set(List.of(page));
    }

    private static void setupBrowser(String browserType, boolean isHeadless, boolean isRemote) {
        //System.setProperty("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
        playwright.set(Playwright.create());
        if(isRemote){
            browser.set(playwright.get().chromium().connect("ws://127.0.0.1:4444/playwright/chromium?headless=false&enableVNC=true"));
        }else{
            switch (BrowserOption.valueOf(browserType.toUpperCase())) {
                case CHROME -> browser.set(BrowserOption.CHROME.getBrowser(isHeadless));
                case EDGE -> browser.set(BrowserOption.EDGE.getBrowser(isHeadless));
                default -> browser.set(BrowserOption.FIREFOX.getBrowser(isHeadless));
            }
        }
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    public static Page getPage() {
        return mainPage.get();
    }

    public static List<Page> getPageList() {
        return pageList.get();
    }

    public static void closeBrowser() {
        pageList.get().forEach(p->Optional.ofNullable(p).ifPresent(Page::close));
        Optional.ofNullable(getBrowserContext()).ifPresent(BrowserContext::close);
        Optional.ofNullable(browser.get()).ifPresent(Browser::close);
        mainPage.remove();
        pageList.remove();
        browser.remove();
        browserContext.remove();
        playwright.remove();
    }

    private enum BrowserOption {
        CHROME("chrome") {
            @Override
            public Browser getBrowser(boolean isHeadless) {
                return playwright.get().chromium().launch(getLaunchOptions(isHeadless).setChannel(getType()));
            }
        },

        EDGE("msedge") {
            @Override
            public Browser getBrowser(boolean isHeadless) {
                return playwright.get().chromium().launch(getLaunchOptions(isHeadless).setChannel(getType()));
            }
        },

        FIREFOX("firefox") {
            @Override
            public Browser getBrowser(boolean isHeadless) {
                return playwright.get().firefox().launch(getLaunchOptions(isHeadless));
            }
        };

        BrowserOption(String browserType) {
            this.type = browserType;
        }

        private final String type;

        public String getType() {
            return this.type;
        }

        public abstract Browser getBrowser(boolean isHeadless);
        public BrowserType.LaunchOptions getLaunchOptions(boolean isHeadless){
            return new BrowserType.LaunchOptions()
                    .setHeadless(isHeadless)
                    .setSlowMo(ConfigurationProvider.getConfiguration().delay())
                    .setArgs(List.of("--start-maximized"));
        }
    }
}
