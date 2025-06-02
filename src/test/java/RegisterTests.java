import Utils.BaseTest;
import Utils.DataTests;
import Api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class RegisterTests extends BaseTest {
    private final DataTests user = new DataTests();
    private String token;
    private final String browserType;
    private final String password = "12345";


    public RegisterTests(String browserType) {
        this.browserType = browserType;
    }

    @Parameterized.Parameters //Запускаем тесты сначала в хроме потом в яндекс браузере
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"},
        });
    }

    @Before
    public void setUp() {
        initDriver(browserType); // инициализируем драйверы бразеров
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации через UI")
    public void testRegistrationSuccess() {
        registerPage.openRegisterUrl();
        registerPage.inputName(user.getName());
        registerPage.inputEmail(user.getEmail());
        registerPage.inputPassword(user.getPassword());
        registerPage.registerButtonClick();
        token = registerPage.loginViaApi(user);// Проверяем созданного пользователя через авторизацию
    }

    @Test
    @DisplayName("Тест проверки некорректного пароля")
    @Description("Проверка некорректного ввода пароля")
    public void testIncorrectPasswordError(){
        registerPage.openRegisterUrl();
        registerPage.inputName(user.getName());
        registerPage.inputEmail(user.getEmail());
        registerPage.inputPassword(password);
        registerPage.clickEmail();
        Assert.assertTrue(
                "Проверка ошибки если пароль менее 6 символов",
                registerPage.errorIncorrectPassword().equals("Некорректный пароль")
        );
    }
    @After
    public void tearDown() {
        driver.quit();//удалили пользователя которым залогинились
        if (token != null) {
            UserApiClient.deleteUser(token)//удалили созданного пользователя
                    .then()
                    .statusCode(202);
        }
    }
}
