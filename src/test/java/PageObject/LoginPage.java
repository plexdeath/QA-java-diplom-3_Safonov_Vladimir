package PageObject;

import Utils.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BaseTest {
    private WebDriver webDriver;
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By enterButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Ввести email")
    public LoginPage inputEmail(String email) {
        webDriver.findElement(emailInput).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage inputPassword(String password) {
        webDriver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    @Step("Нажать на кнопку войти")
    public LoginPage enterButtonClick() {
        webDriver.findElement(enterButton).click();
        return this;
    }
}