package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebElementIntroTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Получаем последнюю версию драйвера браузера Chrome
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        Map<String, Object> contentSettings = new HashMap<String, Object>();

        profile.put("managed_default_content_settings",contentSettings);
        prefs.put("profile",profile);
        options.setExperimentalOption("prefs",prefs);
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        // Создаём новый объект класса ChromeDriver
        driver = new ChromeDriver(options);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClick() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement firstNameInput = driver.findElement(By.xpath("//*[@placeholder='First Enter name']"));
        WebElement svg = driver.findElement(By.xpath("//*[.='Can you enter name here through automation ']/*[local-name()='svg']"));
        svg.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(firstNameInput.isEnabled(), "Actual enabled state of element doesn't match expected!");
    }

    @Test
    public void testSendKeysClear() {
        driver.get("https://www.w3.org/WAI/UA/TS/html401/cp0101/0101-TEXTAREA.html");
        String inputText = "Some new text";
        WebElement textareaInput = driver.findElement(By.cssSelector("#textarea1"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textareaInput.clear();
        textareaInput.sendKeys(inputText);
        textareaInput.sendKeys(Keys.ENTER);
        textareaInput.sendKeys(Keys.ENTER);
        textareaInput.sendKeys("Some another text");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendKeysNotIntractable() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement firstNameInput = driver.findElement(By.xpath("//*[@placeholder='First Enter name']"));

        String inputText = "Some new text";

        if(firstNameInput.isEnabled()) {
            firstNameInput.sendKeys(inputText);
        } else {
            WebElement svg = driver.findElement(By.xpath("//*[.='Can you enter name here through automation ']/*[local-name()='svg']"));
            svg.click();
            firstNameInput.sendKeys(inputText);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelect() {
        driver.get("https://selectorshub.com/xpath-practice-page/");

        WebElement elementH2 = driver.findElement(By.xpath("//h2[.='System Distribution Details']"));
        new Actions(driver).scrollToElement(elementH2).perform();

        WebElement singleSelect = driver.findElement(By.xpath("//select[@name='tablepress-1_length']"));
        Select select = new Select(singleSelect);
        select.selectByIndex(1);

        List<WebElement> allOptions = select.getOptions();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("25", select.getFirstSelectedOption().getText());
    }

    @Test
    public void testSelectInvalid() {
        driver.get("https://demo.mobiscroll.com/javascript/select/single-select");

        WebElement singleSelect =  driver.findElement(By.cssSelector("#single-select-input"));
        Select select = new Select(singleSelect);
        select.selectByIndex(1);

        select.getOptions();

        select.isMultiple();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("25", select.getFirstSelectedOption().getText());
    }

    @Test
    public void testMultiSelect() {
        //HW
        driver.get("https://letcode.in/dropdowns");
        WebElement consent = driver.findElement(By.xpath("//*[@class='fc-button-label']"));
        consent.click();
        WebElement superheroes = driver.findElement(By.xpath("//select[@id='superheros']"));
        WebElement batman = driver.findElement(By.xpath("//option[@value='bt']"));
        WebElement daredevil = driver.findElement(By.xpath("//option[@value='dd']"));
        WebElement robin = driver.findElement(By.xpath("//option[@value='rb']"));

        Select select = new Select(superheroes);
        assertTrue(select.isMultiple());

        Select selectBatman = new Select(superheroes);
        selectBatman.selectByVisibleText("Batman");

        Select selectDaredevil = new Select(superheroes);
        selectDaredevil.selectByValue("dd");

        Select selectRobin = new Select(superheroes);
        selectRobin.selectByVisibleText("Robin");

        ArrayList<WebElement> selectedHeroes = new ArrayList<>(select.getAllSelectedOptions());

        //Напишите приличные локаторы
        //Выберите Batman, Daredevil и Robin используя разные вызовы методов селект
        //Сделайте так, чтобы тест прошёл =)

        assertTrue(batman.isSelected());
        assertTrue(daredevil.isSelected());
        assertTrue(robin.isSelected());
        assertTrue(selectedHeroes.contains(batman));
        assertTrue(selectedHeroes.contains(daredevil));
        assertTrue(selectedHeroes.contains(robin));
    }

    @Test
    public void testGetText() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement sectionText = driver.findElement(By.cssSelector(".elementor-widget-container > div > label"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Can you enter name here through automation", sectionText.getText(), "Actual header doesn't match expected");
    }

    @Test
    public void testGetAttributeValue() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement emailInput = driver.findElement(By.cssSelector("#userId"));

        String expectedString = "Some email we just provided";

        emailInput.sendKeys(expectedString);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(expectedString, emailInput.getAttribute("value"), String.format("Actual input text doesn't match expected! Actual: %s; Expected: %s", emailInput.getText(), expectedString));
    }

    @Test
    public void testGetCssValue() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement checkoutButton = driver.findElement(By.xpath("//button[.='Checkout here']"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("rgba(76, 175, 80, 1)", checkoutButton.getCssValue("background-color"));
    }

    @Test
    public void testGetTagName() {
        driver.get("https://selectorshub.com/xpath-practice-page/");
        WebElement submitButton = driver.findElement(By.xpath("//*[@value='Submit']"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("input", submitButton.getTagName());
    }

    @AfterEach
    public void tearDown() {
        // Закрываем браузер
        driver.quit();
    }
}
