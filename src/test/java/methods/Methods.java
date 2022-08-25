package methods;

import driver.DriverExec;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import model.ProductInfo;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

public class Methods {


    private static final Logger log = Logger.getLogger(String.valueOf(Methods.class));
    public final WebDriverWait wait;
    private final WebDriver driver;
    private final JsMethods jsMethods;

    public Methods() {
        this.driver = DriverExec.driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        jsMethods = new JsMethods();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public ElementInfo getElementInfo(String key) {
        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public By getBy(String key) {
        log.info("Element " + key + " degerinde tutuluyor");
        return ElementHelper.getElementInfoToBy(getElementInfo(key));
    }

    public WebElement findElement(By by) {
        log.info("Element " + by.toString() + " by degerine sahip");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void clickElement(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        log.info(by + " elementine tiklandi.");
    }

    public Boolean ifElementExist(By by) {
        wait.until(ExpectedConditions.visibilityOf(findElement(by)));
        wait.ignoring(NoSuchMethodException.class, StaleElementReferenceException.class);
        boolean exists = findElements(by).size() != 0;
        if (exists) {
            log.info(by + " elementi gorunur.");
            return true;
        } else {
            log.info(by + " elementi bulunamadi !");
            return false;
        }
    }

    public void sendKeys(By by, String key) {
        wait.until(ExpectedConditions.visibilityOf(findElement(by))).sendKeys(key);
    }

    public void waitForSeconds(int seconds) {
        try {
            log.info(seconds + "saniye bekleniyor...");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public String getText(By by) {
        return findElement(by).getText();
    }

    public void isElementContains(By by, String text) {
        WebElement element = findElement(by);
        Assert.assertTrue(element.getText().contains(text));
    }

    public String getCurrentUrl() {
        log.info("Current Url:" + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public String decoderUrl(String url) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
        log.info("Decoded Url:" + decodedUrl);
        return decodedUrl;
    }

    public void isCurrentUrlContains(String text) throws UnsupportedEncodingException {
        String decodedCurrentUrl = decoderUrl(getCurrentUrl());
        Assert.assertTrue("Url beklenen degeri icermiyor", decodedCurrentUrl.contains(text));
    }

    public void waitPageLoaded() {
        jsMethods.waitPageLoaded();
    }

    public void scrollToTextWithClick(By byCategoryFilter, By bySizeType, String sizeType) {
        waitForSeconds(3);
        WebElement element = findElement(byCategoryFilter);
        jsMethods.scrollDown(element);
        waitForSeconds(3);
        if (findElement(bySizeType).getText().equals(sizeType)) {
            wait.until(ExpectedConditions.elementToBeClickable(bySizeType)).click();
            log.info(findElement(bySizeType).getText() + "urunler basariyla yuklendi");
        } else {
            log.finer("Urunler yuklenemedi!");
        }

    }

    public void randomProductSelect(By productListContentItemKey) {
        List<WebElement> products = findElements(productListContentItemKey);
        waitForSeconds(3);
        Random rand = new Random();
        if (products.size() == 0) return;
        int randomProduct = rand.nextInt(products.size() - 1);
        System.out.println("liste " + randomProduct);
        WebElement webElement = products.get(randomProduct);
        jsMethods.scrollDown(webElement);
        waitForSeconds(5);
        wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    ProductInfo productInfoInSeller;

    public void saveProductInfoAtProductDetail(String productKey, String priceKey) {
        String productName = getText(getBy(productKey)).trim().toLowerCase(Locale.ROOT);
        String priceInfo = getText(getBy(priceKey)).trim().toLowerCase(Locale.ROOT);
        productInfoInSeller = new ProductInfo();
        productInfoInSeller.setProductName(productName.trim());
        productInfoInSeller.setPriceInfo(priceInfo.trim().split("\\n")[0]);
    }

    public void saveProductInfoAtBasket(String productKey, String priceKey) {
        String productName = getText(getBy(productKey)).trim().toLowerCase(Locale.ROOT);
        String priceInfo = getText(getBy(priceKey)).trim().toLowerCase(Locale.ROOT).split(" ")[0];

        Assert.assertEquals(productName.trim(), (productInfoInSeller.getProductName()));
        Assert.assertEquals(priceInfo.trim(), (productInfoInSeller.getPriceInfo()));

        if (productName.equals(productInfoInSeller.getProductName()) && priceInfo.equals(productInfoInSeller.getPriceInfo())) {
            log.info("Eklenen urun ile sepetteki urun bilgileri aynıdır!");
        } else {
            log.finer("Urun bilgileri esit degildir!");

        }
    }

    public void selectProductDetailSize(By key) {
        By basketButtonByKey = By.cssSelector(getElementInfo("basketAddBtn").getValue());
        WebElement addBasketButton = findElement(basketButtonByKey);
        WebElement standardSize = null;
        try {
            standardSize = driver.findElement(By.cssSelector("button[class='button-reset '][value='STD']"));
        } catch (Exception ignored) {
        }
        List<WebElement> productWearSize = driver.findElements(key);

        if (standardSize != null) {
            standardSize.click();
            waitForSeconds(3);
            addBasketButton.click();
        } else {
            for (WebElement element : productWearSize) {
                element.click();
                if (elementHasClass(addBasketButton, "active")) {
                    waitForSeconds(3);
                    clickElement(basketButtonByKey);
                    break;
                }
            }
        }
    }

    public boolean elementHasClass(WebElement element, String active) {
        return Arrays.asList(element.getAttribute("class").split(" ")).contains(active);
    }

}
