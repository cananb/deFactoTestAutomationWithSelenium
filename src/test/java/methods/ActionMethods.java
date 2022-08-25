package methods;

import driver.DriverExec;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class ActionMethods {

    private static final Logger log = Logger.getLogger(String.valueOf(ActionMethods.class));
    public final WebDriverWait wait;
    private final WebDriver driver;
    private final Methods methods;

    public ActionMethods() {
        this.driver = DriverExec.driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        methods = new Methods();
    }

    public void hoverElement(WebElement webElement){
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        action.moveToElement(webElement).perform();
    }
    public void hoverByText(By by, String text) {
        List<WebElement> allRows =methods.findElements(by);
        for (WebElement element : allRows) {
            if (element.getText().contains(text)) {
                hoverElement(element);
            }
        }
    }
}