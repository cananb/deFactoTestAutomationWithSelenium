package methods;

import driver.DriverExec;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class JsMethods {
    private static final Logger log = Logger.getLogger(String.valueOf(JsMethods.class));
    private final WebDriver driver;
    JavascriptExecutor js;

    public JsMethods(){
        this.driver= DriverExec.driver;
        js = (JavascriptExecutor) driver;
    }

    public void scrollPageBottom(){
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    public void scrollMiddleOfPage(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)", "");
    }
    public void scrollDown(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }
    public void waitPageLoaded(){
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver ->
                (js).executeScript("return document.readyState;").equals("complete"));
        log.info("Sayfa basarili bir sekilde yuklendi.");

    }
}