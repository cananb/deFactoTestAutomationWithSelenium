package driver;

import com.thoughtworks.gauge.*;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import utils.Constants;

import java.time.Duration;
import java.util.logging.Logger;
import static utils.Constants.BASE_URL;

public class DriverExec {
    private static final Logger log = Logger.getLogger(String.valueOf(DriverExec.class));
    public static WebDriver driver;


    @BeforeScenario
    public void setUp() {
        String browser = "chrome";
        driver = LocalBrowserExec.LocalDriver(browser);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(299)).implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        PropertyConfigurator.configure(Constants.LOG4J_PROPERTIES_FILE_PATH);

    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) {
        log.info("=========================================================================================================");
        log.info("-----------------------------------------SCENARIO--------------------------------------------------------");
        String testCaseName = executionContext.getCurrentScenario().getName();
        String testCaseTagName = executionContext.getCurrentScenario().getTags().toString();
        log.info("SCENARIO NAME:" + testCaseName);
        log.info("SCENARIO TAG:" + testCaseTagName);
        System.out.println("\r\n");
    }

    @BeforeStep
    public void beforeStep(ExecutionContext executionContext) {
        log.info("=========================" + executionContext.getCurrentStep().getDynamicText() + "==============================");
    }

    @AfterStep
    public void afterStep(ExecutionContext executionContext) {
        if (executionContext.getCurrentStep().getIsFailing()) {
            log.finer(executionContext.getCurrentSpecification().getFileName());
            log.finer("Message:" + executionContext.getCurrentStep().getErrorMessage()
                    + executionContext.getCurrentStep().getStackTrace());

            log.finer("==================================================================================================");
            System.out.println("\r\n");
        }
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }
}