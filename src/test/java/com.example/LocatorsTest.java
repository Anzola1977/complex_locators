package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocatorsTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Получаем последнюю версию драйвера браузера Chrome
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        Map<String, Object> contentSettings = new HashMap<String, Object>();

        //contentSettings.put("cookies",2);
        profile.put("managed_default_content_settings",contentSettings);
        prefs.put("profile",profile);
        options.setExperimentalOption("prefs",prefs);

        // Создаём новый объект класса ChromeDriver
        driver = new ChromeDriver(options);

        String baseUrl = "https://selectorshub.com/xpath-practice-page/";
        // Открываем главную страницу демо-сайта
        driver.get(baseUrl);
        // Открываем браузер на полный экран
        driver.manage().window().maximize();

        // Ждём пока страница появится - этот способ подходит только для демонстрации
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testComplexLocators() {
        //Выберите дату начала наших курсов в календаре, чтобы проверка прошла
        WebElement date = driver.findElement(By.xpath("//input[@name='the_date']"));
        date.sendKeys("06.06.2023");
        //Проверка
        assertEquals("2023-06-06", driver.findElement(By.xpath("//input[@name='the_date']")).getAttribute("value"));

        //После этого переходим к таблице
        WebElement elementH2 = driver.findElement(By.xpath("(//div[@class=\"elementor-widget-container\"]/h2)[2]"));
        new Actions(driver).scrollToElement(elementH2).perform();

        //Нужно переключить количество строк в таблице на любое другое значение кроме 10
        WebElement scrollElement = driver.findElement(By.xpath("//select[@name=\"tablepress-1_length\"]"));
        Select select = new Select(scrollElement);
        select.selectByIndex(2);
        //scrollElement.sendKeys("25");
//        scrollElement.click();
//        scrollElement.findElement(By.xpath("//*[@id=\"tablepress-1_length\"]/label/select/option[2]")).click();

        //И нажать чекбокс на строке с данными "windows	Edge Samsun	India" чтобы проверка прошла
        WebElement noteElement = driver.findElement(By.xpath("//tr[contains(@class,\"odd\")]//*[text()='Edge']/..//input"));
        noteElement.click();
        //Проверка
        assertTrue(driver.findElement(By.xpath("//tr[contains(@class,\"odd\")]//*[text()='Edge']/..//input")).isSelected());
    }

    @AfterEach
    public void tearDown() {
        // Закрываем браузер
        driver.quit();
    }
}
