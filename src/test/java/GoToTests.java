import Utils.BaseTest;
import Utils.DataTests;
import Api.UserApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class GoToTests extends BaseTest {
    private final DataTests user = new DataTests();
    private String token;
    private final String browserType;
    private final String password = "12345";


    public GoToTests(String browserType) {
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
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    @Description("Проверка перехода по клику на «Личный кабинет»")
    public void testLKverify() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        openBaseUrl();
        mainPage.clickEnterLK();
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        lkPage.verifyLkURL();// Проверили что URL это URL личного кабинета
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Конструктор» и на логотип Stellar Burgers.")
    @Description("Проверить переход по клику на «Конструктор» и на логотип Stellar Burgers.")
    public void testConstructorLogoVerify() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        openBaseUrl();
        mainPage.clickEnterLK();
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        mainPage.clickConstructorButton();//кликнуть по кнопке Конструктор
        mainPage.verifyMainPageURL();//проверить что мы на главной странице
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        mainPage.clickLogoBurgers();//Кликнули на логотип Бургрной
        mainPage.verifyMainPageURL();// Проверили что мы на главной странице
    }

    @Test
    @DisplayName("Проверка выхода из личного кабинета")
    @Description("Проверить что вышли из личного кабинета")
    public void testExitLK() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        openBaseUrl();
        mainPage.clickEnterLK();
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        lkPage.clickExitButton();//нажали выйти
        loginPage.verifyExitLkURL();//здесь мы убеждаемся что находимся на странице с логином после выхода
    }


    @Test
    @DisplayName("Проверить , что работают переходы к разделам: Булки, Соусы, Начинки")
    @Description("Проверка , что работают переходы к разделам: Булки, Соусы, Начинки")
    public void verifyBunSouse() throws InterruptedException {
        openBaseUrl();
        mainPage.clickSaucesTab(); //Кликнуть на соусы
        mainPage.assertSaucesTabIsActive(); //проверить что соусы активны
        mainPage.clickFillingsTab(); //Кликнуть на начинки
        mainPage.assertFillingsTabIsActive(); //проверить что начинки активны
        mainPage.clickBunsTab();//Кликнуть на булки
        mainPage.assertBunsTabIsActive();//проверить что булки активны
    }


    @After
    public void tearDown() {
        driver.quit();//удалили пользователя которого создали через RestApi
        if (mainPage.getCurrentUserToken() != null) {
            UserApiClient.deleteUser(mainPage.getCurrentUserToken())//удалили созданного пользователя
                    .then()
                    .statusCode(202);
        }
    }
}