import PageObject.LkPage;
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
        lkPage.openAutorizeLkUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        lkPage.verifyLkURL();// Проверили что URL это URL личного кабинета
    }

    @Test
    @DisplayName("Переход по кнопке «Конструктор» из ЛК")
    @Description("Проверить, что при клике на «Конструктор» из ЛК происходит переход на главную страницу")
    public void testConstructorButtonRedirectsToMainPage() {
        mainPage.createUserViaApi(); // Авторизация
        lkPage.openAutorizeLkUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//ввод Email
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввод Пароля
        loginPage.enterButtonClick();//Нажать войти
        mainPage.clickEnterLK();//Перейти в ЛК
        mainPage.clickConstructorButton();//Кликнуть на конструктор
        mainPage.verifyMainPageUrlConstructor();//Проверить что мы на главной странице после клика
    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers из ЛК")
    @Description("Проверить, что при клике на логотип из ЛК происходит переход на главную страницу")
    public void testLogoClickRedirectsToMainPage() {
        mainPage.createUserViaApi();// Авторизация
        lkPage.openAutorizeLkUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//ввод Email
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввод Пароля
        loginPage.enterButtonClick();//Нажать войти
        mainPage.clickEnterLK();//Перейти в ЛК
        mainPage.clickLogoBurgers();//Кликнуть на конструктор
        mainPage.verifyMainPageUrlBurger();//Проверить что мы на главной странице после клика
    }

    @Test
    @DisplayName("Проверка выхода из личного кабинета")
    @Description("Проверить что вышли из личного кабинета")
    public void testExitLK() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        lkPage.openAutorizeLkUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//Ввели почту
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввели пароль
        loginPage.enterButtonClick();// Нажали на кнопку войти
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        lkPage.clickExitButton();//нажали выйти
        loginPage.verifyExitLkURL();//здесь мы убеждаемся что находимся на странице с логином после выхода
    }


    @Test
    @DisplayName("Проверка перехода к разделу Соусы")
    @Description("Проверка, что при клике на вкладку 'Соусы' она становится активной")
    public void testSaucesTabIsActive() {
        openBaseUrl();
        mainPage.clickSaucesTab();
        mainPage.assertSaucesTabIsActive();
    }

    @Test
    @DisplayName("Проверка перехода к разделу Начинки")
    @Description("Проверка, что при клике на вкладку 'Начинки' она становится активной")
    public void testFillingsTabIsActive() {
        openBaseUrl();
        mainPage.clickFillingsTab();
        mainPage.assertFillingsTabIsActive();
    }

    @Test
    @DisplayName("Проверка перехода к разделу Булки")
    @Description("Проверка, что при клике на вкладку 'Булки' она становится активной")
    public void testBunsTabIsActive() throws InterruptedException {
        openBaseUrl();
        mainPage.clickFillingsTab();//Так как булки активен и на него нельзя сделать клик сначала кликаем на начинки а потом на булки
        mainPage.clickBunsTab();//кликаем на булки
        mainPage.assertBunsTabIsActive();//Проверяем, что при клике на вкладку 'Булки' она становится активной"
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