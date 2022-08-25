package steps;

import com.thoughtworks.gauge.Step;
import methods.ActionMethods;
import methods.JsMethods;
import methods.Methods;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class StepImplementation {

    Methods methods;
    ActionMethods actionMethods;
    JsMethods jsMethods;
    private static final Logger log = Logger.getLogger(String.valueOf(StepImplementation.class));

    public StepImplementation() {
        methods = new Methods();
        actionMethods = new ActionMethods();
        jsMethods = new JsMethods();
    }

    @Step("<key> elementine tiklanir.")
    public void clickElement(String key) {
        methods.clickElement(methods.getBy(key));
    }

    @Step("Eger varsa <key> elementine tiklanir.")
    public void ifElementExistClick(String key) {
        if (methods.ifElementExist(methods.getBy(key))) {
            WebElement element = methods.findElement(methods.getBy(key));
            element.click();
        } else {
            log.info("Element gorunur olmadi!");
        }
    }

    @Step("Sayfa yuklenmesi kontrol edilir.")
    public void waitPageLoaded() {
        methods.waitPageLoaded();
    }

    @Step("<key> input alanina <value> degeri yazilir.")
    public void sendKeys(String key, String value) {
        methods.sendKeys(methods.getBy(key), value);
    }

    @Step("<seconds> saniye beklenir.")
    public void waitForSecond(int seconds) {
        methods.waitForSeconds(seconds);
    }

    @Step("Url'in <key> icerdigi kontrol edilir.")
    public void checkContainsCurrentUrl(String key) throws UnsupportedEncodingException {
        methods.isCurrentUrlContains(key);
    }

    @Step("<key> elementinin textinin <text> degerini icerdigi kontrol edilir.")
    public void isElementContains(String key, String text) {
        methods.isElementContains(methods.getBy(key), text);
    }

    @Step("<key> elementlerinden <text> degerini iceren elementi uzerine gelinir.")
    public void hoverByText(String key, String text) {
        actionMethods.hoverByText(methods.getBy(key), text);
    }

    @Step("<key> degerinin <number> degerine esit olup olmadigi kontrol edilir.")
    public void findElementStringControl(String key, Integer number) {
        String value = methods.findElement(methods.getBy(key)).getText();
        assertThat(Integer.parseInt(value), greaterThanOrEqualTo(number));
    }

    @Step("<key> elementini bulana kadar sayfayi kaydir")
    public void scrollUntilView(String element) {
        jsMethods.scrollDown(methods.findElement(methods.getBy(element)));
    }

    @Step("Sayfayi asagi kaydir")
    public void scrollDownBottom() {
        jsMethods.scrollPageBottom();
    }

    @Step("<key> elementinde bulunan <key2> elementlerinden <text> degerini tasiyan elemente tikla")
    public void scrollToTextWithClick(String key, String key2, String text) {
        methods.scrollToTextWithClick(methods.getBy(key), methods.getBy(key2), text);
    }

    @Step("<key> elementinin icerdigi rastgele bir elemente tiklanir.")
    public void randomProductSelect(String key) {
        methods.randomProductSelect(methods.getBy(key));
    }

    @Step("<key> elementinin icerdigi rastgele bir size elementine tiklanir.")
    public void selectProductDetailSize(String key) {
        methods.selectProductDetailSize(methods.getBy(key));

    }

    @Step(("Urun <productNameKey> ve fiyat bilgileri <priceInfo> map listesine eklenir."))
    public void saveProductInfoAtProductDetail(String productNameKey, String priceInfoKey) {
        methods.saveProductInfoAtProductDetail(productNameKey, priceInfoKey);
    }

    @Step(("<productNameAndModelInfoBasket> ve <priceInfoBasket> bilgileri map listesine eklenir ve listedeki diger degerler ile karsilastirilir."))
    public void saveProductInfoAtBasket(String productNameKey, String priceInfoKey) {
        methods.saveProductInfoAtBasket(productNameKey, priceInfoKey);
    }
}