package PageObject;

import Utils.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LkPage extends BaseTest {
    private WebDriver webDriver;
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By loginInput = By.xpath(".//label[text()='Логин']/following-sibling::input");
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final By profileLink = By.xpath(".//a[text()='Профиль']");
    private final By historySales = By.xpath(".//a[text()='История заказов']");

    public LkPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Получить текст поля логин")
    public String getLkLoginText() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));
        return element.getAttribute("value");
    }

}