package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

public class LocalBrowserExec {

    private static WebDriver driver;

    public static WebDriver LocalDriver(String browser){

        DesiredCapabilities desiredCapabilities;

        switch (browser.toLowerCase(Locale.ENGLISH)) {

            case "chrome" :
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setBrowserName(browser);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--disable-notifications");
                options.setExperimentalOption("excludeSwitches",
                        Collections.singletonList("enable-automation"));
                options.setExperimentalOption("excludeSwitches",
                        Arrays.asList("disable-popup-blocking"));

               Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("download.prompt_for_download", false);
                prefs.put("safebrowsing.enabled", true);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                options.merge(desiredCapabilities);
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setBrowserName(browser);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("dom.webnotifications.enable",false);
                firefoxOptions.setProfile(firefoxProfile);
                firefoxOptions.merge(desiredCapabilities);
                driver = new FirefoxDriver(firefoxOptions);
                break;
        }
        return driver;
    }
}