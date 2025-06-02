package PageObject;

import Api.UserApiClient;
import Utils.BaseTest;
import Utils.DataTests;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.CreateUser;

import java.time.Duration;


public class MainPage extends BaseTest {
    private WebDriver webDriver;
    private final By enterAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By enterLK = By.xpath(".//p[text()='Личный Кабинет']");
    private String currentUserToken;
    private CreateUser createdUser;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Кликнуть по кнопке войти в аккаунт")
    public MainPage clickEnterInAccount() {
        WebElement element = waitForElementToBeClickable(enterAccount);
        element.click();
        return this;
    }
    @Step("Кликнуть по кнопке личный кабинет")
    public MainPage clickEnterLK() {
        WebElement element = waitForElementToBeClickable(enterLK);
        element.click();
        return this;
    }
    @Step("Создание нового пользователя через API")
    public MainPage createUserViaApi() {
        DataTests data = new DataTests();
        createdUser = new CreateUser(data.getEmail(), data.getPassword(), data.getName());
        Response response = UserApiClient.createUser(createdUser);
        response.then().statusCode(200);
        Response loginResponse = UserApiClient.login(createdUser.getEmail(), createdUser.getPassword());
        loginResponse.then().statusCode(200);
        currentUserToken = UserApiClient.getAccessToken(loginResponse);
        return this;
    }

    public String getCurrentUserToken() {
        return currentUserToken;
    }

    public String getCreatedUserEmail() {
        return createdUser != null ? createdUser.getEmail() : null;
    }

    public String getCreatedUserPassword() {
        return createdUser != null ? createdUser.getPassword() : null;
    }


}
