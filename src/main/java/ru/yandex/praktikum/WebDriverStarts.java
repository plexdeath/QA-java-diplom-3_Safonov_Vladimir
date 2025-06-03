package ru.yandex.praktikum;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverStarts {
    public static WebDriver createDriver(String browserType) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if ("yandex".equalsIgnoreCase(browserType)) {
            System.setProperty("webdriver.chrome.driver", ".\\drivers\\yandexdriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
        }
        return new ChromeDriver(options);
    }
}
